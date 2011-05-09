package com.grt192.prototyper;

import java.util.Vector;

/**
 * Sends events on prototyped hosts going online/offline
 * Note: this sensor does not make use of Sensor state
 * @author ajc
 */
public class PrototypeSensor extends Thread {

    private static final int POLLTIME = 50;
    //maps prototype to prototype.isUp()
    private boolean[] oldState;
    private Prototyper p;
    //PrototypeingListeners to notify on event
    private Vector listeners;           //list of PrototypingListeners
    private boolean running;            //true if polling 
    private int pollTime = POLLTIME;    //time to wait between polls

    PrototypeSensor(Prototyper p) {
        this.p = p;
        oldState = new boolean[p.getHosts().length];
        listeners = new Vector();
    }

    public void run() {
        running = true;
        while (running) {
            try {
                poll();
                sleep(pollTime);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /** Polls each Prototyped host for changes of online-ness */
    public void poll() {
        for (int i = 0; i < p.getHosts().length; i++) {
            PrototypedHost h = p.getHost(i);
            boolean up = h.isUp();
            if (oldState[i] != up) {
                notifyListeners(h.getAnAddress(), i, up);
                oldState[i] = up;
            }
        }
    }

    /** Gets all online prototyped hosts */
    public PrototypedHost[] getOnlineHosts() {
        //store online hosts
        Vector upHosts = new Vector();
        //loop through all hosts
        for (int i = 0; i < p.getHosts().length; i++) {
            PrototypedHost host = p.getHost(i);
            if (host.isUp()) {
                upHosts.addElement(host);
            }
        }

        PrototypedHost[] retu = new PrototypedHost[upHosts.size()];
        upHosts.copyInto(retu);
        return retu;
    }

    /** Prints data of all  online prototyped hosts */
    public void printStatus() {
        System.out.println("HOSTS:");
        PrototypedHost[] onlineHosts = getOnlineHosts();
        for (int i = 0; i < onlineHosts.length; i++) {
            System.out.println(
                    "\t["
                    + onlineHosts[i].getPrototype()
                    + ":"
                    + onlineHosts[i].getAddress()
                    + "]");

        }
    }

    /** 
     * Notifies <code>PrototypeingListener</code>s on prototyping event
     * @param host host affected
     * @param prototype prototype affected
     * @param up new state
     */
    public void notifyListeners(String host, int prototype, boolean up) {
        if (up) {
            for (int i = 0; i < listeners.size(); i++) {
                ((PrototypeingListener) listeners.elementAt(i)).hostUp(prototype, host);
            }
        } else {
            for (int i = 0; i < listeners.size(); i++) {
                ((PrototypeingListener) listeners.elementAt(i)).hostDown(prototype, host);
            }
        }
    }

    /** Adds provided <code>PrototypeingListener</code> to be called on prototypeing events */
    public void addPrototypeListener(PrototypeingListener l) {
        listeners.addElement(l);
    }

    /** Removes provided <code>PrototypeingListener</code> from being called on prototypeing events */
    public void removePrototypeListener(PrototypeingListener l) {
        listeners.removeElement(l);
    }
}
