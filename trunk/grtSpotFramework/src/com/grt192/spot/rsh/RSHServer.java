
package com.grt192.spot.rsh;

import com.grt192.core.GRTObject;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketListener;
import com.grt192.radio.Ports;
import com.grt192.radio.RadioServer;
import com.grt192.utils.Util;
import com.sun.squawk.util.Arrays;
import java.util.Hashtable;

/**
 * A tool for receiving commands for user defined interpreters.
 * @author ajc
 */
public class RSHServer extends GRTObject implements Ports, SocketListener{

    private static class RSHSingleton  {
        public static final RSHServer INSTANCE = new RSHServer();
    }

    public static RSHServer getInstance() {
        return RSHSingleton.INSTANCE;
    }

    private RadioServer s;
    private Hashtable listeners;

    private RSHServer() {
        s = new RadioServer(RSH_PORT);
        s.start();
        s.addSocketListener(this);
        listeners = new Hashtable();
    }

    /** Listen for commands with this class's name */
    private void addListener(Commandable c){
        addListener(c.getClass().getName(), c);
    }

    /** Listen for commands with a given name */
    private void addListener(String name, Commandable c){
        listeners.put(name, c);
    }

    public void onConnect(SocketEvent e) {
    }

    public void onDisconnect(SocketEvent e) {
    }

    /** Sends command data to relevant listeners-- by looking at first arg. **/
    public void dataRecieved(SocketEvent e) {
                if (e.getData() == null) {
            System.out.println("null data");
            return;
        }
        log("new data: " + Util.arraytoString(Util.separateString(e.getData())));
        String[] commands = Util.separateString(e.getData());
        notifyListeners(commands);
    }

    private void notifyListeners(String[] args){
        String[] commands = new String[args.length-1];
//        (Object[] src, int srcPos, Object[] dest, int destPos, int length)
        Arrays.copy(args, 1, commands, 0, commands.length-1);
        log("command arguments: " +Util.arraytoString(commands));
        ((Commandable) listeners.get(args[0])).command(args);
    }

    

}
