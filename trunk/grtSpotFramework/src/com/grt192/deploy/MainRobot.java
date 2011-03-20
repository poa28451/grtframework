package com.grt192.deploy;

import com.grt192.spot.actuator.GRTDemoLED;
import com.grt192.core.GRTRobot;
import com.sun.spot.service.BootloaderListenerService;

/**
 * This is a bare version of grtframework + WPI Libraries.
 * @author ajc
 */
public class MainRobot extends GRTRobot {

    public void startRobot() {
        BootloaderListenerService.getInstance().start();
        log("StartRobot");

        GRTDemoLED overallstatus = new GRTDemoLED(0);
        overallstatus.start();
        overallstatus.enqueueCommand(GRTDemoLED.Color.ORANGE);

    }
}
