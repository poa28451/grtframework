/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.mechanism.partybot;

import com.grt192.core.Sensor;
import com.grt192.mechanism.*;
import com.grt192.sensor.GRTJoystick;

/**
 *
 * @author anand
 */
public class PartyBotDriverStation extends GRTDriverStation{
    public static final int BETA_ARM_BUTTON = 1;
    public static final int ALPHA_ARM_BUTTON = 1;
    public static final int CONTENUOUS_BALL_BUTTON = 2;
    public static final int TIMED_BALL_BUTTON = 3;
    public static final int BALLOON_BUTTON = 2;
    public static final int BALOON_RESET_BUTTON = 6;
    public static final int INC_SPEED_BUTTON = 7;
    public static final int DEC_SPEED_BUTTON = 6;

    public PartyBotDriverStation(GRTJoystick left, GRTJoystick right) {
        super(left, right);
    }

    public PartyBotDriverStation(int left, int right) {
        super(left,right);
    }

    public boolean isAlphaPressed(){
        return getSensor("rightJoystick").getState("Button" + ALPHA_ARM_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isBetaPressed(){
        return getSensor("leftJoystick").getState("Button" + BETA_ARM_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isContinuousBallButtonPressed() {
        return getSensor("leftJoystick").getState("Button" + CONTENUOUS_BALL_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isTimedBallButtonPressed() {
        return getSensor("leftJoystick").getState("Button" + TIMED_BALL_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isBalloonButtonPressed() {
        return getSensor("rightJoystick").getState("Button" + BALLOON_BUTTON)
                == Sensor.TRUE;
    }
    
    public boolean isBalloonResetButtonPressed() {
        return getSensor("rightJoystick").getState("Button" + BALOON_RESET_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isIncSpeedButtonPressed() {
        return getSensor("leftJoystick").getState("Button" + INC_SPEED_BUTTON)
                == Sensor.TRUE;
    }

    public boolean isDecSpeedButtonPressed() {
        return getSensor("leftJoystick").getState("Button" + DEC_SPEED_BUTTON)
                == Sensor.TRUE;
    }
}
