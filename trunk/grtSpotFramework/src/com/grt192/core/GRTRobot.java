package com.grt192.core;

import java.util.Hashtable;
import java.util.Vector;

import com.grt192.event.GlobalEvent;
import com.grt192.event.GlobalListener;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * 
 * @author anand, ajc
 */
public abstract class GRTRobot extends MIDlet {
    // Shared objects

    protected Hashtable globals;
    protected Vector autonomousControllers;
    protected Vector teleopControllers;
    protected Vector globalListeners;
    protected static GRTRobot instance;
    private GRTLogger logger;

    public GRTRobot() {
        this(true);
    }

    public GRTRobot(boolean useLogger){
        instance = this;
        autonomousControllers = new Vector();
        teleopControllers = new Vector();

        globals = new Hashtable();
        globalListeners = new Vector();

        if(useLogger){
            logger = GRTLogger.getInstance();
            System.out.println("Logger:              \tREADY");
        }

        System.out.println("grtSpotFramework:       \tREADY");

        startRobot();
    }

    public abstract void startRobot();

    public synchronized Hashtable getGlobals() {
        return globals;
    }

    /**
     * Get an object shared between controllers
     *
     * @param key
     * @return value
     */
    public synchronized Object getGlobal(String key) {
        return globals.get(key);
    }

    /**
     * Add an object to be shared between controllers
     *
     * @param key
     * @param value
     */
    public synchronized void putGlobal(String key, Object value) {
        globals.put(key, value);
        notifyGlobalListeners(key);
    }

    /**
     * This method is called when the robot enters Autonomous mode if any Teleop
     * controllers are started, they should be stopped then, all autonomous
     * controllers are started
     */
    public void autonomous() {
        logger.write("GRTRobot", "AUTO");
        // Stop teleopcontrollers if started
        for (int i = 0; i < teleopControllers.size(); i++) {
            Controller c = (Controller) teleopControllers.elementAt(i);
            if (c.isRunning()) {
                c.stopControl();
            }
        }

        // Start
        for (int i = 0; i < autonomousControllers.size(); i++) {
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if (!c.isRunning()) {
                c.start();
            }
        }

    }

    /**
     * This method is called when the robot enters Operator Control mode if any
     * Autonomous controllers are started, they should be stopped then, all
     * teleop controllers are started
     */
    public void operatorControl() {
        // Stop AutonomousControllers if started
        logger.write("GRTRobot", "OP");
        for (int i = 0; i < autonomousControllers.size(); i++) {
            Controller c = (Controller) autonomousControllers.elementAt(i);
            if (c.isRunning()) {
                c.stopControl();
            }
        }

        // Start
        for (int i = 0; i < teleopControllers.size(); i++) {
            Controller c = (Controller) teleopControllers.elementAt(i);
            if (!c.isRunning()) {
                c.start();
            }
        }
    }

    protected void notifyGlobalListeners(String key) {
        for (int i = 0; i < globalListeners.size(); i++) {
            ((GlobalListener) globalListeners.elementAt(i)).globalChanged(new GlobalEvent(GlobalEvent.DEFAULT, key,
                    globals));
        }
    }

    public void addGlobalListener(GlobalListener gl) {
        this.globalListeners.addElement(gl);
    }

    public void removeGlobalListener(GlobalListener gl) {
        this.globalListeners.removeElement(gl);
    }

    public static GRTRobot getInstance() {
        return instance;
    }

    public GRTLogger getLogger() {
        return logger;
    }

    protected void log(String message) {
        logger.write("GRTRobot", message);
    }
    protected void log(String type,String message) {
        logger.write(type,message);
    }
    protected void logVar(String name,String message) {
        logger.write("(var)"+name, message);
    }
    protected void logVar(String string, double i) {
        logVar(string,Double.toString(i));
    }

    /**
     * Starting point for the applications. Starts the OtaServer and then runs
     * the robot.
     * @throws javax.microedition.midlet.MIDletStateChangeException
     */
    protected final void startApp() throws MIDletStateChangeException {
//        boolean errorOnExit = false;

//        Watchdog.getInstance().setExpiration(0.1);
//        Watchdog.getInstance().setEnabled(false);

//        try {
//            this.startCompetition();
//        } catch (Throwable t) {
//            t.printStackTrace();
//            errorOnExit = true;
//        } finally {
//            if (errorOnExit) {
//                System.err.println("---> The startCompetition() method (or methods called by it) should have handled the exception above.");
//            } else {
//                System.err.println("---> Unexpected return from startCompetition() method.");
//            }
//        }
    }

    /**
     * Pauses the application
     */
    protected final void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     *
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true when this method is called, the MIDlet must
     *    cleanup and release all resources. If false the MIDlet may throw
     *    MIDletStateChangeException  to indicate it does not want to be destroyed
     *    at this time.
     * @throws MIDletStateChangeException if there is an exception in terminating the midlet
     */
    protected final void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
