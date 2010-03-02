package edu.wpi.first.wpilibj.host;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;

public class DashboardSerializer {
    protected static final HashMap<Integer, String> INDENT_STRINGS = new HashMap<Integer, String>(); 

    public static String nextIndentString(String currentIndentString) {
        int nextIndent = currentIndentString.length() + 2;
        String nextIndentString = INDENT_STRINGS.get(nextIndent);
        if (nextIndentString == null) {
            StringBuilder builder = new StringBuilder(nextIndent);
            for (int i=0; i < nextIndent; i++) {
                builder.append(' ');
            }
            nextIndentString = builder.toString();
            INDENT_STRINGS.put(nextIndent, nextIndentString);
        }
        return nextIndentString;
    }
    
    public static int readByte(DataInputStream in) {
        try {
            return in.read() & 0xFF;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static int readShort(DataInputStream in) {
        try {
            return in.readShort() & 0xFFFF;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void readClusterHeader(DataInputStream in) {
    }
    
    public static void readClusterFooter(DataInputStream in) {
    }
    
    public static float readFloat(DataInputStream in) {
        try {
            return in.readFloat();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void writePadded(StringBuilder builder, int padding, float number) {
        String string = String.valueOf(number);
        if (string.length() < padding) {
            for (int i=padding - string.length(); i > 0; i--) {
                builder.append(' ');
            }
            builder.append(string);
        } else {
            builder.append(string, 0, padding);
        }
    }

}
