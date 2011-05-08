package com.grt192.core.controller;

import com.grt192.core.Controller;
import java.util.Hashtable;

/**
 * A <code>MacroController </code> is a state-machine style controller 
 * which manipulates <code>Macro</code>s, atomic execution units
 * @see Macro
 * @author ajc
 */
public abstract class MacroController extends Controller {

    protected static final int DEFAULT_STATE = -1;
    private Hashtable macros; //maps state to Macro
    private int state;        //current state

    public MacroController() {
        macros = new Hashtable();
        state = DEFAULT_STATE;
    }

    /**
     * Manually establish a state while the controller is offline
     */
    public void setState(int state) {
        this.state = state;
        //should halt macro execution and run the macro for this state
    }

    /**
     * Adds a provided <code>Macro</code> to be executed on provided state
     * @param macro 
     * @param state 
     */
    public void addMacro(Macro macro, int state) {
        macros.put(new Integer(state), macro);
    }

    /**
     * Gets the <code>Macro</code>  associated with a provided state
     * @param state state macro runs on
     * @return macro
     */
    public Macro getMacro(int state) {
        return (Macro) macros.get(new Integer(state));
    }

    /**
     * True where state is bound to a <code>Macro</code>
     * @param state
     * @return 
     */
    public boolean isValidState(int state) {
        return macros.containsKey(new Integer(state));
    }

    /**
     * Runs automated <code>Macro</code> execution loop
     */
    public void run() {
        running = true;
        while (running) {
            try {
                step();
            } catch (InvalidMacroStateException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Executes a single <code>Macro</code> bound to our current state,
     * sets new state on completion.
     * 
     * @throws InvalidMacroStateException on unrecognized state, halts automatic
     * execution loop.
     */
    public void step() throws InvalidMacroStateException {
        if (isValidState(state)) {
            //maybe constantly poll the macro for its state?
            //if it changes time to halt and move on?

            //how to halt a macro?
            state = getMacro(state).execute();
        } else {
            //TODO handle this better
            running = false;
            throw new InvalidMacroStateException(state);
        }
    }
}
