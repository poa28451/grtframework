/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.mechanism;

import com.grt192.core.Mechanism;
import com.grt192.core.Sensor;
import com.grt192.event.SensorChangeListener;
import com.grt192.event.SensorEvent;
import com.grt192.sensor.GRTJoystick;

/**
 * A standard KwikByte Driverstation with two joysticks connected
 * Contains basic button states and throttle access
 * @author Student
 */
public class GRTDriverStation extends Mechanism{

    public GRTDriverStation(GRTJoystick left, GRTJoystick right) {
        left.start();
        right.start();
        addSensor("leftJoystick", left);
        addSensor("rightJoystick", right);
    }

    public GRTDriverStation(int left, int right) {
        addSensor("leftJoystick", new GRTJoystick(left, 50));
        getSensor("leftJoystick").start();
        addSensor("rightJoystick", new GRTJoystick(right, 50));
        getSensor("rightJoystick").start();
    }

    public boolean getLeftButton(int button) {
        return getSensor("leftJoystick").getState("Button" + button)
                == Sensor.TRUE;
    }

    public boolean getRightButton(int button) {
        return getSensor("leftJoystick").getState("Button" + button)
                == Sensor.TRUE;
    }

    public double getXLeftJoystick() {
        return getSensor("leftJoystick").getState("xValue");
    }

    public double getYLeftJoystick() {
        return getSensor("leftJoystick").getState("yValue");
    }

    public double getZLeftJoystick() {
        return getSensor("leftJoystick").getState("zValue");
    }

    public double getXRightJoystick() {
        return getSensor("rightJoystick").getState("xValue");
    }
       
    public double getYRightJoystick() {
        return getSensor("rightJoystick").getState("yValue");
    }

    public double getZRightJoystick() {
        return getSensor("rightJoystick").getState("zValue");
    }
    
    public double getLeftThrottle() {
        return getSensor("rightJoystick").getState("Throttle");
    }

    public double getRightThrottle() {
        return getSensor("rightJoystick").getState("Throttle");
    }

    public String toString() {
        return "Driver Station with two joysticks";
    }


}
