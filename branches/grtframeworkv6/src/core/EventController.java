/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

/**
 * A controller describes behavior that can be started/stopped. 
 * @author ajc
 */
public abstract class EventController extends GRTLoggedProcess {

    public EventController(String name) {
        super(name);
        running = true; //TODO does this belong
    }

    /**
     * Adds listeners.
     * Requires a call to super.enable.
     */
    protected abstract void addListeners();

    /**
     * Removes listeners
     */
    protected abstract void removeListeners();

    public void enable() {
        if (running) {
            super.enable();
            addListeners();
        }
    }

    public void disable() {
        super.disable();
        removeListeners();
    }
    
}
