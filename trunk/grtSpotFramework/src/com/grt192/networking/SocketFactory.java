package com.grt192.networking;

import com.grt192.networking.spot.RadiogramClient;
import com.grt192.networking.spot.RadiogramServer;

/**
 * Abstracts socket creation for code carryover between platforms
 * @author ajc
 */
public class SocketFactory {

    /**
     * Create a new multiclient server on a specified port
     * @param port to listen for clients on
     * @return
     */
    public static GRTSocket createServer(int port) {
        return new RadiogramServer(port);
    }

    /**
     * Createa a new single client Socket to a specific host and port
     * @param spotSerialNumber labeled on mainboard tab
     * @param port to connect to server with
     * @return
     */
    public static GRTSocket createClient(String spotSerialNumber, int port) {
        return new RadiogramClient(spotSerialNumber, port);
    }
}
