package com.grt192.networking;

/**
 * Ports of all server and client services for use with <code>GRTSocket</code>s
 * @author ajc
 */
public interface Ports {

    /** For PacketRouter handshakes */
    public static final int PACKET_PORT = 57;
    /** For RSH: "robot shell" */
    public static final int RSH_PORT = 41;
    /** @deprecated use PACKET_PORT and the PacketRouter */
    public static final int BROADCAST_PORT = 42;
    /** For our logging framework */
    public static final int LOGGER_PORT = 43;
    /** For prototyping service */
    public static final int PROTOTYPE_PORT = 44;
}
