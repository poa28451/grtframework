/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;
import sensor.GRTGyro;

/**
 *
 * @author calvin
 */
public class RobotTiltGyro {
    private static final int POLLTIME = 10;
    private static final int SLOT = 4;
    GRTGyro g = new GRTGyro(SLOT, POLLTIME, "Robot Tilt");
}
