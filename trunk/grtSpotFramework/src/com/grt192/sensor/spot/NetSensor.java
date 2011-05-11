package com.grt192.sensor.spot;

import com.grt192.controller.spot.TelemetryServer;
import com.grt192.core.Sensor;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.grt192.networking.spot.RadiogramClient;
import com.grt192.utils.Util;

/**
 * A NetSensor reads Sensor data from a matching <code>TelemetryServer</code>, 
 * effectually making a copy of a remote sensor locally. Events are not replicated.
 * @author ajc
 */
public class NetSensor extends Sensor implements SocketListener {

    private RadiogramClient client;

    public NetSensor(String sourceAddress, int port) {
        client = new RadiogramClient(sourceAddress, port);
        client.start();
//        setPrinting(true);
    }

    public void start() {
        client.addSocketListener(this);
        //these don't start.
    }

    public void poll() {
        //no poll: only on event
    }

    public void onConnect(SocketEvent e) {
    }

    public void onDisconnect(SocketEvent e) {
    }

    public void dataRecieved(SocketEvent e) {
        String s = e.getData();
        String type = s.substring(0, s.indexOf(TelemetryServer.SEPARATOR));
        String message = s.substring(s.indexOf(TelemetryServer.SEPARATOR) + 1);
        setState(type, Util.doubleValue(message));
//        log("Type:\t" + type);
//        log("Data:\t" + message);
    }
}
