package com.grt192.actuator;

import com.grt192.actuator.exception.ActuatorException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.grt192.core.CommandArray;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;

/**
 * A SunSPOT sensorboard TriColorLED
 * @author ajc
 */
public class SPOTLED extends Actuator {

    /** CommandArray indicies **/
    public static final int POWER = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    /** Power states **/
    public static final int ON = 1;
    public static final int OFF = 0;
    /** Sample Commands **/
    public static final CommandArray COLOR_RED = new CommandArray(new double[]{1, 255, 0, 0});
    public static final CommandArray COLOR_GREEN = new CommandArray(new double[]{1, 0, 255, 0});
    public static final CommandArray COLOR_BLUE = new CommandArray(new double[]{1, 0, 0, 255});
    public static final CommandArray COLOR_CYAN = new CommandArray(new double[]{1, 0, 255, 255});
    public static final CommandArray COLOR_MAGENTA = new CommandArray(new double[]{1, 255, 0, 255});
    public static final CommandArray COLOR_YELLOW = new CommandArray(new double[]{1, 255, 128, 0});
    public static final CommandArray COLOR_TURQUOISE = new CommandArray(new double[]{1, 0, 100, 255});
    public static final CommandArray COLOR_PUCE = new CommandArray(new double[]{1, 204, 136, 153});
    public static final CommandArray COLOR_MAUVE = new CommandArray(new double[]{1, 153, 51, 102});
    public static final CommandArray COLOR_CHARTREUSE = new CommandArray(new double[]{1, 127, 255, 0});
    public static final CommandArray COLOR_ORANGE = new CommandArray(new double[]{1, 255, 32, 0});
    public static final CommandArray COLOR_WHITE = new CommandArray(new double[]{1, 255, 255, 255});
    public static final CommandArray COLOR_OFF = new CommandArray(new double[]{0, 0, 0, 0});
    private ITriColorLED led;
    private final int id;

    public SPOTLED(int id) {
        this.id = id;
        led = ((ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class)).getLED(id);

    }

    /**
     * Actuates a SPOTLED with power, redValue, greenValue, blueValue.
     * Color values range from 0-255
     * @throws ActuatorException
     */
    protected void executeCommand(Command c) {
        CommandArray ca = (CommandArray) c;
        if (!(c instanceof CommandArray)) {
            System.err.println("Send ");
            return;
        }
        led.setOn(ca.getValue(POWER) == ON);
        led.setRGB((int) ca.getValue(RED), (int) ca.getValue(GREEN), (int) ca.getValue(BLUE));

    }

    protected void halt() {
        led.setOff();
    }
}
