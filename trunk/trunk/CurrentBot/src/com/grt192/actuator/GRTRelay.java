package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Relay;

/**
 *
 * @author Student
 */
public class GRTRelay extends Actuator {

    public static final double RELAY_FORWARD = 1.0;
    public static final double RELAY_REVERSE = -1.0;
    public static final double RELAY_OFF = 0.0;
    private Relay relay;
    

    public GRTRelay(int channel) {
        relay = new Relay(channel);
    }

    public void executeCommand(Command c) {
        if (c.getValue() == RELAY_OFF) {
            relay.set(Relay.Value.kOff);
        } else if(c.getValue() == RELAY_FORWARD) {
            relay.set(Relay.Value.kForward);
        } else if(c.getValue() == RELAY_FORWARD) {
            relay.set(Relay.Value.kReverse);
        }
    }

    public void halt() {
        relay.set(Relay.Value.kOff);
    }

    public String toString() {
        return "Relay";
    }
}
