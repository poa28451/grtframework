package com.grt192.prototyper;

/**
 * A prototyped host represents a host recognized by the Prototyper.
 * @author ajc
 */
public class PrototypedHost {

    /**
     * Constructs a new prototyped host given a prototype
     * @param prototype Prototype, or enumeration, of this host
     * @return 
     */
    static PrototypedHost fromID(int prototype) {
        return new PrototypedHost(null, prototype);
    }
    //max time since being feed for a PrototypedHost to be online
    public static final int MAX_TIME_SINCE_FEED = 5000;
    //Prototype, or enumeration, of this host
    private final int prototype;
    //Host's MAC Address
    private String address;
    //time this host has last been feed
    private long lastFeed;

    /**
     * Construct a new Prototyped Host
     * @param address Hosts's MAC Address
     * @param prototype Prototype, or enumeration, of this host
     */
    private PrototypedHost(String address, int prototype) {
        this.address = address;
        this.prototype = prototype;
    }

    /**
     * Called by the <code>Prototyper</code>, updates this host's address and
     * feeds it.
     * @param address 
     */
    void updateAddress(String address) {
        this.address = address;
        feed();
    }

    /** "Feeds" this host, establishing that it is online */
    void feed() {
        lastFeed = System.currentTimeMillis();
        //if previously down, send event here? perhaps too complicated.
    }

    /** True if the host is online */
    public boolean isUp() {
        return System.currentTimeMillis() - lastFeed < MAX_TIME_SINCE_FEED;
    }

    /** Gets the enumeration assigned to this host */
    public int getPrototype() {
        return prototype;
    }

    /** Gets the MAC Address associated with this host */
    public String getAddress() {
        return isUp() ? getAnAddress() : null;
    }

    /** Gets the last address that had been assigned to this host */
    public String getAnAddress() {
        return address;
    }
}
