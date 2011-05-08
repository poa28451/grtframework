package com.grt192.prototyper;

import com.grt192.actuator.spot.GRTDemoLED;
import com.grt192.core.GRTObject;
import com.grt192.mechanism.spot.SLight;
import com.grt192.networking.Ports;
import com.grt192.utils.Assert;
import com.grt192.utils.Util;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * A Prototyper manages the online presence itself as a <code>PrototypedHost</code> 
 * to other controllers, as well as the online presence of foreign <code>PrototypedHost</code> s
 * as it receives their packets.
 * @author ajc
 */
public class Prototyper extends GRTObject implements Ports {

    //
    public static int standardProtocol() {
        getInstance().setPrinting(true);
        int controlType = getInstance().getPrototype();
        getInstance().setPrinting(false);//stop debugging
        extLog("Prototyper", "prototyping complete!: " + controlType);
        return controlType;
    }

    //stores the prototyper singleton
    private static class PacketRouterSingleton {

        public static final Prototyper INSTANCE = new Prototyper();
    }

    /** Gets an instance of a Prototyper */
    public static Prototyper getInstance() {
        return PacketRouterSingleton.INSTANCE;
    }
    /** Time between checking for prototype equilibrium */
    public static final int PROTOTYPE_POLLTIME = 125;
    /**
     * Number of times equilibrium must be established for in order to
     * complete prototyping
     */
    public static final int NUM_RUNS_FOR_EQUILIBRIUM = 20;
    /** Time interval for broadcasting prototype */
    public static final int BROADCAST_SLEEP_TIME = 1000;
    /** Maximum number of hosts the prototyper can recognize */
    private static final int MAX_NUM_HOSTS = 100;
    /** True if the Prototyper is running on a basestation */
    private static final boolean IS_BASESTATION = Spot.getInstance().isRunningOnHost();
    /** First address recognized as from a basestation */
    private static final int FIRST_BASESTATION = 80;
    /** pseudo prototype assigned to prototyped hosts while Prototyping */
    private static final int PROTOTYPING = -1;
    /** ID of the statuslight to indicate state in the prototyping process */
    private static final int STATUSLIGHT = 1;
    /** Base10 representation of our address, to handle multiple prototypers */
    private final long MY_ADDRESS = RadioFactory.getRadioPolicyManager().getIEEEAddress();
    //radio
    private RadiogramConnection rx, tx;
    private Datagram dgRx, dgTx;
    //state
    private boolean listening, broadcasting;
    private int prototype;
    //packet array
    private PrototypedHost[] hosts;
    private PrototypedHost vagabond;
    //sensor
    private PrototypeSensor sensor;

    private Prototyper() {
        lightOrange();
        //radio
        try {
            tx = (RadiogramConnection) Connector.open("radiogram://broadcast:" + PROTOTYPE_PORT);
            rx = (RadiogramConnection) Connector.open("radiogram://:" + PROTOTYPE_PORT);
            dgRx = rx.newDatagram(rx.getMaximumLength());
            dgTx = tx.newDatagram(tx.getMaximumLength());
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
        }
        //initial state
        prototype = PROTOTYPING;
        listening = false;
        broadcasting = false;
        hosts = new PrototypedHost[MAX_NUM_HOSTS];
    }

    /** Gets a list of all prototyped hosts bound to ID's.
     * Note: this doesn't ensure construction of each PrototypedHost
     * @return 
     */
    public PrototypedHost[] getHosts() {
        return hosts;
    }

    /** Gets a prototyped host with a provided offset ID from a basestation */
    public PrototypedHost getBaseStation(int id) {
        return getHost(id + FIRST_BASESTATION);
    }

    /** Gets a prototyped host with the provided ID */
    public PrototypedHost getHost(int id) {
        if (id == PROTOTYPING) {//must be better way than this.
            if (vagabond == null) {
                vagabond = PrototypedHost.fromID(PROTOTYPING);
            }
            return vagabond;
        }
        PrototypedHost retu = hosts[id];
        if (retu == null) {
            retu = PrototypedHost.fromID(id);
            hosts[id] = retu;
        }
        return retu;
    }

    /** Tries to get a prototyped host with the provided address */
    public PrototypedHost getHost(String address) {
        for (int id = 0; id < hosts.length; id++) {
            String nAddress = getHost(id).getAddress();
            if (nAddress != null && nAddress.equals(address)) {
                return getHost(id);
            }
        }
        return null;
    }

