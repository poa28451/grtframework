The GRT Framework is a system that allows for high level, event-driven development of robot controls though abstractions of hardware and a friendly API, while managing the continuous operation and availability of robot components.

**Updated to V5.2**
  * _Support for 2011.4 Update with CANExceptions_
  * Backwards Compatible with all GRTFramework Builds
  * Improved Event Selection by Threshold for Gyro and ADXL345
  * New Logging Framework
  * Out-of-the-box Dashboard Support
  * Added Line Sensor for 2011 Season

For developers this means that robot code can be developed in modules for specific mechanisms(eg a robotic arm), and then can be controlled using modular, mechanism-specific controllers--which contain logic.
Further, the framework's event model allows robot code to respond to circumstances as they change--if a switch is pressed you can respond to it immediately.

The GRT Framework is implemented in Java for the Squawk embedded JVM(https://squawk.dev.java.net/), and uses WPI's WPILibJ for the FIRST Robotics Competition.