package com.grt192.sensor;

import com.grt192.core.Sensor;
import edu.wpi.first.wpilibj.Gyro;

/**
 * A single axis MEMS Gyroscope driver
 * @author anand
 */
public class GRTGyro extends Sensor{
    private Gyro gyro;
    
    public GRTGyro(int channel, int pollTime){
        gyro = new Gyro(channel);
        setSleepTime(pollTime);
        setState("Angle", 0.0);
    }

    public void poll() {
        setState("Angle", gyro.getAngle());
    }

    public String toString() {
        return "Gyro";
    }

}
