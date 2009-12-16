/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.partybot;

import com.grt192.actuator.GRTGPIORelay;
import com.grt192.core.Command;
import com.grt192.core.Mechanism;

/**
 *
 * @author Student
 */
public class ArmBeta extends Mechanism {
    private int armState = 0;
    public final int ARM_FORWARD = 1;
    public final int ARM_BACK = 0;
    
    public ArmBeta(GRTGPIORelay arm) {
        arm.start();
        addActuator("Arm", arm);
    }

    public ArmBeta(int relayFwdPort, int relayBwdPort) {
        addActuator("Arm", new GRTGPIORelay(relayFwdPort, relayBwdPort));
        getActuator("Arm").start();
    }

    public void moveArm() {
        if(armState == ARM_BACK) {
            getActuator("Arm").enqueueCommand(
                    new Command(GRTGPIORelay.RELAY_FORWARD));
            armState = ARM_FORWARD;
        }
        if(armState == ARM_FORWARD) {
            getActuator("Arm").enqueueCommand(
                    new Command(GRTGPIORelay.RELAY_FORWARD));
            armState = ARM_BACK;
        }
    }

    public int getArmState() {
        return armState;
    }
}
