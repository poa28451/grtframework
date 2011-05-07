/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.prototyper;

/**
 *
 * @author ajc
 */
public interface PrototypeingListener {
    
    public void hostUp(int id, String address);
    
    public void hostDown(int id, String address);
    
}
