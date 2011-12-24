/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import core.EventController;
import edu.wpi.first.wpilibj.DriverStation;
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
public class Driver extends EventController implements XboxJoystickListener {

    private final XBoxJoystick primary;
    private final GRTRobotBase dt;
    
    private double leftVelocity;
    private double rightVelocity;

    public Driver(String name, GRTRobotBase dt, GRTDriverStation ds) {
        super(name);
        this.dt = dt;
        primary = ds.getPrimary();
    }

    protected void startListening() {
        primary.addJoystickListener(this);
    }

    protected void stopListening() {
        primary.removeJoystickListener(this);
    }

    public void leftXAxisMoved(XboxJoystickEvent e) {
    }

    public void leftYAxisMoved(XboxJoystickEvent e) {
        //right Y axis sets the right velocity
        leftVelocity = e.getValue();
        dt.tankDrive(leftVelocity, rightVelocity);
    }

    public void rightXAxisMoved(XboxJoystickEvent e) {
    }

    public void rightYAxisMoved(XboxJoystickEvent e) {
        //right Y axis sets the right velocity
        rightVelocity = e.getValue();
        dt.tankDrive(leftVelocity, rightVelocity);
    }

    public void padMoved(XboxJoystickEvent e) {
    }

    public void triggerMoved(XboxJoystickEvent e) {
    }
}
