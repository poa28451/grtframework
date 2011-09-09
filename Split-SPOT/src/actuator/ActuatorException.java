package actuator;

public abstract class ActuatorException extends Exception {

	private Actuator source;

	public ActuatorException(Actuator source) {
		this.source = source;
	}

	public Actuator getSource() {
		return source;
	}

}
