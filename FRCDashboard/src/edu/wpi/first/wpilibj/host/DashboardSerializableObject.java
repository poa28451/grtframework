package edu.wpi.first.wpilibj.host;

import java.io.DataOutputStream;

public abstract class DashboardSerializableObject implements DashboardSerializable {
    public String name;
    
    public DashboardSerializableObject(String name) {
        this.name = name;
    }
    
    public void dashboardSerializableStringChildrenTo(StringBuilder builder, String indent) {
    }
    
    public void dashboardSerializableStringShallowTo(StringBuilder builder, String indent) {
        builder.append(indent);
        builder.append(getClass().getSimpleName());
        builder.append('.');
        builder.append(name);
        builder.append('\n');
    }
    
    public void dashboardSerializableWriteTo(DataOutputStream out) {
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        String indent = "";
        dashboardSerializableStringShallowTo(builder, indent);
        dashboardSerializableStringChildrenTo(builder, DashboardSerializer.nextIndentString(indent));
        return builder.toString();
    }

}
