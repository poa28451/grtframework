package com.grt192.prototyper.spot;

import com.grt192.actuator.spot.GRTDemoLED;
import com.grt192.core.CommandArray;
import com.grt192.mechanism.spot.SLight;
import com.grt192.prototyper.Prototyper;

/**
 * A FreeRangeProtototyper simply can use LED's to display status
 * @author ajc
 */
public class FreeRangeProtototyper extends Prototyper{

    private static final int STATUSLIGHT = 1;
    private SLight status; 
    
    public FreeRangeProtototyper(){
        status = SLight.get(STATUSLIGHT);
    }

    protected void indicateUnprototyped() {
        status.rawColor(GRTDemoLED.Color.ORANGE);
    }

    protected void indicatePrototype(int type) {
        CommandArray c;
        switch(type){
            case 0:
                c = GRTDemoLED.Color.BLUE;
                break;
            case 1:
                c = GRTDemoLED.Color.GREEN;
                break;
            default:
                c = GRTDemoLED.Color.RED;
                break;
        }
        status.rawColor(c);
    
    }

    protected void indicatePrototypeBlink() {
        status.blinkonBlack();
    }

}
