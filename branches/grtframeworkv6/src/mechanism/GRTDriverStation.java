/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import core.Sensor;
import event.ButtonEvent;
import event.ButtonListener;
import event.DrivingEvent;
import event.DrivingListener;
import event.DrivingProfileEvent;
import event.DrivingProfileListener;
import event.XboxJoystickEvent;
import event.XboxJoystickListener;
import java.util.Vector;
import sensor.XBoxJoystick;

/**
 * Defines driver interface to robots: including joysticks and live config tools.
 * 
 * @author ajc
 */
public class GRTDriverStation extends Sensor implements XboxJoystickListener, ButtonListener {

    public static final int PROFILE_LINEAR = 0;
    public static final int PROFILE_SQUARED = 1;
    private static IDriverProfile[] CURVES = new IDriverProfile[]{new LinearDrive(), new SquareDrive()};
    private final XBoxJoystick primary;
    private final XBoxJoystick secondary;
    /*
     * maps the profile index to the button that should register it
     * so {3 4} means button 3 will register PROFILE_LINEAR,
     * while button 4 will register PROFILE_SQUARED.
     */
    private final int[] profileButtons;
    private final Vector drivingListeners;
    private final Vector profileListeners;

    /**
     * @param primary 
     * @param secondary 
     */
    public GRTDriverStation(XBoxJoystick primary, XBoxJoystick secondary, int[] profileButtons, String name) {
        super(name);
        this.primary = primary;
        this.secondary = secondary;
        this.profileButtons = profileButtons;


        drivingListeners = new Vector();
        profileListeners = new Vector();

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
        primary.addButtonListener(this);
    }

    protected void stopListening() {
        primary.removeJoystickListener(this);
        primary.removeButtonListener(this);
    }

    public void addDrivingListener(DrivingListener l) {
        drivingListeners.addElement(l);
    }

    public void removeDrivingListener(DrivingListener l) {
        drivingListeners.removeElement(l);
    }

    public void addDrivingProfileListener(DrivingProfileListener l) {
        profileListeners.addElement(l);
    }

    public void removeDrivingProfileListener(DrivingProfileListener l) {
        profileListeners.removeElement(l);
    }

    /*
     * JOYSTICK EVENTS
     */
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

    /*
     * BUTTON EVENTS
     */
    public void buttonPressed(ButtonEvent e) {
    }

    public void buttonReleased(ButtonEvent e) {

        //we receive the button.
        //the button corresponds to an element in the profileButtons list
        //we need to find the index from that array that the button ID is
        int profileID = getIndex(profileButtons, e.getButtonID());
        if (profileID != -1) {//meaning it exists, see #getIndex(int[], int)
            notifyProfileChange(CURVES[profileID]);
        }
    }

    /**
     * 
     * @param array
     * @param value
     * @return
     */
    private static int getIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (value == array[i]) {
                return i;
            }
        }
        return -1;
    }

    private void notifyLeftDriveSpeed(double speed) {
        DrivingEvent ev = new DrivingEvent(this, DrivingEvent.SIDE_LEFT, speed);
        for (int i = 0; i < drivingListeners.size(); i++) {
            ((DrivingListener) drivingListeners.elementAt(i)).driverLeftSpeed(ev);
        }
    }

    private void notifyRightDriveSpeed(double speed) {
        DrivingEvent ev = new DrivingEvent(this, DrivingEvent.SIDE_RIGHT, speed);
        for (int i = 0; i < drivingListeners.size(); i++) {
            ((DrivingListener) drivingListeners.elementAt(i)).driverRightSpeed(ev);
        }
    }

    private void notifyProfileChange(IDriverProfile profile) {
        DrivingProfileEvent e = new DrivingProfileEvent(this, profile);
        for (int i = 0; i < profileListeners.size(); i++) {
            ((DrivingProfileListener) profileListeners.elementAt(i)).drivingProfileChanged(e);

        }
    }
}
