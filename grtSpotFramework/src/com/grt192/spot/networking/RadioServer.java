package com.grt192.spot.networking;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * A stream <code>GRTSocket</code> that handles multiple <code>RadioClient</code>
 * connections. <code>RadioServer</code> automatically connects to
 * <code> RadioClient</code>s.
 * @see RadioClient
 * @author data, ajc
 */
public class RadioServer extends Thread implements GRTSocket, PacketTypes {

    /**
     * A singleConnect, an instance of a single connection to a <code>RadioClient</code>
     */
    private class RadioSingleConnect extends Thread implements GRTSocket {

        private RadioDataIOStream client;
        private boolean connected = true;
        private boolean running;
        Vector serverSocketListeners;

        public RadioSingleConnect(RadioDataIOStream client) {
            this.client = client;
            serverSocketListeners = new Vector();
        }

        public void run() {
            running = true;
            while (running) {
                try {
                    debug("Listening for data...");
                    String data = client.readUTF();
                    if (data != null) {
                        notifyMyListeners(data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    this.disconnect();
                }

            }
        }

        /**
         * Pause reading
         */
        public void pause() {
            running = false;
        }

        public void sendData(String data) {
            try {
                client.writeUTF(data);
                client.flush();
            } catch (IOException ex) {
                this.disconnect();
                ex.printStackTrace();
            }
        }

        public boolean isConnected() {
            return connected && client.isOpen();
        }

        public void connect() {
            connected = true;
        }

        public void disconnect() {
            try {
                client.flush();
                client.close();
                clients.removeElement(this);
                running = false;
                notifyMyDisconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        public void addSocketListener(SocketListener s) {
            serverSocketListeners.addElement(s);
        }

        public void removeSocketListener(SocketListener s) {
            serverSocketListeners.removeElement(s);
        }

        private void notifyMyListeners(String text) {
            if (text == null) {
                return;
            }
            for (int i = 0; i < serverSocketListeners.size(); i++) {
                SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
                s.dataRecieved(new SocketEvent(this, SocketEvent.ON_DATA, text));
            }
            notifyListeners(text, this);
        }

        private void notifyMyDisconnect() {
            for (int i = 0; i < serverSocketListeners.size(); i++) {
                SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
                s.onDisconnect(new SocketEvent(this, SocketEvent.ON_DISCONNECT, null));
            }
            notifyDisconnect(this);
        }
    }

    private Vector clients;
    private final int port;
    private boolean running;
    private boolean debug;
    private RadiogramConnection radio = null;
    private Vector serverSocketListeners;

    public RadioServer(int port) {
        this(port, false);
    }

    public RadioServer(int port, boolean debug) {
        this.port = port;
        this.debug = debug;
        try {
            radio = (RadiogramConnection) Connector.open("radiogram://:" + port);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
        serverSocketListeners = new Vector();
        clients = new Vector();
    }

    public void sendData(String data) {
        for (int i = 0; i < clients.size(); i++) {
            ((RadioSingleConnect) clients.elementAt(i)).sendData(data);
        }
    }

    public boolean isConnected() {
        return clients.size() > 0;
    }

    /**
     * Listen for a new client connection and accept if found
     */
    public void connect() {

        try {
            debug("listening for packet...");
            Datagram dg = radio.newDatagram(radio.getMaximumLength());
            radio.receive(dg);
            byte packetType = dg.readByte();
            debug("Received: " + packetType + "from " + dg.getAddress());

            if (packetType == PACKET_REQUEST_CONNECTION) {
                debug("Request packet read from " + dg.getAddress() + ": sending respond");
                Datagram rdg = radio.newDatagram(radio.getMaximumLength());
                rdg.reset();
                rdg.setAddress(dg.getAddress());
                rdg.writeByte(PACKET_RESPOND);
                //rdg.writeLong(otherAddressAsNumber);// not needed because we send packet directly to sender-- not broadcast
                radio.send(rdg);
            }
            if (packetType == PACKET_CONFIRM) {
                debug("Confirm packet read from " + dg.getAddress());
                RadioSingleConnect rsc =
                        new RadioSingleConnect(
                        RadioDataIOStream.open(dg.getAddress(), port));
                clients.addElement(rsc);
                notifyConnect(rsc);
                rsc.start();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public void run() {
        running = true;
        while (running) {
            connect();
        }
    }

    /**
     * Disconnect all clients 
     */
    public void disconnect() {
        for (int i = 0; i < clients.size(); i++) {
//            ((RadioSingleConnect) clients.elementAt(i)).stop();
            ((RadioSingleConnect) clients.elementAt(i)).disconnect();
        }
    }

    public void addSocketListener(SocketListener s) {
        serverSocketListeners.addElement(s);
    }

    public void removeSocketListener(SocketListener s) {
        serverSocketListeners.removeElement(s);
    }

    private void notifyListeners(String text, GRTSocket source) {
        for (int i = 0; i < serverSocketListeners.size(); i++) {
            SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
            s.dataRecieved(new SocketEvent(source, SocketEvent.ON_DATA, text));
        }
    }

    private void notifyDisconnect(GRTSocket source) {
        for (int i = 0; i < serverSocketListeners.size(); i++) {
            SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
            s.onDisconnect(new SocketEvent(source, SocketEvent.ON_DISCONNECT, null));
        }
    }

    private void notifyConnect(GRTSocket source) {
        for (int i = 0; i < serverSocketListeners.size(); i++) {
            SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
            s.onConnect(new SocketEvent(source, SocketEvent.ON_CONNECT, null));
        }
    }

    private void debug(String s) {
        if (debug) {
            System.out.println("[SimpleRadioServer]: " + s);
        }
    }
}
