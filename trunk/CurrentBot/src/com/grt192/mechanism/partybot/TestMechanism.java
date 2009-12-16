/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.partybot;

import com.grt192.actuator.GRTJaguar;
import com.grt192.actuator.GRTRelay;
import com.grt192.actuator.GRTVictor;
import com.grt192.core.Command;
import com.grt192.sensor.GRTAccelerometer;
import com.grt192.core.Mechanism;

/**
 *
 * @author ryo
 */
public class TestMechanism extends Mechanism {

    //this test Mechanism has a Jaguar and an Accelerometer
    public TestMechanism(GRTVictor victor1, GRTVictor victor2,GRTVictor victor3,GRTVictor victor4,
            GRTRelay relay1, GRTRelay relay2, GRTRelay relay3, GRTRelay relay4, GRTRelay relay5,
            GRTRelay relay6) {
        victor1.start();
        victor2.start();
        victor3.start();
        victor4.start();
        relay1.start();
        relay2.start();
        relay3.start();
        relay4.start();
        relay5.start();
        relay6.start();
        addActuator("Relay1", relay1);
        addActuator("Relay2", relay2);
        addActuator("Relay3", relay3);
        addActuator("Relay4", relay4);
        addActuator("Relay5", relay5);
        addActuator("Relay6", relay6);
        addActuator("Victor1", victor1);
        addActuator("Victor2", victor2);
        addActuator("Victor3", victor3);
        addActuator("Victor4", victor4);
        
        /*
        addActuator("Jaguar", j);
        j.start();
         * */
        
    }

    public TestMechanism(int[] relayPorts, int[] victorPorts) {
        for(int i = 1; i <= 6; i++) {
            addActuator("Relay" + i, new GRTRelay(relayPorts[i]));
            getActuator("Relay" + i).start();
        }
        for(int i = 1; i <= 4; i++) {
            addActuator("Victor" + i, new GRTVictor(victorPorts[i]));
            getActuator("Victor" + i).start();
        }
    }

    //returns the Acceeleration given by the accelerometer

    public void setSpeed(int relayNum, double speed) {
        Command c = new Command(speed, 0); //100 millisec of sleep, so why does it say 0?
        getActuator("Victor" + relayNum).enqueueCommand(new Command(speed));
    }

    public void Forward(int relayNum) {
        getActuator("Relay" + relayNum).enqueueCommand(new Command(GRTRelay.RELAY_FORWARD));
    }

    public void Off(int relayNum) {
        getActuator("Relay" + relayNum).enqueueCommand(new Command(GRTRelay.RELAY_OFF));
    }
        
    public void Reverse(int relayNum) {
        getActuator("Relay" + relayNum).enqueueCommand(new Command(GRTRelay.RELAY_REVERSE));
    }

    public String toString() {
        return "a test mechanism of some sort";
    }
}
