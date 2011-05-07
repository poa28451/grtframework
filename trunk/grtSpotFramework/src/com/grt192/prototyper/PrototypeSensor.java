package com.grt192.prototyper;

import com.grt192.core.Sensor;
import java.util.Vector;

/**
 * Sends events on a host going online/offline
 * @author ajc
 */
public class PrototypeSensor extends Sensor {

    private boolean[] oldState;
    private Prototyper p;
    private Vector listeners;

    public PrototypeSensor() {
        p = Prototyper.getInstance();
        oldState = new boolean[p.getHosts().length];
        listeners = new Vector();
    }

    public void poll() {
        for (int i = 0; i < p.getHosts().length; i++) {
            boolean up = p.getHost(i).isUp();
            if (oldState[i] != up) {
                notifyListeners(p.getHost(i).getAnAddress(), i, up);
                oldState[i] = up;
            }
        }
    }

    public void notifyListeners(String host, int prototype, boolean up) {
        if (up) {
            for (int i = 0; i < listeners.size(); i++) {
                ((PrototypeingListener) listeners.elementAt(i)).hostUp(i, host);
            }
        } else {
            for (int i = 0; i < listeners.size(); i++) {
                ((PrototypeingListener) listeners.elementAt(i)).hostDown(i, host);
            }
        }
    }

    public void addPrototypeListener(PrototypeingListener l) {
        listeners.addElement(l);
    }

    public void removePrototypeListener(PrototypeingListener l) {
        listeners.removeElement(l);
    }
}
