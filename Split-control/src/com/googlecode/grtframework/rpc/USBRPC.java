package com.googlecode.grtframework.rpc;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.io.IOException;

import com.googlecode.grtframework.util.USBUtil;

/**
 * USB wrapper
 * 
 * @author ajc
 * 
 */
public class USBRPC implements RPCConnection {

	RPCStream conn;

	/**
	 * 
	 * @param spotID
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 * @throws IOException
	 */
	public USBRPC(int spotID) throws NoSuchPortException, PortInUseException,
			IOException {
		System.out.println(USBUtil.getSpotPorts());
		SerialPort comm = USBUtil.getSerialPort(USBUtil.getSpotPorts().get(
				spotID));
		conn = new RPCStream(comm.getInputStream(), comm.getOutputStream());
		conn.start();
	}

	/**
	 * 
	 * @param id
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 * @throws IOException
	 */
	public USBRPC(String address) throws NoSuchPortException,
			PortInUseException, IOException {
		SerialPort comm = USBUtil.getSerialPort(address);
		conn = new RPCStream(comm.getInputStream(), comm.getOutputStream());
		conn.start();
	}

	public void start() {
		conn.start();
	}

	@Override
	public void addMessageListener(RPCMessageListener l) {
		conn.addMessageListener(l);
	}

	@Override
	public void removeMessageListener(RPCMessageListener l) {
		conn.removeMessageListener(l);
	}

	@Override
	public void send(RPCMessage message) {
		conn.send(message);
	}

}
