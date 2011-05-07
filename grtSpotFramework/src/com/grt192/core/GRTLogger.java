package com.grt192.core;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketFactory;
import com.grt192.networking.SocketListener;
import com.grt192.networking.Ports;
import java.util.Vector;

/**
 *  A daemon which runs a log server and regulates robot console output.
 * Sends log messages and variable data to clients.
 * @author ajc, data
 */
public class GRTLogger implements SocketListener, Ports {

    private static class RSHSingleton {
        public static final GRTLogger INSTANCE = new GRTLogger();
    }

    /**
     * Get the singleton <code>GRTLogger</code> that is thread-safe.
     * @return 
     */
    public static GRTLogger getInstance() {
        return RSHSingleton.INSTANCE;
    }

    
    /** List of all keys to accept to print  */
    private Vector printers;
    private GRTSocket socket;

    private GRTLogger() {
        printers = new Vector();
        initServer();
        initPrinters();
    }

    private void initServer() {
        socket = SocketFactory.createServer(LOGGER_PORT);
        socket.addSocketListener(this);
        ((Thread) socket).start();
    }

    private void initPrinters() {
        printers.addElement("GRTRobot");
    }

    /**
     * Receives log message from <code>key</code>, message <code>message</code>
     * for sending to logger clients and printing to console output.
     * @param key
     * @param data
     */
    public void write(String key, String data) {
        if (printers.contains(key)) {
            System.out.println("[" + key + "]: " + data);
        }
        socket.sendData(key + "/" + data);

    }

    /**
     * Enables printing of all messages with a provided key </code> key</code>
     * @param key 
     */
    public void addPrinter(String key) {
        printers.addElement(key);
    }

    /**
     * Disables printing of all messages with a provided key </code> key</code>
     * @param key
     */
    public void removePrinter(String key) {
        printers.removeElement(key);
    }

    public void onConnect(SocketEvent e) {
    }

    public void onDisconnect(SocketEvent e) {
    }

    //TODO implement printer control
    public void dataRecieved(SocketEvent e) {
    }
}
