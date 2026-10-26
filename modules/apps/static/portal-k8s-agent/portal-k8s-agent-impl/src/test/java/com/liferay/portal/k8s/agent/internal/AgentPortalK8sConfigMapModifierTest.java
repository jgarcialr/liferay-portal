/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.k8s.agent.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.k8s.agent.configuration.PortalK8sAgentConfiguration;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.fabric8.kubernetes.client.Config;

import java.io.File;

import java.nio.file.Files;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.mockito.Mockito;

/**
 * @author Jorge García Jiménez
 */
public class AgentPortalK8sConfigMapModifierTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToConfig() throws Exception {
		File file = temporaryFolder.newFile();
		String serviceAccountToken = RandomTestUtil.randomString();

		Files.writeString(file.toPath(), serviceAccountToken);

		System.setProperty(
			Config.KUBERNETES_AUTH_SERVICEACCOUNT_TOKEN_FILE_SYSTEM_PROPERTY,
			file.getPath());

		try {
			_testToConfig(serviceAccountToken, StringPool.BLANK);
			_testToConfig(serviceAccountToken, null);

			String saToken = RandomTestUtil.randomString();

			_testToConfig(saToken, saToken);
		}
		finally {
			System.clearProperty(
				Config.
					KUBERNETES_AUTH_SERVICEACCOUNT_TOKEN_FILE_SYSTEM_PROPERTY);
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _testToConfig(String expectedOAuthToken, String saToken) {
		Config config = ReflectionTestUtil.invoke(
			Mockito.mock(
				AgentPortalK8sConfigMapModifier.class,
				Mockito.CALLS_REAL_METHODS),
			"_toConfig", new Class<?>[] {PortalK8sAgentConfiguration.class},
			ConfigurableUtil.createConfigurable(
				PortalK8sAgentConfiguration.class,
				HashMapBuilder.<String, Object>put(
					"apiServerHost", RandomTestUtil.randomString()
				).put(
					"apiServerPort", RandomTestUtil.randomInt()
				).put(
					"caCertData", RandomTestUtil.randomString()
				).put(
					"namespace", RandomTestUtil.randomString()
				).put(
					"saToken", saToken
				).build()));

		Assert.assertEquals(expectedOAuthToken, config.getOauthToken());
	}

}