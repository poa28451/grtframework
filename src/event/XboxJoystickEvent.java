/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package event;

import sensor.XBoxJoystick;

/**
 *
 * @author student
 */
public class XboxJoystickEvent {

    public static final int DEFAULT = 0;
    private XBoxJoystick source;
    private int id;
    private double value;

    public XboxJoystickEvent(XBoxJoystick source, int id, double value) {
        this.source = source;
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public XBoxJoystick getSource() {
        return source;
    }

    public double getValue() {
        return value;
    }
}