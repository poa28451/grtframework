package com.grt192.networking;

/**
 * An interface for using event driven <code>GRTSocket</code>s
 * @author ajc
 */
public interface SocketListener {

	public void onConnect(SocketEvent e);
	public void onDisconnect(SocketEvent e);
	public void dataRecieved(SocketEvent e);
}
