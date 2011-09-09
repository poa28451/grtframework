package com.googlecode.grtframework.rpc;

import com.googlecode.grtframework.core.EventPublisher;

public interface RPCConnection extends EventPublisher {

	public void send(RPCMessage message);

	public void addMessageListener(RPCMessageListener l);

	public void removeMessageListener(RPCMessageListener l);

}
