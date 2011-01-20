package com.grt192.actuator;

import com.grt192.actuator.exception.GRTCANJaguarException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.grt192.sensor.canjaguar.GRTJagEncoder;
import com.grt192.sensor.canjaguar.GRTJagPowerSensor;
import com.grt192.sensor.canjaguar.GRTJagSwitch;

import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.PIDOutput;

public class GRTCANJaguar extends Actuator implements PIDOutput {
    // Control Modes

    public static final int PERCENT_CONTROL = 1;
    public static final int SPEED_CONTROL = 2;
    public static final int POSITION_CONTROL = 3;
    public static final int CURRENT_CONTROL = 4;
    // Position Sensors
    public static final int POTENTIOMETER = 0;
    public static final int ENCODER = 1;
    
    //Speed Sensors 
    public static final int INV_ENCODER = 2;
    public static final int QUAD_ENCODER = 3;
    
    // Neutral Modes
    public static final int COAST = 0;
    public static final int BRAKE = 1;
    public static final int JUMPER = 2;
    
    public static final int ERROR = -999;
    
    private CANJaguar jaguar;
    private GRTJagEncoder encoder;
    private GRTJagPowerSensor powerSensor;
    private GRTJagSwitch switches;

    /**
     * Constructs  GRTCANJaguar on a channel and in a default control mode of 0
     * @param channel
     */
    public GRTCANJaguar(int channel) {
        this(channel, 0);
    }

