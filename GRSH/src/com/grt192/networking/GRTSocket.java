package com.grt192.networking;

/**
 * A <code>GRTSocket</code> is a generic bi-directional connection to another
 * <code>GRTSocket</code>
 * @author ajc
 */
public interface GRTSocket {

    /**
     * Send a <code>String</code> data to all <code>GRTSocket</code> clients or
     * single server.
     * @param data
     */
    public void sendData(String data);

    /**
     * Gets the connection state of the <code>GRTSocket</code>
     * @return true if there is more than 1 client or if server is connected
     */
    public boolean isConnected();

    /**
     * Connect to another <code>GRTSocket</code> 
     */
    public void connect();

    public void disconnect();

    /**
     * Add <code>s</code> as a listener for <code>GRTSocket</code> events.
     * @see SocketListener
     * @param s
     */
    public void addSocketListener(SocketListener s);

    /**
     * Remove <code>s</code> as a listener for <code>GRTSocket</code> events.
     * @see SocketListener
     * @param s
     */
    public void removeSocketListener(SocketListener s);
}
