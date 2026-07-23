/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

/**
 * The states of Liferay DXP's FIPS application state machine.
 *
 * <p>
 * The validated cryptographic provider maintains its own internal finite state
 * machine. These states form an application level overlay that tracks Liferay
 * DXP's relationship to that validated module, modeled per ISO/IEC 19790:2025
 * §7.11.4 and the §2.2 application state machine.
 * </p>
 *
 * @author Jorge García Jiménez
 */
public enum FIPSApplicationState {

	ERROR, INITIALIZING, KEY_CSP_ENTRY, OPERATIONAL, POWER_OFF, QUIESCENT,
	SELF_TEST

}