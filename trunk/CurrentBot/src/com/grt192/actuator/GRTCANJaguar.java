package com.grt192.actuator;

import com.grt192.actuator.exception.GRTCANJaguarException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.grt192.sensor.canjaguar.GRTJagEncoder;
import com.grt192.sensor.canjaguar.GRTJagPowerSensor;
import com.grt192.sensor.canjaguar.GRTJagSwitch;

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
    // Neutral Modes
    public static final int COAST = 0;
    public static final int BRAKE = 1;
    public static final int JUMPER = 2;
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
    }

    /**
     *Sets the encoder or potentiometer used to gauge position
     * @param sensor
     */
    public void setPositionSensor(int sensor) {
        if (sensor == ENCODER) {
            jaguar.setPositionReference(CANJaguar.PositionReference.kQuadEncoder);
        } else if (sensor == POTENTIOMETER) {
            jaguar.setPositionReference(CANJaguar.PositionReference.kPotentiometer);
        }
    }

    /**
     *Set PID controls
     * @param p
     * @param i
     * @param d
     */
    public void setPID(double p, double i, double d) {
        jaguar.setPID(p, i, d);
    }

    /**
     *
     * @param p
     */
    public void setP(double p) {
        jaguar.setPID(p, jaguar.getI(), jaguar.getD());
    }

    /**
     *
     * @param i
     */
    public void setI(double i) {
        jaguar.setPID(jaguar.getP(), i, jaguar.getD());
    }

    /**
     *
     * @param d
     */
    public void setD(double d) {
        jaguar.setPID(jaguar.getP(), jaguar.getI(), d);
    }

    /**
     *
     * @return
     */
    public double getP() {
        return jaguar.getP();
    }

    /**
     *
     * @return
     */
    public double getI() {
        return jaguar.getI();
    }

    /**
     *
     * @return
     */
    public double getD() {
        return jaguar.getD();
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
        jaguar.enableControl(initialPosition);
    }

    /**
     *
     */
    public void disableClosedLoop() {
        jaguar.disableControl();
    }

    /**
     *Will return the input voltage of the jaguar speed controller
     * @return
     */
    public double getInputVoltage() {
        return jaguar.getBusVoltage();
    }

    /**
     *Will return the output voltage of the jaguar
     * @return
     */
    public double getOutputVoltage() {
        return jaguar.getOutputVoltage();
    }

    /**
     *Will return the output current of the jaguar
     * @return
     */
    public double getOutputCurrent() {
        return jaguar.getOutputCurrent();
    }

    /**
     *will return the temperature in the Jaguar
     * @return
     */
    public double getTemperature() {
        return jaguar.getTemperature();
    }

    /**
     *Get the position of the encoder or potentiometer.
     * @return
     */
    public double getPosition() {
        return jaguar.getPosition();
    }

    /**
     *Gets the Speed of the motor in RPM
     * @return
     */
    public double getSpeed() {
        return jaguar.getSpeed();
    }

    /**
     *The motor can turn forward if true
     * @return
     */
    public boolean getLeftLimitStatus() {
        return jaguar.getForwardLimitOK();
    }

    /**
     *The motor can turn backward if true
     * @return
     */
    public boolean getRightLimitStatus() {
        return jaguar.getReverseLimitOK();
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
        jaguar.setVoltageRampRate(rate);
    }

    /**
     *Sets the encoder resolution
     * @param countsPerRev
     */
    public void setEncoderResolution(int countsPerRev) {
        jaguar.configEncoderCodesPerRev((short) (countsPerRev));
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
        jaguar.configSoftPositionLimits(leftLimit, rightLimit);
    }

    /**
     *
     */
    public void disableSoftLimits() {
        jaguar.disableSoftPositionLimits();
    }

    /**
     * Configure what the controller does to the H-Bridge when neutral (not driving the output).
     *
     * This allows you to override the jumper configuration for brake or coast.
     *
     * @param mode
     */
    public void setNeutralMode(int mode) {
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
    }

    /**
     * Configure the number of turns on the potentiometer.
     *
     * There is no special support for continuous turn potentiometers.
     * Only integer numbers of turns are supported.
     *
     * @param turns
     */
    public void setPotentiometerTurns(int turns) {
        jaguar.configPotentiometerTurns((short) turns);
    }

    /**
     * Get the recently set outputValue setpoint.
     *
     * In PercentVoltage Mode, the outputValue is in the range -1.0 to 1.0
     *
     * @return
     */
    public double getLastCommand() {
        return jaguar.get();
    }

    /**
     *
     * @param c
     * @throws GRTCANJaguarException
     */
    protected void executeCommand(Command c) throws GRTCANJaguarException {
        double value = c.getValue();
        jaguar.set(value);
        // TODO throw fault exceptions
    }

    protected void halt() {
        jaguar.set(0);
    }

    /**
     *
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

    /**
     *
     * @param encoder
     */
    public void setEncoder(GRTJagEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     *
     * @param powerSensor
     */
    public void setPowerSensor(GRTJagPowerSensor powerSensor) {
        this.powerSensor = powerSensor;
    }

    /**
     *
     * @param switches
     */
    public void setSwitches(GRTJagSwitch switches) {
        this.switches = switches;
    }
}
