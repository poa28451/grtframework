package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.grt192.event.component.GyroEvent;
import com.grt192.event.component.GyroListener;
import edu.wpi.first.wpilibj.Gyro;
import java.util.Vector;

/**
 * GRTGyro is a continuously running sensor driver that collects and
 * serves data from a single-axis gyro
 * @author anand
 */
public class GRTGyro extends Sensor {

    public static final double SPIKE_THRESHOLD = 1.0;
    public static final double CHANGE_THRESHOLD = .001;
    private Gyro gyro;
    private Vector gyroListeners;

    public GRTGyro(int channel, int pollTime) {
        gyro = new Gyro(channel);
        setSleepTime(pollTime);
        setState("Angle", 0.0);
        gyroListeners = new Vector();

    }

    public void poll() {
        double previousValue = getState("Angle");
        setState("Angle", gyro.getAngle());

        if (Math.abs(getState("Angle") - previousValue) >= SPIKE_THRESHOLD) {
            notifyGyroSpike();
        }
        if (Math.abs(getState("Angle") - previousValue) >= CHANGE_THRESHOLD) {
            notifyGyroChange();
        }
        notifyGyroListeners();
    }

    protected void notifyGyroSpike() {
        for (int i = 0; i < gyroListeners.size(); i++) {
            ((GyroListener) gyroListeners.elementAt(i)).didAngleSpike(
                    new GyroEvent(this,
                    GyroEvent.DEFAULT,
                    getState("Angle")));
        }
    }

    protected void notifyGyroChange() {
        for (int i = 0; i < gyroListeners.size(); i++) {
            ((GyroListener) gyroListeners.elementAt(i)).didAngleChange(
                    new GyroEvent(this,
                    GyroEvent.DEFAULT,
                    getState("Angle")));
        }
    }

    protected void notifyGyroListeners() {
        for (int i = 0; i < gyroListeners.size(); i++) {
            ((GyroListener) gyroListeners.elementAt(i)).didReceiveAngle(
                    new GyroEvent(this,
                    GyroEvent.DEFAULT,
                    getState("Angle")));
        }
    }

    public Vector getGyroListeners() {
        return gyroListeners;
    }

    public void addGyroListener(GyroListener a) {
        gyroListeners.addElement(a);
    }

    public void removeGyroListener(GyroListener a) {
        gyroListeners.removeElement(a);
    }

    public String toString() {
        return "gyro: " + getState("Angle");
    }
}
