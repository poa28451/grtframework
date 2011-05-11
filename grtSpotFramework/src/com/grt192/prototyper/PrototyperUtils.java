package com.grt192.prototyper;

import com.grt192.actuator.spot.GRTDemoLED;
import com.grt192.core.GRTObject;
import com.grt192.mechanism.spot.SLight;
import com.grt192.utils.Util;

/**
 * Misc. Utilities for the Prototyper
 * @author Andrew Chen <andrewtheannihilator@gmail.com>
 */
public class PrototyperUtils extends GRTObject {

    /** Time between polls for if a host is up */
    public static final int WAIT_POLLTIME = 250;

    /**
     * Safely gets the address of a host, and waits for the host to come 
     * online if is offline 
     * @param prototype desired host's known prototype
     * @param blinker light for effects
     */
    public static String getAddress(int prototype, SLight blinker) {
        if (blinker != null) {
            blinker.rawColor(GRTDemoLED.Color.RED);
        }
        String host = PrototyperFactory.getPrototyper().getHost(prototype).getAddress();
        while (host == null) {
            host = PrototyperFactory.getPrototyper().getHost(prototype).getAddress();
            if (blinker != null) {
                blinker.blinkToBlack();
            }
            Util.sleep(WAIT_POLLTIME);
        }
        if (blinker != null) {
            blinker.rawColor(GRTDemoLED.Color.GREEN);
        }
        return host;
    }

    /**
     * Safely gets the address of a host, and waits for the host to come 
     * online if is offline
     * @param prototype desired host's known prototype
     */
    public static String getAddress(int prototype) {
        return getAddress(prototype, null);
    }
}
