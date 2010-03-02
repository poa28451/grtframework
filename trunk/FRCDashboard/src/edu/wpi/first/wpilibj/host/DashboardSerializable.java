package edu.wpi.first.wpilibj.host;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface DashboardSerializable {

    public void dashboardSerializableReadFrom(DataInputStream in);
    
    public void dashboardSerializableStringChildrenTo(StringBuilder builder, String indent);

    public void dashboardSerializableStringShallowTo(StringBuilder builder, String indent);
    
    public void dashboardSerializableWriteTo(DataOutputStream out);
    
}
