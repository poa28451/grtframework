/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import com.sun.squawk.util.MathUtils;
import core.PollingSensor;
import sensor.ADXL345;
import event.RobotTiltEvent;
import event.RobotTiltListener;
import java.util.Vector;
/**
 *
 * @author calvin
 */
public class RobotTiltAccel  extends PollingSensor{
    private Vector robotTiltListeners;
    private static final int POLLTIME = 10;
    private static final int I2C_SLOT = 4;
    private static final int RANGE_VALUE = 8;

    private static final int NUM_DATA = 4;//TODO change
    private int ANGLE = 1;
    public double angle;

    public RobotTiltAccel(int pollTime, String name){
        super(name, pollTime, NUM_DATA);
        ADXL345 accelerometer = new ADXL345(I2C_SLOT, RANGE_VALUE, POLLTIME, "Accelerometer Angle");
        double normalDeviation = Math.sqrt(((accelerometer.getXAxis())*(accelerometer.getXAxis()))+
            ((accelerometer.getYAxis())*(accelerometer.getYAxis())));
        angle = MathUtils.atan(normalDeviation / accelerometer.getZAxis());
        robotTiltListeners = new Vector();
    }
    
    protected void poll() {
        setState(ANGLE, angle);
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        RobotTiltEvent e = new RobotTiltEvent(this, id, newDatum);
        for (int i = 0; i < robotTiltListeners.size(); i++) {
            ((RobotTiltListener) robotTiltListeners.elementAt(i)).RobotTiltChange(e);
        }
    }
}
