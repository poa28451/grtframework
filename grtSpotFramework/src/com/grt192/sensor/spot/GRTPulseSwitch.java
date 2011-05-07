
package com.grt192.sensor.spot;

import com.grt192.core.Sensor;
import com.grt192.event.component.spot.PulseSwitchListener;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;
import java.util.Vector;

/**
 * A "switch" which can listen for very quick pulses.
 * It doesn't store state information because periods of high or low are too quick for use.
 * @author ajc
 */
public class GRTPulseSwitch extends Sensor {

    public static final double CLOSED = Sensor.TRUE;
    public static final double OPEN = Sensor.FALSE;
    private IIOPin input;
    private Vector switchListeners;
    private EDemoBoard spotBoard;

    
    public GRTPulseSwitch(int pin, int pollTime, String name) {
        input = EDemoBoard.getInstance().getIOPins()[pin];
        setSleepTime(pollTime);
        setId(name);
        switchListeners = new Vector();
        spotBoard = EDemoBoard.getInstance();
        input.setAsOutput(false);
//        setState("State", input.isHigh());
    }

    public void poll() {
        //getPulse() hangs until a pulse arrives, returns the pulse width.
        //immediately notifies listeners
        notifyListeners(spotBoard.getPulse(input, true, 0));
    }

    private void notifyListeners(int time) {
        for (int i = 0; i < switchListeners.size(); i++) {
            ((PulseSwitchListener) switchListeners.elementAt(i)).pulseRead(this, time);
        }
    }

    public void addPulseSwitchListener(PulseSwitchListener listener) {
        switchListeners.addElement(listener);
    }

    public void removePulseSwitchListener(PulseSwitchListener listener) {
        switchListeners.removeElement(listener);
    }
}