    /**
     * Way to enable all to be powered up simultaneously:
     * a separate "broadcast signal" to signify that something is happening.
     * If your address is less than that of the other's, then you go first!
     * By going first-- I mean no halting:
     * So if you detect that your address is larger and other thing is prototyping...
     * #1. you have to pause prototyping.
     * Should you still listen... yes. 
     *
     * Way to keep updated addresses: use time:
     * if a hashtable's get() of the address is a sufficiently high time,
     * we assume the host is offline.
     *
     * OR: simply update times as we update the address.
     * If the time interval is greater than 2x, we assume it's dead.
     *
     */
    private void listen() {
        new Thread() {

            public void run() {
                listening = true;
                while (listening) {
                    try {
                        rx.receive(dgRx);
                        byte id = dgRx.readByte();
//                        log("packet: "+ packet  +" received from" + dgRx.getAddress());
                        getHost(id).updateAddress(dgRx.getAddress());

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.start();

    }

    /** True if a better spot is already prototyping */
    private boolean resetPrototyping() {
        //reset only if the reset time is pretty recent: within 2 seconds.
//        return resetPrototyping && System.currentTimeMillis() - resetTime < 2000;
        //we reset if there is a prototyping host && that host is better than us.
        return getHost(PROTOTYPING).isUp() && IEEEAddress.toLong(getHost(PROTOTYPING).getAddress()) < MY_ADDRESS;
    }

    /**
     * Prototypes this host. 
     * Process involves broadcasting our state, listening for other prototypers,
     * and trying repeatedly to get the best available prototype based on our
     * prototype map
     * 
     * @return new prototype
     */
    public int getPrototype() {
        if (prototype == PROTOTYPING) {
            if (!broadcasting) {
                broadcast();
            }
            if (!listening) {
                listen();
            }


            log("Starting protoTyping...");
            prototype = PROTOTYPING;
            int tmpType = PROTOTYPING;
            for (int i = 0; i <= NUM_RUNS_FOR_EQUILIBRIUM; i++) {
                lightBlink();
                //print a cool slider for progress
                log(Util.slider(((double) i) / ((double) NUM_RUNS_FOR_EQUILIBRIUM)));
                //save old type
                int previous = tmpType;
                //new type
                tmpType = IS_BASESTATION ? getBestAvailableBaseType() : getBestAvailableType();
                if (tmpType != previous || resetPrototyping()) {
                    i = 0;
                }
                Util.sleep(PROTOTYPE_POLLTIME);

            }
            prototype = tmpType;

            lightBlueGreen(tmpType);

            //TODO automatic color 
        }
        return prototype;

    }

    /** Gets the best available(unoccupied) prototype for a basestation */
    private int getBestAvailableBaseType() {
        /*
         * To get type, we take the lowest prototype that's available.
         */
        for (int id = FIRST_BASESTATION; id < hosts.length; id++) {
            if (!getHost(id).isUp()) { //if the host isn't up, we take the type
                return id;
            }
        }
        Assert.shouldNotReachHere("type can always be calculated");
        return -1;//TODO error numbers?
    }

    /** Gets the best available(unoccupied) prototype based on the map */
    private int getBestAvailableType() {
        /*
         * To get type, we take the lowest prototype that's available.
         */
        for (int id = 0; id < hosts.length; id++) {
            if (!getHost(id).isUp()) { //if the host isn't up, we take the type
                return id;
            }
        }
        Assert.shouldNotReachHere("type can always be calculated");
        return -1;//TODO error numbers?
    }

    /** Send packets with our state information, or prototype */
    public void broadcast() {
        new Thread() {

            public void run() {
                broadcasting = true;
                while (broadcasting) {
                    try {
                        //send command
                        dgTx.reset();
                        dgTx.writeByte(prototype);
                        tx.send(dgTx);
                        sleep((long) (BROADCAST_SLEEP_TIME + Util.random() * BROADCAST_SLEEP_TIME));
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
     * Get a PrototypeSensor associated with this Prototyper
     */
    synchronized public PrototypeSensor getSensor() {
        if (sensor == null) {
            sensor = new PrototypeSensor(this);
        }
        return sensor;
    }

    private void lightOrange() {
        SLight.get(STATUSLIGHT).rawColor(GRTDemoLED.Color.ORANGE);
    }

    private void lightBlueGreen(int percent) {
        switch (percent) {
            case 0:
                SLight.get(STATUSLIGHT).rawColor(GRTDemoLED.Color.BLUE);
                break;
            case 1:
                SLight.get(STATUSLIGHT).rawColor(GRTDemoLED.Color.GREEN);
                break;
            default:
                SLight.get(STATUSLIGHT).rawColor(GRTDemoLED.Color.RED);
        }
    }

    private void lightBlink() {
        SLight.get(STATUSLIGHT).blinkonBlack();

    }
}
