/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.security.fips.FIPSModeUtil;
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
public class FIPSLDAPAuthConfigurationModelListenerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testAcceptsAllAlgorithmsWhenFIPSOff() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = _mockFIPS(false)) {
			_listener.onBeforeSave(_PID, _properties("MD5"));
			_listener.onBeforeSave(_PID, _properties("BCRYPT"));
			_listener.onBeforeSave(_PID, _properties("SSHA"));
			_listener.onBeforeSave(_PID, _properties("SHA-256"));
		}
	}

	@Test
	public void testAcceptsApprovedAlgorithmsWhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("SHA-256"));
			_listener.onBeforeSave(_PID, _properties("SHA-384"));
			_listener.onBeforeSave(_PID, _properties("PBKDF2"));
		}
	}

	@Test
	public void testEmptyAlgorithmIsNoOpWhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties(""));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsBcryptWhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("BCRYPT"));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsMD5WhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("MD5"));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsNoneWhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("NONE"));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsSha1WhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("SHA"));
		}
	}

	@Test(expected = ConfigurationModelListenerException.class)
	public void testRejectsSshaWhenFIPSOn() throws Exception {
		try (MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
				FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS)) {

			mocked.when(FIPSModeUtil::isEnabled).thenReturn(true);

			_listener.onBeforeSave(_PID, _properties("SSHA"));
		}
	}

	private MockedStatic<FIPSModeUtil> _mockFIPS(boolean enabled) {
		MockedStatic<FIPSModeUtil> mocked = Mockito.mockStatic(
			FIPSModeUtil.class, Mockito.CALLS_REAL_METHODS);

		mocked.when(FIPSModeUtil::isEnabled).thenReturn(enabled);

		return mocked;
	}

	private Dictionary<String, Object> _properties(String algorithm) {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("passwordEncryptionAlgorithm", algorithm);

		return properties;
	}

	private static final String _PID =
		"com.liferay.portal.security.ldap.authenticator.configuration.LDAPAuthConfiguration";

	private final FIPSLDAPAuthConfigurationModelListener _listener =
		new FIPSLDAPAuthConfigurationModelListener();

}
