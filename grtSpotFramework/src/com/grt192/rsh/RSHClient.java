
package com.grt192.rsh;

import com.grt192.networking.GRTSocket;
import com.grt192.networking.Ports;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketFactory;
import com.grt192.networking.SocketListener;
import java.util.Hashtable;

/**
 *
 * @author ajc
 */
public class RSHClient implements SocketListener,Ports {

    public synchronized static RSHClient getClient(String host){
        RSHClient rshc = (RSHClient) instances.get(host);
        if(rshc == null){
            rshc = new RSHClient(host);
        }
        return rshc;
    }

    private static Hashtable instances = new Hashtable();

    private final String host;

    private GRTSocket socket;

    private RSHClient(String host){
        this.host = host;
        socket = SocketFactory.createClient(host, RSH_PORT);
        socket.addSocketListener(this);
        ((Thread)socket).start();
    }

    public void send(String commands){
        socket.sendData(commands);
    }

    public void onConnect(SocketEvent e) {
    }

    public void onDisconnect(SocketEvent e) {
    }

    public void dataRecieved(SocketEvent e) {
    }

}
