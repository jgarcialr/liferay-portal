/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import java.util.List;

/**
 * @author Jorge García Jiménez
 */
public class FIPSProviderStatus {

	public FIPSProviderStatus(
		boolean approvedMode, String failedCheck, String jsseProviderName,
		String providerName, List<String> providerOrder,
		String providerVersion) {

		_approvedMode = approvedMode;
		_failedCheck = failedCheck;
		_jsseProviderName = jsseProviderName;
		_providerName = providerName;
		_providerOrder = providerOrder;
		_providerVersion = providerVersion;
	}

	public String getFailedCheck() {
		return _failedCheck;
	}

	public String getJSSEProviderName() {
		return _jsseProviderName;
	}

	public String getProviderName() {
		return _providerName;
	}

	public List<String> getProviderOrder() {
		return _providerOrder;
	}

	public String getProviderVersion() {
		return _providerVersion;
	}

	public boolean isApprovedMode() {
		return _approvedMode;
	}

	public boolean isValid() {
		return _failedCheck == null;
	}

	private final boolean _approvedMode;
	private final String _failedCheck;
	private final String _jsseProviderName;
	private final String _providerName;
	private final List<String> _providerOrder;
	private final String _providerVersion;

}