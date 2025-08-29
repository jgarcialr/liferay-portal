/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.analytics.configuration.banner;

import aQute.bnd.annotation.metatype.Meta;
import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;

/**
 * @author Christopher Kian
 */
@ExtendedObjectClassDefinition(
	category = "privacy", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.product.analytics.configuration.banner.ProductAnalyticsBannerConfiguration",
	localization = "content/Language", name = "product-analytics-banner-configuration-name"
)
public interface ProductAnalyticsBannerConfiguration {

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:product-analytics-banner-title}", name = "title",
		required = false
	)
	public LocalizedValuesMap title();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:product-analytics-banner-content}", name = "content",
		required = false
	)
	public LocalizedValuesMap content();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(name = "privacy-policy-link", required = false)
	public String privacyPolicyLink();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "${language:visit-our-privacy-policy}",
		name = "link-display-text", required = false
	)
	public LocalizedValuesMap linkDisplayText();

}