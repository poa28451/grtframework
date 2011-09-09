package sensor.spot;

import java.io.IOException;

import sensor.hardware.HWVoltageSensor;

import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;

public class SpotADC extends HWVoltageSensor {

	private IAnalogInput an;

	public SpotADC(int index, int pollTime, String id) {
		an = EDemoBoard.getInstance().getAnalogInputs()[index];
		setSleepTime(pollTime);
		setID(id);
	}

	public double getVoltage() {
		try {
			return an.getVoltage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ERROR;
	}

}
