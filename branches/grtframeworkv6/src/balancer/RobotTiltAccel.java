/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package balancer;

import com.sun.squawk.util.MathUtils;
import core.Sensor;
import event.ADXL345Event;
import event.ADXL345Listener;
import sensor.GRTADXL345;
import event.RobotTiltEvent;
import event.RobotTiltListener;
import java.util.Vector;
/**
 *
 * @author calvin
 */
public class RobotTiltAccel  extends Sensor implements ADXL345Listener {
    private Vector robotTiltListeners;

    private static final int NUM_DATA = 4;//TODO change
    private int ANGLE = 1;
    private double angle;
    private double xAccel;
    private double yAccel;
    private double zAccel;

    public RobotTiltAccel(int pollTime, String name){
        super(name);
        robotTiltListeners = new Vector();
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        RobotTiltEvent e = new RobotTiltEvent(this, id, newDatum);
        for (int i = 0; i < robotTiltListeners.size(); i++) {
            ((RobotTiltListener) robotTiltListeners.elementAt(i)).RobotTiltChange(e);
        }
    }
    
    private void updateAngle(){
        double normalDeviation = Math.sqrt(((xAccel)*(xAccel))+
            ((yAccel)*(yAccel)));
        angle = MathUtils.atan(normalDeviation / zAccel);
    }
    
    public double getTilt(){
        return angle;
    }
    
    public void addRobotTiltListeners(RobotTiltListener l){
        robotTiltListeners.addElement(l);
    }
    
    public void removeRobotTiltListeners(RobotTiltListener l){
        robotTiltListeners.removeElement(l);
    }

    protected void startListening() {
    }

    protected void stopListening() {
    }

    public void XAccelChange(ADXL345Event e) {
        xAccel = e.getAcceleration();
        updateAngle();
    }

    public void YAccelChange(ADXL345Event e) {
        yAccel = e.getAcceleration();
        updateAngle();
    }

    public void ZAccelChange(ADXL345Event e) {
        zAccel = e.getAcceleration();
        updateAngle();
    }
}