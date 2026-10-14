/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectDefinitionAPI,
} from '@liferay/object-admin-rest-client-js';
import {Page, expect, mergeTests} from '@playwright/test';
import {readFileSync} from 'fs';
import {IncomingHttpHeaders, RequestListener, Server, createServer} from 'http';
import {createServer as createSecureServer} from 'https';
import {join} from 'path';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {OpenIdInstanceSettingsPage} from '../../../pages/portal-settings-authentication-openid-connect-web/OpenIdInstanceSettingsPage';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {performLogout} from '../../../utils/performLogin';
import {waitForAlert} from '../../../utils/waitForAlert';
import {openIdSettingsPagesTest} from '../../openid-link/main/fixtures/openIdSettingsPagesTest';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-108193': {enabled: true},
		'LPS-135430': {enabled: true},
	}),
	loginTest(),
	objectPagesTest,
	openIdSettingsPagesTest
);

const clientExtensionRequests: {headers: IncomingHttpHeaders; url: string}[] =
	[];

const keycloakIssuer =
	process.env.KEYCLOAK_ISSUER || 'http://localhost:18080/realms/liferay';

function getClaims(idToken: string | string[]) {
	return JSON.parse(
		Buffer.from(String(idToken).split('.')[1], 'base64url').toString()
	);
}

const requestListener: RequestListener = (request, response) => {
	clientExtensionRequests.push({headers: request.headers, url: request.url});

	const idToken = request.headers['x-upstream-id-token'];

	let issuer = '';
	let upstreamIdToken = 'Not received';

	if (idToken) {
		const claims = getClaims(idToken);

		issuer = claims.iss;
		upstreamIdToken = 'Received for ' + claims.email;
	}

	response.writeHead(200, {'Content-Type': 'application/json'});

	response.end(
		JSON.stringify({
			items: [
				{
					externalReferenceCode: 'upstream-id-token',
					issuer,
					upstreamIdToken,
				},
			],
			totalCount: 1,
		})
	);
};

let insecureServer: Server;
let secureServer: Server;

test.beforeAll(async () => {
	const tlsDir = process.env.UPSTREAM_TOKEN_FORWARDING_TLS_DIR;

	if (!tlsDir) {
		throw new Error(
			'Set UPSTREAM_TOKEN_FORWARDING_TLS_DIR to the directory holding cx.crt and cx.key'
		);
	}

	secureServer = createSecureServer(
		{
			cert: readFileSync(join(tlsDir, 'cx.crt')),
			key: readFileSync(join(tlsDir, 'cx.key')),
		},
		requestListener
	);

	await new Promise<void>((resolve) => secureServer.listen(58443, resolve));

	insecureServer = createServer(requestListener);

	await new Promise<void>((resolve) => insecureServer.listen(58080, resolve));
});

test.afterAll(async () => {
	await new Promise((resolve) => insecureServer.close(resolve));
	await new Promise((resolve) => secureServer.close(resolve));
});

function getObjectField(label: string, name: string) {
	return {
		DBType: 'String',
		businessType: 'Text',
		externalReferenceCode: name,
		indexed: false,
		indexedAsKeyword: false,
		indexedLanguageId: '',
		label: {en_US: label},
		listTypeDefinitionId: 0,
		localized: false,
		name,
		required: false,
		system: false,
		type: 'String',
	} as const;
}

async function getUpstreamIdToken(
	objectDefinition: ObjectDefinition,
	page: Page
) {
	clientExtensionRequests.length = 0;

	await expect
		.poll(
			() =>
				page.evaluate(async (restContextPath) => {
					const response = await fetch(restContextPath, {
						headers: {
							'x-csrf-token': (window as any).Liferay.authToken,
						},
					});

					return response.status;
				}, objectDefinition.restContextPath),
			{timeout: 30000}
		)
		.toBe(200);

	const clientExtensionRequest = clientExtensionRequests
		.filter(({url}) =>
			url.startsWith(
				`/object-entries/${objectDefinition.externalReferenceCode}?`
			)
		)
		.pop();

	expect(clientExtensionRequest).toBeDefined();

	return clientExtensionRequest.headers['x-upstream-id-token'];
}

