/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.utils;

import java.util.Hashtable;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * 
 * @author Bonan
 */
public class Util {

	public static double roundValue(double val) {
		return round(val / .025) * .025;
	}

	public static int round(double x) {
		return ((int) x) + (x % 1 < .5 ? 0 : 1);
	}

	public static double distance(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	public static JSONObject toJSONObject(Hashtable hashtable) throws JSONException {
		JSONObject json = new JSONObject();
		while (hashtable.keys().hasMoreElements()) {
			String key = ((String) hashtable.keys().nextElement());
			json.accumulate(key, hashtable.get(key));
		}
		return json;

	}
}
