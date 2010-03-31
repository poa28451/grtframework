/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.EncoderEvent;
import com.grt192.event.component.EncoderListener;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import java.util.Vector;

/**
 *
 * @author grtstudent, code for an Encoder with one channel.
 */
public class GRTSingleChannelEncoder extends Sensor{
    private DigitalInput rotaryEncoder;
    private Vector encoderListeners;
    public static final double DIST_PER_PULSE = Math.PI * 16/(360 * 4 *12);
    private boolean previousValue;
    private int count = 0;
        public GRTSingleChannelEncoder(int channela, int pollTime, String id){
        rotaryEncoder = new DigitalInput(channela);
        setSleepTime(pollTime);
        encoderListeners = new Vector();
        this.id = id;
    }

    public void poll() {
        boolean currentValue = rotaryEncoder.get();
        System.out.println("Switch: " + currentValue);
        if (currentValue != previousValue){
            count++;
            System.out.println("Added count");
        }
        setState("Count", count);
        previousValue = currentValue;
    }


}
