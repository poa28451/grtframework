
package com.grt192.spot.networking;

/**
 * PacketTypes for negotiating connection of <code>RadioServer</code> and
 * <code>RadioClient</code>.
 * @see RadioServer
 * @see RadioClient
 * @author ajc
 */
public interface PacketTypes {

    public static final byte PACKET_REQUEST_CONNECTION = 31;
    public static final byte PACKET_RESPOND = 33;
    public static final byte PACKET_CONFIRM = 37;
}
