/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grt192.sensor.spot;

import com.grt192.core.Sensor;
import com.grt192.event.component.spot.DemoSwitchListener;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.sensorboard.EDemoBoard;
import java.util.Vector;

/**
 * A SunSPOT Demoboard pushbutton switch
 * @author ajc
 */
public class GRTDemoSwitch extends Sensor {

    public static final double CLOSED = Sensor.TRUE;
    public static final double OPEN = Sensor.FALSE;
    private ISwitch s;
    private Vector switchListeners;

    public GRTDemoSwitch(int pin, int pollTime, String name) {
        s = EDemoBoard.getInstance().getSwitches()[pin];
        setSleepTime(pollTime);
        setId(name);
        switchListeners = new Vector();
        setState("State", s.isClosed());

    }

    public void poll() {
        double previous = getState("State");
        setState("State", s.isClosed() ? CLOSED : OPEN);
        if (getState("State") != previous) {
            notifyListeners(getState("State") == CLOSED);
        }
    }

    private void notifyListeners(boolean closing) {
        for (int i = 0; i < switchListeners.size(); i++) {
            if (closing) {
                ((DemoSwitchListener) switchListeners.elementAt(i)).switchPressed(this);
            } else {
                ((DemoSwitchListener) switchListeners.elementAt(i)).switchReleased(this);
            }
        }
    }

    public void addSwitchListener(DemoSwitchListener listener) {
        switchListeners.addElement(listener);
    }

    public void removeSwitchListener(DemoSwitchListener listener) {
        switchListeners.removeElement(listener);
    }
}
