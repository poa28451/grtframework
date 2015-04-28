
```

// add needed imports

/**
A mechanism class is an abstraction of all the properties of a mechanism
(what actuators and sensors it has) and what it can do.
For example with a drive train a method you would include here is a method
to change the speed of the motor but you would not include any real logic
as to when and how it should change speed.
*/

public class SwerveMechanism extends Mechanism{
    
    public SwerveMechanism(GRTVictor swerveVictor, GRTVictor moveVictor, GRTEncoder encoder){
        
        this.swerveVictor = swerveVictor;
        this.moveVictor = moveVictor;
        this.encoder = encoder;
        init();
	}
         
    private void init() {
        //start the actuators and sensors
        swerveVictor.start();
        moveVictor.start();
        encoder.start();

        //add actuators and sensors with string tags
        addActuator("SwerveVictor", swerveVictor);
        addActuator("MoveVictor", swerveVictor);
        addSensor("Encoder", encoder);
	}

    public double pollEncoder() {
        return getSensor("Encoder").getState("State");
    }

    public void setDriveSpeed(double speed) {
        getActuator("DriveVictor").enqueueCommand(new Command(speed));
	}

    public void setMoveSpeed(double speed) {
        getActuator("MoveVictor").enqueueCommand(new Command(speed));
    }
    
    //other things that each swerve module can do, if anything.
}

```