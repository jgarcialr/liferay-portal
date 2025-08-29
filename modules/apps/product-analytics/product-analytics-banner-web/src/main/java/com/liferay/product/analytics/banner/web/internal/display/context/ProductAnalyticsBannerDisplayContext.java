/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.analytics.banner.web.internal.display.context;

import com.liferay.layout.utility.page.kernel.constants.LayoutUtilityPageEntryConstants;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.analytics.configuration.ProductAnalyticsConfigurationProvider;
import com.liferay.product.analytics.configuration.banner.ProductAnalyticsBannerConfiguration;
import com.liferay.product.analytics.banner.web.internal.constants.ProductAnalyticsBannerPortletKeys;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import java.util.Locale;
import java.util.Map;

/**
 * @author Christopher Kian
 */
public class ProductAnalyticsBannerDisplayContext {

	public ProductAnalyticsBannerDisplayContext(
		ProductAnalyticsConfigurationProvider productAnalyticsConfigurationProvider,
		LayoutUtilityPageEntryLayoutProvider
			layoutUtilityPageEntryLayoutProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		this.layoutUtilityPageEntryLayoutProvider =
			layoutUtilityPageEntryLayoutProvider;
		_productAnalyticsConfigurationProvider = productAnalyticsConfigurationProvider;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		productAnalyticsBannerConfiguration = _getProductAnalyticsBannerConfiguration(
			renderRequest);
	}

	public String getContent(Locale locale) {
		LocalizedValuesMap contentLocalizedValuesMap =
			productAnalyticsBannerConfiguration.content();

		return contentLocalizedValuesMap.get(locale);
	}

	public Map<String, Object> getContext(Locale locale) {
		LocalizedValuesMap titleLocalizedValuesMap =
			productAnalyticsBannerConfiguration.title();

		return HashMapBuilder.<String, Object>put(
			"configurationNamespace",
			ProductAnalyticsBannerPortletKeys.PRODUCT_ANALYTICS_CONFIGURATION
		).put(
//			"configurationURL", getConfigurationURL()
//		).put(
//			"includeDeclineAllButton", isIncludeDeclineAllButton()
//		).put(
//			"optionalConsentCookieTypeNames",
//			getConsentCookieTypeNamesJSONArray(getOptionalConsentCookieTypes())
//		).put(
//			"requiredConsentCookieTypeNames",
//			getConsentCookieTypeNamesJSONArray(getRequiredConsentCookieTypes())
//		).put(
			"title", titleLocalizedValuesMap.get(locale)
		).build();
	}


	public String getLinkDisplayText(Locale locale) {
		LocalizedValuesMap linkDisplayTextLocalizedValuesMap =
			productAnalyticsBannerConfiguration.linkDisplayText();

		return linkDisplayTextLocalizedValuesMap.get(locale);
	}

	public String getPrivacyPolicyLink() throws PortalException {
		String privacyPolicyLink =
			productAnalyticsBannerConfiguration.privacyPolicyLink();

		if (Validator.isNotNull(privacyPolicyLink)) {
			return privacyPolicyLink;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout =
			layoutUtilityPageEntryLayoutProvider.
				getDefaultLayoutUtilityPageEntryLayout(
					themeDisplay.getScopeGroupId(),
					LayoutUtilityPageEntryConstants.TYPE_COOKIE_POLICY);

		if (layout != null) {
			return PortalUtil.getLayoutURL(layout, themeDisplay);
		}

		return StringPool.POUND;
	}

	public String getTitle(Locale locale) {
		LocalizedValuesMap titleLocalizedValuesMap =
			productAnalyticsBannerConfiguration.title();

		return titleLocalizedValuesMap.get(locale);
	}

	protected ProductAnalyticsBannerConfiguration productAnalyticsBannerConfiguration;

	protected LayoutUtilityPageEntryLayoutProvider
		layoutUtilityPageEntryLayoutProvider;

	protected RenderRequest renderRequest;
	protected RenderResponse renderResponse;

	private ProductAnalyticsBannerConfiguration _getProductAnalyticsBannerConfiguration(
		RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			return _productAnalyticsConfigurationProvider.getProductAnalyticsBannerConfiguration(
				themeDisplay);
		}
		catch (Exception exception) {
			_log.error("Unable to get product analytics banner configuration", exception);
		}

		return null;
	}


	private final ProductAnalyticsConfigurationProvider
		_productAnalyticsConfigurationProvider;


	private static final Log _log = LogFactoryUtil.getLog(
		ProductAnalyticsBannerDisplayContext.class);

}