/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor.spot;

import com.grt192.core.Sensor;
import com.grt192.event.component.spot.SwitchListener;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;
import java.util.Vector;

/**
 *
 * @author ajc
 */
public class GRTSwitch extends Sensor {

    public static final double CLOSED = Sensor.TRUE;
    public static final double OPEN = Sensor.FALSE;
    private IIOPin input;

    private Vector switchListeners;

    public GRTSwitch(int pin, int pollTime, String name) {
        input = EDemoBoard.getInstance().getIOPins()[pin];
        setSleepTime(pollTime);
        setID(name);
        switchListeners = new Vector();
        input.setAsOutput(false);
    }

    public void poll() {
        double previous = getState("State");
        setState("State", input.isHigh() ? CLOSED : OPEN);
        if(getState("State") != previous){
            notifyListeners(getState("State") == CLOSED);
        }
    }

    private void notifyListeners(boolean closing) {
        for (int i = 0; i < switchListeners.size(); i++) {
            if (closing) {
                ((SwitchListener) switchListeners.elementAt(i)).switchPressed(this);
            }else
                ((SwitchListener) switchListeners.elementAt(i)).switchReleased(this);
        }
    }

    public void addSwitchListener(SwitchListener listener) {
        switchListeners.addElement(listener);
    }

    public void removeSwitchListener(SwitchListener listener) {
        switchListeners.removeElement(listener);
    }
}
