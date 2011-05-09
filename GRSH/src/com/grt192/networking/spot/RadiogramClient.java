package com.grt192.networking.spot;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * Listens for datagrams from a single address
 * @author ajc
 */
public class RadiogramClient extends Thread implements GRTSocket {

    private RadiogramConnection radio;
    private Datagram dg;
    private Vector listeners;
    private boolean running;

    public RadiogramClient(String address, int port) {
        try {
            radio = (RadiogramConnection) Connector.open("radiogram://" + address + ":" + port);
            dg = radio.newDatagram(radio.getMaximumLength());
        } catch (Exception e) {
            radio = null;
            dg = null;
            System.err.println("Caught " + e + " in connection initialization.");
        }
        listeners = new Vector();
    }

    public void run() {
        running = true;
        while (running) {
            poll();
        }
    }

    public void poll() {
        try {
//                System.out.println("listening...");
            radio.receive(dg);
            String data = dg.readUTF();
            if (data != null && data.length() > 0) {
                notifyListeners(data);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendData(String data) {
        try {
            Datagram dg = radio.newDatagram(radio.getMaximumLength());
            dg.reset();
            dg.writeUTF(data);
            radio.send(dg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isConnected() {
        return true;
    }

    public void connect() {
        //no action
    }

    public void disconnect() {
        //no action
    }

    public void notifyListeners(String data) {
        SocketEvent send = new SocketEvent(this, SocketEvent.ON_DATA, data);
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).dataRecieved(send);
        }
    }

    public void addSocketListener(SocketListener s) {
        listeners.addElement(s);
    }

    public void removeSocketListener(SocketListener s) {
        listeners.removeElement(s);
    }
}
