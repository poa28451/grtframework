/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.grt192.controller.partybot;

import com.grt192.core.StepController;
import com.grt192.mechanism.partybot.ArmAlpha;
import com.grt192.mechanism.partybot.PartyBotDriverStation;

/**
 *
 * @author ryo
 */
public class ArmAlphaController extends StepController{
    public static final double[] ARM_POSITIONS = {340, 0, 20};

    private boolean firing = false;
    private boolean armMoving = false;
    private boolean balloonReset = false;
    private boolean armInited = false;
    private int state = 0;
    private int balloonIndex = 1;

    public ArmAlphaController(ArmAlpha alpha, PartyBotDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        addMechanism("ArmAlpha", alpha);
    }
    public void act() {
        if(!armInited) {
            ((ArmAlpha) getMechanism("ArmAlpha")).initArm();
            armInited = true;
        } else {
            boolean balloonButton = ((PartyBotDriverStation)
                    getMechanism("DriverStation")).isBalloonButtonPressed();
            boolean armButton = ((PartyBotDriverStation)
                    getMechanism("DriverStation")).isAlphaPressed();
            boolean balloonResetButton = ((PartyBotDriverStation)
                    getMechanism("DriverStation")).isBalloonResetButtonPressed();

            if(balloonButton && !firing && (balloonIndex <= 4)) {
                ((ArmAlpha) getMechanism("ArmAlpha")).fireBalloon(balloonIndex);
                balloonIndex++;
                firing = true;
                System.out.println("Firing Balloon");
            }
            firing = !(!balloonButton && firing);

            if(armButton && !armMoving) {
                ((ArmAlpha) getMechanism("ArmAlpha")).MoveArm();
                armMoving = true;
                System.out.println("Moving Arm Alpha");
            }
            armMoving = !(armMoving && !armButton);

            if(balloonResetButton && !balloonReset) {
                balloonIndex = 1;
                ((ArmAlpha) getMechanism("ArmAlpha")).resetBalloons();
            }
            balloonReset = !(balloonReset && !balloonResetButton);
        }
    }

}
