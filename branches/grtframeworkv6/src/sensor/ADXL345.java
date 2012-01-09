/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sensor;
import core.PollingSensor;
import edu.wpi.first.wpilibj.*;
import event.ADXL345Event;
import event.ADXL345Listener;
import java.util.Vector;
        
/**
 *
 * @author calvin
 */


public class ADXL345 extends PollingSensor{
    private static final int X_AXIS = 0;
    private static final int Y_AXIS = 1;
    private static final int Z_AXIS = 2;
    private static final int NUM_DATA = 3;
    private final Vector ADXL345Listener;
    
    //default range
    private byte range = DATA_FORMAT_02G;
    
    //i2c Initialization
    private I2C i2c;
    
    // default address
    private static final byte kAddress = 0x3A;
    
    // register map from datasheet
    private static final byte OFSX = 0x1E;
    private static final byte OFSY = 0x1F;
    private static final byte OFSZ = 0x20;
    private static final byte BW_RATE = 0x2C;
    private static final byte  POWER_CTL = 0x2D;
    private static final byte DATA_FORMAT = 0x31;
    private static final byte DATAX0 = 0x32;
    private static final byte DATAY0 = 0x34;
    private static final byte DATAZ0 = 0x36;
    private static final byte FIFO_CTL = 0x38;
    private static final byte FIFO_STATUS = 0x39;

    // would use enums here if we had them
    // BW_RATE 0x2C
    private static final byte BW_RATE_R3200B1600 = 0x0F;
    private static final byte BW_RATE_R1600B0800 = 0x0E;
    private static final byte BW_RATE_R0800B0400 = 0x0D;
    private static final byte BW_RATE_R0400B0200 = 0x0C;
    private static final byte BW_RATE_R0200B0100 = 0x0B;
    private static final byte BW_RATE_R0100B0050 = 0x0A;
    private static final byte BW_RATE_R0050B0025 = 0x09;
    private static final byte BW_RATE_R0025B0012 = 0x08;
    private static final byte BW_RATE_R0012B0006 = 0x07;
    private static final byte BW_RATE_R0006B0003 = 0x06;

    private static final byte BW_RATE_LOW_POWER = 0x10;

    // POWER_CTL 0x2D
    private static final byte POWER_CTL_LINK = 0x20;
    private static final byte POWER_CTL_AUTO_SLEEP = 0x10;
    private static final byte POWER_CTL_MEASURE = 0x08;
    private static final byte POWER_CTL_SLEEP = 0x04;
    private static final byte POWER_CTL_WAKEUP8 = 0x00;
    private static final byte POWER_CTL_WAKEUP4 = 0x01;
    private static final byte POWER_CTL_WAKEUP2 = 0x02;
    private static final byte POWER_CTL_WAKEUP1 = 0x03;

    // DATA_FORMAT
    public static final byte DATA_FORMAT_02G = 0x00;
    public static final byte DATA_FORMAT_04G = 0x01;
    public static final byte DATA_FORMAT_08G = 0x02;
    public static final byte DATA_FORMAT_16G = 0x03;
    // default address
   
    public ADXL345(int slot, int range_value, int Polltime, String id){
        super(id, Polltime, NUM_DATA);
        this.setRange(range_value);
        i2c = new I2C( DigitalModule.getInstance(slot), kAddress );
        ADXL345Listener = new Vector();
        intitialize();
    }
    
    public final void intitialize()
    {
        // set BW_RATE
        i2c.write(BW_RATE, BW_RATE_R0100B0050);
        // set POWER_CTL
        i2c.write(POWER_CTL, POWER_CTL_MEASURE);
    }
    
    protected void poll() {
        setState(X_AXIS, getXAxis());
        setState(Y_AXIS, getYAxis());
        setState(Z_AXIS, getZAxis());
    }

    protected void notifyListeners(int id, double oldDatum, double newDatum) {
        ADXL345Event e = new ADXL345Event(this, id, newDatum);
        switch(id){
            case X_AXIS:{
                for (int i = 0; i < ADXL345Listener.size(); i++) {
                    ((ADXL345Listener) ADXL345Listener.elementAt(i)).XAccelChange(e);
                }
            }
            case Y_AXIS:{
                for (int i = 0; i < ADXL345Listener.size(); i++) {
                    ((ADXL345Listener) ADXL345Listener.elementAt(i)).YAccelChange(e);
                }
            }
            case Z_AXIS:{
                for (int i = 0; i < ADXL345Listener.size(); i++) {
                    ((ADXL345Listener) ADXL345Listener.elementAt(i)).ZAccelChange(e);
                }
            }
        }
    }

    //returning axis values
    public double getXAxis()
    {
        return getAxis( DATAX0 );
    }

    public double getYAxis()
    {
        return getAxis( DATAY0 );
    }

    public double getZAxis()
    {
        return getAxis( DATAZ0 );
    }

    protected double getAxis( byte registerParam )
    {
        // setup array for our data
        byte[] data = new byte[2];
        // read consecutive registers
        this.i2c.read( registerParam, (byte) data.length, data);

        // convert to 2s complement integer
        // [0] has low byte [1] has the high byte
        // jave does not have unsigned so we have to do it this way
        int intResult = ( data[0] & 0xFF ) | ( data[1] << 8 );

        // convert to double based on 10 bit result
        double returnValue = (double)intResult / 512.0 ;

        // now scale based upon our range
        switch( range )
        {
            case DATA_FORMAT_02G:
                returnValue *= 2.0;
                break;
            case DATA_FORMAT_04G:
                returnValue *= 4.0;
                break;
            case DATA_FORMAT_08G:
                returnValue *= 8.0;
                break;
            case DATA_FORMAT_16G:
                returnValue *= 16.0;
                break;
        }
        return returnValue;
    }
    
    
    private void setRange(int value) {
        switch (value) {
            case 2:
                    range = DATA_FORMAT_02G;
                    break;
            case 4:
                    range = DATA_FORMAT_04G;
                    break;
            case 8:
                    range = DATA_FORMAT_08G;
                    break;
            case 16:
                    range = DATA_FORMAT_16G;
                    break;
            default:
                    range = DATA_FORMAT_02G;
            }
        i2c.write(DATA_FORMAT, range);

    }
    
    public void addADXL345Listener(ADXL345Listener a) {
        ADXL345Listener.addElement(a);
    }

    public void removeADXL345Listener(ADXL345Listener a) {
        ADXL345Listener.removeElement(a);
    }

}
