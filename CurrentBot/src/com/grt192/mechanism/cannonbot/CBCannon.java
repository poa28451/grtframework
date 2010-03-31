package com.grt192.mechanism.cannonbot;

import com.grt192.actuator.GRTSolenoid;
import com.grt192.core.Mechanism;
import com.grt192.mechanism.GRTCompressor;
import com.grt192.sensor.GRTSwitch;

public class CBCannon extends Mechanism {

	private GRTSolenoid tankSelectorSolenoid;
	private GRTSolenoid tankASolenoid;
	private GRTSolenoid tankBSolenoid;
	private GRTCompressor cannonCompressor;
	private GRTSwitch fireSwitch;

	public CBCannon(int tasolpin, int tbsolpin, int tssolpin) {
		this(new GRTSolenoid(tasolpin), new GRTSolenoid(tbsolpin),
				new GRTSolenoid(tssolpin));

	}

	public CBCannon(GRTSolenoid tankasol, GRTSolenoid tankbsol,
			GRTSolenoid tankselecsol) {

		this.tankASolenoid = tankasol;
		tankASolenoid.start();

		this.tankBSolenoid = tankbsol;
		tankBSolenoid.start();

		this.tankSelectorSolenoid = tankselecsol;
		tankSelectorSolenoid.start();

		this.fireSwitch = new GRTSwitch(3, 9, "fireswitch");
		fireSwitch.start();

		this.cannonCompressor = new GRTCompressor(1, 1);
		cannonCompressor.startCompressor();

		addActuator("tankasol", tankASolenoid);
		addActuator("tankbsol", tankBSolenoid);
		addActuator("tankselecsol", tankSelectorSolenoid);
		addSensor("fireswitch", fireSwitch);

	}
}