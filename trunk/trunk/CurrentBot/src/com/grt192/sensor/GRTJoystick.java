/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.ButtonListener;
import com.grt192.event.SensorEvent;
import edu.wpi.first.wpilibj.Joystick;
import java.util.Vector;

/**
 * A driverstation-connected Joystick/Game Controller driver
 * @author Student
 */
public class GRTJoystick extends Sensor {

    public static final int NUM_OF_BUTTONS = 10;
    private Joystick joystick;
    private Vector buttonListeners;

    public GRTJoystick(int channel, int pollTime) {
        joystick = new Joystick(channel);
        setSleepTime(pollTime);
        setState("xValue", 0.0);
        setState("yValue", 0.0);
        setState("zValue", 0.0);
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            setState("Button" + i, FALSE);
        }
        setState("Throttle", 0.0);
        buttonListeners = new Vector();
    }

    public void poll() {
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            String button = "Button" + i;
            double previousState = getState(button);
            setState(button, (joystick.getRawButton(i)) ? TRUE : FALSE);
            notifyButtonListeners(button, previousState);
        }
        setState("xValue", joystick.getX());
        setState("yValue", joystick.getY());
        setState("zValue", joystick.getZ());
        setState("Throttle", joystick.getThrottle());
    }

    public String toString() {
        return "Joystick";
    }

    public Vector getButtonListeners() {
        return buttonListeners;
    }

    public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }

    private void notifyButtonListeners(String button, double previousState) {
        for (int i = 0; i < buttonListeners.size(); i++) {
            if (getState(button) == TRUE && getState(button) != previousState) {
                ((ButtonListener) buttonListeners.elementAt(i)).buttonDown(
                        new SensorEvent(this,
                        SensorEvent.DATA_AVAILABLE,
                        this.state), button);
            } else if (getState(button) != TRUE && getState(button) != previousState) {
                ((ButtonListener) buttonListeners.elementAt(i)).buttonUp(
                        new SensorEvent(this,
                        SensorEvent.DATA_AVAILABLE,
                        this.state),
                        button);
            }
        }
    }
}
