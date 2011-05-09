package com.grt192.sensor.spot;

import com.grt192.core.Sensor;
import com.sun.spot.peripheral.II2C;
import com.sun.spot.sensorboard.EDemoBoard;
import java.io.IOException;

/**
 * Honeywell HMC6352 compass driver
 * @author ajc
 */
public class GRTCompass extends Sensor {

    /** State Key ID */
    public static final String ANGLE = "Angle";
    //i2c constants
    private static final byte SLA_HMC6352_SLAVE_ADDRESS = (byte) 0x42;
    private static final byte SLA_HMC6352_WRITE = (byte) 0x41;
    private static final byte SLA_HMC6352_READ_COMMAND = 'A';
    private static final int NUM_BYTES_READ = 2;
    private II2C i2cDevice;

    public GRTCompass(double changeThreshold, int pollTime, String id) {
        this(EDemoBoard.getInstance().getI2C(), changeThreshold, pollTime, id);
    }

    public GRTCompass(II2C e, double changeThreshold, int pollTime, String id) {
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
        setState(ANGLE, 0);
        setSleepTime(pollTime);
        setID(id);
    }

    /** Commands the compass to get a reading, and calculates direct heading from it */
    public double getAngle() {

        byte[] data = new byte[NUM_BYTES_READ];
        try {
            i2cDevice.read(SLA_HMC6352_SLAVE_ADDRESS, SLA_HMC6352_READ_COMMAND, 1, data, 0, NUM_BYTES_READ);
            double headingvalue = (data[0] << 8) + data[1];
            return ((int) headingvalue / 10) + ((headingvalue % 10) / 10);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ERROR;
    }

    public void poll() {
        //TODO perhaps calculate velocity
        setState(ANGLE, getAngle());
    }
}
