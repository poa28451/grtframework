package com.grt192.radio;

import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import java.io.*;
import java.util.Random;
import javax.microedition.io.*;

/**
 * A small utility to find the mac address of another spot for the same port.
 * @author ajc
 */
public class SpotListener {

    public static final byte PING = 1;
    public static final byte RESPOND = 2;
    public static final byte CONFIRM = 3;
    public static final int BROADCAST_PORT = 42;
    private static final int BROADCAST_SLEEP_TIME = 1000;
    private RadiogramConnection rcvConn = null;
    private DatagramConnection txConn = null;
    private final int port;
    private boolean broadcasting;
    private boolean debug = true;

    /** Gets the address of another spot for a given port number **/
    public static String getAddress(int port) {
         return getAddress(port, false);
    }

    public static String getAddress(int port, boolean debug) {
        SpotListener sl = new SpotListener(port, debug);
        sl.broadcast();
        return sl.RAMERIEZ_listenForSpots();
    }

    private SpotListener(int service, boolean debug) {
        try {
            rcvConn = (RadiogramConnection) Connector.open("radiogram://:" + BROADCAST_PORT);
            txConn = (DatagramConnection) Connector.open("radiogram://broadcast:" + BROADCAST_PORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.port = service;
        this.debug = debug;
    }

    private void broadcast() {
        new Thread() {

            private Datagram dg;
            private Random r = new Random();

            public void run() {
                try {
                    dg = txConn.newDatagram(txConn.getMaximumLength());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                broadcasting = true;
                while (broadcasting) {
                    try {
                        dg.reset();
                        dg.writeInt(port);
                        dg.writeByte(PING);
                        txConn.send(dg);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Utils.sleep(BROADCAST_SLEEP_TIME + r.nextInt(BROADCAST_SLEEP_TIME));
                }
            }
        }.start();
    }

    /** Waits for a remote SPOT to request a connection. */
    private String RAMERIEZ_listenForSpots() {
        debug("listen for spots");
        String otherAddress = "";
        int requestedPort = -1;
        byte packetType = -1;

        long thisAddressAsNumber = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        debug("our address: " + System.getProperty("IEEE_ADDRESS") + "," + thisAddressAsNumber);
        while (true) {
            try {
                Datagram dg = rcvConn.newDatagram(rcvConn.getMaximumLength());
                debug("listening for connection");
                rcvConn.receive(dg);            // wait until we receive a request
                if ((requestedPort = dg.readInt()) != port) {
                    debug("Wrong service" + requestedPort);
                    continue;
                }
                otherAddress = dg.getAddress();
                long otherAddressAsNumber = IEEEAddress.toLong(otherAddress);
                packetType = dg.readByte();

                debug("received message from  " + otherAddress + ": packet" + packetType + ", service" + requestedPort);
                if (requestedPort != port) {
                    debug("Wrong service" + requestedPort);
                    continue;
                }
                //Connection symmetry problem solved by radio with smaller address number responding FIRST
                if (thisAddressAsNumber < otherAddressAsNumber && packetType == PING) {
                    debug("Received Ping");
                    Datagram rdg = rcvConn.newDatagram(rcvConn.getMaximumLength());
                    rdg.reset();
                    rdg.setAddress(otherAddress);
                    rdg.writeInt(port);
                    rdg.writeByte(RESPOND);
                    rdg.writeLong(otherAddressAsNumber);
                    rcvConn.send(rdg);
                } else if (thisAddressAsNumber > otherAddressAsNumber && packetType == RESPOND) {
                    debug("Received Acknowledgement");
                    long sentMacAddress = dg.readLong();
                    debug("receieved mac address" + sentMacAddress);
                    if (sentMacAddress == thisAddressAsNumber) {
                        debug("connection successful");
                        Datagram rdg = rcvConn.newDatagram(rcvConn.getMaximumLength());
                        rdg.reset();
                        rdg.setAddress(otherAddress);
                        rdg.writeInt(port);
                        rdg.writeByte(CONFIRM);
                        rdg.writeLong(otherAddressAsNumber);
                        rcvConn.send(rdg);
                        broadcasting = false;
                        return otherAddress;
                    }
                } else if (thisAddressAsNumber < otherAddressAsNumber && packetType == CONFIRM) {
                    debug("Received Confirm");
                    long sentMacAddress = dg.readLong();
                    debug("receieved mac address" + sentMacAddress);
                    if (sentMacAddress == thisAddressAsNumber) {
                        debug("connection successful to" + otherAddress);
                        broadcasting = false;
                        return otherAddress;
                    }
                }
            } catch (IOException ex) {
                System.out.println("Error waiting for remote Spot: " + ex.toString());
                ex.printStackTrace();
                return "";
            }
        }
    }

    private void debug(String s) {
        if (debug) {
            System.out.println("[SpotListener:" + port + "] " + s);
        }
    }
}
