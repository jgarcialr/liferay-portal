/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.audit;

import com.liferay.portal.kernel.security.fips.FIPSApplicationState;
import com.liferay.portal.kernel.security.fips.FIPSApplicationStateMachineUtil;
import com.liferay.portal.kernel.security.fips.FIPSAuditEvent;
import com.liferay.portal.kernel.security.fips.FIPSAuditEventEmitterUtil;
import com.liferay.portal.kernel.util.PropsValues;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.cxf.jaxrs.utils.JAXRSUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

/**
 * @author Jorge García Jiménez
 */
public class FIPSFederationTokenAuditUtil {

	public static void emitFederationTokenRejected(
		String issuer, String offendingValue) {

		if (!PropsValues.FIPS_ENABLED) {
			return;
		}

		FIPSApplicationState fipsApplicationState =
			FIPSApplicationStateMachineUtil.getFIPSApplicationState();

		FIPSAuditEvent fipsAuditEvent = new FIPSAuditEvent(
			"federation-token-rejected", FIPSAuditEvent.Severity.WARNING);

		fipsAuditEvent.put("fips-state", fipsApplicationState.getValue());
		fipsAuditEvent.put("offending-value", offendingValue);
		fipsAuditEvent.put("receiving-endpoint", _fetchReceivingEndpoint());
		fipsAuditEvent.put("token-issuer", issuer);
		fipsAuditEvent.put("token-type", "JWT");

		FIPSAuditEventEmitterUtil.emit(fipsAuditEvent);
	}

	private static String _fetchReceivingEndpoint() {
		Message message = JAXRSUtils.getCurrentMessage();

		if (message == null) {
			return null;
		}

		HttpServletRequest httpServletRequest = (HttpServletRequest)message.get(
			AbstractHTTPDestination.HTTP_REQUEST);

		if (httpServletRequest == null) {
			return null;
		}

		return httpServletRequest.getRequestURI();
	}

}