/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.dto.v1_0;

import com.liferay.portal.security.fips.rest.client.function.UnsafeSupplier;
import com.liferay.portal.security.fips.rest.client.serdes.v1_0.FIPSModeSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
public class FIPSMode implements Cloneable, Serializable {

	public static FIPSMode toDTO(String json) {
		return FIPSModeSerDes.toDTO(json);
	}

	public Boolean getApprovedMode() {
		return approvedMode;
	}

	public void setApprovedMode(Boolean approvedMode) {
		this.approvedMode = approvedMode;
	}

	public void setApprovedMode(
		UnsafeSupplier<Boolean, Exception> approvedModeUnsafeSupplier) {

		try {
			approvedMode = approvedModeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean approvedMode;

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		try {
			enabled = enabledUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean enabled;

	@Override
	public FIPSMode clone() throws CloneNotSupportedException {
		return (FIPSMode)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FIPSMode)) {
			return false;
		}

		FIPSMode fipsMode = (FIPSMode)object;

		return Objects.equals(toString(), fipsMode.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return FIPSModeSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:1000067447