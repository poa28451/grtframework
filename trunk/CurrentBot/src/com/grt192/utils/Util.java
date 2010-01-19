/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.utils;

/**
 *
 * @author GRTstudent
 */
public class Util {
    
    public static double roundValue(double val) {
        return round(val / .025) * .025;
    }

    private static double round(double x) {
        double decimal = x - (int) x;
        if (decimal >= .5) {
            return (int) x + 1;
        } else {
            return (int) x;
        }
    }
}
