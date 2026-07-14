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
@GraphQLName(description = "The FIPS operating state.", value = "FIPSMode")
@io.swagger.v3.oas.annotations.media.Schema(
	description = "The FIPS operating state."
)
@JsonFilter("Liferay.Vulcan")
@XmlRootElement(name = "FIPSMode")
public class FIPSMode implements Serializable {

	public static FIPSMode toDTO(String json) {
		return ObjectMapperUtil.readValue(FIPSMode.class, json);
	}

	public static FIPSMode unsafeToDTO(String json) {
		return ObjectMapperUtil.unsafeReadValue(FIPSMode.class, json);
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether the validated provider is in approved-only mode and passed its integrity self-test."
	)
	public Boolean getApprovedMode() {
		if (_approvedModeSupplier != null) {
			approvedMode = _approvedModeSupplier.get();

			_approvedModeSupplier = null;
		}

		return approvedMode;
	}

	public void setApprovedMode(Boolean approvedMode) {
		this.approvedMode = approvedMode;

		_approvedModeSupplier = null;
	}

	@JsonIgnore
	public void setApprovedMode(
		UnsafeSupplier<Boolean, Exception> approvedModeUnsafeSupplier) {

		_approvedModeSupplier = () -> {
			try {
				return approvedModeUnsafeSupplier.get();
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
		description = "Whether the validated provider is in approved-only mode and passed its integrity self-test."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean approvedMode;

	@JsonIgnore
	private Supplier<Boolean> _approvedModeSupplier;

	@io.swagger.v3.oas.annotations.media.Schema(
		description = "Whether FIPS mode is enabled through configuration."
	)
	public Boolean getEnabled() {
		if (_enabledSupplier != null) {
			enabled = _enabledSupplier.get();

			_enabledSupplier = null;
		}

		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;

		_enabledSupplier = null;
	}

	@JsonIgnore
	public void setEnabled(
		UnsafeSupplier<Boolean, Exception> enabledUnsafeSupplier) {

		_enabledSupplier = () -> {
			try {
				return enabledUnsafeSupplier.get();
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
		description = "Whether FIPS mode is enabled through configuration."
	)
	@JsonProperty(access = JsonProperty.Access.READ_WRITE)
	protected Boolean enabled;

	@JsonIgnore
	private Supplier<Boolean> _enabledSupplier;

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
		StringBundler sb = new StringBundler();

		sb.append("{");

		Boolean approvedMode = getApprovedMode();

		if (approvedMode != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"approvedMode\": ");

			sb.append(approvedMode);
		}

		Boolean enabled = getEnabled();

		if (enabled != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(enabled);
		}

		sb.append("}");

		return sb.toString();
	}

	@io.swagger.v3.oas.annotations.media.Schema(
		accessMode = io.swagger.v3.oas.annotations.media.Schema.AccessMode.READ_ONLY,
		defaultValue = "com.liferay.portal.security.fips.rest.dto.v1_0.FIPSMode",
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
// LIFERAY-REST-BUILDER-HASH:853224357