package com.grt192.prototyper;

import com.grt192.core.GRTObject;

/**
 * 
 * @author ajc
 */
public class PrototypedHost extends GRTObject {

    static PrototypedHost fromID(int prototype) {
        return new PrototypedHost(null, prototype);
    }
    
    public static final int MAX_TIME_SINCE_FEED = 2000;
    private String address;
    private final int prototype;
    private long lastFeed;

    private PrototypedHost(String address, int prototype) {
        this.address = address;
        this.prototype = prototype;
    }

    void updateAddress(String address) {
        this.address = address;
        feed();
    }

    void feed() {
        lastFeed = System.currentTimeMillis();
        //if previously down, send event here? perhaps too complicated.
    }

    public boolean isUp() {
        return getAddress() != null;
    }

    public int getPrototype() {
        return prototype;
    }

    public String getAddress() {
        return System.currentTimeMillis() - lastFeed < MAX_TIME_SINCE_FEED ? address : null;
    }
    
    public String getAnAddress(){
        return address;
    }
}
