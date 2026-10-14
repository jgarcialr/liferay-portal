/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices} from '@playwright/test';

export const config = {
	name: 'portal-security-sso-openid-connect-impl.upstream-token-forwarding',
	testDir:
		'tests/portal-security-sso-openid-connect-impl/upstream-token-forwarding',
	timeout: 300000,
	use: {
		...devices['Desktop Chrome'],
		...(process.env.UPSTREAM_TOKEN_FORWARDING_VIDEO && {
			launchOptions: {
				slowMo: 1000,
			},
			video: {
				mode: 'on',
				size: {
					height: 720,
					width: 1280,
				},
			},
		}),
	},
};
