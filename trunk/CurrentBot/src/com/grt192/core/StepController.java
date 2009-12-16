/*
 * MVC controller for logically issuing commands to mechanisms
 * based on desire robot function
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.core;

/**
 *
 * @author anand
 */
public abstract class StepController extends Controller {
    public static final int SLEEP_INTERVAL = 50; //ms

    public void run() {
        running = true;
        while(running){
            try{
                act();

                //minimum loop sleep
                sleep(SLEEP_INTERVAL);
            }catch(Exception e){
                //On exception kill this actuator, as it is
                //unsafe to continue operation
                e.printStackTrace();
                stopControl();
            }
        }
    }

    public abstract void act();
}
