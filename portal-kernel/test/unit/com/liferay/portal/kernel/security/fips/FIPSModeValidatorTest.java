/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.security.Provider;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

/**
 * @author Caio Farias
 * @author Jorge García Jiménez
 */
public class FIPSModeValidatorTest {

	@Test
	public void testGetStatus() {
		FIPSProviderStatus fipsProviderStatus = FIPSModeValidator.getStatus();

		Assert.assertNotNull(fipsProviderStatus.getProviderName());
		Assert.assertFalse(
			fipsProviderStatus.getProviderOrder(
			).isEmpty());
	}

	@Test
	public void testValidateFIPSProvider() {
		for (String name : List.of("AmazonCorrettoCryptoProvider", "BCFIPS")) {
			_assertFailedCheck(
				"FIPS provider integrity failed:", "_validateFIPSProvider",
				new Provider[] {_createProvider(name)});
		}

		_assertFailedCheck(
			"The first security provider must be an allowed FIPS provider",
			"_validateFIPSProvider",
			new Provider[] {_createProvider(RandomTestUtil.randomString())});
		_assertFailedCheck(
			"There are no security providers", "_validateFIPSProvider",
			new Provider[0]);
	}

	@Test
	public void testValidatePasswordsEncryptionAlgorithm() {
		ReflectionTestUtil.invoke(
			FIPSModeValidator.class, "_validatePasswordsEncryptionAlgorithm",
			new Class<?>[] {String.class}, "PBKDF2WithHmacSHA256/256/1300000");

		_assertSecurityException(
			"is not allowed in FIPS mode",
			() -> ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validatePasswordsEncryptionAlgorithm",
				new Class<?>[] {String.class}, "bcrypt/10"));
		_assertSecurityException(
			"is not allowed in FIPS mode",
			() -> ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validatePasswordsEncryptionAlgorithm",
				new Class<?>[] {String.class},
				"PBKDF2WithHmacSHA1/160/1300000"));
		_assertSecurityException(
			"iteration count",
			() -> ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validatePasswordsEncryptionAlgorithm",
				new Class<?>[] {String.class},
				"PBKDF2WithHmacSHA256/256/600000"));
		_assertSecurityException(
			"output length",
			() -> ReflectionTestUtil.invoke(
				FIPSModeValidator.class,
				"_validatePasswordsEncryptionAlgorithm",
				new Class<?>[] {String.class},
				"PBKDF2WithHmacSHA256/64/1300000"));
	}

	@Test
	public void testValidateProviders() {
		Map<String, List<String>> allowedProviderNames =
			ReflectionTestUtil.getFieldValue(
				FIPSModeValidator.class, "_allowedProviderNames");

		for (String allowedProviderName : allowedProviderNames.keySet()) {
			_assertFailedCheck(
				"are not allowed in FIPS mode for", "_validateProviders",
				new Provider[] {
					_createProvider(allowedProviderName),
					_createProvider(RandomTestUtil.randomString())
				});
		}
	}

	private void _assertFailedCheck(
		String expectedMessage, String methodName, Provider[] providers) {

		String failedCheck = ReflectionTestUtil.invoke(
			FIPSModeValidator.class, methodName,
			new Class<?>[] {Provider[].class}, (Object)providers);

		Assert.assertTrue(failedCheck, failedCheck.contains(expectedMessage));
	}

	private void _assertSecurityException(
		String expectedMessage, ThrowingRunnable throwingRunnable) {

		SecurityException securityException = Assert.assertThrows(
			SecurityException.class, throwingRunnable);

		String message = securityException.getMessage();

		Assert.assertTrue(message, message.contains(expectedMessage));
	}

	private Provider _createProvider(String name) {
		return new Provider(
			name, RandomTestUtil.randomString(),
			RandomTestUtil.randomString()) {
		};
	}

}