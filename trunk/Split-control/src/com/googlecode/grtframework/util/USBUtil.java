package com.googlecode.grtframework.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 
 * @author ajc
 * 
 */
public class USBUtil {

	/**
	 * 
	 * @return
	 */
	public static ArrayList<String> getPorts() {
		ArrayList<String> ports = new ArrayList<String>();

		CommPortIdentifier portId;
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();

		// System.out.println(portList);
		// System.out.println();
		// if (portList.hasMoreElements()) {
		// System.out.println("Ports found:");
		// } else {
		// System.out.println("No ports found!");
		// }
		while (portList.hasMoreElements()) {
			// portId = (CommPortIdentifier) portList.nextElement();
			// System.out.println("-> " + portId.getName());
			ports.add(((CommPortIdentifier) portList.nextElement()).getName());

		}
		return ports;
	}

	public static boolean isSPOT(String device) {
		return device.contains("/dev/tty");
	}

	public static ArrayList<String> getSpotPorts() {
		ArrayList<String> ports = getPorts();
		for (String port : ports) {
			if (!isSPOT(port)) {
				ports.remove(port);
			}
		}
		return ports;
	}

	/**
	 * TODO how should we handle exceptions here
	 * 
	 * @param identifier
	 * @return
	 * @throws NoSuchPortException
	 * @throws PortInUseException
	 */
	public static SerialPort getSerialPort(String address)
			throws NoSuchPortException, PortInUseException {
		CommPortIdentifier portIdentifier = CommPortIdentifier
				.getPortIdentifier(address);

		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			CommPort commPort = portIdentifier.open(USBUtil.class.getName(),
					2000);
			// portIdentifier.open

			// commPort.getOutputStream().

			if (commPort instanceof SerialPort) {
				// System.out.println("We're serial!");
				SerialPort serialPort = (SerialPort) commPort;
				// try {
				// serialPort.setBaudBase(115200);
				System.out.println(serialPort.getBaudRate());
				// } catch (UnsupportedCommOperationException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				return serialPort;
				// in = serialPort.getInputStream();
				//
				// serialPort.addEventListener(this);
				// serialPort.notifyOnDataAvailable(true);

			} else {
				System.out
						.println("Error: Only serial ports are handled by this example.");
			}
		}
		return null;
	}

}
