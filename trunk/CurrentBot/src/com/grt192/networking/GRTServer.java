package com.grt192.networking;

import com.sun.squawk.io.BufferedReader;
import javax.microedition.io.ServerSocketConnection;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * A event driven daemon which makes multiple single client connections
 * @author data, ajc
 */
public class GRTServer extends Thread implements GRTSocket {

    /**
     * A single client connection to the server.
     */
    private class GRTSingleConnect extends Thread implements GRTSocket {

        private StreamConnection client;
        private OutputStreamWriter osw;
        private BufferedReader in;
        private OutputStreamWriter out;
        private boolean running;
        private boolean connected = true;
        Vector serverSocketListeners;

        public GRTSingleConnect(StreamConnection client) {
            try {
                this.client = client;
                serverSocketListeners = new Vector();
                in = new BufferedReader(new InputStreamReader(client.openInputStream()));
                out = new OutputStreamWriter(client.openOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            running = true;
            while (running) {
                try {
                    String text = in.readLine();
                    notifyMyListeners(text);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

        public void stop() {
            running = false;
        }

        public void sendData(String data) {
            try {
                out.write(data + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public boolean isConnected() {
            return connected;
        }

        public void connect() {
            connected = true;
        }

        public void disconnect() {
            try {
                in.close();
                out.close();
                client.close();
                clients.removeElement(this);
                notifyMyDisconnect();
            } catch (Exception e) {
                e.printStackTrace();
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
                s.dataRecieved(new SocketEvent(this, SocketEvent.ON_DISCONNECT, null));
            }
            notifyDisconnect(this);
        }
    }

    public GRTServer(int port) {
        try {
            server = (ServerSocketConnection) Connector.open("socket://:" + port);
        } catch (Exception e) {
            e.printStackTrace();
            server = null;
        }
        serverSocketListeners = new Vector();

    }
    private ServerSocketConnection server;
    private Vector clients;
    private boolean running;
    private Vector serverSocketListeners;

    public void sendData(String data) {
        for (int i = 0; i < clients.size(); i++) {
            GRTSingleConnect c = (GRTSingleConnect) clients.elementAt(i);
            c.sendData(data);
        }
    }

    public boolean isConnected() {
        return clients.size() > 0;
    }

    public void connect() {
        try {
            StreamConnection client = server.acceptAndOpen();
            GRTSingleConnect c = new GRTSingleConnect(client);
            c.start();
            clients.addElement(c);
            notifyConnect(c);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        running = true;
        while (running) {
            connect();
        }
    }

    public void disconnect() {
        for (int i = 0; i < clients.size(); i++) {
            ((GRTSingleConnect) clients.elementAt(i)).stop();
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
            s.dataRecieved(new SocketEvent(source, SocketEvent.ON_DISCONNECT, null));
        }
    }

    private void notifyConnect(GRTSocket source) {
        for (int i = 0; i < serverSocketListeners.size(); i++) {
            SocketListener s = (SocketListener) serverSocketListeners.elementAt(i);
            s.dataRecieved(new SocketEvent(source, SocketEvent.ON_CONNECT, null));
        }
    }
}
