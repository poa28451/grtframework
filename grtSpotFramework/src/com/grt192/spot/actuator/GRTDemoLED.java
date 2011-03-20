package com.grt192.spot.actuator;

import com.grt192.actuator.exception.ActuatorException;
import com.grt192.core.Actuator;
import com.grt192.core.Command;
import com.grt192.core.CommandArray;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;

/**
 * A SunSPOT sensorboard TriColorLED.
 * @author ajc
 */
public class GRTDemoLED extends Actuator {

    /** CommandArray indicies **/
    public static final int POWER = 0;
    public static final int RED = 1;
    public static final int GREEN = 2;
    public static final int BLUE = 3;
    /** Power states **/
    public static final int ON = 1;
    public static final int OFF = 0;

    /**
     * Color constants
     */
    public static class Color {
        public static final CommandArray RED = new CommandArray(new double[]{1, 255, 0, 0});
        public static final CommandArray GREEN = new CommandArray(new double[]{1, 0, 255, 0});
        public static final CommandArray BLUE = new CommandArray(new double[]{1, 0, 0, 255});
        public static final CommandArray CYAN = new CommandArray(new double[]{1, 0, 255, 255});
        public static final CommandArray MAGENTA = new CommandArray(new double[]{1, 255, 0, 255});
        public static final CommandArray YELLOW = new CommandArray(new double[]{1, 255, 128, 0});
        public static final CommandArray TURQUOISE = new CommandArray(new double[]{1, 0, 100, 255});
        public static final CommandArray PUCE = new CommandArray(new double[]{1, 204, 136, 153});
        public static final CommandArray MAUVE = new CommandArray(new double[]{1, 153, 51, 102});
        public static final CommandArray CHARTREUSE = new CommandArray(new double[]{1, 127, 255, 0});
        public static final CommandArray ORANGE = new CommandArray(new double[]{1, 255, 32, 0});
        public static final CommandArray WHITE = new CommandArray(new double[]{1, 255, 255, 255});
        public static final CommandArray COLOR_NONE = new CommandArray(new double[]{0, 0, 0, 0});
    }
    private ITriColorLED led;
    private final int id;

    public GRTDemoLED(int id) {
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
