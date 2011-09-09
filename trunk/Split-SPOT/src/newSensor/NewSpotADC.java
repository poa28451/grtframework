package newSensor;

import java.io.IOException;

import rpc.RPCConnection;
import rpc.RPCMessage;
import sensor.core.HWSensor;

import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;

public class NewSpotADC extends HWSensor {

	private final RPCConnection out;
	private final int key;

	private IAnalogInput an;

	public NewSpotADC(int pin, int pollTime, String id, RPCConnection out,
			int key) {

		this.out = out;
		this.key = key;
		an = EDemoBoard.getInstance().getAnalogInputs()[pin];
		setSleepTime(pollTime);
		setID(id);
	}

	private double getVoltage() {
		try {
			return an.getVoltage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ERROR;
		}
	}

	protected void poll() {
		setState(0, getVoltage());

	}

	protected void sensorStateChanged(int id, double prevData, double data) {
		if (id == 0) {
			out.send(new RPCMessage(key, data));
		}
	}

}
