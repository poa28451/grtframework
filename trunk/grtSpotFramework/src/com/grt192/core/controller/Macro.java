
package com.grt192.core.controller;

/**
 * An atomic execution unit associated with a controller/robot state
 * @author ajc
 */
public interface Macro {
    
    /**
     * Execute this macro
     * @return new state of the controller
     */
    public int execute();
    
}
