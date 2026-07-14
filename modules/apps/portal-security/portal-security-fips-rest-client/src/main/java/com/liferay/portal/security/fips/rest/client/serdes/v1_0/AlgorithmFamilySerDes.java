/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.serdes.v1_0;

import com.liferay.portal.security.fips.rest.client.dto.v1_0.AlgorithmFamily;
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
public class AlgorithmFamilySerDes {

	public static AlgorithmFamily toDTO(String json) {
		AlgorithmFamilyJSONParser algorithmFamilyJSONParser =
			new AlgorithmFamilyJSONParser();

		return algorithmFamilyJSONParser.parseToDTO(json);
	}

	public static AlgorithmFamily[] toDTOs(String json) {
		AlgorithmFamilyJSONParser algorithmFamilyJSONParser =
			new AlgorithmFamilyJSONParser();

		return algorithmFamilyJSONParser.parseToDTOs(json);
	}

	public static String toJSON(AlgorithmFamily algorithmFamily) {
		if (algorithmFamily == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (algorithmFamily.getDeclaredProvider() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"declaredProvider\": ");

			sb.append("\"");

			sb.append(_escape(algorithmFamily.getDeclaredProvider()));

			sb.append("\"");
		}

		if (algorithmFamily.getFamily() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"family\": ");

			sb.append("\"");

			sb.append(_escape(algorithmFamily.getFamily()));

			sb.append("\"");
		}

		if (algorithmFamily.getMatches() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"matches\": ");

			sb.append(algorithmFamily.getMatches());
		}

		if (algorithmFamily.getResolvedProvider() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"resolvedProvider\": ");

			sb.append("\"");

			sb.append(_escape(algorithmFamily.getResolvedProvider()));

			sb.append("\"");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		AlgorithmFamilyJSONParser algorithmFamilyJSONParser =
			new AlgorithmFamilyJSONParser();

		return algorithmFamilyJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(AlgorithmFamily algorithmFamily) {
		if (algorithmFamily == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (algorithmFamily.getDeclaredProvider() == null) {
			map.put("declaredProvider", null);
		}
		else {
			map.put(
				"declaredProvider",
				String.valueOf(algorithmFamily.getDeclaredProvider()));
		}

		if (algorithmFamily.getFamily() == null) {
			map.put("family", null);
		}
		else {
			map.put("family", String.valueOf(algorithmFamily.getFamily()));
		}

		if (algorithmFamily.getMatches() == null) {
			map.put("matches", null);
		}
		else {
			map.put("matches", String.valueOf(algorithmFamily.getMatches()));
		}

		if (algorithmFamily.getResolvedProvider() == null) {
			map.put("resolvedProvider", null);
		}
		else {
			map.put(
				"resolvedProvider",
				String.valueOf(algorithmFamily.getResolvedProvider()));
		}

		return map;
	}

	public static class AlgorithmFamilyJSONParser
		extends BaseJSONParser<AlgorithmFamily> {

		@Override
		protected AlgorithmFamily createDTO() {
			return new AlgorithmFamily();
		}

		@Override
		protected AlgorithmFamily[] createDTOArray(int size) {
			return new AlgorithmFamily[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "declaredProvider")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "family")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "matches")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "resolvedProvider")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			AlgorithmFamily algorithmFamily, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "declaredProvider")) {
				if (jsonParserFieldValue != null) {
					algorithmFamily.setDeclaredProvider(
						(String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "family")) {
				if (jsonParserFieldValue != null) {
					algorithmFamily.setFamily((String)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "matches")) {
				if (jsonParserFieldValue != null) {
					algorithmFamily.setMatches((Boolean)jsonParserFieldValue);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "resolvedProvider")) {
				if (jsonParserFieldValue != null) {
					algorithmFamily.setResolvedProvider(
						(String)jsonParserFieldValue);
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
// LIFERAY-REST-BUILDER-HASH:1955625090