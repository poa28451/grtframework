
package com.grt192.radio;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import java.io.IOException;
import java.util.Vector;

/**
 * An event driven daemon which makes multiple single client connections
 * @author data, ajc
 */
public class RadioServer extends Thread implements GRTSocket {

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
                    notifyMyListeners(client.readUTF());
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
                client.writeUTF(data);
                client.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public boolean isConnected() {
            return connected;
        }

        public void connect() {
            connected = true;
        }

        public void disconnect() {
            client.close();
            clients.removeElement(this);
            running = false;
            notifyMyDisconnect();
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
    
    private final int port;
    private Vector clients;
    private boolean running;
    private Vector serverSocketListeners;

    public RadioServer(int port) {
        this.port = port;
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

    public void connect() {
        RadioSingleConnect rsc = new RadioSingleConnect(RadioDataIOStream.open(port));
        rsc.start();
        clients.addElement(rsc);
        notifyConnect(rsc);
    }

    public void run() {
        running = true;
        while (running) {
            connect();
        }
    }

    public void disconnect() {
        for (int i = 0; i < clients.size(); i++) {
            ((RadioSingleConnect) clients.elementAt(i)).stop();
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
