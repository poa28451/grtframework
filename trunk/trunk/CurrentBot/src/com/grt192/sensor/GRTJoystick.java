/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import edu.wpi.first.wpilibj.Joystick;

/**
 * A driverstation-connected Joystick/Game Controller driver
 * @author Student
 */
public class GRTJoystick extends Sensor {

    public static final int NUM_OF_BUTTONS = 10;
    private Joystick joystick;

    public GRTJoystick(int channel, int pollTime) {
        joystick = new Joystick(channel);
        setSleepTime(pollTime);
        setState("xValue", 0.0);
        setState("yValue", 0.0);
        setState("zValue", 0.0);
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            setState("Button"+ i, FALSE);
        }
        setState("Throttle", 0.0);
    }

    public void poll() {
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            setState("Button"+ i, (joystick.getRawButton(i))? TRUE:FALSE);
        }
        setState("xValue", joystick.getX());
        setState("yValue", joystick.getY());
        setState("zValue", joystick.getZ());
        setState("Throttle", joystick.getThrottle());
    }

    public String toString() {
        return "Joystick";
    }
}
