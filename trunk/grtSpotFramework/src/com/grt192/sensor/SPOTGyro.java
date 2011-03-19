package com.grt192.sensor;

import java.io.IOException;

import com.grt192.core.Sensor;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;

/**
 * Generic Analog gyro
 * @author ajc
 */
public class SPOTGyro extends Sensor{

    private IAnalogInput rate;
    private IAnalogInput temp;

    private double angle;

    public SPOTGyro(int rate, int temp, int pollTime, String id) {
        this(EDemoBoard.getInstance().getAnalogInputs()[rate],
                EDemoBoard.getInstance().getAnalogInputs()[temp], pollTime, id);
    }

    public SPOTGyro(IAnalogInput rate, IAnalogInput temp, int pollTime, String id) {
        this.rate = rate;
        this.temp = temp;

        setSleepTime(pollTime);
        setId(id);
    }


    public void poll() {
        try {
            setState("Rate", rate.getVoltage());
            setState("Temp", temp.getVoltage());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
