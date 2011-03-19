package com.grt192.core;

import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.grt192.radio.Ports;
import com.grt192.radio.RadioServer;
import java.util.Vector;

/**
 *  A daemon which runs a log server and regulates CRIO output
 * @author ajc, data
 */
public class GRTLogger implements SocketListener, Ports{
    public static final int PORT = 192;

    /** List of all keys to accept to print  */
    private Vector printers;
    private RadioServer rs;

    public GRTLogger() {
        printers = new Vector();
        initServer();
        initPrinters();
    }

    public void initServer(){
        rs = new RadioServer(LOGGER_PORT);
        rs.addSocketListener(this);
        rs.start();
    }

    public void initPrinters() {
        printers.addElement("GRTRobot");
    }

    public void write(String key, String data) {
        if (printers.contains(key)) {
            System.out.println("["+key + "]: " + data);
        }
        rs.sendData(key + "/" + data);

    }

    public void addPrinter(String key){
        printers.addElement(key);
    }

    public void removePrinter(String key){
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
