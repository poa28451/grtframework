
package com.grt192.deploy;

import com.grt192.core.GRTRobot;

/**
 * 
 * @author student
 */
public class MainRobot extends GRTRobot {


    /**
     * Starting point for the robot. Start and initialize robot function
     */
    public void startRobot() {
        System.out.println("-------------------------------------");
        int x = 0;
        while(true){
            try {
                log(x + "");
                x++;
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}
