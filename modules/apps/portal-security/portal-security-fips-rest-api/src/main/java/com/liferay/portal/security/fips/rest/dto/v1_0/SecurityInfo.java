/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.dto.v1_0;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLField;
import com.liferay.portal.vulcan.graphql.annotation.GraphQLName;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import jakarta.annotation.Generated;

import jakarta.validation.Valid;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
@GraphQLName(
	description = "A snapshot of the active FIPS cryptographic configuration.",
	value = "SecurityInfo"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "A snapshot of the active FIPS cryptographic configuration."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "SecurityInfo")
public class SecurityInfo implements Serializable {

	public static SecurityInfo toDTO(String json) {
		return ObjectMapperUtil.readValue(SecurityInfo.class, json);
	}

	public static SecurityInfo unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(SecurityInfo.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The per-algorithm-family resolver check against the declared provider."
	)
	@Valid
	public AlgorithmFamily[] getAlgorithmFamilies() {
		if (_algorithmFamiliesSupplier != null) {
			algorithmFamilies = _algorithmFamiliesSupplier.get();

			_algorithmFamiliesSupplier = null;
		}

		return algorithmFamilies;
	}

	public void setAlgorithmFamilies(AlgorithmFamily[] algorithmFamilies) {
		this.algorithmFamilies = algorithmFamilies;

		_algorithmFamiliesSupplier = null;
	}

