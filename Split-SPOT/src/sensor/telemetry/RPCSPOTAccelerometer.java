package sensor.telemetry;

import com.sun.spot.sensorboard.peripheral.IAccelerometer3D;
import java.io.IOException;

import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.util.Utils;
import java.util.Enumeration;
import java.util.Vector;
import rpc.RPCConnection;
import rpc.RPCMessage;
import sensor.VoltageSensor;
import sensor.core.HWSensor;
import sensor.event.VoltageChangeEvent;
import sensor.event.VoltageSensorListener;

public class RPCSPOTAccelerometer extends Thread {

    public static final int KEY_X = 0;
    public static final int KEY_Y = 1;
    public static final int KEY_Z = 2;

    private IAccelerometer3D accel;
    boolean running;
    private final RPCConnection conn;
    private final int[] keys;
    private final int sleepTime;
    
    public RPCSPOTAccelerometer(RPCConnection conn, int[] keys, int sleepTime){
        this.conn = conn;
        this.keys = keys;
        accel = EDemoBoard.getInstance().getAccelerometer();
        System.out.println("new spot accel!");
        this.sleepTime = sleepTime;
    }

    public void run(){
        running = true;
        while(running){
            try {
                conn.send(new RPCMessage(keys[KEY_X], accel.getAccelX()));
                conn.send(new RPCMessage(keys[KEY_Y], accel.getAccelY()));
                conn.send(new RPCMessage(keys[KEY_Z], accel.getAccelZ()));
                Utils.sleep(sleepTime);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }





}
