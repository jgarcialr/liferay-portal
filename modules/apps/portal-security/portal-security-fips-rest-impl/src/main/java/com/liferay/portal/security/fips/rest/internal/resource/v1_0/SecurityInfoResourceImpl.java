/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.fips.FIPSModeValidator;
import com.liferay.portal.kernel.security.fips.FIPSProviderStatus;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.security.fips.rest.dto.v1_0.AlgorithmFamily;
import com.liferay.portal.security.fips.rest.dto.v1_0.FIPSMode;
import com.liferay.portal.security.fips.rest.dto.v1_0.SecurityInfo;
import com.liferay.portal.security.fips.rest.dto.v1_0.SecurityProvider;
import com.liferay.portal.security.fips.rest.resource.v1_0.SecurityInfoResource;

import java.security.Provider;
import java.security.Security;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jorge García Jiménez
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/security-info.properties",
	scope = ServiceScope.PROTOTYPE, service = SecurityInfoResource.class
)
public class SecurityInfoResourceImpl extends BaseSecurityInfoResourceImpl {

	@Override
	public SecurityInfo getSecurityInfo() throws Exception {
		_checkPermission();

		FIPSProviderStatus fipsProviderStatus = FIPSModeValidator.getStatus();

		SecurityInfo securityInfo = new SecurityInfo();

		securityInfo.setAlgorithmFamilies(
			() -> _getAlgorithmFamilies(fipsProviderStatus.getProviderName()));
		securityInfo.setFipsMode(() -> _toFIPSMode(fipsProviderStatus));
		securityInfo.setJsseProvider(
			() -> _toSecurityProvider(
				fipsProviderStatus.getJSSEProviderName(), null));
		securityInfo.setProvider(
			() -> _toSecurityProvider(
				fipsProviderStatus.getProviderName(),
				fipsProviderStatus.getProviderVersion()));
		securityInfo.setProviderOrder(
			() -> {
				List<String> providerOrder =
					fipsProviderStatus.getProviderOrder();

				return providerOrder.toArray(new String[0]);
			});

		return securityInfo;
	}

	private void _checkPermission() throws Exception {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(permissionChecker);
		}
	}

	private AlgorithmFamily[] _getAlgorithmFamilies(
		String declaredProviderName) {

		return new AlgorithmFamily[] {
			_toAlgorithmFamily(declaredProviderName, "Cipher"),
			_toAlgorithmFamily(declaredProviderName, "KeyAgreement"),
			_toAlgorithmFamily(declaredProviderName, "KeyStore"),
			_toAlgorithmFamily(declaredProviderName, "Mac"),
			_toAlgorithmFamily(declaredProviderName, "MessageDigest"),
			_toAlgorithmFamily(declaredProviderName, "SecureRandom"),
			_toAlgorithmFamily(declaredProviderName, "Signature")
		};
	}

	private Provider _getServiceProvider(String serviceType) {
		for (Provider provider : Security.getProviders()) {
			for (Provider.Service service : provider.getServices()) {
				if (serviceType.equals(service.getType())) {
					return provider;
				}
			}
		}

		return null;
	}

	private AlgorithmFamily _toAlgorithmFamily(
		String declaredProviderName, String serviceType) {

		Provider provider = _getServiceProvider(serviceType);

		String resolvedProviderName = null;

		if (provider != null) {
			resolvedProviderName = provider.getName();
		}

		boolean matches =
			(declaredProviderName != null) &&
			declaredProviderName.equals(resolvedProviderName);

		String curResolvedProviderName = resolvedProviderName;

		AlgorithmFamily algorithmFamily = new AlgorithmFamily();

		algorithmFamily.setDeclaredProvider(() -> declaredProviderName);
		algorithmFamily.setFamily(() -> serviceType);
		algorithmFamily.setMatches(() -> matches);
		algorithmFamily.setResolvedProvider(() -> curResolvedProviderName);

		return algorithmFamily;
	}

	private FIPSMode _toFIPSMode(FIPSProviderStatus fipsProviderStatus) {
		FIPSMode fipsMode = new FIPSMode();

		fipsMode.setApprovedMode(fipsProviderStatus::isApprovedMode);
		fipsMode.setEnabled(() -> PropsValues.FIPS_ENABLED);

		return fipsMode;
	}

	private SecurityProvider _toSecurityProvider(String name, String version) {
		SecurityProvider securityProvider = new SecurityProvider();

		securityProvider.setName(() -> name);
		securityProvider.setVersion(() -> version);

		return securityProvider;
	}

}