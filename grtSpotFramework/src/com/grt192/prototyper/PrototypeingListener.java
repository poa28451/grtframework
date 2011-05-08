package com.grt192.prototyper;

/**
 * A PrototypingListener listens to a <code>PrototypeSensor</code> for changes
 * of prototypedHost online status
 * @author ajc
 */
public interface PrototypeingListener {

    /** Called when a prototyped host goes online */
    public void hostUp(int id, String address);

    /** Called when a prototyped host goes offline */
    public void hostDown(int id, String address);
}
