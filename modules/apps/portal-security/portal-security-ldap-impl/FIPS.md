# FIPS 140-3 Deployment Guide for LDAP

This document covers the runtime configuration required to run Liferay's LDAP
integration against a FIPS 140-3 validated JVM. The portal enforces policy
(LDAPS-only URLs, approved cipher suites, approved password hashes,
encrypted bind credentials) but does **not** register security providers —
that responsibility stays with the operator so the validated FIPS module is
loaded through the JVM's normal channels.

## Prerequisites

- JDK 17 or later, patched against the module's target (e.g. Bouncy Castle
  FIPS 2.x supports JDK 11/17/21).
- A FIPS-validated JCE module and its JSSE counterpart on the classpath.
  Example (Bouncy Castle):
  - `bc-fips-<version>.jar`
  - `bctls-fips-<version>.jar` (BCJSSE provider)

## 1. Configure `java.security`

Register the FIPS provider first so `SSLContext.getInstance("TLS")` resolves
through the validated module. The cleanest way is to supply an override file
and point the JVM at it:

```
# /opt/liferay/fips.java.security
security.provider.1=org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider
security.provider.2=org.bouncycastle.jsse.provider.BouncyCastleJsseProvider fips:BCFIPS
security.provider.3=sun.security.provider.Sun
# ... keep the rest of the default ordering
```

Start the JVM with:

```
-Djava.security.properties=/opt/liferay/fips.java.security
```

Use `==` (double equals) instead of `=` to replace the default file entirely
when the deployment cannot merge providers.

## 2. Create a BCFKS truststore

`JKS` and `PKCS12` are not FIPS keystore types. Use `BCFKS` instead:

```
keytool \
  -importcert \
  -alias corp-ad-ca \
  -file corp-ad-ca.pem \
  -keystore /opt/liferay/truststore.bcfks \
  -storetype BCFKS \
  -storepass "$TRUSTSTORE_PASSWORD" \
  -providerclass org.bouncycastle.jcajce.provider.BouncyCastleFipsProvider \
  -providerpath /opt/bc/bc-fips-2.0.0.jar
```

Repeat for every LDAP server CA (or the root CA of your internal PKI).

## 3. JVM flags

Add to `setenv.sh` / Tomcat's `CATALINA_OPTS`:

```
-Djavax.net.ssl.trustStore=/opt/liferay/truststore.bcfks
-Djavax.net.ssl.trustStorePassword=${TRUSTSTORE_PASSWORD}
-Djavax.net.ssl.trustStoreType=BCFKS
```

Keep the password out of shell history — prefer a wrapper that reads it from a
secrets manager and injects it via `-D`.

## 4. Portal properties

In `portal-ext.properties`:

```
security.fips.mode.enabled=true
```

Recommended companion setting (ensures portal password storage is
FIPS-approved, which in turn lets "Enable User Password on Import" work):

```
passwords.encryption.algorithm=PBKDF2WithHmacSHA256/160/1300000
```

Restart the portal after changing either of these — both are read once at
startup.

## 5. LDAP configuration

In *Control Panel → Instance Settings → Authentication → LDAP*:

- Set the **Base Provider URL** to `ldaps://<host>:<port>` (the save will be
  rejected otherwise while FIPS mode is on).
- Set **Password Encryption Algorithm** to `SHA-256`, `SHA-384`, or `PBKDF2`.
  Use `NONE` only when the authentication method is `bind` — the LDAP server
  then performs all hashing.
- Leave "**Enable User Password on Import**" enabled only when the portal's
  `passwords.encryption.algorithm` is on the approved list.

Optionally override the cipher-suite allowlist per company through the
`SystemLDAPConfiguration` factory (property name: `fipsCipherSuites`). Leaving
it empty uses the built-in NIST SP 800-52 Rev. 2 AEAD allowlist.

## 6. Validation checklist

- [ ] `catalina.out` shows no `NoSuchAlgorithmException`, `InvalidKeyException`,
      or `FIPS mode: ...` errors on startup.
- [ ] *Test LDAP Connection* succeeds against an `ldaps://` URL and fails with
      the *"FIPS mode requires the ldaps scheme"* message against `ldap://`.
- [ ] User login via LDAPS binds successfully.
- [ ] User and group synchronisation complete without TLS handshake errors.
- [ ] OSGi configuration file for any LDAP server factory shows
      `securityCredential="{ENC}..."` — never plaintext.
- [ ] The *Password Encryption Algorithm* dropdown in the UI lists only
      `NONE`, `SHA-256`, `SHA-384`, `PBKDF2`.
- [ ] The *Enable User Password on Import* checkbox is disabled whenever
      `passwords.encryption.algorithm` is not FIPS-approved.

## Known limitations

- Portal subsystems other than LDAP are out of scope for this release. SAML,
  SMTP, outbound HTTP clients, and cluster link continue to use default JVM
  providers; audit them separately against your FIPS obligations.
- `security.fips.mode.enabled` is JVM-wide; per-company toggling is not
  supported. Multi-tenant deployments must move all tenants to FIPS together
  or run them in separate JVMs.
- The portal does not register the Bouncy Castle FIPS provider automatically.
  Misconfigured JVMs will fail the LDAPS handshake — check `java.security`
  before opening a support case.