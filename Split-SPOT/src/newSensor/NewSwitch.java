package newSensor;

import rpc.RPCConnection;
import rpc.RPCMessage;
import sensor.core.HWSensor;

import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.sensorboard.EDemoBoard;

public class NewSwitch extends HWSensor {

	private IIOPin in;
	private final RPCConnection out;
	private final int key;

	public NewSwitch(int pin, int pollTime, String id, RPCConnection out,
			int key) {
		this.out = out;
		this.key = key;
		in = EDemoBoard.getInstance().getIOPins()[pin];
		setSleepTime(pollTime);
		setID(id);

	}

	private boolean getSwitchState() {
		return in.getState();
	}

	protected void poll() {
		setState(0, getSwitchState());

	}

	protected void sensorStateChanged(int id, double prevData, double data) {
		out.send(new RPCMessage(id, data));
	}

}
