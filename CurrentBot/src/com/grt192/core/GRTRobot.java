package com.grt192.core;

import com.grt192.controller.WatchDogController;
import edu.wpi.first.wpilibj.SimpleRobot;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author anand
 */
public abstract class GRTRobot extends SimpleRobot{
    //Shared objects
    protected Hashtable globals;
    protected Vector autonomousControllers;
    protected Vector teleopControllers;

    private WatchDogController watchDogCtl;

    public GRTRobot(){
        globals = new Hashtable();
        autonomousControllers = new Vector();
        teleopControllers = new Vector();

        watchDogCtl = new WatchDogController(getWatchdog());
        watchDogCtl.start();
        System.out.println("Started GRT Framework");
    }

    public synchronized Hashtable getGlobals() {
        return globals;
    }

    /**
     * Get an object shared between controllers
     * @param key
     * @return value
     */
    public synchronized Object getGlobal(String key) {
        return globals.get(key);
    }

    /**
     * Add an object to be shared between controllers
     * @param key
     * @param value
     */
    public synchronized void putGlobal(String key, Object value){
        globals.put(key, value);
    }

    /**
     * This method is called when the robot enters Autonomous mode
     * if any Teleop controllers are started, they should be stopped
     * then, all autonomous controllers ar started
     */
    public void autonomous() {
        System.out.println("AUTO");
        //Stop teleopcontrollers if started
        for(int i=0; i<teleopControllers.size(); i++){
            Controller c = (Controller) teleopControllers.elementAt(i);
            if(c.isRunning())
                c.stopControl();
        }
        
        //Start
        for(int i=0; i<autonomousControllers.size(); i++){
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if(!c.isRunning())
                c.start();
        }

    }

    /**
     * This method is called when the robot enters Operator Control mode
     * if any Autonomous controllers are started, they should be stopped
     * then, all teleop controllers ar started
     */
    public void operatorControl() {
        //Stop AutonomousControllers if started
        System.out.println("OP");
        for(int i=0; i<autonomousControllers.size(); i++){
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if(c.isRunning())
                c.stopControl();
        }

        //Start
        for(int i=0; i<teleopControllers.size(); i++){
            Controller c = (Controller) teleopControllers.elementAt(i);
            if(!c.isRunning())
                c.start();
        }
    }

    
}
