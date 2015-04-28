
```

import com.grt192.core.EventController;
//Add other needed imports

	/** 
	This class extends EventController. This basically means that actions and changes
	of state will only occur if a sensor event of some kind (Joystick moves, encoder postition chages, etc).
	Almost all robot code can be written this way.
	You then implement all the sensors that this class needs to listen for.
	*/
	
public class SwerveController extends EventController implements JoystickListener{
    
    private SwerveMechanism module1;
    private GRTDriverStation driverStation;r
    
    /**
	The constructor should take in all relevant mechanisms. 
	This class is used to tell the mechanisms what to do,
	when to do it, and how to do it.   
    */
    public SwerveController(SwerveMechanism module1 GRTDriverStation ds){
        this.module1 = module1;
        //same for other modules
        
        init();
	}
		
    private void init(){
		// Here you are registering the mechanisms to the hashtable.
			this.addMechanism("dt", diveTrain);
			this.addMechanism("ds", driverStation);
			
		// add listeners to actually tell the object to listen for sensor 
		// whether they are joysticks, encoders, or switches, etc
			((GRTJoystick)ds.getSensor("leftJoystick")).addJoystickListener(this);
			((GRTJoystick) ds.getSensor("rightJoystick")).addJoystickListener(this);
			((GRTEncoder) module1.getSensor("Encoder").addEncoderListener(this));
	}
			
//Methods that control the robot using all of the SwerveMechanisms
//Eg. vectorMove, scalarMove, spin

//Joystick example

   public void yAxisMoved(JoystickEvent e) {
		if (e.getSource().getId().equals("leftJoystick")) {
			//methods that you want to happen when you move the stick.
		} else if 
		(e.getSource().getId().equals("rightJoystick")) {
			//same as above
		}

      // In our code the tank drive method is called to change the speed and direction of the drivetrain depending on the position of the joysticks.
//             
//		driveTrain.tankDrive(driverStation.getYLeftJoystick(),     
//				driverStation.getYRightJoystick());
//

		}
}

```