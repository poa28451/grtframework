package sensor.spot;

import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.sensorboard.EDemoBoard;

import sensor.hardware.HWSwitch;

public class SensorboardSwitch extends HWSwitch {
    
    private ISwitch sw;

	/**
	 * 
	 * @param id
	 * @param pollTime
	 * @param name
	 */
	public SensorboardSwitch(int id, int pollTime, String name) {
             sw = EDemoBoard.getInstance().getSwitches()[id];
             setSleepTime(pollTime);
             setID(name);
	}

	public boolean getSwitchState() {
            return sw.isClosed();
	}

}
