/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Solenoid;

/**
 *
 * @author Student
 */
public class GRTSolenoid extends Actuator{
    private Solenoid solenoid;
    public static final double ON = 1.0;
    public static final double OFF = 0.0;
    
    public GRTSolenoid(int channel) {
        solenoid = new Solenoid(channel);
    }

    public void executeCommand(Command c) {
        if(c.getValue() == ON) {
            solenoid.set(true);
        } else if(c.getValue() == OFF) {
            solenoid.set(false);
        }
    }

    protected void halt() {
        solenoid.set(false);
    }

    public String toString() {
        return "Solenoid";
    }
}
