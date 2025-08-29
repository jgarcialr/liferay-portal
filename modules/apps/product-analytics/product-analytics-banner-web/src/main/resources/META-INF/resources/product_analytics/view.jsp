<%--
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
--%>

<%@ include file="/init.jsp" %>

<%
ProductAnalyticsBannerDisplayContext
	productAnalyticsBannerDisplayContext = (ProductAnalyticsBannerDisplayContext)request.getAttribute(
	ProductAnalyticsBannerWebKeys.PRODUCT_ANALYTICS_BANNER_DISPLAY_CONTEXT);
%>

<clay:container-fluid
	cssClass="container-view"
>
	<clay:row>
		<clay:content-row
			cssClass="autofit-float-sm-down px-2 px-md-0"
			noGutters="true"
			verticalAlign="center"
		>
			<clay:content-col
				expand="<%= true %>"
			>
				<p class="mb-2 text-5 text-weight-semi-bold">
					<%= productAnalyticsBannerDisplayContext.getTitle(locale) %>
				</p>

				<p class="mb-0">
					<%= productAnalyticsBannerDisplayContext.getContent(locale) %>

					<clay:link
						href="<%= productAnalyticsBannerDisplayContext.getPrivacyPolicyLink() %>"
						label="<%= productAnalyticsBannerDisplayContext.getLinkDisplayText(locale) %>"
					/>
				</p>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					displayType="link"
					id='<%= liferayPortletResponse.getNamespace() + "configurationButton" %>'
					label='<%= LanguageUtil.get(request, "configuration") %>'
					small="<%= true %>"
				/>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "acceptAllButton" %>'
					label='<%= LanguageUtil.get(request, "accept-all") %>'
					small="<%= true %>"
				/>
			</clay:content-col>

			<clay:content-col>
				<clay:button
					displayType="secondary"
					id='<%= liferayPortletResponse.getNamespace() + "declineAllButton" %>'
					label='<%= LanguageUtil.get(request, "decline-all") %>'
					small="<%= true %>"
				/>
			</clay:content-col>
		</clay:content-row>
	</clay:row>
</clay:container-fluid>

<liferay-frontend:component
	componentId="ProductAnalyticsBanner"
	context="<%= productAnalyticsBannerDisplayContext.getContext(locale) %>"
	module="{ProductAnalyticsBanner} from product-analytics-banner-web"
/>