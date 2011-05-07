package com.grt192.sensor.spot;

import java.io.IOException;

import com.grt192.core.Sensor;
import com.grt192.utils.Util;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;

/**
 * Generic Analog gyro at the 110deg/S speed
 * @author ajc
 */
public class IDG500Gyro extends Sensor {

    /** sensor state keys */
    public static final String ANGLE = "Angle";
    public static final String VELOCITY = "Velocity";
    //conversion rates
    private static final double MV_PER_VOLT = 1000.0;
    private static final double S_PER_MS = 1.0 / 1000.0;
    private static final double DEG_PER_SEC_PER_MV = 1.0 / 9.1;
    private static final double MV_PER_DEG_PER_SEC = 9.1;
    private final IAnalogInput rate110;
    private final IAnalogInput vref;
    private final IAnalogInput temp; //TODO implement Temperature
    private double angle;
    private double velocity;
    private double time;
    private double temperature;
    private double zeroV;

    public IDG500Gyro(int rate, int ref, int temp, int pollTime, String id) {
        this(EDemoBoard.getInstance().getAnalogInputs()[rate],
                EDemoBoard.getInstance().getAnalogInputs()[ref],
                EDemoBoard.getInstance().getAnalogInputs()[temp], pollTime, id);
    }

    public IDG500Gyro(IAnalogInput rate110, IAnalogInput vref, IAnalogInput temp, int pollTime, String id) {
        this.rate110 = rate110;
        this.vref = vref;
        this.temp = temp;
        angle = 0;
        velocity = 0;
        zeroV = 0;
        time = System.currentTimeMillis();

        setSleepTime(pollTime);
        setId(id);
        calibrate();
    }

    /**
     * Finds the zero rate output of the gyro. We assume the robot is unmoving 
     * when we run this method.
     */
    public void calibrate(){
//        zeroV = getVelocity();
        
        //Find the average velocity over NUM_POLLS x POLL_TIME ms.
        final int NUM_POLLS = 100;
        final int POLL_TIME = 50;
        double sum = 0;
        for(int i = 0; i < NUM_POLLS; i++) {
            sum += getVelocity();
            Util.sleep(POLL_TIME);
        }
        sum /= NUM_POLLS;
        zeroV = sum;
        
    }

    /**
     * Calculates the temperature reported by the gyro.
     * @return
     */
    public double getTemperature() {
        //TODO 
        return 0;
    }

    /**
     * Calculates the velocity reported by the gyro.
     * @return velocity in units of degrees per second
     */
    public double getVelocity() {
        try {
            return (((rate110.getVoltage() - vref.getVoltage()) * MV_PER_VOLT) / MV_PER_DEG_PER_SEC) - zeroV ;
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;//TODO errors
        }
    }

    /**
     * Reads velocity and integrates it with accumulated angle
     */
    public double getAngle() {
        double prevV = velocity;
        double prevTime = time;
        velocity = getVelocity();
        time = System.currentTimeMillis();
        //trapazoid rule: multiply difference in time to the average of current and past reading.
        return angle += .5 * ((time - prevTime) * S_PER_MS) * (prevV + velocity);
    }

    public void poll() {
        setState(ANGLE, getAngle());
        //use last stored velocity because it is the same velocity used 
        //to integrate the angle
        setState(VELOCITY, velocity); 
    }
}
