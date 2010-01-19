/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.PotentiometerEvent;
import com.grt192.event.component.PotentiometerListener;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.PIDSource;
import java.util.Vector;


/**
 *
 * @author ryo
 */
public class GRTPotentiometer extends Sensor implements PIDSource{
    public static final double CHANGE_THRESHOLD = .001;

    private AnalogChannel input;
    private Vector potentiometerListeners;

    public GRTPotentiometer(int channel, int pollTime, String id){
        input = new AnalogChannel(channel);
        this.sleepTime = pollTime;
        setState("Value", 2.5);
        potentiometerListeners = new Vector();
        this.id = id;
    }

    public void poll() {
        double previousValue = getState("Value");
        setState("Value", input.getAverageVoltage());
        if(Math.abs(getState("Value") - previousValue) >= CHANGE_THRESHOLD ){
            notifyPotentiometerChange();
        }
        notifyPotentiometerListeners();
    }

    public String toString() {
        return "Potentiometer";
    }

    protected void notifyPotentiometerListeners() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((PotentiometerListener) 
                    potentiometerListeners.elementAt(i)).didReceivePosition(
                                new PotentiometerEvent(
                                    this,
                                    PotentiometerEvent.DEFAULT,
                                    getState("Value")
                                ));
        }
    }

    protected void notifyPotentiometerChange() {
        for (int i = 0; i < potentiometerListeners.size(); i++) {
            ((PotentiometerListener)
                    potentiometerListeners.elementAt(i)).positionChanged(
                                new PotentiometerEvent(
                                    this,
                                    PotentiometerEvent.DEFAULT,
                                    getState("Value")
                                ));
        }
    }

    public double pidGet() {
        return this.input.pidGet();
    }
}
