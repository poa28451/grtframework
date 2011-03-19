
package com.grt192.sensor;

import com.grt192.core.Sensor;
import com.sun.spot.peripheral.II2C;
import com.sun.spot.sensorboard.EDemoBoard;
import java.io.IOException;
import java.util.Vector;

/**
 * Honeywell HMC6352 compass driver
 * @author ajc
 */
public class GRTSCompass extends Sensor {

    public static final byte SLA_HMC6352_READ = (byte) 0x42;
    public static final byte SLA_HMC6352_WRITE = (byte) 0x41;
    private II2C i2cDevice;

    public GRTSCompass(double changeThreshold, int pollTime, String id) {
        this(EDemoBoard.getInstance().getI2C(), changeThreshold,pollTime,id);
    }

    public GRTSCompass(II2C e, double changeThreshold, int pollTime, String id) {
        i2cDevice = e;
        for (int i = 0; i < 4; i++) {
            try {
                i2cDevice.open();
                break;
            } catch (IOException ex) {
                System.err.println("Failed to open I2C bus ");
            }
        }
        setChangeThreshold(changeThreshold);
        setState("Angle", 0);
        setSleepTime(pollTime);
        setId(id);
    }

    public void poll() {
        double angle = 0;
        byte[] command = new byte[1];
        command[0] = (byte) ('A');
        byte[] data = new byte[2];
        try {
            i2cDevice.read(SLA_HMC6352_READ, command[0], 1, data, 0, 2);
            angle = ((((data[0]) << 8) + (data[1])) / 10); //(((0xFF & data[0]) << 8) | ((0xFF) & data[1]));
        } catch (IOException e) {
            angle = -999;
            log("Compass reading failed");
        }
        setState("Angle", angle);
    }

}