	@JsonIgnore
	public void setAlgorithmFamilies(
		UnsafeSupplier<AlgorithmFamily[], Exception>
			algorithmFamiliesUnsafeSupplier) {

		_algorithmFamiliesSupplier = () -> {
			try {
				return algorithmFamiliesUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(
		description = "The per-algorithm-family resolver check against the declared provider."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected AlgorithmFamily[] algorithmFamilies;

	@JsonIgnore
	private Supplier<AlgorithmFamily[]> _algorithmFamiliesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public FIPSMode getFipsMode() {
		if (_fipsModeSupplier != null) {
			fipsMode = _fipsModeSupplier.get();

			_fipsModeSupplier = null;
		}

		return fipsMode;
	}

	public void setFipsMode(FIPSMode fipsMode) {
		this.fipsMode = fipsMode;

		_fipsModeSupplier = null;
	}

	@JsonIgnore
	public void setFipsMode(
		UnsafeSupplier<FIPSMode, Exception> fipsModeUnsafeSupplier) {

		_fipsModeSupplier = () -> {
			try {
				return fipsModeUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected FIPSMode fipsMode;

	@JsonIgnore
	private Supplier<FIPSMode> _fipsModeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public SecurityProvider getJsseProvider() {
		if (_jsseProviderSupplier != null) {
			jsseProvider = _jsseProviderSupplier.get();

			_jsseProviderSupplier = null;
		}

		return jsseProvider;
	}

	public void setJsseProvider(SecurityProvider jsseProvider) {
		this.jsseProvider = jsseProvider;

		_jsseProviderSupplier = null;
	}

	@JsonIgnore
	public void setJsseProvider(
		UnsafeSupplier<SecurityProvider, Exception>
			jsseProviderUnsafeSupplier) {

		_jsseProviderSupplier = () -> {
			try {
				return jsseProviderUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SecurityProvider jsseProvider;

	@JsonIgnore
	private Supplier<SecurityProvider> _jsseProviderSupplier;

	@io.swagger.v3.oas.annotations.media.Schema
	@Valid
	public SecurityProvider getProvider() {
		if (_providerSupplier != null) {
			provider = _providerSupplier.get();

			_providerSupplier = null;
		}

		return provider;
	}

	public void setProvider(SecurityProvider provider) {
		this.provider = provider;

		_providerSupplier = null;
	}

	@JsonIgnore
	public void setProvider(
		UnsafeSupplier<SecurityProvider, Exception> providerUnsafeSupplier) {

		_providerSupplier = () -> {
			try {
				return providerUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected SecurityProvider provider;

	@JsonIgnore
	private Supplier<SecurityProvider> _providerSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The full security provider order, by name."
	)
	public String[] getProviderOrder() {
		if (_providerOrderSupplier != null) {
			providerOrder = _providerOrderSupplier.get();

			_providerOrderSupplier = null;
		}

		return providerOrder;
	}

	public void setProviderOrder(String[] providerOrder) {
		this.providerOrder = providerOrder;

		_providerOrderSupplier = null;
	}

	@JsonIgnore
	public void setProviderOrder(
		UnsafeSupplier<String[], Exception> providerOrderUnsafeSupplier) {

		_providerOrderSupplier = () -> {
			try {
				return providerOrderUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The full security provider order, by name.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String[] providerOrder;

	@JsonIgnore
	private Supplier<String[]> _providerOrderSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		AlgorithmFamily[] algorithmFamilies = getAlgorithmFamilies();

		if (algorithmFamilies != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"algorithmFamilies\": ");

			sb.append("[");

			for (int i = 0; i < algorithmFamilies.length; i++) {
				sb.append(String.valueOf(algorithmFamilies[i]));

				if ((i + 1) < algorithmFamilies.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		FIPSMode fipsMode = getFipsMode();

		if (fipsMode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fipsMode\": ");

			sb.append(String.valueOf(fipsMode));
		}

		SecurityProvider jsseProvider = getJsseProvider();

		if (jsseProvider != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsseProvider\": ");

			sb.append(String.valueOf(jsseProvider));
		}

		SecurityProvider provider = getProvider();

		if (provider != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"provider\": ");

			sb.append(String.valueOf(provider));
		}

		String[] providerOrder = getProviderOrder();

		if (providerOrder != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"providerOrder\": ");

			sb.append("[");

			for (int i = 0; i < providerOrder.length; i++) {
				sb.append("\"");

				sb.append(_escape(providerOrder[i]));

				sb.append("\"");

				if ((i + 1) < providerOrder.length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.security.fips.rest.dto.v1_0.SecurityInfo",
		name = "x-class-name"
	)
	public String xClassName;

	private static String _escape(Object object) {
		return StringUtil.replace(
			String.valueOf(object), _JSON_ESCAPE_STRINGS[0],
			_JSON_ESCAPE_STRINGS[1]);
	}

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static String _toJSON(Map<String, ?> map) {
		StringBuilder sb = new StringBuilder("{");

		@SuppressWarnings("unchecked")
		Set set = map.entrySet();

		@SuppressWarnings("unchecked")
		Iterator<Map.Entry<String, ?>> iterator = set.iterator();

		while (iterator.hasNext()) {
			Map.Entry<String, ?> entry = iterator.next();

			sb.append("\"");
			sb.append(_escape(entry.getKey()));
			sb.append("\": ");

			Object value = entry.getValue();

			if (_isArray(value)) {
				sb.append("[");

				Object[] valueArray = (Object[])value;

				for (int i = 0; i < valueArray.length; i++) {
					if (valueArray[i] instanceof Map) {
						sb.append(_toJSON((Map<String, ?>)valueArray[i]));
					}
					else if (valueArray[i] instanceof String) {
						sb.append("\"");
						sb.append(valueArray[i]);
						sb.append("\"");
					}
					else {
						sb.append(valueArray[i]);
					}

					if ((i + 1) < valueArray.length) {
						sb.append(", ");
					}
				}

				sb.append("]");
			}
			else if (value instanceof Map) {
				sb.append(_toJSON((Map<String, ?>)value));
			}
			else if (value instanceof String) {
				sb.append("\"");
				sb.append(_escape(value));
				sb.append("\"");
			}
			else {
				sb.append(value);
			}

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static final String[][] _JSON_ESCAPE_STRINGS = {
		{"\\", "\"", "\b", "\f", "\n", "\r", "\t"},
		{"\\\\", "\\\"", "\\b", "\\f", "\\n", "\\r", "\\t"}
	};

	private Map<String, Serializable> _extendedProperties;

}
// LIFERAY-REST-BUILDER-HASH:-2137771434