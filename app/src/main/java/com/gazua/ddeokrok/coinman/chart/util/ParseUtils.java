package com.gazua.ddeokrok.coinman.chart.util;

import org.json.JSONException;
import org.json.JSONObject;

public class ParseUtils {

	public static double getDoubleFromString(JSONObject jsonObject, String name) throws NumberFormatException, JSONException {
		return Double.parseDouble(jsonObject.getString(name));
	}
}
