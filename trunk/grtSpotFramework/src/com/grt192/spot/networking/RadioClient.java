package com.grt192.spot.networking;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * A <code>RadioClient</code> connects to either a <code>RadioServer</code>
 * for multiple connections, or a single client <code> SimpleRadioServer</code>
 * @see RadioServer
 * @see RadioClient
 * @author ajc
 */
public class RadioClient extends Thread implements GRTSocket, PacketTypes {

    private final int port;
    private final String host;
    private RadioDataIOStream socket;
    private boolean connected;
    private boolean running;
    private boolean broadcasting;
    private RadiogramConnection radio = null;
    private Vector listeners;
    private final boolean debug;

    /**
     * Constructs a new RadioClient, without printing debug messages.
     * @param host SpotSerialNumber of server to connect to
     * @param port port number of server to connect to
     */
    public RadioClient(String host, int port) {
        this(host, port, false);
    }

    public RadioClient(String host, int port, boolean debug) {
        this.host = host;
        this.port = port;
        this.debug = debug;
        try {
            radio = (RadiogramConnection) Connector.open("radiogram://" + host + ":" + port);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
        connected = false;
        broadcasting = false;
        listeners = new Vector();
    }

    public void run() {
        running = true;
        while (running) {
            poll();
        }
    }

    private void poll() {
        if (!connected) {
            connect();
        }
        try {
            debug("Listening for input");
            notifyDataReceived(socket.readUTF());
        } catch (IOException ex) {
            ex.printStackTrace();
            disconnect();
        }

    }

    /** Sends request packets to host in order to negotiate connection **/
    private void broadcastRequest() {
        final int MIN_SLEEP_TIME = 2000;

        new Thread() {

            public void run() {
                Random r = new Random();
                Datagram dg = null;
                try {
                    dg = radio.newDatagram(radio.getMaximumLength());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                broadcasting = true;
                while (broadcasting) {
                    try {
                        debug("Sending request packet");
                        dg.reset();
                        dg.writeByte(PACKET_REQUEST_CONNECTION);
                        radio.send(dg);
                        sleep(MIN_SLEEP_TIME + r.nextInt(MIN_SLEEP_TIME));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }

    /**
     * Opens a connection after negotiating connection with server to verify
     */
    public void connect() {
        debug("Connecting...");
        broadcastRequest();
        while (!connected) {
            openConnection();
        }
    }

    /** Negotiates connection with server to ensure both sides are connected **/
    private void openConnection() {
        try {
            //listen for packet
            Datagram dg = radio.newDatagram(radio.getMaximumLength());
            radio.receive(dg);
            byte packetType = dg.readByte();
            debug("Packet read: " + packetType + "from " + dg.getAddress());
            if (packetType == PACKET_RESPOND) {
                debug("Sending confirm packet to " + dg.getAddress() + ":" + port);
                Datagram rdg = radio.newDatagram(radio.getMaximumLength());
                rdg.reset();
                rdg.writeByte(PACKET_CONFIRM);
                radio.send(rdg);
                connected = true;
                broadcasting = false;
                socket = RadioDataIOStream.open(dg.getAddress(), port);
                debug("connection successful");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isConnected() {
        return socket != null && socket.isOpen();
    }

    public void sendData(String data) {
        try {
            socket.writeUTF(data);
            socket.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /** Closes the Socket for reconnection */
    public void disconnect() {
        try {
            socket.flush();
            socket.close();
            connected = false;
            notifyDisconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void notifyDataReceived(String data) {
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).dataRecieved(new SocketEvent(this, SocketEvent.ON_DATA, data));
        }
    }

    public void notifyConnect() {
        for (int i = 0; i < listeners.size(); i++) {
            ((SocketListener) listeners.elementAt(i)).onConnect(new SocketEvent(this, SocketEvent.ON_CONNECT, null));
        }
    }

    public void notifyDisconnect() {
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

    private void debug(String s) {
        if (debug) {
            System.out.println("[SimpleRadioServer]: " + s);
        }
    }
}
