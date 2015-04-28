# Introduction #

EventControllers manipulate mechanisms on events, for example, JoyStickEvent. They can also be altered to also behave like step controllers, which is the distinction between Partial and Standard Event Control


# Standard Event Control #
Standard Implementation produces an exclusively event based controller.
  1. Determine what events the controller should listen to.
  1. Have the controller implement the appropriate interfaces;
```
public class KitBotTeleopDriveController extends StepController implements ButtonListener, GyroListener { 
```
  1. Override EventController's startListening() and stopListening() methods appropriately. These are crucial as these will be called on Controller.start() and Controller.stopControl().
```
public void startListening() {
        ((GRTJoystick) getMechanism("DriverStation").getSensor("rightJoystick")).addButtonListener(this);
    }

public void stopListening() {
        ((GRTJoystick) getMechanism("DriverStation").getSensor("rightJoystick")).removeButtonListener(this);
    }
```
  1. Implement events as desired
```
    public void didReceiveAngle(GyroEvent e) {
        logVar("Angle ", e.getAngle());
    }
```

# Partial Event Control #
Sometimes it may be useful to have a controller actuate on its own without an event. To do this, simply implement EventController.run();
```
    public void run() {
        running = true;
        while (running) {
            think();
            tune();
            Util.sleep(50);
        }
    }
```
This will be run when start() is called, which also calls startListening(). No other work is necessary, as run is always called, but in normal instances of EventController it immediately returns.