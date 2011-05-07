package com.grt192.mechanism.spot;

import com.grt192.actuator.spot.GRTDemoLED;
import com.grt192.core.CommandArray;
import com.grt192.core.Mechanism;
import com.grt192.utils.Util;

/**
 * A status light: abstracted complex control for a <code>GRTDemoLED</code>
 * @author ajc
 */
public class SLight extends Mechanism {

    private static class StatusLightSingletons {

        public static final SLight[] INSTANCES = getStatusLights();
        private static final int NUM_LIGHTS = 8;

        /** Makes status light instances */
        private static SLight[] getStatusLights() {
            SLight[] lights = new SLight[NUM_LIGHTS];
            for (int i = 0; i < NUM_LIGHTS; i++) {
                lights[i] = new SLight(new GRTDemoLED(i));
                lights[i].rawColor(GRTDemoLED.Color.NONE);
            }
            return lights;
        }
    }

    /**
     * Gets an instance of a StatusLight
     * @param i index of the light to get [0-7]
     * @return 
     */
    public static SLight get(int i) {
        return StatusLightSingletons.INSTANCES[i];
    }
    private static int BLINK_TIME = 50;
    private static int FADE_STEP = 50;
    private GRTDemoLED led;
    private CommandArray currentColor;
    private boolean blinking;

    public SLight(GRTDemoLED led) {
        this.led = led;
        led.start();
        currentColor = GRTDemoLED.Color.NONE;
    }

    public void rawColor(CommandArray color) {
        led.enqueueCommand(currentColor = color);
    }

    //to handle: blinking during fading
    public void blinkOnExistingColor(CommandArray color0) {
        blink(color0, currentColor, BLINK_TIME);
    }

    /**
     * Performs a blink, or a quick color change, starting from color0 to color1
     * @param color0 temporary color
     * @param color1 persistent color
     * @param time that color0 persists before going to color1
     */
    public void blink(CommandArray color0, CommandArray color1, int time) {
        color0 = color0.clone();//.setSleepTime(time);
        color0.setSleepTime(time);
        led.enqueueCommand(color0);
        led.enqueueCommand(color1);
    }

    public void blinkonBlack(CommandArray color, int time) {
        blink(color, GRTDemoLED.Color.NONE, time);
    }

//    public void blinkonBlack(CommandArray color) {
//        blink(color, GRTDemoLED.Color.NONE, BLINK_TIME);
//    }
    public void blinkonBlack() {
        blink(GRTDemoLED.Color.NONE, currentColor, BLINK_TIME);
    }

    /**
     * Light the LED between red and green.
     * @param percent 0 for full red, 1 for full green
     */
    public void slideRedGreen(double percent) {
        //TODO
    }

    /** Start blinking until <code>stopBlinkLoop()</code> called **/
    public void startBlinkLoop() {
        blinking = true;
        new Thread() {

            public void run() {
                while (blinking) {
                    blinkonBlack();//sends commands that will take 50ms to run
                    //sleep 1 BLINK_TIME for the on, sleep 1 BLINK_TIME for off.
                    Util.sleep(BLINK_TIME * 2);
                }
            }
        }.start();
    }

    /** Stops light blinking automatically after <code>startBlinkLoop()</code> called **/
    public void stopBlinkLoop() {
        blinking = false;
    }

    /** Graceful transition from color start to color end in a given amount of time **/
    public void fade(final CommandArray startColor, final CommandArray endColor, final int time) {
        new Thread() {//new thread in order to not hang controls TODO do this with actuator thread

            public void run() {
//                final int FADE_STEP = 50;
//                CommandArray current = (GRTDemoLED.Color.NONE).clone();

                //final conditions
                double finalR = endColor.getValue(GRTDemoLED.RED);
                double finalG = endColor.getValue(GRTDemoLED.GREEN);
                double finalB = endColor.getValue(GRTDemoLED.BLUE);
//                log("finals " + finalR + "\t" + finalG + "\t" + finalB);
                long startTime = System.currentTimeMillis();

                //initial conditions
                double r = startColor.getValue(GRTDemoLED.RED);
                double g = startColor.getValue(GRTDemoLED.GREEN);
                double b = startColor.getValue(GRTDemoLED.BLUE);
//                log("standard" + r + "\t" + g + "\t" + b);

                //num steps we use to take is time / time per step
                double numSteps = time / FADE_STEP;
//                log("num steps: " + numSteps);

//                final int FADE_STEP = 50;
                //deltacolor is change in color per step
                double dR = (finalR - r) / numSteps;
                double dG = (finalG - r) / numSteps;
                double dB = (finalB - r) / numSteps;
//                log("delta_ " + dR + "\t" + dG + "\t" + dB);


                for (int i = 0; i <= time; i += FADE_STEP) {
                    if (System.currentTimeMillis() - startTime > time) {//we're out of time-- use final color
//                        log("break at " + i);
//                        log("" + r + "\t" + g + "\t" + b);
                        rawColor(new CommandArray(finalR, finalG, finalB));
                        break;
                    }
                    //change color and increment tricolor values
                    rawColor(new CommandArray(r += dR, g += dG, b += dB));
//                    log("" + r + "\t" + g + "\t" + b);
                    Util.sleep(FADE_STEP);
                }
            }
        }.start();

    }

    public void fadeTo(final CommandArray endColor, final int time) {
        fade(currentColor, endColor, time);
    }

    /** @deprecated Fades from off to a given color **/
    public void fadeOffToDim(final CommandArray endColor, final int time) {
        fade(GRTDemoLED.Color.NONE, endColor, time);
//        new Thread() {
//
//            public void run() {
//                final int STEP = 50;
//                CommandArray current = (GRTDemoLED.Color.NONE).clone();
//                double finalR = endColor.getValue(GRTDemoLED.RED);
//                double finalG = endColor.getValue(GRTDemoLED.GREEN);
//                double finalB = endColor.getValue(GRTDemoLED.BLUE);
//                log("finals" + finalR + "\t" + finalG + "\t" + "\t" + finalB);
//                long startTime = System.currentTimeMillis();
//
//                double numSteps = time / STEP;
//                double dR = finalR / numSteps;
//                double dG = finalG / numSteps;
//                double dB = finalB / numSteps;
//
//                double r = 0;
//                double g = 0;
//                double b = 0;
//                for (int i = 0; i <= time; i += STEP) {
//                    if (System.currentTimeMillis() - startTime > time) {
//                        log("break at " + i);
//                        log("" + r + "\t" + g + "\t" + "\t" + b);
//                        rawColor(new CommandArray(finalR, finalG, finalB));
//                        break;
//                    }
//                    rawColor(new CommandArray(r += dR, g += dG, b += dB));
//                    log("" + r + "\t" + g + "\t" + "\t" + b);
//                    Util.sleep(STEP);
//                }
//            }
//        }.start();

    }
}
