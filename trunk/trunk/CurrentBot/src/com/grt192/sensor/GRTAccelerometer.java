package com.grt192.sensor;

import com.grt192.core.Sensor;
import edu.wpi.first.wpilibj.Accelerometer;

/**
 * GRTAccelerometer is a continuously running sensor driver that collects and
 * serves data from a single-axis accelerometer
 * @author anand
 */
public class GRTAccelerometer extends Sensor {

    private Accelerometer accelerometer;

    public GRTAccelerometer(int channel, int pollTime) {
        accelerometer = new Accelerometer(channel);
        setSleepTime(pollTime);
        setState("Acceleration", 0.0);
    }

    public void poll() {
        setState("Acceleration", accelerometer.getAcceleration());
    }


    public String toString() {
        return "accelerometer: " +getState("Acceleration");
    }
}
