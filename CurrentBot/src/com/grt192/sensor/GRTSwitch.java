/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * A simple switch utilizing a GPIO port
 * Binary switch
 * @author Bonan
 */
public class GRTSwitch extends Sensor {
    public static final double CLOSED = Sensor.TRUE;
    public static final double OPEN = Sensor.FALSE;
    private DigitalInput input;

    public GRTSwitch(int i, int i0) {
        this(i, i0, "");
    }

    public GRTSwitch(int channel, int pollTime, String id) {
        this.sleepTime = pollTime;
        input = new DigitalInput(channel);
        setState("State", OPEN);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DigitalInput getInput() {
        return input;
    }

    public void setInput(DigitalInput input) {
        this.input = input;
    }

    public void poll() {
        setState("State", input.get() ? CLOSED : OPEN);
    }

    public String toString() {
        return "switch";
    }
}
