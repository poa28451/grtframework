/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.utils;

import com.sun.squawk.util.StringTokenizer;
import java.util.Enumeration;
import java.util.Hashtable;

import org.json.me.JSONException;
import org.json.me.JSONObject;

/**
 * 
 * @author Bonan
 */
public class Util {

    public static double doubleValue(String s) {
        return Double.valueOf(s).doubleValue();
    }

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
        Enumeration e = hashtable.keys();
        while (e.hasMoreElements()) {
            String key = ((String) e.nextElement());
            json.accumulate(key, hashtable.get(key));
        }
        return json;

    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }



    public static String[] separateString(String msg) {
        StringTokenizer stk = new StringTokenizer(msg, " ");
        String[] result = new String[stk.countTokens()];
        for (int i = 0; stk.hasMoreTokens(); i++) {
            result[i] = stk.nextToken();
        }
        return result;
    }

    public static String arraytoString(String[] s) {
        String retu = "[";
        for (int i = 0; i < s.length; i++) {
            retu += s[i] + ", ";
        }
        retu += " ]";
        return retu;
    }
}
