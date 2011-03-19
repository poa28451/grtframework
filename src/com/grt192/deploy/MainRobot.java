package com.grt192.deploy;

import com.grt192.core.EventController;
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
        
            new EventController() {
                public void start(){
                    super.start();
                    System.out.println("start called");
                }

//                public void run(){
//                    System.out.println("run called");
//                }

            public void startListening() {
                System.out.println("start listening");
            }

            public void stopListening() {
                System.out.println("stop listening");
            }
        }.start();
        
    }
}
