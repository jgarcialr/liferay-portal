/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.fips;

import com.liferay.petra.string.StringBundler;

import java.util.Map;
import java.util.Set;

/**
 * Tracks Liferay DXP's current {@link FIPSApplicationState} and enforces the
 * legal transitions of the §2.2 application state machine.
 *
 * <p>
 * The machine starts in {@link FIPSApplicationState#INITIALIZING} and only
 * moves along an allowed edge; an illegal transition is rejected with an
 * {@link IllegalStateException} and leaves the current state unchanged.
 * {@link FIPSApplicationState#ERROR} is latched to a single
 * {@link FIPSApplicationState#POWER_OFF} exit and
 * {@link FIPSApplicationState#POWER_OFF} is terminal, so an error state is left
 * only by restarting the portal (see §2.4).
 * </p>
 *
 * <p>
 * Each successful transition is the seam where FIPS state transition audit
 * events are recorded (see §5.2).
 * </p>
 *
 * @author Jorge García Jiménez
 */
public class FIPSApplicationStateMachine {

	public FIPSApplicationState getFIPSApplicationState() {
		return _fipsApplicationState;
	}

	public synchronized void transition(
		FIPSApplicationState fipsApplicationState) {

		Set<FIPSApplicationState> nextFIPSApplicationStates =
			_allowedTransitions.get(_fipsApplicationState);

		if (!nextFIPSApplicationStates.contains(fipsApplicationState)) {
			throw new IllegalStateException(
				StringBundler.concat(
					"Unable to transition the FIPS application state from \"",
					_fipsApplicationState, "\" to \"", fipsApplicationState,
					"\""));
		}

		_fipsApplicationState = fipsApplicationState;
	}

	private static final Map<FIPSApplicationState, Set<FIPSApplicationState>>
		_allowedTransitions = Map.of(
			FIPSApplicationState.ERROR, Set.of(FIPSApplicationState.POWER_OFF),
			FIPSApplicationState.INITIALIZING,
			Set.of(
				FIPSApplicationState.ERROR, FIPSApplicationState.POWER_OFF,
				FIPSApplicationState.SELF_TEST),
			FIPSApplicationState.KEY_CSP_ENTRY,
			Set.of(
				FIPSApplicationState.ERROR, FIPSApplicationState.OPERATIONAL,
				FIPSApplicationState.POWER_OFF),
			FIPSApplicationState.OPERATIONAL,
			Set.of(
				FIPSApplicationState.ERROR, FIPSApplicationState.KEY_CSP_ENTRY,
				FIPSApplicationState.POWER_OFF, FIPSApplicationState.QUIESCENT),
			FIPSApplicationState.POWER_OFF, Set.of(),
			FIPSApplicationState.QUIESCENT,
			Set.of(
				FIPSApplicationState.ERROR, FIPSApplicationState.OPERATIONAL,
				FIPSApplicationState.POWER_OFF),
			FIPSApplicationState.SELF_TEST,
			Set.of(
				FIPSApplicationState.ERROR, FIPSApplicationState.OPERATIONAL,
				FIPSApplicationState.POWER_OFF));

	private volatile FIPSApplicationState _fipsApplicationState =
		FIPSApplicationState.INITIALIZING;

}