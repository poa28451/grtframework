package com.googlecode.grtframework.rpc;

import com.googlecode.grtframework.core.EventPublisher;

/**
 * An RPCConnection is the transport mechanism for sending data between the
 * computer and the microcontroller
 * 
 * @author Andrew Chen <andrewtheannihilator@gmail.com>
 * 
 */
public interface RPCConnection extends EventPublisher {

	public void send(RPCMessage message);

	public void addMessageListener(RPCMessageListener l);

	public void removeMessageListener(RPCMessageListener l);

}
