package edu.wpi.first.wpilibj.host;

import java.io.DataInputStream;

public class AnalogModuleState extends DashboardSerializableObject {
    public float input0;
    public float input1;
    public float input2;
    public float input3;
    public float input4;
    public float input5;
    public float input6;
    public float input7;

    public AnalogModuleState(String name) {
        super(name);
    }
    
    public void dashboardSerializableReadFrom(DataInputStream in) {
        DashboardSerializer.readClusterHeader(in);
        input0 = DashboardSerializer.readFloat(in);
        input1 = DashboardSerializer.readFloat(in);
        input2 = DashboardSerializer.readFloat(in);
        input3 = DashboardSerializer.readFloat(in);
        input4 = DashboardSerializer.readFloat(in);
        input5 = DashboardSerializer.readFloat(in);
        input6 = DashboardSerializer.readFloat(in);
        input7 = DashboardSerializer.readFloat(in);
        DashboardSerializer.readClusterFooter(in);
    }

    public void dashboardSerializableStringShallowTo(StringBuilder builder, String indent) {
        super.dashboardSerializableStringShallowTo(builder, indent);
        builder.append(indent);
        builder.append("input: [0]=");
        DashboardSerializer.writePadded(builder, 6, input0);
        builder.append(" [1]=");
        DashboardSerializer.writePadded(builder, 6, input1);
        builder.append(" [2]=");
        DashboardSerializer.writePadded(builder, 6, input2);
        builder.append(" [3]=");
        DashboardSerializer.writePadded(builder, 6, input3);
        builder.append(" [4]=");
        DashboardSerializer.writePadded(builder, 6, input4);
        builder.append(" [5]=");
        DashboardSerializer.writePadded(builder, 6, input5);
        builder.append(" [6]=");
        DashboardSerializer.writePadded(builder, 6, input6);
        builder.append(" [7]=");
        DashboardSerializer.writePadded(builder, 6, input7);
        builder.append('\n');
    }
        
}
