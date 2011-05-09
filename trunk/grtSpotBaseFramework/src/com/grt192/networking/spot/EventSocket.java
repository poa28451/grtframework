package com.grt192.networking.spot;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import java.util.Vector;

/**
 * A generic event-sending grtsocket
 * Establishes the event and light debug framework
 * @author ajc
 */
public abstract class EventSocket extends Thread implements GRTSocket {

    private Vector listeners;
    public boolean debug;
    protected boolean running;
    private String classname;

    public EventSocket() {
        listeners = new Vector();
        debug = false;
        classname = getClass().getName();
        int dot = classname.lastIndexOf('.');
        classname = classname.substring(dot + 1);

        running = false;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void notifyDataReceived(String data) {
        notifyDataReceived(data, this);
    }

    public void notifyConnect() {
        notifyConnect(this);
    }

    public void notifyDisconnect() {
        notifyDisconnect(this);
    }

    public void notifyDataReceived(String data, GRTSocket s) {
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).dataRecieved(new SocketEvent(this, SocketEvent.ON_DATA, data));
        }
    }

    public void notifyConnect(GRTSocket s) {
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).onConnect(new SocketEvent(this, SocketEvent.ON_CONNECT, null));
        }
    }

    public void notifyDisconnect(GRTSocket s) {
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).onDisconnect(new SocketEvent(this, SocketEvent.ON_DISCONNECT, null));
        }
    }

    public void addSocketListener(SocketListener s) {
        listeners.addElement(s);
    }

    public void removeSocketListener(SocketListener s) {
        listeners.removeElement(s);
    }

    protected void debug(String s) {
        if (debug) {
            System.out.println("[" + classname + "]: " + s);
        }
    }
}
