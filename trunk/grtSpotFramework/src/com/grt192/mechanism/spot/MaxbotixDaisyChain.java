package com.grt192.mechanism.spot;

import com.grt192.core.Mechanism;
import com.grt192.event.SensorChangeListener;
import com.grt192.sensor.spot.GRTSonar;
import com.sun.spot.resources.transducers.IIOPin;

/**
 * A <code>MaxbotixDaisyChain</code> provides an interface for higher order
 * daisy chain configurations
 * 
 * Note: while this class does not directly use the sonars, they are required because:
 * 1. This class doesn't make sense without sonars to chain
 * 2. Future configurations may need direct control of the sensors.
 * 
 * @see GRTSonar
 * @author ajc
 */
public abstract class MaxbotixDaisyChain extends Mechanism {

    private GRTSonar[] sonars;
    private IIOPin rx;

    public MaxbotixDaisyChain() {
    }

    /**
     * Constructs a new MaxbotixDaisyChain
     * @param sonars sonars to range from
     * @param rx range control pin, connects to first sonar's rx pin
     */
    public MaxbotixDaisyChain(GRTSonar[] sonars, IIOPin rx) {
        this.sonars = sonars;
        this.rx = rx;

        for (int i = 0; i < sonars.length; i++) {
            if (!sonars[i].isRunning()) {
                sonars[i].start();
            }
        }

        rx.setAsOutput(true);
        setRxLow();
    }

    /** Start ranging the sonars */
    public abstract void stopRanging();

    /** Stop ranging the sonars */
    public abstract void startRanging();

    /** Sends a 300usec. pulse to the rx pin */
    protected void pulseRx() {
        rx.setHigh();
        rx.setLow();
    }

    /* Sets the rx Pin to a High state */
    protected void setRxHigh() {
        setRx(true);
    }

    /** Sets the rx pin to a Low state */
    protected void setRxLow() {
        setRx(false);
    }

    /** Sets the rx pin to a desired state */
    protected void setRx(boolean high) {
        rx.setHigh(high);
    }

    /** Access a particular sonar */
    public GRTSonar getSonar(int i) {
        return sonars[i];
    }

    /** Adds provided <code>SensorChangeListener</code> to all provided sonars */
    public void addSonarsListener(SensorChangeListener s) {
        for (int i = 0; i < sonars.length; i++) {
            sonars[i].addSensorChangeListener(s);
        }
    }

    /** Removes provided <code>SensorChangeListener</code> from all provided sonars */
    public void removeSonarsListener(SensorChangeListener s) {
        for (int i = 0; i < sonars.length; i++) {
            sonars[i].removeSensorChangeListener(s);
        }
    }
}
