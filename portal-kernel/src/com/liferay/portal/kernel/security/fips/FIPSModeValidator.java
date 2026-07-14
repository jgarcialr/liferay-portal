/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.security.pwd.PasswordEncryptor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.lang.reflect.Method;

import java.security.Provider;
import java.security.Security;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

/**
 * @author Caio Farias
 * @author Jorge García Jiménez
 */
public class FIPSModeValidator {

	public static FIPSProviderStatus getStatus() {
		Provider[] providers = Security.getProviders();

		List<String> providerOrder = TransformUtil.transformToList(
			providers, Provider::getName);

		String providerName = null;
		String providerVersion = null;

		if (ArrayUtil.isNotEmpty(providers)) {
			Provider provider = providers[0];

			providerName = provider.getName();
			providerVersion = provider.getVersionStr();
		}

		String failedCheck = _validateFIPSProvider(providers);

		boolean approvedMode = false;

		if (failedCheck == null) {
			approvedMode = true;
		}

		if (approvedMode) {
			failedCheck = _validateProviders(providers);
		}

		return new FIPSProviderStatus(
			approvedMode, failedCheck, _getJSSEProviderName(), providerName,
			providerOrder, providerVersion);
	}

	public static void validate() {
		FIPSProviderStatus fipsProviderStatus = getStatus();

		if (!fipsProviderStatus.isValid()) {
			throw new SecurityException(fipsProviderStatus.getFailedCheck());
		}

		_validatePasswordsEncryptionAlgorithm(
			PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM));
	}

	private static String _getJSSEProviderName() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			Provider provider = sslContext.getProvider();

			return provider.getName();
		}
		catch (Exception exception) {
			return null;
		}
	}

	private static String _validateFIPSProvider(Provider[] providers) {
		if (ArrayUtil.isEmpty(providers)) {
			return "There are no security providers";
		}

		Provider provider = providers[0];

		String name = provider.getName();

		if (!_allowedProviderNames.containsKey(name)) {
			return "The first security provider must be an allowed FIPS " +
				"provider";
		}

		try {
			if (Objects.equals(name, "AmazonCorrettoCryptoProvider")) {
				Class<?> providerClass = provider.getClass();

				Method assertHealthyMethod = ReflectionUtil.getDeclaredMethod(
					providerClass, "assertHealthy");

				assertHealthyMethod.invoke(provider);

				Method isExperimentalFIPSMethod =
					ReflectionUtil.getDeclaredMethod(
						providerClass, "isExperimentalFips");
				Method isFIPSMethod = ReflectionUtil.getDeclaredMethod(
					providerClass, "isFips");

				if (GetterUtil.getBoolean(
						isExperimentalFIPSMethod.invoke(provider)) ||
					!GetterUtil.getBoolean(isFIPSMethod.invoke(provider))) {

					return "AmazonCorrettoCryptoProvider must be a " +
						"nonexperimental FIPS build";
				}
			}
			else if (Objects.equals(name, "BCFIPS")) {
				Class<?> providerClass = provider.getClass();

				ClassLoader classLoader = providerClass.getClassLoader();

				Method isInApprovedOnlyModeMethod =
					ReflectionUtil.getDeclaredMethod(
						Class.forName(
							"org.bouncycastle.crypto.CryptoServicesRegistrar",
							true, classLoader),
						"isInApprovedOnlyMode");

				if (!GetterUtil.getBoolean(
						isInApprovedOnlyModeMethod.invoke(null))) {

					return "BCFIPS is not in approved only mode";
				}

				Class<?> fipsStatusClass = Class.forName(
					"org.bouncycastle.crypto.fips.FipsStatus", true,
					classLoader);

				Method isReadyMethod = ReflectionUtil.getDeclaredMethod(
					fipsStatusClass, "isReady");

				if (!GetterUtil.getBoolean(isReadyMethod.invoke(null))) {
					Method getStatusMessageMethod =
						ReflectionUtil.getDeclaredMethod(
							fipsStatusClass, "getStatusMessage");

					return "BCFIPS integrity self test failed: " +
						getStatusMessageMethod.invoke(null);
				}
			}
		}
		catch (Throwable throwable) {
			Throwable causeThrowable = throwable.getCause();

			if (causeThrowable == null) {
				causeThrowable = throwable;
			}

			String message = causeThrowable.getMessage();

			if (message == null) {
				message = causeThrowable.toString();
			}

			return "FIPS provider integrity failed: " + message;
		}

		return null;
	}

	private static void _validatePasswordsEncryptionAlgorithm(
		String algorithm) {

		String upperCaseAlgorithm = StringUtil.toUpperCase(
			GetterUtil.getString(algorithm));

		if (!upperCaseAlgorithm.startsWith(PasswordEncryptor.TYPE_PBKDF2) ||
			!upperCaseAlgorithm.contains("SHA256")) {

			throw new SecurityException(
				StringBundler.concat(
					"Algorithm \"", algorithm,
					"\" is not allowed in FIPS mode"));
		}

		int keySize = _PASSWORDS_ENCRYPTION_ALGORITHM_KEY_SIZE_MIN;
		int rounds = _PASSWORDS_ENCRYPTION_ALGORITHM_ROUNDS_MIN;

		Matcher matcher = _pbkdf2Pattern.matcher(upperCaseAlgorithm);

		if (matcher.matches()) {
			keySize = GetterUtil.getInteger(
				matcher.group(1), _PASSWORDS_ENCRYPTION_ALGORITHM_KEY_SIZE_MIN);
			rounds = GetterUtil.getInteger(
				matcher.group(2), _PASSWORDS_ENCRYPTION_ALGORITHM_ROUNDS_MIN);
		}

		if (keySize < _PASSWORDS_ENCRYPTION_ALGORITHM_KEY_SIZE_MIN) {
			throw new SecurityException(
				StringBundler.concat(
					"PBKDF2 output length ", keySize,
					" bits is below the minimum allowed value of ",
					_PASSWORDS_ENCRYPTION_ALGORITHM_KEY_SIZE_MIN, " bits"));
		}

		if (rounds < _PASSWORDS_ENCRYPTION_ALGORITHM_ROUNDS_MIN) {
			throw new SecurityException(
				StringBundler.concat(
					"PBKDF2 iteration count ", rounds,
					" is below the minimum allowed value of ",
					_PASSWORDS_ENCRYPTION_ALGORITHM_ROUNDS_MIN));
		}
	}

	private static String _validateProviders(Provider[] providers) {
		Provider provider = providers[0];

		List<String> allowedProviderNames = _allowedProviderNames.get(
			provider.getName());

		Provider[] notAllowedProviders = ArrayUtil.filter(
			providers,
			curProvider ->
				!curProvider.equals(provider) &&
				!allowedProviderNames.contains(curProvider.getName()));

		if (ArrayUtil.isEmpty(notAllowedProviders)) {
			return null;
		}

		return StringBundler.concat(
			"The security providers ", Arrays.toString(notAllowedProviders),
			" are not allowed in FIPS mode for ", provider.getName());
	}

	private static final int _PASSWORDS_ENCRYPTION_ALGORITHM_KEY_SIZE_MIN = 112;

	private static final int _PASSWORDS_ENCRYPTION_ALGORITHM_ROUNDS_MIN =
		1300000;

	private static final Map<String, List<String>> _allowedProviderNames =
		Map.of(
			"AmazonCorrettoCryptoProvider",
			List.of(
				"JdkLDAP", "JdkSASL", "SUN", "SunEC", "SunJCE", "SunJGSS",
				"SunJSSE", "SunRsaSign", "SunSASL", "XMLDSig"),
			"BCFIPS",
			List.of(
				"BCJSSE", "JdkLDAP", "JdkSASL", "SUN", "SunJCE", "SunJGSS",
				"SunSASL", "XMLDSig"));
	private static final Pattern _pbkdf2Pattern = Pattern.compile(
		"^[^/]*(?:/([0-9]+))?/([0-9]+)$");

}