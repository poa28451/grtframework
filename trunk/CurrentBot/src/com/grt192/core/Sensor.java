/*
 * Superclass for all specific sensors on a robot.
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.core;

import com.grt192.event.SensorChangeListener;
import com.grt192.event.SensorDataListener;
import com.grt192.event.SensorEvent;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author anand
 */
public abstract class Sensor extends Thread {

    public static final double FALSE = 0.0;
    public static final double TRUE = 1.0;
    protected Hashtable state;
    protected int sleepTime = 50;
    private boolean running;
    private boolean suspended;
    private Vector sensorDataListeners;
    private Vector sensorChangeListeners;


    public Sensor() {
        state = new Hashtable();
        running = false;
        suspended = false;
        sensorDataListeners = new Vector();
        sensorChangeListeners = new Vector();
    }

    public void run() {
        running = true;
        while (running) {
            try {
                if (!suspended) {
                    poll();
                    notifyDataListeners();
                }
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public abstract void poll();

    public void setState(String key, double value) {
        double previousState = getState(key);
        state.put(key, new Double(value));
        notifySensorChangeListeners(key, previousState);
    }

    public void setState(String key, boolean value) {
        if (value) {
            state.put(key, new Double(TRUE));
        } else {
            state.put(key, new Double(FALSE));
        }

    }

    public synchronized double getState(String key) {
        if (state.containsKey(key)) {
            return ((Double) (state.get(key))).doubleValue();
        } else {
            return -1.0;
        }
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public Hashtable getState() {
        return state;
    }

    public void stopSensor() {
        running = false;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void suspend() {
        this.suspended = true;
    }

    public void resume() {
        this.suspended = false;
    }

    public void addSensorDataListener(SensorDataListener s) {
        sensorDataListeners.addElement(s);
    }

    public void removeSensorDataListener(SensorDataListener s) {
        sensorDataListeners.removeElement(s);
    }

    public Vector getSensorDataListeners() {
        return sensorDataListeners;
    }

    protected void notifyDataListeners() {
        for (int i = 0; i < sensorDataListeners.size(); i++) {
            ((SensorDataListener) sensorDataListeners.elementAt(i)).didRecieveData(
                    new SensorEvent(this,
                    SensorEvent.DATA_AVAILABLE,
                    this.state));
        }
    }

    protected void notifySensorChangeListeners(String key, double previousState) {
        for (int i = 0; i < sensorChangeListeners.size(); i++) {
            if (getState(key) != previousState) {
                ((SensorChangeListener) sensorChangeListeners.elementAt(i)).sensorStateChanged(
                        new SensorEvent(this,
                        SensorEvent.DATA_AVAILABLE,
                        this.state),
                        key);
            }
        }
    }

    public void removeSensorChangeListener(SensorChangeListener s) {
        sensorChangeListeners.removeElement(s);
    }

    public Vector getSensorChangeListeners() {
        return sensorChangeListeners;
    }

    public void addSensorChangeListener(SensorChangeListener s) {
        sensorChangeListeners.addElement(s);
    }
}
