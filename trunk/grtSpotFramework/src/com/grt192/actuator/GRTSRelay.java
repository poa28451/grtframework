
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.sensorboard.EDemoBoard;

/**
 * A SPIKE relay: signal is 5v. Be sure to bridge 5v to VH.
 * @author ajc
 */
public class GRTSRelay extends Actuator{

    public static final double ON = 1d;
    public static final double OFF = 0d;

    private IOutputPin out;
    private final int pin;

    public GRTSRelay(int pin){
        out = EDemoBoard.getInstance().getOutputPins()[pin];
        this.pin = pin;
    }

    protected void executeCommand(Command c){
        out.setHigh(c.getValue() == ON);
    }

    protected void halt() {
        out.setLow();
    }

    public String toString(){
        return "Relay" +pin;
    }



}
