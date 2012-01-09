/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;
import sensor.GyroSensor;

/**
 *
 * @author calvin
 */
public class RobotTiltGyro {
    private static final int POLLTIME = 10;
    private static final int SLOT = 4;
    GyroSensor g = new GyroSensor(SLOT, POLLTIME, "Robot Tilt");
}
