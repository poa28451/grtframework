package actuator.instance;

import actuator.Actuator;
import actuator.ActuatorException;
import actuator.Command;
import actuator.CommandArray;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.sensorboard.EDemoBoard;

public class TriLED extends Actuator {

	private ITriColorLED led;

	public TriLED(int index) {
		led = EDemoBoard.getInstance().getLEDs()[index];
		led.setOn();
	}

	public void executeCommand(Command c) throws ActuatorException {
		if (c instanceof CommandArray) {
			CommandArray ca = (CommandArray) c;
			led.setRGB((int) ca.getValue(0), (int) ca.getValue(1),
					(int) ca.getValue(2));
		}

	}

	protected void halt() {
		led.setRGB(0, 0, 0);
	}

}
