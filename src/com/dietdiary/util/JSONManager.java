package com.dietdiary.util;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONManager {
	public static synchronized JSONObject parse(String jsonString) {
		JSONParser parser = new JSONParser();
		JSONObject obj = null;
		try {
			obj = (JSONObject) parser.parse(jsonString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static List<JSONObject> getJSONArrayToList(Object obj) {
		ArrayList<JSONObject> result = new ArrayList<>();

		if (obj instanceof JSONArray) {
			JSONArray array = (JSONArray)obj;
			for (int i = 0; i < array.size(); i++) {
				result.add((JSONObject)array.get(i));
			}
		}
		return result;
	}

//	public static void main(String[] args) {
//
//		StringBuffer sql = new StringBuffer();
//		// JSON 표기법은 사실 Map이다1
//		sql.append("{");
//		sql.append("	\"name\":\"철수\",");
//		sql.append("	\"age\": 29,");
//		sql.append("	\"hasPet\": true,");
//		sql.append("	\"pets\": [");
//		sql.append("		{");
//		sql.append("			\"name\":\"doggy\",");
//		sql.append("			\"type\":\"dog\"");
//		sql.append("		},");
//		sql.append("		{");
//		sql.append("			\"name\":\"catty\",");
//		sql.append("			\"type\":\"cat\"");
//		sql.append("		}");
//		sql.append("	]");
//		sql.append("}");
//		JSONObject obj = JSONManager.parse(sql.toString());
//		List<JSONObject> list = JSONManager.getJSONArrayToList(obj.get("pets"));
//	}
}
