Consider the file DriveEventController.java (`com.grt192.controllers.teleop.DriveEventController.java`), which is the code that takes input from joysticks and instructs the robot's base to move motors appropriately for "Tank-drive" locomotion.

http://code.google.com/p/grtframework/source/browse/trunk/CurrentBot/src/com/grt192/controller/teleop/DriveEventController.java

First, we are in a `DriveEventController`, which is an `EventController` and a `JoystickListener`. This means it can operate Robot mechanisms as well as act on Joystick events.

In the constructor we register the `GRTRobotBase` (Drivetrains and sensors involved in moving) with this controller by `addMechanism`-ing it.
```
addMechanism("DriverStation", ds);
```

We also subscribe this controller to events pushed by each joystick, as follows:
```
((GRTJoystick) ds.getSensor("leftJoystick")).addJoystickListener( this);
```

When the x-axis Joystick, changes position, that joystick sends an event and calls `xAxisMoved`...When this happens we do nothing--this is tank drive--as is the case with the z axis and throttle.

When the joystick yAxis is moved, `yAxisMoved()` is called, and we do two things:
1st we figure out if it was the left or right joystick by asking for its ID.
```
e.getSource().getId().equals("left")
```
2nd we tell the mechanism that it should be tankdriving at the new desired speed.
```
base.tankDrive(e.getValue(), base.getRightSpeed());
```