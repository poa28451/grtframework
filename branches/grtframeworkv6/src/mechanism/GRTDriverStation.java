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

    /**
     * @param primary 
     * @param secondary 
     */
    public GRTDriverStation(XBoxJoystick primary, XBoxJoystick secondary) {
        this.primary = primary;
        this.secondary = secondary;
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
}
