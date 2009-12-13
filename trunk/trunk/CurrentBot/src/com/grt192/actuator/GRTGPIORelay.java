/*
 * This is a workaround for the limitations of the digital sidecar spike relay
 * module, allowing for relay control via GPIO pins.
 */

package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 * @author ryo
 */
public class GRTGPIORelay extends Actuator {
    public static final double RELAY_FORWARD = 1.0;
    public static final double RELAY_REVERSE = -1.0;
    public static final double RELAY_OFF = 0.0;
    private DigitalOutput fwd;
    private DigitalOutput bwd;

    public GRTGPIORelay(int fwdChannel, int bwdChannel) {
        fwd = new DigitalOutput(fwdChannel);
        bwd = new DigitalOutput(bwdChannel);

    }

    protected void executeCommand(Command c) {
        if (c.getValue() == RELAY_OFF) {
            fwd.set(false);
            bwd.set(false);
        } else if(c.getValue() == RELAY_FORWARD) {
            fwd.set(true);
            bwd.set(false);
        } else if(c.getValue() == RELAY_REVERSE) {
            fwd.set(false);
            bwd.set(true);
        }
    }

    protected void halt() {
            fwd.set(false);
            bwd.set(false);
    }

    public String toString() {
        return "GPIO Relay";
    }


}
