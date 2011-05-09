/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.utils;

import com.sun.squawk.util.StringTokenizer;
import java.util.Random;


/**
 * 
 * @author Bonan
 */
public class Util {

    private static Random random = new Random();

    public static double random(){
        return random.nextDouble();
    }

    /**
     *
     * Example output: each line is a successive call to slider()
     [java] [Prototyper]: |===                                                         | 5.0%
     [java] [Prototyper]: |======                                                      | 10.0%
     [java] [Prototyper]: |=========                                                   | 15.0%
     [java] [Prototyper]: |============                                                | 20.0%
     [java] [Prototyper]: |===============                                             | 25.0%
     [java] [Prototyper]: |==================                                          | 30.0%
     [java] [Prototyper]: |=====================                                       | 35.0%
     [java] [Prototyper]: |========================                                    | 40.0%
     [java] [Prototyper]: |===========================                                 | 45.0%
     [java] [Prototyper]: |==============================                              | 50.0%
     [java] [Prototyper]: |=================================                           | 55.00000000000001%
     [java] [Prototyper]: |====================================                        | 60.0%
     [java] [Prototyper]: |=======================================                     | 65.0%
     [java] [Prototyper]: |==========================================                  | 70.0%
     [java] [Prototyper]: |=============================================               | 75.0%
     [java] [Prototyper]: |================================================            | 80.0%
     [java] [Prototyper]: |===================================================         | 85.0%
     [java] [Prototyper]: |======================================================      | 90.0%
     [java] [Prototyper]: |=========================================================   | 95.0%

     * @param percent
     * @return
     */
    public static String slider(double percent) {
        final int WIDTH = 60;
        String s = "|";
        for (int i = 0; i < WIDTH; i++) {
            if (WIDTH * percent > i) {
                s += "=";
            } else {
                s += " ";
            }
        }
        s += "| " + percent * 100 + "%";
        return s;
    }

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