test(
	'Forward the upstream ID token only when every guard holds',
	{tag: '@LPD-108189'},
	async ({
		apiHelpers,
		backendPage,
		openIDInstanceSettingsPage,
		page,
		viewObjectEntriesPage,
	}) => {

		// Add the proxy objects

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const objectDefinitions: {[storageType: string]: ObjectDefinition} = {};

		for (const storageType of [
			'upstream-token-forwarding',
			'upstream-token-forwarding-cross-origin',
			'upstream-token-forwarding-disabled',
			'upstream-token-forwarding-insecure',
		]) {
			const name = 'UpstreamToken' + getRandomInt();

			const {body: objectDefinition} =
				await objectDefinitionAPIClient.postObjectDefinition({
					active: true,
					externalReferenceCode: name,
					label: {en_US: name},
					name,
					objectFields: [
						getObjectField('Issuer', 'issuer'),
						getObjectField('Upstream ID Token', 'upstreamIdToken'),
					],
					panelCategoryKey: 'control_panel.object',
					pluralLabel: {en_US: name},
					portlet: true,
					scope: 'company',
					status: {code: 0},
					storageType: `function#${storageType}`,
					titleObjectFieldName: 'upstreamIdToken',
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			objectDefinitions[storageType] = objectDefinition;
		}

		// Check a password session

		expect(
			await getUpstreamIdToken(
				objectDefinitions['upstream-token-forwarding'],
				page
			)
		).toBeUndefined();

		// Add the OpenID Connect provider connection

		const providerName = 'Keycloak ' + getRandomString();

		await openIDInstanceSettingsPage.goto();

		await openIDInstanceSettingsPage.enableOpenIDConnect();

		await openIDInstanceSettingsPage.addOpenIDConnectProviderConnectionConfiguration(
			`${keycloakIssuer}/.well-known/openid-configuration`,
			providerName,
			undefined,
			undefined,
			{
				allowUpstreamTokenForwarding: true,
				openIdConnectClientId:
					process.env.KEYCLOAK_CLIENT_ID || 'liferay',
				openIdConnectClientSecret: process.env.KEYCLOAK_CLIENT_SECRET,
			}
		);

		try {

			// Sign in through Keycloak

			await performLogout(page);

			await page.goto('/c/portal/login');

			await page.getByRole('menuitem', {name: 'OpenId Connect'}).click();

			const clientNameSelect = page.getByLabel(
				'OpenId Connect Client Name'
			);

			await clientNameSelect.selectOption({
				label: 'Client to ' + providerName,
			});

			await page
				.locator('form', {has: clientNameSelect})
				.getByRole('button', {name: 'Sign In'})
				.click();

			await page
				.getByLabel('Username or email')
				.fill(process.env.KEYCLOAK_USER_NAME || 'upstream-test');

			await page
				.getByLabel('Password', {exact: true})
				.fill(process.env.KEYCLOAK_USER_PASSWORD);

			await page.getByRole('button', {name: 'Sign In'}).click();

			await page.waitForFunction(() =>
				(window as any).Liferay?.ThemeDisplay?.isSignedIn()
			);

			// Check the forwarded ID token

			const userEmail =
				process.env.KEYCLOAK_USER_EMAIL || 'test@liferay.com';

			await viewObjectEntriesPage.goto(
				objectDefinitions['upstream-token-forwarding'].className
			);

			await expect(
				page.getByText('Received for ' + userEmail)
			).toBeVisible();
			await expect(page.getByText(keycloakIssuer)).toBeVisible();

			const claims = getClaims(
				await getUpstreamIdToken(
					objectDefinitions['upstream-token-forwarding'],
					page
				)
			);

			expect(claims.aud).toContain(
				process.env.KEYCLOAK_CLIENT_ID || 'liferay'
			);
			expect(claims.email).toBe(userEmail);
			expect(claims.iss).toBe(keycloakIssuer);
			expect(claims.typ).toBe('ID');

			// Check the client extensions that must not get it

			expect(
				await getUpstreamIdToken(
					objectDefinitions['upstream-token-forwarding-cross-origin'],
					page
				)
			).toBeUndefined();
			expect(
				await getUpstreamIdToken(
					objectDefinitions['upstream-token-forwarding-disabled'],
					page
				)
			).toBeUndefined();
			expect(
				await getUpstreamIdToken(
					objectDefinitions['upstream-token-forwarding-insecure'],
					page
				)
			).toBeUndefined();

			// Disable the feature flag

			await apiHelpers.featureFlag.updateFeatureFlag('LPD-108193', false);

			expect(
				await getUpstreamIdToken(
					objectDefinitions['upstream-token-forwarding'],
					page
				)
			).toBeUndefined();

			await apiHelpers.featureFlag.updateFeatureFlag('LPD-108193', true);

			// Disallow upstream token forwarding on the provider connection

			await openIDInstanceSettingsPage.goto();

			await openIDInstanceSettingsPage.goToOpenIDConnectProviderConnectionConfiguration(
				providerName
			);

			await expect(
				openIDInstanceSettingsPage.allowUpstreamTokenForwardingCheckbox
			).toBeChecked();

			await openIDInstanceSettingsPage.allowUpstreamTokenForwardingCheckbox.uncheck();

			await openIDInstanceSettingsPage.updateButton.click();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(
				objectDefinitions['upstream-token-forwarding'].className
			);

			await expect(page.getByText('Not received')).toBeVisible();
		}
		finally {
			const backendOpenIdInstanceSettingsPage =
				new OpenIdInstanceSettingsPage(backendPage);

			await backendOpenIdInstanceSettingsPage.goto();

			await backendOpenIdInstanceSettingsPage.disableOpenIDConnect();

			await backendOpenIdInstanceSettingsPage.removeOpenIDConnectProviderConnectionConfiguration(
				providerName
			);
		}
	}
);
