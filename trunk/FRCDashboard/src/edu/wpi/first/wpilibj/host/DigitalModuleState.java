package edu.wpi.first.wpilibj.host;

import java.io.DataInputStream;

public class DigitalModuleState extends DashboardSerializableObject {
    public int relayForwardBits;
    public int relayReverseBits;
    public int inputOutputValueBits;
    public int inputOutputDirectionBits;
    public int[] pwmValues;

    public DigitalModuleState(String name) {
        super(name);
        pwmValues = new int[10];
    }
    
    public void dashboardSerializableReadFrom(DataInputStream in) {
        DashboardSerializer.readClusterHeader(in);
        relayForwardBits = DashboardSerializer.readByte(in);
        relayReverseBits = DashboardSerializer.readByte(in);
        inputOutputValueBits = DashboardSerializer.readShort(in);
        inputOutputDirectionBits = DashboardSerializer.readShort(in);
        {
            DashboardSerializer.readClusterHeader(in);
            for (int i=0; i < pwmValues.length; i++) {
                pwmValues[i] = DashboardSerializer.readByte(in);
            }
            DashboardSerializer.readClusterFooter(in);
        }
        DashboardSerializer.readClusterFooter(in);
    }

    public void dashboardSerializableStringShallowTo(StringBuilder builder, String indent) {
        super.dashboardSerializableStringShallowTo(builder, indent);
        builder.append(indent);
        builder.append("relayForwardBits: ");
        builder.append(relayForwardBits);
        builder.append('\n');
        builder.append(indent);
        builder.append("relayReverseBits: ");
        builder.append(relayReverseBits);
        builder.append('\n');
        builder.append(indent);
        builder.append("inputOutputValueBits: ");
        builder.append(inputOutputValueBits);
        builder.append('\n');
        builder.append(indent);
        builder.append("inputOutputDirectionBits: ");
        builder.append(inputOutputDirectionBits);
        builder.append('\n');
        builder.append(indent);
        builder.append("pwm: ");
        for (int i=0; i < pwmValues.length; i++) {
            builder.append('[');
            builder.append(i);
            builder.append("]=");
            DashboardSerializer.writePadded(builder, 6, pwmValues[i]);
            builder.append(' ');
        }
        builder.append('\n');
    }
        
}
