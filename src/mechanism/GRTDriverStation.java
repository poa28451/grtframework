/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import core.Sensor;
import event.DrivingEvent;
import event.DrivingListener;
import event.XboxJoystickEvent;
import event.XboxJoystickListener;
import java.util.Vector;
import sensor.XBoxJoystick;

/**
 * Defines driver interface to robots: including joysticks and live config tools.
 * 
 * @author ajc
 */
public class GRTDriverStation extends Sensor implements XboxJoystickListener {

    private final XBoxJoystick primary;
    private final XBoxJoystick secondary;
    private final Vector listeners;

    /**
     * @param primary 
     * @param secondary 
     */
    public GRTDriverStation(XBoxJoystick primary, XBoxJoystick secondary, String name) {
        super(name);
        this.primary = primary;
        this.secondary = secondary;

        listeners = new Vector();

        primary.start();
        primary.enable();
        secondary.start();
        secondary.enable();

    }

    public XBoxJoystick getPrimary() {
        return primary;
    }

    public XBoxJoystick getSecondary() {
        return primary;
    }

    protected void startListening() {
        primary.addJoystickListener(this);
    }

    protected void stopListening() {
        primary.removeJoystickListener(this);
    }

    public void addDrivingListener(DrivingListener l) {
        listeners.addElement(l);
    }

    public void removeDrivingListener(DrivingListener l) {
        listeners.removeElement(l);
    }

    public void leftXAxisMoved(XboxJoystickEvent e) {
    }

    public void leftYAxisMoved(XboxJoystickEvent e) {
        if (e.getSource() == primary) {
            notifyLeftDriveSpeed(e.getValue());
        }
    }

    public void rightXAxisMoved(XboxJoystickEvent e) {
    }

    public void rightYAxisMoved(XboxJoystickEvent e) {
        if (e.getSource() == primary) {
            notifyRightDriveSpeed(e.getValue());
        }
    }

    public void padMoved(XboxJoystickEvent e) {
    }

    public void triggerMoved(XboxJoystickEvent e) {
    }

    private void notifyLeftDriveSpeed(double speed) {
        DrivingEvent ev = new DrivingEvent(this, DrivingEvent.SIDE_LEFT, speed);
        for (int i = 0; i < listeners.size(); i++) {
            ((DrivingListener) listeners.elementAt(i)).driverLeftSpeed(ev);
        }
    }

    private void notifyRightDriveSpeed(double speed) {
        DrivingEvent ev = new DrivingEvent(this, DrivingEvent.SIDE_RIGHT, speed);
        for (int i = 0; i < listeners.size(); i++) {
            ((DrivingListener) listeners.elementAt(i)).driverRightSpeed(ev);
        }

    }
}
