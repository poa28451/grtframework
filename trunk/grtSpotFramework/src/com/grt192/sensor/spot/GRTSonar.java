
package com.grt192.sensor.spot;

import com.grt192.core.Sensor;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;
import java.io.IOException;

/**
 * 
 * @author ajc
 */
public class GRTSonar extends Sensor {

    private static double MV_PER_VOLT = 1000;
    private static double INCH_PER_MV = 1.0/9.8;
    private IAnalogInput analog;//note: min pollTime is 17ms.

    public GRTSonar(int pin, int pollTime, String id) {
        this(EDemoBoard.getInstance().getAnalogInputs()[pin], pollTime, id);
    }

    public GRTSonar(IAnalogInput analog, int pollTime, String id) {
        this.analog = analog;
        setSleepTime(pollTime);
        setId(id);
        setState("Distance", 0);
    }

    public void poll() {
        try {
            setState("Distance", analog.getVoltage()* MV_PER_VOLT * INCH_PER_MV);
        } catch (IOException ex) {
            setState("Distance", -255);//yeah i'll fix this up...
            ex.printStackTrace();
        }
    }
}
