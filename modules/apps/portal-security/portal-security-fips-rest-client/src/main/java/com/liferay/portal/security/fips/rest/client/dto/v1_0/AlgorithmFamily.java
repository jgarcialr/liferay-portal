/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.dto.v1_0;

import com.liferay.portal.security.fips.rest.client.function.UnsafeSupplier;
import com.liferay.portal.security.fips.rest.client.serdes.v1_0.AlgorithmFamilySerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
public class AlgorithmFamily implements Cloneable, Serializable {

	public static AlgorithmFamily toDTO(String json) {
		return AlgorithmFamilySerDes.toDTO(json);
	}

	public String getDeclaredProvider() {
		return declaredProvider;
	}

	public void setDeclaredProvider(String declaredProvider) {
		this.declaredProvider = declaredProvider;
	}

	public void setDeclaredProvider(
		UnsafeSupplier<String, Exception> declaredProviderUnsafeSupplier) {

		try {
			declaredProvider = declaredProviderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String declaredProvider;

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public void setFamily(
		UnsafeSupplier<String, Exception> familyUnsafeSupplier) {

		try {
			family = familyUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String family;

	public Boolean getMatches() {
		return matches;
	}

	public void setMatches(Boolean matches) {
		this.matches = matches;
	}

	public void setMatches(
		UnsafeSupplier<Boolean, Exception> matchesUnsafeSupplier) {

		try {
			matches = matchesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean matches;

	public String getResolvedProvider() {
		return resolvedProvider;
	}

	public void setResolvedProvider(String resolvedProvider) {
		this.resolvedProvider = resolvedProvider;
	}

	public void setResolvedProvider(
		UnsafeSupplier<String, Exception> resolvedProviderUnsafeSupplier) {

		try {
			resolvedProvider = resolvedProviderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String resolvedProvider;

	@Override
	public AlgorithmFamily clone() throws CloneNotSupportedException {
		return (AlgorithmFamily)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AlgorithmFamily)) {
			return false;
		}

		AlgorithmFamily algorithmFamily = (AlgorithmFamily)object;

		return Objects.equals(toString(), algorithmFamily.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return AlgorithmFamilySerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:907886239