/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.Joystick;
import event.ButtonEvent;
import event.ButtonListener;
import event.XboxJoystickEvent;
import event.XboxJoystickListener;
import java.util.Vector;

/**
 * 
 * @author ajc
 */
public class XBoxJoystick extends PollingSensor {

    /**
     * Keys of data
     */
    public static final int BUTTON_0 = 0;
    public static final int BUTTON_1 = 1;
    public static final int BUTTON_2 = 2;
    public static final int BUTTON_3 = 3;
    public static final int BUTTON_4 = 4;
    public static final int BUTTON_5 = 5;
    public static final int BUTTON_6 = 6;
    public static final int BUTTON_7 = 7;
    public static final int BUTTON_8 = 8;
    public static final int BUTTON_9 = 9;
    public static final int LEFT_X = 10;
    public static final int LEFT_Y = 11;
    public static final int RIGHT_X = 12;
    public static final int RIGHT_Y = 13;
    public static final int JOYSTICK_ANGLE = 14;
    public static final int TRIGGER = 15;
    public static final int PAD = 16;
    
    private static final int NUM_DATA = 17;
    private static final int NUM_OF_BUTTONS = 10;
    public static final double PRESSED = TRUE;
    public static final double RELEASED = FALSE;
    private final Joystick joystick;
    private final Vector buttonListeners;
    private final Vector joystickListeners;

    public XBoxJoystick(int channel, int pollTime, String name) {
        super(name, pollTime, NUM_DATA);
        joystick = new Joystick(channel);
        
        buttonListeners = new Vector();
        joystickListeners = new Vector();
    }

    protected void poll() {
        for (int i = 0; i < NUM_OF_BUTTONS; i++) {
            //if we measure true, this indicates pressed state
            setState(i, joystick.getRawButton(i) ? PRESSED : RELEASED);
        }
        setState(LEFT_X, joystick.getX());
        setState(LEFT_Y, joystick.getY());
        setState(RIGHT_X, joystick.getRawAxis(4));
        setState(RIGHT_Y, joystick.getRawAxis(5));
        setState(JOYSTICK_ANGLE, joystick.getDirectionRadians());
        setState(TRIGGER, joystick.getZ());
        setState(PAD, joystick.getRawAxis(6));
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        if (id < NUM_OF_BUTTONS) {
            //ID maps directly to button ID
            ButtonEvent e = new ButtonEvent(this, id, newDatum == PRESSED);
            if (newDatum == PRESSED) { //true
                for (int i = 0; i < buttonListeners.size(); i++) {
                    ((ButtonListener) buttonListeners.elementAt(i)).buttonPressed(e);
                }
            } else {
                for (int i = 0; i < buttonListeners.size(); i++) {
                    ((ButtonListener) buttonListeners.elementAt(i)).buttonReleased(e);
                }
            }

        } else { //we are now a joystick
            //only reach here if not a button
            XboxJoystickEvent e = new XboxJoystickEvent(this, id, newDatum);

            //call various events based on which datum we are
            switch (id) {
                case LEFT_X: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).leftXAxisMoved(e);
                    }

                }
                case LEFT_Y: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).leftYAxisMoved(e);
                    }
                    break;
                }
                case RIGHT_X: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).rightXAxisMoved(e);
                    }
                    break;
                }
                case RIGHT_Y: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).rightYAxisMoved(e);
                    }
                    break;
                }
                case JOYSTICK_ANGLE: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).leftXAxisMoved(e);
                    }
                    break;
                }
                case TRIGGER: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).triggerMoved(e);
                    }
                    break;
                }
                case PAD: {
                    for (int i = 0; i < joystickListeners.size(); i++) {
                        ((XboxJoystickListener) joystickListeners.elementAt(i)).padMoved(e);
                    }
                    break;
                }

            }
        }


    }

    public void addButtonListener(ButtonListener b) {
        buttonListeners.addElement(b);
    }

    public void removeButtonListener(ButtonListener b) {
        buttonListeners.removeElement(b);
    }

    public void addJoystickListener(XboxJoystickListener l) {
        joystickListeners.addElement(l);
    }

    public void removeJoystickListener(XboxJoystickListener l) {
        joystickListeners.removeElement(l);
    }
}
