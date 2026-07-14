/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.serdes.v1_0;

import com.liferay.portal.security.fips.rest.client.dto.v1_0.FIPSMode;
import com.liferay.portal.security.fips.rest.client.json.BaseJSONParser;

import jakarta.annotation.Generated;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Jorge García Jiménez
 * @generated
 */
@Generated("")
public class FIPSModeSerDes {

	public static FIPSMode toDTO(String json) {
		FIPSModeJSONParser fipsModeJSONParser = new FIPSModeJSONParser();

		return fipsModeJSONParser.parseToDTO(json);
	}

	public static FIPSMode[] toDTOs(String json) {
		FIPSModeJSONParser fipsModeJSONParser = new FIPSModeJSONParser();

		return fipsModeJSONParser.parseToDTOs(json);
	}

	public static String toJSON(FIPSMode fipsMode) {
		if (fipsMode == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (fipsMode.getApprovedMode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"approvedMode\": ");

			sb.append(fipsMode.getApprovedMode());
		}

		if (fipsMode.getEnabled() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"enabled\": ");

			sb.append(fipsMode.getEnabled());
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		FIPSModeJSONParser fipsModeJSONParser = new FIPSModeJSONParser();

		return fipsModeJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(FIPSMode fipsMode) {
		if (fipsMode == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (fipsMode.getApprovedMode() == null) {
			map.put("approvedMode", null);
		}
		else {
			map.put("approvedMode", String.valueOf(fipsMode.getApprovedMode()));
		}

		if (fipsMode.getEnabled() == null) {
			map.put("enabled", null);
		}
		else {
			map.put("enabled", String.valueOf(fipsMode.getEnabled()));
		}

		return map;
	}

	public static class FIPSModeJSONParser extends BaseJSONParser<FIPSMode> {

		@Override
		protected FIPSMode createDTO() {
			return new FIPSMode();
		}

		@Override
		protected FIPSMode[] createDTOArray(int size) {
			return new FIPSMode[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "approvedMode")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "enabled")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			FIPSMode fipsMode, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "approvedMode")) {
				if (jsonParserFieldValue != null) {
					fipsMode.setApprovedMode((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "enabled")) {
				if (jsonParserFieldValue != null) {
					fipsMode.setEnabled((Boolean)jsonParserFieldValue);
				}
			}
		}

	}

	private static String _escape(Object object) {
		String string = String.valueOf(object);

		for (String[] strings : BaseJSONParser.JSON_ESCAPE_STRINGS) {
			string = string.replace(strings[0], strings[1]);
		}

		return string;
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
			sb.append(entry.getKey());
			sb.append("\": ");

			Object value = entry.getValue();

			sb.append(_toJSON(value));

			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}

		sb.append("}");

		return sb.toString();
	}

	private static String _toJSON(Object value) {
		if (value == null) {
			return "null";
		}

		if (value instanceof Map) {
			return _toJSON((Map)value);
		}

		Class<?> clazz = value.getClass();

		if (clazz.isArray()) {
			StringBuilder sb = new StringBuilder("[");

			Object[] values = (Object[])value;

			for (int i = 0; i < values.length; i++) {
				sb.append(_toJSON(values[i]));

				if ((i + 1) < values.length) {
					sb.append(", ");
				}
			}

			sb.append("]");

			return sb.toString();
		}

		if (value instanceof String) {
			return "\"" + _escape(value) + "\"";
		}

		return String.valueOf(value);
	}

}
// LIFERAY-REST-BUILDER-HASH:-992450011