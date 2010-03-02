package edu.wpi.first.wpilibj.host;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class DashboardPacket extends DashboardSerializableObject {
    public static final int USER_DATA_BYTES_SIZE = 984;

    protected int packetNumber;
    
    public DriverStationState driverStationState;
    
    protected int replyPacketNumber;
    
    protected int crcChecksum;
    
    protected byte[] userDataBytes;
    
    protected ByteArrayInputStream userDataBytesIn;
    
    protected DataInputStream userDataDataIn;
    
    public CRioState cRioState;

    public DashboardPacket() {
        super("");
        driverStationState = new DriverStationState();
        userDataBytes = new byte[USER_DATA_BYTES_SIZE];
        userDataBytesIn = new ByteArrayInputStream(userDataBytes);
        userDataDataIn = new DataInputStream(userDataBytesIn);
        cRioState = new CRioState();
    }

    public void dashboardSerializableReadFrom(DataInputStream in) {
        try {
            packetNumber = in.readUnsignedShort();
            driverStationState.dashboardSerializableReadFrom(in);
            replyPacketNumber = (int) in.readLong();
            int readCount = in.read(userDataBytes);
            if (readCount != userDataBytes.length) {
                throw new RuntimeException("Tried to read " + userDataBytes.length + " bytes but was only able to read " + readCount);
            }
            
            crcChecksum = (int) in.readLong();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // CRC32 crc = new CRC32();
        // crc.reset();
        // for (int i=offset; i < (length - 8); i++) {
        // crc.update(bytes[i]);
        // }
        // if (crcChecksum != crc.getValue()) {
        // throw new IOException("Failed CRC32 checksum, computed " +
        // crc.getValue() + " got " + crcChecksum);
        // }
        userDataBytesIn.reset();
        driverStationState.userDataReadFrom(userDataDataIn);
        // read the length of the string, and ignore it
        try {
            userDataDataIn.readInt();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cRioState.dashboardSerializableReadFrom(userDataDataIn);
    }

    public void dashboardSerializableStringChildrenTo(StringBuilder builder, String indent) {
        String nextIndent = DashboardSerializer.nextIndentString(indent);
        driverStationState.dashboardSerializableStringShallowTo(builder, nextIndent);
        driverStationState.dashboardSerializableStringChildrenTo(builder, nextIndent);
        cRioState.dashboardSerializableStringShallowTo(builder, nextIndent);
        cRioState.dashboardSerializableStringChildrenTo(builder, nextIndent);
    }

    public void dashboardSerializableStringShallowTo(StringBuilder builder, String indent) {
        super.dashboardSerializableStringShallowTo(builder, indent);
        builder.append(indent);
        builder.append("packetNumber: ");
        builder.append(packetNumber);
        builder.append('\n');
        builder.append(indent);
        builder.append("replyPacketNumber: ");
        builder.append(replyPacketNumber);
        builder.append('\n');
        builder.append(indent);
        builder.append("crcChecksum: ");
        builder.append(crcChecksum);
        builder.append('\n');
    }

}
