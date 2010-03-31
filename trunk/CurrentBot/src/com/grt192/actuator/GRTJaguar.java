 /*
 * Luminary Micro Jaguar Speed Controller
 * Reviewed AG 11/2/2009 -- OK
 */
package com.grt192.actuator;

import com.grt192.core.Actuator;
import com.grt192.core.Command;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 *
 * @author Student
 */
public class GRTJaguar extends Actuator implements PIDOutput{

    private Jaguar jaguar;

    public GRTJaguar(int channel) {
        jaguar = new Jaguar(channel);
    }

    public void executeCommand(Command c) {
        double value = c.getValue();
        if (value > 1.0) {
            value = 1.0;
        }
        if(value < -1.0) {
            value = -1.0;
        }
        jaguar.set(value);
    }

    public void halt() {
        //Sets motor speed to 0;
        jaguar.set(0);
    }

    public String toString() {
        return "Jaguar";
    }

    public void pidWrite(double output){
        this.enqueueCommand(output);
    }

}
