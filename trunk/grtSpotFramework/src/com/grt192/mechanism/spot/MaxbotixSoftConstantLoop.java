package com.grt192.mechanism.spot;

import com.grt192.event.component.spot.PulseSwitchListener;
import com.grt192.sensor.spot.GRTPulseSwitch;
import com.grt192.sensor.spot.GRTSonar;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;

/**
 * An automatically strobing MaxBotix Daisy Chain.
 * The behavior mimics a MaxBotix hardware-constant loop which continuously
 * ranges each sonar in the chain, but loops the signal software side.
 * @author ajc
 */
public class MaxbotixSoftConstantLoop extends MaxbotixDaisyChain implements PulseSwitchListener {

    private final GRTPulseSwitch tx;

    /**
     * Constructs a new Daisy chain using analog sonars
     * @param sonarPins analog pins for to sensor
     * @param rxPin rx pin of first sonar in the chain
     * @param txPin tx pin of the last sonar in the chain
     * @return 
     */
    public static MaxbotixSoftConstantLoop create(int[] sonarPins, int rxPin, int txPin) {
        GRTSonar[] sonars = new GRTSonar[sonarPins.length];
        for (int i = 0; i < sonarPins.length; i++) {
            sonars[i] = GRTSonar.fromAnalog(sonarPins[i], 50, "a" + sonarPins[i]);
            sonars[i].start();
        }

        IIOPin rx = EDemoBoard.getInstance().getIOPins()[rxPin];
        GRTPulseSwitch tx = new GRTPulseSwitch(txPin, 5, "d" + txPin);
        tx.start();
        return new MaxbotixSoftConstantLoop(sonars, rx, tx);
    }

    /**
     * 
     * @param sonars array of sonars to range. 
     * @param rx connected to the rx pin on the first sonar in the chain.
     * @param tx connected to the tx pin on the last sonar in the chain.
     */
    public MaxbotixSoftConstantLoop(GRTSonar[] sonars, IIOPin rx, GRTPulseSwitch tx) {
        super(sonars, rx);
        this.tx = tx;
//        if (!tx.isRunning()) {
//            tx.start();
//        }
    }

    /** Starts ranging the sonars in a loop*/
    public void startRanging() {
        tx.addPulseSwitchListener(this); //listen to tx, rangeChain on command
        rangeChain(); //first signal, to be repeated by event
    }

    /** Stops ranging the sonars in a loop */
    public void stopRanging() {
        tx.removePulseSwitchListener(this);//stop listening to tx
        setRxLow();//for sure stop ranging
    }

    /**
     * Commands the first sonar (connected to rx) to range, which then tells the next...
     */
    public void rangeChain() {
        pulseRx();
    }

    //receive a pulse from the tx pin
    public void pulseRead(GRTPulseSwitch s, int usec) {
        //This event indicates that the last sonar in the chain has finished ranging
        //thus we range the chain again
        rangeChain();
    }
    
}
