/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.security.fips.rest.client.dto.v1_0.AlgorithmFamily;
import com.liferay.portal.security.fips.rest.client.dto.v1_0.FIPSMode;
import com.liferay.portal.security.fips.rest.client.dto.v1_0.SecurityInfo;
import com.liferay.portal.security.fips.rest.client.dto.v1_0.SecurityProvider;
import com.liferay.portal.security.fips.rest.client.resource.v1_0.SecurityInfoResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class SecurityInfoResourceTest extends BaseSecurityInfoResourceTestCase {

	@Override
	@Test
	public void testGetSecurityInfo() throws Exception {
		SecurityInfo securityInfo = securityInfoResource.getSecurityInfo();

		FIPSMode fipsMode = securityInfo.getFipsMode();

		Assert.assertEquals(
			Boolean.valueOf(PropsValues.FIPS_ENABLED), fipsMode.getEnabled());

		Assert.assertTrue(securityInfo.getProviderOrder().length > 0);

		SecurityProvider securityProvider = securityInfo.getProvider();

		Assert.assertNotNull(securityProvider.getName());

		for (AlgorithmFamily algorithmFamily :
				securityInfo.getAlgorithmFamilies()) {

			Assert.assertNotNull(algorithmFamily.getMatches());
		}
	}

	@Test
	public void testGetSecurityInfoWithoutPermission() throws Exception {
		String password = RandomTestUtil.randomString();

		User user = UserTestUtil.addUser(testCompany, password);

		SecurityInfoResource unauthorizedSecurityInfoResource =
			SecurityInfoResource.builder(
			).authentication(
				user.getEmailAddress(), password
			).endpoint(
				testCompany.getVirtualHostname(),
				PortalUtil.getPortalServerPort(false), "http"
			).locale(
				LocaleUtil.getDefault()
			).build();

		Assert.assertEquals(
			404,
			unauthorizedSecurityInfoResource.getSecurityInfoHttpResponse(
			).getStatusCode());
	}

}