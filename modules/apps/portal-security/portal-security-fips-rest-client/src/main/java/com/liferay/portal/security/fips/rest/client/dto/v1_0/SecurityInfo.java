/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.dto.v1_0;

import com.liferay.portal.security.fips.rest.client.function.UnsafeSupplier;
import com.liferay.portal.security.fips.rest.client.serdes.v1_0.SecurityInfoSerDes;

import jakarta.annotation.Generated;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
public class SecurityInfo implements Cloneable, Serializable {

	public static SecurityInfo toDTO(String json) {
		return SecurityInfoSerDes.toDTO(json);
	}

	public AlgorithmFamily[] getAlgorithmFamilies() {
		return algorithmFamilies;
	}

	public void setAlgorithmFamilies(AlgorithmFamily[] algorithmFamilies) {
		this.algorithmFamilies = algorithmFamilies;
	}

	public void setAlgorithmFamilies(
		UnsafeSupplier<AlgorithmFamily[], Exception>
			algorithmFamiliesUnsafeSupplier) {

		try {
			algorithmFamilies = algorithmFamiliesUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected AlgorithmFamily[] algorithmFamilies;

	public FIPSMode getFipsMode() {
		return fipsMode;
	}

	public void setFipsMode(FIPSMode fipsMode) {
		this.fipsMode = fipsMode;
	}

	public void setFipsMode(
		UnsafeSupplier<FIPSMode, Exception> fipsModeUnsafeSupplier) {

		try {
			fipsMode = fipsModeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected FIPSMode fipsMode;

	public SecurityProvider getJsseProvider() {
		return jsseProvider;
	}

	public void setJsseProvider(SecurityProvider jsseProvider) {
		this.jsseProvider = jsseProvider;
	}

	public void setJsseProvider(
		UnsafeSupplier<SecurityProvider, Exception>
			jsseProviderUnsafeSupplier) {

		try {
			jsseProvider = jsseProviderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SecurityProvider jsseProvider;

	public SecurityProvider getProvider() {
		return provider;
	}

	public void setProvider(SecurityProvider provider) {
		this.provider = provider;
	}

	public void setProvider(
		UnsafeSupplier<SecurityProvider, Exception> providerUnsafeSupplier) {

		try {
			provider = providerUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected SecurityProvider provider;

	public String[] getProviderOrder() {
		return providerOrder;
	}

	public void setProviderOrder(String[] providerOrder) {
		this.providerOrder = providerOrder;
	}

	public void setProviderOrder(
		UnsafeSupplier<String[], Exception> providerOrderUnsafeSupplier) {

		try {
			providerOrder = providerOrderUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String[] providerOrder;

	@Override
	public SecurityInfo clone() throws CloneNotSupportedException {
		return (SecurityInfo)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SecurityInfo)) {
			return false;
		}

		SecurityInfo securityInfo = (SecurityInfo)object;

		return Objects.equals(toString(), securityInfo.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return SecurityInfoSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-745462923