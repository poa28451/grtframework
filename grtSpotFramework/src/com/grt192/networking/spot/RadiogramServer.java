package com.grt192.networking.spot;

import com.grt192.utils.Assert;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * A service for sending data to many clients.
 * @author ajc
 */
public class RadiogramServer extends EventSocket {

    private RadiogramConnection tx, rx;
    private Datagram dgTx, dgRx;
    private final int port;

    public RadiogramServer(int port) {
        this.port = port;
        try {
            tx = (RadiogramConnection) Connector.open("radiogram://broadcast:" + port);
            rx = (RadiogramConnection) Connector.open("radiogram://:" + port);
            dgTx = tx.newDatagram(tx.getMaximumLength());
            dgRx = rx.newDatagram(rx.getMaximumLength());
        } catch (Exception e) {
            tx = null;
            dgTx = null;
            rx = null;
            dgRx = null;
            Assert.shouldNotReachHere("radio fail");
            System.err.println("Caught " + e + " in connection initialization.");
        }
    }

    public void sendData(String data) {
        try {
            dgTx.reset();
            dgTx.writeUTF(data);
            tx.send(dgTx);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void run() {
        running = true;
        while (running) {
            poll();
        }
    }

    public void poll() {
        try {
            rx.receive(dgRx);
            String data = dgRx.readUTF();
            if (data != null && data.length() > 0) {
                notifyDataReceived(data);
            }
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
}
