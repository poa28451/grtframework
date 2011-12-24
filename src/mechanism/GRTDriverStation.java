/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import sensor.XBoxJoystick;

/**
 * Defines driver interface to robots: including joysticks and live config tools.
 * 
 * @author ajc
 */
public class GRTDriverStation {

    private final XBoxJoystick primary;
    private final XBoxJoystick secondary;

    public GRTDriverStation(XBoxJoystick primary, XBoxJoystick secondary) {
        this.primary = primary;
        this.secondary = secondary;

    }

    public XBoxJoystick getPrimary() {
        return primary;
    }

    public XBoxJoystick getSecondary() {
        return primary;
    }
}
