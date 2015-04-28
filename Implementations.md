# Introduction #

Beyond the basic framework and abstractions, the GRTFramework provides a method by which more sophisticated logic and abstractions can be developed, provided a particular code structure is followed in line with design methodology.

# Details #

User code is split into two major parts, being mechanisms and controllers. Additionally an initiating file (the class selected in the manifest, extending GRTRobot...) constructs these components and provides them access to any hardware resources that are necessary.

### Mechanisms ###

Mechanisms are collections of sensors and actuators that represent a specific robot assembly, providing an API by which that assembly can be controlled globally, as opposed to in terms of its individual parts. Mechanisms should not operate on their own, simply translating abstract commands into specific hardware directives. Mechanism may be nested, and also may provide closed loop operation--for an example of closed loop operation see the ` GRTCompressor.java ` class, where a switch stops the compressor without the intervention of a controller.

### Controllers ###

Controllers are governing 'applications' that provide instructions for the various mechanisms on a robot. A controller may operate over one or more mechanisms, defining the logic by which each assembly is used. Controllers may be fully autonomous or draw on sensor or user feedback to command Mechanisms in the appropriate way. As a matter of abstraction, Controllers should never have direct access to Sensors or Actuators.

Many controllers can run on the same robot completely in parallel, and controllers may share mechanisms as is needed. Controllers can communicate through the globals hashtable provided via GRTRobot.


---


In summary, Controllers decide when the robot should do something, and instruct entire mechanisms to do that sequence of instructions, which that mechanism translates into a set of actuator commands or sensor tasks and instructs the appropriate hardware resources accordingly.

```
Controller(s)
       |
       '--- Mechanism(s)
                       |
                       |--- Actuator(s)
                       '--- Sensor(s)
```