    /**
     * Constructs GRTCANJaguar at a certain channel and using a specified control
     * @param channel
     * @param controlMode
     */
    public GRTCANJaguar(int channel, int controlMode) {
        try{
            switch (controlMode) {
                case PERCENT_CONTROL:
                    jaguar = new CANJaguar(channel,
                            CANJaguar.ControlMode.kPercentVbus);
                    break;
                case SPEED_CONTROL:
                    jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kSpeed);
                    break;
                case POSITION_CONTROL:
                    jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kPosition);
                    break;
                case CURRENT_CONTROL:
                    jaguar = new CANJaguar(channel, CANJaguar.ControlMode.kCurrent);
                    break;
                default:
                    jaguar = new CANJaguar(channel);
            }
        }catch(CANTimeoutException e){
           e.printStackTrace();
        }
    }
    
    public void changeControlMode(int controlMode){
        try{
            switch (controlMode) {
                case PERCENT_CONTROL:
                    jaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
                    break;
                case SPEED_CONTROL:
                    jaguar.changeControlMode(CANJaguar.ControlMode.kSpeed);
                    break;
                case POSITION_CONTROL:
                    jaguar.changeControlMode(CANJaguar.ControlMode.kPosition);
                    break;
                case CURRENT_CONTROL:
                    jaguar.changeControlMode(CANJaguar.ControlMode.kCurrent);
                    break;
                default:
                    jaguar.changeControlMode(CANJaguar.ControlMode.kPercentVbus);
            }
        }catch(CANTimeoutException e){
           e.printStackTrace();
        }
    }

    /**
     *Sets the encoder or potentiometer used to gauge position
     * @param sensor
     */
    public void setPositionSensor(int sensor) {
        try{
            if (sensor == ENCODER) {
                jaguar.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
            } else if (sensor == POTENTIOMETER) {
                jaguar.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
            } else {
                jaguar.setPositionReference(CANJaguar.PositionReference.kNone);
            }
        }catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }


    public void setSpeedSensor(int sensor){
        try{
            if (sensor == QUAD_ENCODER) {
                jaguar.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
            } else if (sensor == ENCODER) {
                jaguar.setSpeedReference(CANJaguar.SpeedReference.kEncoder);
            } else if(sensor == INV_ENCODER){
                jaguar.setSpeedReference(CANJaguar.SpeedReference.kInvEncoder);
            } else {
                jaguar.setSpeedReference(CANJaguar.SpeedReference.kNone);
            }
        }catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }
    
    /**
     *Set PID controls
     * @param p
     * @param i
     * @param d
     */
    public void setPID(double p, double i, double d) {
        try {
            jaguar.setPID(p, i, d);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param p
     */
    public void setP(double p) {
        try {
            jaguar.setPID(p, jaguar.getI(), jaguar.getD());
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param i
     */
    public void setI(double i) {
        try {
            jaguar.setPID(jaguar.getP(), i, jaguar.getD());
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param d
     */
    public void setD(double d) {
        try {
            jaguar.setPID(jaguar.getP(), jaguar.getI(), d);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @return
     */
    public double getP() {
        try {
            return jaguar.getP();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *
     * @return
     */
    public double getI() {
        try {
            return jaguar.getI();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *
     * @return
     */
    public double getD() {
        try {
            return jaguar.getD();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *
     */
    public void enableClosedLoop() {
        enableClosedLoop(0.0);
    }

    /**
     *
     * @param initialPosition
     */
    public void enableClosedLoop(double initialPosition) {
        try {
            jaguar.enableControl(initialPosition);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void disableClosedLoop() {
        try {
            jaguar.disableControl();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }    
    }

    /**
     *Will return the input voltage of the jaguar speed controller
     * @return
     */
    public double getInputVoltage() {
        try {
            return jaguar.getBusVoltage();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *Will return the output voltage of the jaguar
     * @return
     */
    public double getOutputVoltage() {
        try {
            return jaguar.getOutputVoltage();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;   
    }

    /**
     *Will return the output current of the jaguar
     * @return
     */
    public double getOutputCurrent() {
        try {
            return jaguar.getOutputCurrent();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *will return the temperature in the Jaguar
     * @return
     */
    public double getTemperature() {
        try {
            return jaguar.getTemperature();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *Get the position of the encoder or potentiometer.
     * @return
     */
    public double getPosition() {
        try {
            return jaguar.getPosition();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *Gets the Speed of the motor in RPM
     * @return
     */
    public double getSpeed() {
        try {
            return jaguar.getSpeed();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *The motor can turn forward if true
     * @return
     */
    public boolean getLeftLimitStatus() {
        try {
            return jaguar.getForwardLimitOK();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *The motor can turn backward if true
     * @return
     */
    public boolean getRightLimitStatus() {
        try {
            return jaguar.getReverseLimitOK();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     *Set the maximum voltage change rate.
     *
     * When in percent voltage output mode, the rate at which the voltage changes can
     * be limited to reduce current spikes.  Set this to 0.0 to disable rate limiting.
     *
     * @param rate
     */
    public void setVoltageRampRate(double rate) {
        try{
            jaguar.setVoltageRampRate(rate);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *Sets the encoder resolution
     * @param countsPerRev
     */
    public void setEncoderResolution(int countsPerRev) {
        try{
            jaguar.configEncoderCodesPerRev(countsPerRev);
        }catch(CANTimeoutException e){
            e.printStackTrace();
        }    
    }

    /**
     * Configure Soft Position Limits when in Position Controller mode.
     *
     * When controlling position, you can add additional limits on top of the limit switch inputs
     * that are based on the position feedback.  If the position limit is reached or the
     * switch is opened, that direction will be disabled.
     *
     *
     * @param leftLimit
     * @param rightLimit
     */
    public void setSoftLimits(double leftLimit, double rightLimit) {
        try{
            jaguar.configSoftPositionLimits(leftLimit, rightLimit);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void disableSoftLimits() {
        try {
            jaguar.disableSoftPositionLimits();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     * Configure what the controller does to the H-Bridge when neutral (not driving the output).
     *
     * This allows you to override the jumper configuration for brake or coast.
     *
     * @param mode
     */
    public void setNeutralMode(int mode) {
        try{
            switch (mode) {
                case COAST:
                    jaguar.configNeutralMode(CANJaguar.NeutralMode.kCoast);
                    break;
                case BRAKE:
                    jaguar.configNeutralMode(CANJaguar.NeutralMode.kBrake);
                    break;
                case JUMPER:
                    jaguar.configNeutralMode(CANJaguar.NeutralMode.kJumper);
                    break;
            }
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     * Configure the number of turns on the potentiometer.
     *
     * There is no special support for continuous turn potentiometers.
     * Only integer numbers of turns are supported.
     *
     * @param turns
     */
     public void setPotentiometerTurns(int turns){
         try {
             jaguar.configPotentiometerTurns(turns);
         }catch(CANTimeoutException e){
             e.printStackTrace();
         }
     }

    /**
     * Get the recently set outputValue setpoint.
     *
     * In PercentVoltage Mode, the outputValue is in the range -1.0 to 1.0
     *
     * @return
     */
    public double getLastCommand() {
        try {
            return jaguar.getX();
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    /**
     *
     * @param c
     * @throws GRTCANJaguarException
     */
    protected void executeCommand(Command c) throws GRTCANJaguarException {
        try {
            double value = c.getValue();
            jaguar.setX(value);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
        // TODO throw fault exceptions
    }

    protected void halt() {
        try {
            jaguar.setX(0);
        } catch(CANTimeoutException e){
            e.printStackTrace();
        }
    }

    /**
     * @deprecated
     * @param output
     */
    public void pidWrite(double output) {
        jaguar.pidWrite(output);
    }

    /**
     *
     * @return
     */
    public GRTJagEncoder getEncoder() {
        if (encoder == null) {
            encoder = new GRTJagEncoder(this, 25, "Encoder" + this);
            encoder.start();
        }
        return encoder;
    }

    /**
     *
     * @return
     */
    public GRTJagPowerSensor getPowerSensor() {
        if (powerSensor == null) {
            powerSensor = new GRTJagPowerSensor(this, 50, "PowerSensor" + this);
            powerSensor.start();
        }
        return powerSensor;
    }

    /**
     *
     * @return
     */
    public GRTJagSwitch getSwitches() {
        if (switches == null) {
            switches = new GRTJagSwitch(this, 5, "Switch" + this);
            switches.start();
        }
        return switches;
    }

}
