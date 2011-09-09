package rpc;

import core.EventListener;

/**
 * 
 * @author ajc
 * 
 */
public interface RPCMessageListener extends EventListener {

	public void messageReceived(RPCMessage message);
}
