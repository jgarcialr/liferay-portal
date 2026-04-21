/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.security.fips.FIPSModeUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Jorge Garc&iacute;a Jim&eacute;nez
 */
public class FIPSLDAPImportConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAllowsImportPasswordWhenFIPSOffRegardlessOfAlgorithm()
		throws Exception {

		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(false);

			_listener.onBeforeSave(_PID, _properties(true));
		}
	}

	@Test
	public void testAllowsImportPasswordWhenPortalAlgorithmApproved()
		throws Exception {

		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS);
			MockedStatic<PropsUtil> propsUtilMocked = Mockito.mockStatic(
				PropsUtil.class)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			propsUtilMocked.when(
				() -> PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)
			).thenReturn(
				"PBKDF2WithHmacSHA1/160/1300000"
			);

			_listener.onBeforeSave(_PID, _properties(true));
		}
	}

	@Test
	public void testIgnoresWhenImportPasswordDisabled() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS);
			MockedStatic<PropsUtil> propsUtilMocked = Mockito.mockStatic(
				PropsUtil.class)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			propsUtilMocked.when(
				() -> PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)
			).thenReturn(
				"MD5"
			);

			_listener.onBeforeSave(_PID, _properties(false));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsImportPasswordWhenPortalAlgorithmNotApproved()
		throws Exception {

		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS);
			MockedStatic<PropsUtil> propsUtilMocked = Mockito.mockStatic(
				PropsUtil.class)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			propsUtilMocked.when(
				() -> PropsUtil.get(PropsKeys.PASSWORDS_ENCRYPTION_ALGORITHM)
			).thenReturn(
				"MD5"
			);

			_listener.onBeforeSave(_PID, _properties(true));
		}
	}

	private Dictionary<String, Object> _properties(boolean importPasswordEnabled) {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("importUserPasswordEnabled", importPasswordEnabled);

		return properties;
	}

	private static final String _PID =
		"com.liferay.portal.security.ldap.exportimport.configuration.LDAPImportConfiguration";

	private final FIPSLDAPImportConfigurationModelListener _listener =
		new FIPSLDAPImportConfigurationModelListener();

}
