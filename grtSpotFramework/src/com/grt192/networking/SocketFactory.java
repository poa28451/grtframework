package com.grt192.networking;

import com.grt192.spot.networking.RadioClient;
import com.grt192.spot.networking.RadioServer;

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
        return new RadioServer(port,true);
    }

    /**
     * Createa a new single client Socket to a specific host and port
     * @param spotSerialNumber labeled on mainboard tab
     * @param port to connect to server with
     * @return
     */
    public static GRTSocket createClient(String spotSerialNumber, int port) {
        return new RadioClient(spotSerialNumber,port);
    }
}
