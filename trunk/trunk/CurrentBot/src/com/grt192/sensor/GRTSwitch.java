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
    private DigitalInput input;

    public GRTSwitch(int channel, int pollTime){
        this.sleepTime = pollTime;
        input = new DigitalInput(channel);
        setState("State", FALSE);
    }

    public void poll() {
        setState("State", input.get() ? TRUE : FALSE);
    }

    public String toString() {
        return "switch";
    }
}
