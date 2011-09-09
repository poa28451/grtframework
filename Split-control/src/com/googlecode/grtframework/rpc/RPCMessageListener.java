package com.googlecode.grtframework.rpc;

import com.googlecode.grtframework.core.EventListener;

/**
 * 
 * @author ajc
 * 
 */
public interface RPCMessageListener extends EventListener {

	public void messageReceived(RPCMessage message);
}
