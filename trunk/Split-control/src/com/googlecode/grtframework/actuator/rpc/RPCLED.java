package com.googlecode.grtframework.actuator.rpc;

import com.googlecode.grtframework.rpc.RPCConnection;
import com.googlecode.grtframework.rpc.RPCMessage;

/**
 * 
 * 
 * @author ajc
 * 
 */
public class RPCLED {

	private final int key;
	private final RPCConnection out;

	public RPCLED(int key, RPCConnection out) {
		this.key = key;
		this.out = out;
	}

	public void lightValue(int value) {
		out.send(new RPCMessage(key, value));
	}

}
