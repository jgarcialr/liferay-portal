/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.dto.v1_0;

import com.liferay.portal.security.fips.rest.client.function.UnsafeSupplier;
import com.liferay.portal.security.fips.rest.client.serdes.v1_0.SecurityProviderSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
public class SecurityProvider implements Cloneable, Serializable {

	public static SecurityProvider toDTO(String json) {
		return SecurityProviderSerDes.toDTO(json);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setName(UnsafeSupplier<String, Exception> nameUnsafeSupplier) {
		try {
			name = nameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String name;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setVersion(
		UnsafeSupplier<String, Exception> versionUnsafeSupplier) {

		try {
			version = versionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String version;

	@Override
	public SecurityProvider clone() throws CloneNotSupportedException {
		return (SecurityProvider)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SecurityProvider)) {
			return false;
		}

		SecurityProvider securityProvider = (SecurityProvider)object;

		return Objects.equals(toString(), securityProvider.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SecurityProviderSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1151178081