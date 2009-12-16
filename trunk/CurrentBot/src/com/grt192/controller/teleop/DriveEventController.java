package com.grt192.controller.teleop;

import com.grt192.core.EventController;
import com.grt192.event.component.JoystickEvent;
import com.grt192.event.component.JoystickListener;
import com.grt192.mechanism.GRTDriverStation;
import com.grt192.mechanism.GRTRobotBase;
import com.grt192.sensor.GRTJoystick;

/**
 *
 * @author anand
 */
public class DriveEventController extends EventController
        implements JoystickListener {

    public DriveEventController(GRTRobotBase rb, GRTDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        ((GRTJoystick) ds.getSensor("leftJoystick")).addJoystickListener(this);
        ((GRTJoystick) ds.getSensor("rightJoystick")).addJoystickListener(this);
        addMechanism("RobotBase", rb);
    }

    public void yAxisMoved(JoystickEvent e) {
        GRTRobotBase base = ((GRTRobotBase) getMechanism("RobotBase"));

        if (e.getSource().getId().equals("left")) {
            base.tankDrive(e.getValue(), base.getRightSpeed());

        } else if (e.getSource().getId().equals("right")) {
            base.tankDrive(base.getLeftSpeed(), e.getValue());
        }
    }

    public void xAxisMoved(JoystickEvent e) {
    }

    public void zAxisMoved(JoystickEvent e) {
    }

    public void throttleMoved(JoystickEvent e) {
    }
}
