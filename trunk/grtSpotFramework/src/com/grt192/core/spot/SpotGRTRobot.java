package com.grt192.core.spot;

import com.grt192.core.GRTRobot;
import com.sun.spot.service.BootloaderListenerService;

/**
 * Adds SPOT Specific automatic functions
 * @author ajc
 */
public abstract class SpotGRTRobot extends GRTRobot {

    public SpotGRTRobot() {
        BootloaderListenerService.getInstance().start();
    }
}
