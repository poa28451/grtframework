/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import edu.wpi.first.wpilibj.DriverStation;
import event.DrivingEvent;
import event.DrivingListener;
import event.XboxJoystickEvent;
import event.XboxJoystickListener;
import mechanism.GRTDriveTrain;
import mechanism.GRTDriverStation;
import mechanism.GRTRobotBase;
import sensor.XBoxJoystick;

/**
 * Xbox linear control
 * @author ajc
 */
public class Driver extends EventController implements DrivingListener {

    private final GRTDriverStation ds;
    private final GRTRobotBase dt;
    private double leftVelocity;
    private double rightVelocity;

    public Driver(String name, GRTRobotBase dt, GRTDriverStation ds) {
        super(name);
        this.dt = dt;
        this.ds = ds;
    }

    protected void startListening() {
        ds.addDrivingListener(this);
    }

    protected void stopListening() {
        ds.removeDrivingListener(this);
    }

    public void driverLeftSpeed(DrivingEvent e) {
        leftVelocity = e.getPercentSpeed();
    }

    public void driverRightSpeed(DrivingEvent e) {
        rightVelocity = e.getPercentSpeed();
    }

}
