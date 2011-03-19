/*
 * Copyright (c) 2006, 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/
package com.grt192.radio;

import com.sun.spot.util.IEEEAddress;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * A kind of data stream that wraps both an input and output stream, so
 * as to act as both. This has create methods that will automatically
 * initialize connections from desired device type to desired device type.
 *
 * @author randy, ajc
 */
public class RadioDataIOStream implements DataInput, DataOutput {

    String otherAddress;
    StreamConnection conn;
    DataInputStream dis;
    DataOutputStream dos;
    private int portNumber;
    private boolean open = false;

    /**
     * Broadcasts and listens for requests for a connection for a known port.
     * @param port to connect to
     * @return
     * @throws ProtectedPortException
     */
    public static RadioDataIOStream open(int port){
        if(port <= ProtectedPortException.NUM_PROTECTED_PORTS){
            System.err.println("Bad port: " + port + " is less than " + ProtectedPortException.NUM_PROTECTED_PORTS);
        }
        return open(GRTSSpotListener.getAddress(port),port);
    }

    /**
     * Opens a connection to a known address and port.
     * @param otherAddress
     * @param p
     * @return
     */
    public static RadioDataIOStream open(String addr, int p) {
        RadioDataIOStream s = new RadioDataIOStream();
        s.otherAddress = addr;
        s.init(p, addr);
        return s;
    }

    synchronized public void init(int p, String addr) {
        String url = "radiostream://" + addr + ":" + p;
        portNumber = p;
        try {
            conn = (StreamConnection) Connector.open(url);
            dos = conn.openDataOutputStream();
            dis = conn.openDataInputStream();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        open = true;
    }

    synchronized public void close() {
        try {
            if (dos != null) {
                dos.close();
                dos = null;
            }
            if (dis != null) {
                dis.close();
                dis = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        open = false;
    }

    public void readFully(byte[] b) throws IOException {
        dis.readFully(b);
    }

    public void readFully(byte[] b, int i, int j) throws IOException {
        dis.readFully(b, i, j);
    }

    public int skipBytes(int n) throws IOException {
        return dis.skipBytes(n);
    }

    public boolean readBoolean() throws IOException {
        return dis.readBoolean();
    }

    public byte readByte() throws IOException {
        return dis.readByte();
    }

    public int readUnsignedByte() throws IOException {
        return dis.readUnsignedByte();
    }

    public short readShort() throws IOException {
        return dis.readShort();
    }

    public int readUnsignedShort() throws IOException {
        return dis.readUnsignedShort();
    }

    public char readChar() throws IOException {
        return dis.readChar();
    }

    public int readInt() throws IOException {
        return dis.readInt();
    }

    public long readLong() throws IOException {
        return dis.readLong();
    }

    public float readFloat() throws IOException {
        return dis.readFloat();
    }

    public double readDouble() throws IOException {
        return dis.readDouble();
    }

    public String readLine() throws IOException {
        throw new Error("Depricated, unimplemented");
    }

    public String readUTF() throws IOException {
        return dis.readUTF();
    }

    public void write(int b) throws IOException {
        dos.write(b);
    }

    public void write(byte[] b) throws IOException {
        dos.write(b);
    }

    public void write(byte[] b, int i, int j) throws IOException {
        dos.write(b, i, j);
    }

    public void writeBoolean(boolean v) throws IOException {
        dos.writeBoolean(v);
    }

    public void writeByte(int v) throws IOException {
        dos.writeByte(v);
    }

    public void writeShort(int v) throws IOException {
        dos.writeShort(v);
    }

    public void writeChar(int v) throws IOException {
        dos.writeChar(v);
    }

    public void writeInt(int v) throws IOException {
        dos.writeInt(v);
    }

    public void writeLong(long v) throws IOException {
        dos.writeLong(v);
    }

    public void writeFloat(float v) throws IOException {
        dos.writeFloat(v);
    }

    public void writeDouble(double v) throws IOException {
        dos.writeDouble(v);
    }

    public void writeBytes(String s) throws IOException {
        throw new Error("Unimplemented for J2ME compatibility.");
    }

    public void writeChars(String s) throws IOException {
        dos.writeChars(s);
    }

    public void writeUTF(String str) throws IOException {
        dos.writeUTF(str);
    }

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public void flush() throws IOException {
        //Sometimes a flush is not necessary but is sent "just to be sure."
        // This fails silently if already closed.
        if (dos != null) {
            dos.flush();
        }
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public String toString(){
        return "RadioDataIOStream to " + getOtherAddress() +":"+ getPortNumber();
    }
}
