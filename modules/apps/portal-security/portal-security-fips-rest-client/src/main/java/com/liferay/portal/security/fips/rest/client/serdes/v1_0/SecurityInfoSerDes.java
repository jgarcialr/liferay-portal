/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.rest.client.serdes.v1_0;

import com.liferay.portal.security.fips.rest.client.dto.v1_0.AlgorithmFamily;
import com.liferay.portal.security.fips.rest.client.dto.v1_0.SecurityInfo;
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
public class SecurityInfoSerDes {

	public static SecurityInfo toDTO(String json) {
		SecurityInfoJSONParser securityInfoJSONParser =
			new SecurityInfoJSONParser();

		return securityInfoJSONParser.parseToDTO(json);
	}

	public static SecurityInfo[] toDTOs(String json) {
		SecurityInfoJSONParser securityInfoJSONParser =
			new SecurityInfoJSONParser();

		return securityInfoJSONParser.parseToDTOs(json);
	}

	public static String toJSON(SecurityInfo securityInfo) {
		if (securityInfo == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder();

		sb.append("{");

		if (securityInfo.getAlgorithmFamilies() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"algorithmFamilies\": ");

			sb.append("[");

			for (int i = 0; i < securityInfo.getAlgorithmFamilies().length;
				 i++) {

				sb.append(
					String.valueOf(securityInfo.getAlgorithmFamilies()[i]));

				if ((i + 1) < securityInfo.getAlgorithmFamilies().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		if (securityInfo.getFipsMode() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"fipsMode\": ");

			sb.append(String.valueOf(securityInfo.getFipsMode()));
		}

		if (securityInfo.getJsseProvider() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"jsseProvider\": ");

			sb.append(String.valueOf(securityInfo.getJsseProvider()));
		}

		if (securityInfo.getProvider() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"provider\": ");

			sb.append(String.valueOf(securityInfo.getProvider()));
		}

		if (securityInfo.getProviderOrder() != null) {
			if (sb.length() > 1) {
				sb.append(", ");
			}

			sb.append("\"providerOrder\": ");

			sb.append("[");

			for (int i = 0; i < securityInfo.getProviderOrder().length; i++) {
				sb.append(_toJSON(securityInfo.getProviderOrder()[i]));

				if ((i + 1) < securityInfo.getProviderOrder().length) {
					sb.append(", ");
				}
			}

			sb.append("]");
		}

		sb.append("}");

		return sb.toString();
	}

	public static Map<String, Object> toMap(String json) {
		SecurityInfoJSONParser securityInfoJSONParser =
			new SecurityInfoJSONParser();

		return securityInfoJSONParser.parseToMap(json);
	}

	public static Map<String, String> toMap(SecurityInfo securityInfo) {
		if (securityInfo == null) {
			return null;
		}

		Map<String, String> map = new TreeMap<>();

		if (securityInfo.getAlgorithmFamilies() == null) {
			map.put("algorithmFamilies", null);
		}
		else {
			map.put(
				"algorithmFamilies",
				String.valueOf(securityInfo.getAlgorithmFamilies()));
		}

		if (securityInfo.getFipsMode() == null) {
			map.put("fipsMode", null);
		}
		else {
			map.put("fipsMode", String.valueOf(securityInfo.getFipsMode()));
		}

		if (securityInfo.getJsseProvider() == null) {
			map.put("jsseProvider", null);
		}
		else {
			map.put(
				"jsseProvider", String.valueOf(securityInfo.getJsseProvider()));
		}

		if (securityInfo.getProvider() == null) {
			map.put("provider", null);
		}
		else {
			map.put("provider", String.valueOf(securityInfo.getProvider()));
		}

		if (securityInfo.getProviderOrder() == null) {
			map.put("providerOrder", null);
		}
		else {
			map.put(
				"providerOrder",
				String.valueOf(securityInfo.getProviderOrder()));
		}

		return map;
	}

	public static class SecurityInfoJSONParser
		extends BaseJSONParser<SecurityInfo> {

		@Override
		protected SecurityInfo createDTO() {
			return new SecurityInfo();
		}

		@Override
		protected SecurityInfo[] createDTOArray(int size) {
			return new SecurityInfo[size];
		}

		@Override
		protected boolean parseMaps(String jsonParserFieldName) {
			if (Objects.equals(jsonParserFieldName, "algorithmFamilies")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "fipsMode")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "jsseProvider")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "provider")) {
				return false;
			}
			else if (Objects.equals(jsonParserFieldName, "providerOrder")) {
				return false;
			}

			return false;
		}

		@Override
		protected void setField(
			SecurityInfo securityInfo, String jsonParserFieldName,
			Object jsonParserFieldValue) {

			if (Objects.equals(jsonParserFieldName, "algorithmFamilies")) {
				if (jsonParserFieldValue != null) {
					Object[] jsonParserFieldValues =
						(Object[])jsonParserFieldValue;

					AlgorithmFamily[] algorithmFamiliesArray =
						new AlgorithmFamily[jsonParserFieldValues.length];

					for (int i = 0; i < algorithmFamiliesArray.length; i++) {
						algorithmFamiliesArray[i] = AlgorithmFamilySerDes.toDTO(
							(String)jsonParserFieldValues[i]);
					}

					securityInfo.setAlgorithmFamilies(algorithmFamiliesArray);
				}
			}
			else if (Objects.equals(jsonParserFieldName, "fipsMode")) {
				if (jsonParserFieldValue != null) {
					securityInfo.setFipsMode(
						FIPSModeSerDes.toDTO((String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "jsseProvider")) {
				if (jsonParserFieldValue != null) {
					securityInfo.setJsseProvider(
						SecurityProviderSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "provider")) {
				if (jsonParserFieldValue != null) {
					securityInfo.setProvider(
						SecurityProviderSerDes.toDTO(
							(String)jsonParserFieldValue));
				}
			}
			else if (Objects.equals(jsonParserFieldName, "providerOrder")) {
				if (jsonParserFieldValue != null) {
					securityInfo.setProviderOrder(
						toStrings((Object[])jsonParserFieldValue));
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
// LIFERAY-REST-BUILDER-HASH:-196237236