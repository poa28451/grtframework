package actuator.instance;

import actuator.Actuator;
import actuator.ActuatorException;
import actuator.Command;

import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.Servo;

public class GRTServo extends Actuator {

	private Servo servo;

	public GRTServo(int pin) {
		IOutputPin iOutputPin = EDemoBoard.getInstance().getOutputPins()[pin];
		servo = new Servo(iOutputPin);
	}

	// takes a pulse width size, assigns to servo
	protected void executeCommand(Command c) throws ActuatorException {
		System.out.println(c.getValue());
		servo.setValue((int) c.getValue());
	}

	protected void halt() {
		// TODO Auto-generated method stub

	}

}
