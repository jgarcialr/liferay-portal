/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';
import {InstanceSettingsPage} from '../configuration-admin-web/InstanceSettingsPage';

export class OpenIdInstanceSettingsPage {
	readonly addButton: Locator;
	readonly allowUpstreamTokenForwardingCheckbox: Locator;
	readonly customClaimField: Locator;
	readonly discoveryEndpointField: Locator;
	readonly enabledCheckbox: Locator;
	readonly instanceSettingsPage: InstanceSettingsPage;
	readonly matcherField: Locator;
	readonly openIDConnectClientIDField: Locator;
	readonly openIDConnectClientSecret: Locator;
	readonly openIdConnectMenuItem: Locator;
	readonly openIDConnectProviderConnection: Locator;
	readonly page: Page;
	readonly providerNameField: Locator;
	readonly saveButton: Locator;
	readonly updateButton: Locator;
	readonly userCustomFieldsSelect: Locator;

	constructor(page: Page) {
		this.addButton = page.getByRole('link', {name: 'Add'});
		this.allowUpstreamTokenForwardingCheckbox = page.getByLabel(
			'Allow Upstream Token Forwarding'
		);
		this.customClaimField = page.getByLabel('Custom Claim');
		this.discoveryEndpointField = page.getByLabel(
			'Discovery Endpoint Set the'
		);
		this.enabledCheckbox = page.getByText(' Enabled ');
		this.instanceSettingsPage = new InstanceSettingsPage(page);
		this.matcherField = page.getByLabel('Matcher Field');
		this.openIDConnectClientIDField = page.getByLabel(
			'OpenID Connect Client ID'
		);
		this.openIDConnectClientSecret = page.getByLabel(
			'OpenID Connect Client Secret'
		);
		this.openIdConnectMenuItem = page.getByRole('menuitem', {
			exact: true,
			name: 'OpenID Connect',
		});
		this.openIDConnectProviderConnection = page.getByRole('menuitem', {
			name: 'OpenID Connect Provider Connection',
		});
		this.page = page;
		this.providerNameField = page.getByLabel('Provider Name');
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.updateButton = page.getByRole('button', {name: 'Update'});
		this.userCustomFieldsSelect = page.getByLabel('User Custom Fields');
	}

	async addOpenIDConnectProviderConnectionConfiguration(
		openIdProvider: string,
		providerName: string,
		customClaim?: TCustomClaim,
		matcherField?: string,
		{
			allowUpstreamTokenForwarding = false,
			openIdConnectClientId = getRandomString(),
			openIdConnectClientSecret = getRandomString(),
		}: {
			allowUpstreamTokenForwarding?: boolean;
			openIdConnectClientId?: string;
			openIdConnectClientSecret?: string;
		} = {}
	) {
		await this.clickOpenIDConnectProviderConnectionMenuItem();
		await this.addButton.click();
		await this.providerNameField.fill(providerName);
		await this.discoveryEndpointField.fill(openIdProvider);
		await this.openIDConnectClientIDField.fill(openIdConnectClientId);
		await this.openIDConnectClientSecret.fill(openIdConnectClientSecret);

		if (customClaim) {
			await this.userCustomFieldsSelect.selectOption({
				label: customClaim.expandoColumnName,
			});
			await this.customClaimField.fill(
				customClaim.oidcProviderCustomClaim
			);
		}

		if (matcherField) {
			await this.matcherField.selectOption(matcherField);
		}

		if (allowUpstreamTokenForwarding) {
			await this.allowUpstreamTokenForwardingCheckbox.check();
		}

		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async clickOpenIDConnectProviderConnectionMenuItem() {
		await this.openIDConnectProviderConnection.click();
	}

	async clickSetupOpenIdConnectionMenuItem() {
		await this.openIdConnectMenuItem.click();
	}

	async disableOpenIDConnect() {
		await this.clickSetupOpenIdConnectionMenuItem();
		await this.enabledCheckbox.uncheck();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async enableOpenIDConnect() {
		await this.clickSetupOpenIdConnectionMenuItem();
		await this.enabledCheckbox.check();
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async goto() {
		await this.instanceSettingsPage.goToSSO();
	}

	async goToOpenIDConnectProviderConnectionConfiguration(
		providerName: string
	) {
		await this.clickOpenIDConnectProviderConnectionMenuItem();
		await this.page
			.getByRole('link', {exact: true, name: providerName})
			.click();

		await expect(this.providerNameField).toHaveValue(providerName);
	}

	async removeOpenIDConnectProviderConnectionConfiguration(
		providerName: string
	) {
		await this.clickOpenIDConnectProviderConnectionMenuItem();
		await this.page.waitForLoadState('networkidle');
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('link', {
				name: 'Delete',
			}),
			trigger: this.page
				.getByRole('row', {
					name: providerName,
				})
				.locator('div')
				.first()
				.locator('a')
				.first(),
		});
		await waitForAlert(this.page);
	}
}
