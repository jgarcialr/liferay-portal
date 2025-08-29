/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.analytics.internal.configuration.provider;

import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.product.analytics.configuration.ProductAnalyticsConfiguration;
import com.liferay.product.analytics.configuration.ProductAnalyticsConfigurationProvider;
import com.liferay.product.analytics.configuration.banner.ProductAnalyticsBannerConfiguration;
import jakarta.portlet.PortletRequest;

import java.io.IOException;

import java.util.function.Function;
import java.util.function.Supplier;

import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Sanz
 */
@Component(service = ProductAnalyticsConfigurationProvider.class)
public class ProductAnalyticsConfigurationProviderImpl
	implements ProductAnalyticsConfigurationProvider {

	@Override
	public ProductAnalyticsBannerConfiguration getProductAnalyticsBannerConfiguration(
		ThemeDisplay themeDisplay) throws Exception {
		return _getProductAnalyticsConfiguration(
			ProductAnalyticsBannerConfiguration.class, themeDisplay);
	}

	@Override
	public ProductAnalyticsConfiguration getProductAnalyticsConfiguration(
		ThemeDisplay themeDisplay) throws Exception {
		return _getProductAnalyticsConfiguration(
			ProductAnalyticsConfiguration.class, themeDisplay);
	}

	private HashMapDictionary<String, Object> _createDictionary(
		boolean enabled, boolean explicitConsentMode) {

		return HashMapDictionaryBuilder.<String, Object>put(
			"enabled", enabled
		).put(
			"explicitConsentMode", explicitConsentMode
		).build();
	}

	private <T> T _getProductAnalyticsConfiguration(
		Class<T> clazz, ThemeDisplay themeDisplay)
		throws Exception {

		Object configurationProvider = null;

		if (Validator.isNotNull(themeDisplay.getScopeGroupId())) {
			configurationProvider =
				_configurationProvider.getGroupConfiguration(
					clazz, themeDisplay.getScopeGroupId());
		}

		if (configurationProvider == null) {
			configurationProvider =
				_configurationProvider.getCompanyConfiguration(
				clazz, themeDisplay.getCompanyId());
		}

		return (T) configurationProvider;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductAnalyticsConfigurationProviderImpl.class);

	@Reference
	private ConfigurationProvider _configurationProvider;


	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

}