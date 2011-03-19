/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.radio;

/**
 *
 * @author ajc
 */
public class ProtectedPortException extends Exception {

    public static final int NUM_PROTECTED_PORTS = 30;

    private int port;

    public ProtectedPortException(int port) {
        this.port = port;
    }

    public String toString() {
        return "Bad port: " + port + " is less than " + NUM_PROTECTED_PORTS ;
    }
}
