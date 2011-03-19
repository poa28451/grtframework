
package com.grt192.spot.rsh;

import com.grt192.radio.GRTSRadioServer;

/**
 * Spot radio RSH server
 * @author ajc
 */
public class GRTSpotRSH extends GRTRSHServer {

    private static class RSHSingleton {
        public static final GRTRSHServer INSTANCE = new GRTSpotRSH();
    }

    public GRTRSHServer getInstance() {
        return RSHSingleton.INSTANCE;
    }

    private GRTSpotRSH() {
        socket = new GRTSRadioServer(RSH_PORT);
    }
}
