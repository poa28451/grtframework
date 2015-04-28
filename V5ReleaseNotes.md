# Release notes v5 #

The GRTFramework has been tested under the 2011 FRC Java SDK with its updates to the WPILib and Squawk VM. All features are currently working, and new 2011 systems are supported. This includes support for the CAN interface for Jaguars, the I2C interface to the ADXL345, and the new Line Detecting sensors (Rockwell Automation).

In addition, new I/O options are now integrated into the framework. These include support for server and client sockets utilizing TCP/IP (accompanied by event support for this I/O), File I/O and JSON serialization to persistent storage on the compactRIO, and enhanced logging over the network and to files.

Users should note that addon classes for WPILibJ still are present in this release of the GRTFramework (under the `edu.wpilibj.*` packages), even though these features have been included in the latest builds of the FRC SDK. This is the case to ensure stability and backwards compatibility, as these classes have been tested more thoroughly in the framework than the new ones. So far as we have tested, they provide the same functionality provided in the new SDK, no more or less.In the coming builds, however, these addons will be replaced by calls to the SDK's equivalents.

Please report any bugs to the bug tracker, and enjoy this build!