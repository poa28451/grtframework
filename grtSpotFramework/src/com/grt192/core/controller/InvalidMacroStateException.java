package com.grt192.core.controller;

/**
 * InvalidMacroStateException is an exception thrown when a Macro returns
 * a state not recognized by its <code>MacroController</code>.
 * @author ajc
 */
public class InvalidMacroStateException extends Exception {

    private final int state;

    /**
     * 
     * @param state state that is not recognized by the <code>MacroController</code>
     */
    public InvalidMacroStateException(int state) {
        this.state = state;
    }

    public String toString() {
        return "InvalidMacroState:" + state;
    }
}
