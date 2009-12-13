/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.sensor;

import com.grt192.core.Sensor;
import edu.wpi.first.wpilibj.AnalogChannel;


/**
 *
 * @author ryo
 */
public class GRTPotentiometer extends Sensor {
    private AnalogChannel input;

    public GRTPotentiometer(int channel, int pollTime){
        input = new AnalogChannel(channel);
        this.sleepTime = pollTime;
        setState("State", 2.5);
    }

    public void poll() {
        setState("Value", input.getAverageVoltage());
    }

    public String toString() {
        return "switch";
    }
}
