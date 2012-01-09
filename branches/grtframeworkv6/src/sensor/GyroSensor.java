/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;

import core.PollingSensor;
import edu.wpi.first.wpilibj.Gyro;
import java.util.Vector;
import event.GyroEvent;
import event.GyroListener;

/**
 *
 * @author calvin
 */
public class GyroSensor extends PollingSensor{
    private Gyro gyro;
    private Vector gyroListeners;
    private int ANGLE = 1;
    
    public GyroSensor(int channel, int pollTime, String name) {
        super(name, pollTime, 1);
        gyro = new Gyro(channel);
        gyroListeners = new Vector();
    }

    protected void poll() {
        setState(ANGLE, gyro.getAngle());
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        GyroEvent e = new GyroEvent(this, id, newDatum);
        for (int i = 0; i < gyroListeners.size(); i++) {
            ((GyroListener) gyroListeners.elementAt(i)).AngleChange(e);
        }
        
    }

    
}
