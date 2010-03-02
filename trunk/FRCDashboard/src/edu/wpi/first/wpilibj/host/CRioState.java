package edu.wpi.first.wpilibj.host;

import java.io.DataInputStream;

public class CRioState extends DashboardSerializableObject {
    public AnalogModuleState analogModule1;

    public AnalogModuleState analogModule2;

    public DigitalModuleState digitalModule1;

    public DigitalModuleState digitalModule2;

    protected int solenoidBits;

    public CRioState() {
        super("");
        analogModule1 = new AnalogModuleState("1");
        analogModule2 = new AnalogModuleState("2");
        digitalModule1 = new DigitalModuleState("1");
        digitalModule2 = new DigitalModuleState("2");
    }

    public void dashboardSerializableReadFrom(DataInputStream in) {
        {
            DashboardSerializer.readClusterHeader(in);
            {
                DashboardSerializer.readClusterHeader(in);
                analogModule1.dashboardSerializableReadFrom(in);
                analogModule2.dashboardSerializableReadFrom(in);
                DashboardSerializer.readClusterFooter(in);
            }
            {
                DashboardSerializer.readClusterHeader(in);
                digitalModule1.dashboardSerializableReadFrom(in);
                digitalModule2.dashboardSerializableReadFrom(in);
                DashboardSerializer.readClusterFooter(in);
            }
            solenoidBits = DashboardSerializer.readByte(in);
            DashboardSerializer.readClusterFooter(in);
        }
    }

    public void dashboardSerializableStringChildrenTo(StringBuilder builder, String indent) {
        analogModule1.dashboardSerializableStringShallowTo(builder, indent);
        analogModule1.dashboardSerializableStringChildrenTo(builder, indent);
        analogModule2.dashboardSerializableStringShallowTo(builder, indent);
        analogModule2.dashboardSerializableStringChildrenTo(builder, indent);
        digitalModule1.dashboardSerializableStringShallowTo(builder, indent);
        digitalModule1.dashboardSerializableStringChildrenTo(builder, indent);
        digitalModule2.dashboardSerializableStringShallowTo(builder, indent);
        digitalModule2.dashboardSerializableStringChildrenTo(builder, indent);
    }

}
