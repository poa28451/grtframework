package com.grt192.radio;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

public class BroadcastConnection implements Ports {

    private static class HostSingleton {
        public static final BroadcastConnection INSTANCE = new BroadcastConnection();
    }

    public static BroadcastConnection getInstance() {
        return HostSingleton.INSTANCE;
    }
    private RadiogramConnection txCon = null;
    private Datagram dg = null;

    private BroadcastConnection() {
        try {
            txCon = (RadiogramConnection) Connector.open("radiogram://broadcast:" + BROADCAST_PORT);
            dg = txCon.newDatagram(txCon.getMaximumLength());  // only sending 12 bytes of data
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
    }

    public void sendData(String s) {
        System.out.println("Broadcast " + s);
        try {
            dg.reset();
            dg.writeUTF(s);
            txCon.send(dg);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
