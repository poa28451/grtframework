package com.grt192.rsh;

import com.grt192.core.GRTObject;
import com.grt192.networking.GRTSocket;
import com.grt192.networking.SocketEvent;
import com.grt192.networking.SocketFactory;
import com.grt192.networking.SocketListener;
import com.grt192.networking.Ports;
import com.grt192.utils.Util;
import com.sun.squawk.util.Arrays;
import java.util.Hashtable;

/**
 * A utility for receiving 'shell' commands for user defined interpreters.
 * @author ajc
 */
public class RSHServer extends GRTObject implements Ports, SocketListener {

    private static class RSHSingleton {

        public static final RSHServer INSTANCE = new RSHServer();
    }

    /**
     * Get the singleton <code>RSHServer</code> that is thread-safe.
     * @return
     */
    public static RSHServer getInstance() {
        return RSHSingleton.INSTANCE;
    }
    protected GRTSocket socket;
    private Hashtable listeners;

    private RSHServer() {
        socket = SocketFactory.createServer(RSH_PORT);
        socket.addSocketListener(this);
        ((Thread) socket).start();
        listeners = new Hashtable();
    }

    /**
     * Listen for commands with this class's name
     * @param c <code>Commandable</code> to command
     */
    public void addListener(Commandable c) {
        addListener(((GRTObject) c).getClassName(), c);
    }

    /**
     * Listen for commands with a given name
     * @param name to listen for to command <code>c</code>
     * @param c <code>Commandable</code> to command
     */
    public void addListener(String name, Commandable c) {
        listeners.put(name, c);
    }

    /**
     * Stop listening for commands of given <code>Commandable</code> using
     * class's name.
     * @param c <code>Commandable</code> to stop listening for
     */
    public void removeListener(Commandable c) {
        removeListener(((GRTObject) c).getClassName());
    }

    /**
     * Stop listening for commands with a given name
     * @param name to stop listening for commands
     */
    public void removeListener(String name) {
        listeners.remove(name);
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
        //tokenize the command
        String[] commands = Util.separateString(e.getData());
        log("RSHServer receive: " + Util.arraytoString(commands));
        notifyListeners(commands);
    }

    private void notifyListeners(String[] args) {
        //move all but first argument to new array
        String[] commands = new String[args.length - 1];
//        (Object[] src, int srcPos, Object[] dest, int destPos, int length)
        Arrays.copy(args, 1, commands, 0, commands.length - 1);
//        log("RSHServer arguments: " + Util.arraytoString(commands));

        //find Commandable with key first args.
        Commandable toCommand = (Commandable) listeners.get(args[0]);
        if (toCommand != null) {
            toCommand.command(commands);
        } else {
            log("Commandable not found...");
        }
    }
}
