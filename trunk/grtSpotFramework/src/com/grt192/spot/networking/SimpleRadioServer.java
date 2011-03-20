
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
 * Simple radio server listens for a single client.
 * It listens for an established RadioDataIOStream from a broadcasted
 * Radiogram connection, and connects to the host that sent it.
 *
 * Clients connect to a SimpleRadioServer by providing a port and and mac address.
 *
 * @deprecated This should only be used for testing-- use RadioServer
 * @author ajc
 */
public class SimpleRadioServer extends Thread implements GRTSocket, PacketTypes{

    private boolean connected;
    private boolean running;
    private RadioDataIOStream socket;
    private final int port;
    private RadiogramConnection radio = null;
    private Vector listeners;
    private final boolean debug;

    public SimpleRadioServer(int port) {
        this(port, false);
    }

    public SimpleRadioServer(int port, boolean debug) {
        this.port = port;
        try {
            radio = (RadiogramConnection) Connector.open("radiogram://:" + port);
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
        listeners = new Vector();
        connected = false;
        this.debug = debug;
    }

    public void run() {
        running = true;
        while (running) {
            poll();
        }
    }

    private void poll() {
        while (!connected) {
            debug("Attempting connection...");
            connect();
        }
        try {
            debug("Listening for input");
            notifyDataReceived(socket.readUTF());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

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
                socket = RadioDataIOStream.open(dg.getAddress(), port);
                notifyConnect();
                connected = true;

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void sendData(String data) {
        try {
            socket.writeUTF(data);
            socket.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isConnected() {
        return connected;
    }

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
        System.out.println("[SimpleRadioServer]: " + s);
    }
}
