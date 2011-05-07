package com.grt192.actuator.spot;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.sensorboard.EDemoBoard;

/**
 * A SunSPOT demoBoard relay. Uses a pin H{0,1,2,3}. Voltage is determined by VH.
 * @author ajc
 */
public class GRTRelay extends Actuator {

    public static final double ON = 1d;
    public static final double OFF = 0d;
    private IOutputPin out;
    private final int pin;

    /**
     * Constructs a new GRTRelay
     * @param pin [0-3] high current pin on the EDemoBoard
     */
    public GRTRelay(int pin) {
        out = EDemoBoard.getInstance().getOutputPins()[pin];
        this.pin = pin;
    }

    protected void executeCommand(Command c) {
        out.setHigh(c.getValue() == ON);
    }

    /**
     * "Analog" out, pulses this pin at a duty
     * @param duty [0-255] PWM duty
     */
    public void pwm(int duty) {
        EDemoBoard.getInstance().setPDM(out, duty);
    }

    protected void halt() {
        out.setLow();
    }

    public String toString() {
        return "Relay" + pin;
    }
}
