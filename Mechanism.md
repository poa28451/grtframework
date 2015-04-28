Mechanism is an abstraction of a mechanism on a robot, such as an arm, a kicker, or a drivetrain. This allows for commands such as `kicker.kick();` which are more clear and intuitive than having to say something along the lines of `kickerMotor.setSpeed(1);` when we have many many motors on the robot.

Further this allows for encapsulating a series of coordinated actions -- for example, moving an arm might require a motor to be turned on for a certain amount of time, then reversed, then turned off. The three motor instructions that correspond to these actions can be hidden within the `kick()` method, isolated from other parts of the robot.

Each mechanism has ownership of actuators and sensors, directly representing a physical robot part--a robotic arm physically has a combination of motors and switches. These actuators and sensors are contained within the mechanism, and are not accessible to other mechanisms. This is akin to the separation of our digestive system from one of our legs. A leg has possession of the muscles and sensory neurons in the leg, while the digestive system has possession of its own muscles and sensory neurons. Neither should be confused with the other in the brain.

Mechanisms are controlled by Controllers, which are used for all the logic inherent to the mechanism. This includes things like using sensor data from an encoder to turn a wheel to a certain position. This is analogous to our nervous system communicating with one of our arms. Our arms won't react to a hot piece of metal without the reflex arc to the spinal cord telling it to pull away.

To start, one should specify the actuators and sensors associated with the mechanism as fields.
While this is not actually required, it generally makes working with them easier. _Note that there is no memory cost here, because we are handling pointers to Objects, not the objects themselves._

```
public class Kicker extends Mechanism { 
    //Fields
    private boolean kicking;
    //Actuators
    private GRTJaguar kickingMotor;
    //Sensors
    private GRTSwitch loadingSwitch;

}
```

We then declare a constructor for the entire mechanism, wherein a particular instance of a this mechanism is initialized. This is a three step process:

1) Give the mechanism ownership over sensors and actuators

```
public Kicker(GRTJaguar jaguar, GRTSwitch limitSwitch) {
        kicking = false;
        this.kickingMotor = jaguar;
        this.limitSwitch = limitSwitch;
```

2) Notify the actuators and sensors that they should be ready to use

```
        //start jaguar, limitSwitch
        jaguar.start();
        limitSwitch.start();
```

3) Register the actuators and sensors with this mechanism for automatic management

```
        //add actuators of jaguar and limitSwitch with string tags
        addActuator("Motor", jaguar);
        addSensor("LimitSwitch", limitSwitch);
 }
```

Next we can get data from the sensors by asking the sensor for its state:

```
    public boolean hitSwitch() {
        //you can also do
        //return limitSwitch.getState("State") == SWITCH_HIT;
        return getSensor("LimitSwitch").getState("State") == SWITCH_HIT;
    }
```

To move an actuator, we enqueue commands at the end of the queue of things this actuator must do. _Note that if you want to make a command **high priority** or superseding all others, use `actuator.doCommand()`. This is NOT recommended. (More detail in Actuator)._

```
    public void setSpeed(double speed) {
        getActuator("Motor").enqueueCommand(new Command(speed));
    }
```

An example of using setSpeed:

```
    public void kick() {
        setSpeed(KICK_SPEED);
        kicking = true;
    }
```

It's only a matter of using these methods to achieve further abstraction in the Controller.