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
	description = "Resolver check for one algorithm family.",
	value = "AlgorithmFamily"
)
@io.swagger.v3.oas.annotations.media.Schema(
	description = "Resolver check for one algorithm family."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "AlgorithmFamily")
public class AlgorithmFamily implements Serializable {

	public static AlgorithmFamily toDTO(String json) {
		return ObjectMapperUtil.readValue(AlgorithmFamily.class, json);
	}

	public static AlgorithmFamily unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(AlgorithmFamily.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The declared FIPS provider."
	)
	public String getDeclaredProvider() {
		if (_declaredProviderSupplier != null) {
			declaredProvider = _declaredProviderSupplier.get();

			_declaredProviderSupplier = null;
		}

		return declaredProvider;
	}

	public void setDeclaredProvider(String declaredProvider) {
		this.declaredProvider = declaredProvider;

		_declaredProviderSupplier = null;
	}

	@JsonIgnore
	public void setDeclaredProvider(
		UnsafeSupplier<String, Exception> declaredProviderUnsafeSupplier) {

		_declaredProviderSupplier = () -> {
			try {
				return declaredProviderUnsafeSupplier.get();
			}
			catch (RuntimeException runtimeException) {
				throw runtimeException;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		};
	}

	@GraphQLField(description = "The declared FIPS provider.")
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String declaredProvider;

	@JsonIgnore
	private Supplier<String> _declaredProviderSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The algorithm family, for example \"SIGNATURE\" or \"CIPHER\"."
	)
	public String getFamily() {
		if (_familySupplier != null) {
			family = _familySupplier.get();

			_familySupplier = null;
		}

		return family;
	}

	public void setFamily(String family) {
		this.family = family;

		_familySupplier = null;
	}

	@JsonIgnore
	public void setFamily(
		UnsafeSupplier<String, Exception> familyUnsafeSupplier) {

		_familySupplier = () -> {
			try {
				return familyUnsafeSupplier.get();
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
		description = "The algorithm family, for example \"SIGNATURE\" or \"CIPHER\"."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String family;

	@JsonIgnore
	private Supplier<String> _familySupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the resolved provider matches the declared provider."
	)
	public Boolean getMatches() {
		if (_matchesSupplier != null) {
			matches = _matchesSupplier.get();

			_matchesSupplier = null;
		}

		return matches;
	}

	public void setMatches(Boolean matches) {
		this.matches = matches;

		_matchesSupplier = null;
	}

	@JsonIgnore
	public void setMatches(
		UnsafeSupplier<Boolean, Exception> matchesUnsafeSupplier) {

		_matchesSupplier = () -> {
			try {
				return matchesUnsafeSupplier.get();
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
		description = "Whether the resolved provider matches the declared provider."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean matches;

	@JsonIgnore
	private Supplier<Boolean> _matchesSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "The provider that actually resolves a representative algorithm."
	)
	public String getResolvedProvider() {
		if (_resolvedProviderSupplier != null) {
			resolvedProvider = _resolvedProviderSupplier.get();

			_resolvedProviderSupplier = null;
		}

		return resolvedProvider;
	}

	public void setResolvedProvider(String resolvedProvider) {
		this.resolvedProvider = resolvedProvider;

		_resolvedProviderSupplier = null;
	}

	@JsonIgnore
	public void setResolvedProvider(
		UnsafeSupplier<String, Exception> resolvedProviderUnsafeSupplier) {

		_resolvedProviderSupplier = () -> {
			try {
				return resolvedProviderUnsafeSupplier.get();
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
		description = "The provider that actually resolves a representative algorithm."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected String resolvedProvider;

	@JsonIgnore
	private Supplier<String> _resolvedProviderSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		String declaredProvider = getDeclaredProvider();

		if (declaredProvider != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"declaredProvider\": ");

			sb.append("\"");

			sb.append(_escape(declaredProvider));

			sb.append("\"");
		}

		String family = getFamily();

		if (family != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"family\": ");

			sb.append("\"");

			sb.append(_escape(family));

			sb.append("\"");
		}

		Boolean matches = getMatches();

		if (matches != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matches\": ");

			sb.append(matches);
		}

		String resolvedProvider = getResolvedProvider();

		if (resolvedProvider != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"resolvedProvider\": ");

			sb.append("\"");

			sb.append(_escape(resolvedProvider));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.security.fips.rest.dto.v1_0.AlgorithmFamily",
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
// LIFERAY-REST-BUILDER-HASH:-307336433