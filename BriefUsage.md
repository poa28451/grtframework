# Introduction #

The GRTFramework is built in such a way that developers can easily develop sophisticated code for their robots, without the hassles of execution management or pseudokernels. Understanding how to use it is mostly a function of figuring out what to ignore, and where your code goes!

# Details #

All user code in the GRTFramework falls under either [Mechanism](Mechanism.md), [Controller](Controller.md), or MainRobot. For example of these explore the classes already contained under ` com.grt192.controller.* and com.grt192.mechanism.* `

## Mechanisms ##

A [Mechanism](Mechanism.md) is a _model_ of a robot mechanism, containing all the [actuators](Actuator.md) (motors, servos, pneumatics) and [sensors](Sensor.md) that are a part of that mechanism's function. Examples include: Arms, Shooting mechanisms, Elevators, and Wheeled-bases.

A mechanism is an object--it is not executed continuously, but it has methods that allow for specific actions to take place.

### Usage ###

For each of the mechanisms on your robot:
  1. Create a class that ` extends Mechanism `
  1. Create a constructor for that class with parameters of ALL the electronic components you will use:
```
public GRTRobotBase(GRTDriveTrain dt, GRTGyro gy,
            GRTAccelerometer ax) {
```
  1. For each of the electronic components, start it and then add it to the mechanism as either a Sensor or an Actuator:
```
        gy.start();
        ax.start();
        dt.start();
        addActuator("DriveTrain", dt);
        addSensor("Gyro", gy);
        addSensor("Accelerometer", ax);
```
  1. Write high-level methods associated with that mechanism--for robotbase that means driving & turning commands(driveForward, turnLeft..etc)

### A Word About Writing "High-Level" Methods ###
First, to get your actuators out from your mechanism, just call
```
this.getActuator("actuatorName");
```

It may actually be more convenient for you to have private variables for each of the actuators you use, pointing to the objects that are in the hashtable:

```
//in declaration
private GRTDriveTrain drive; 

...

//to call
this.drive.enqueueCommand(speed);
```

Ok so there's one trick you should know about actually using [Actuators](Actuator.md) (GRTVictor, GRTServo, GRTJaguar..etc)
You can't call GRTJaguar.setSpeed(XX). Instead what you need to do is send it a [Command](Command.md), which it will execute as it is available.
```
   getActuator("Victor").enqueueCommand(speed);
```

If you need a [command](Command.md) to supercede any pending [commands](Command.md)(ie RUN RIGHT NOW), you can

```
   getActuator("Victor").doCommand(speed);
```


You can issue as many commands as you like, whenever you like, and as long as an actuator is not suspended or disabled, it'll do them. However, note that this is NOT recommended.

## Controllers ##

Once your [mechanisms](Mechanism.md) are written, all you need to do is write the logic that controls them...This logic sits in [Controllers](Controller.md), which are independently running (Threads) that exercise control over a [mechanism](Mechanism.md) or many [mechanisms](Mechanism.md)

### Usage ###

  1. Create a class ` extends Controller `
  1. Create a constructor for that class with parameters of ALL the mechanisms you will use, and "addMechanism" them:
```
    public DriveController(GRTRobotBase rb, GRTDriverStation ds) {
        super();
        addMechanism("DriverStation", ds);
        addMechanism("RobotBase", rb);
    }
```
  1. Write a run() method that contains your logic!

### A Word About StepControllers ###
A StepController is a variant on the [controller](Controller.md) system, where instead of populating a run() method with an inifinite loop, you can simply write an act() method, that is executed repeatedly inside a loop that happens behind the scenes. This allows for easy statemachine code(each act call executes a different piece of code based on the state), and our code behind the scenes takes care of thread execution safety and cleanup.

## MainRobot ##
This is really really simple even though it looks super long.
A MainRobot houses the [Controller](Controller.md) and [Mechanism](Mechanism.md) instances, and instantiates all of them on VM start. see the included example for more information.