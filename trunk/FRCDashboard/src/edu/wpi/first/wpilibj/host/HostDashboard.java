package edu.wpi.first.wpilibj.host;

import team192.dashboard.ui.DashboardPanel;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
//import javax.swing.JTextArea;

public class HostDashboard {
    public static final int FROM_DRIVERSTATION_SOCKET_PORT = 1165;
    
    protected DashboardPacket dashboardPacket = new DashboardPacket();
    /*
    private JTextArea driveStationStateTextArea;

    private JTextArea analogModule1StateTextArea;

    private JTextArea digitalModule1StateTextArea;

    private JTextArea dashboardPacketStateTextArea;
    */
    private DashboardPanel panel;

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        HostDashboard dashboard = new HostDashboard();
        dashboard.openUi();
        dashboard.receiveAndProcessPackets();
    }

    public HostDashboard() {
        dashboardPacket = new DashboardPacket();
    }
    
    public void receiveAndProcessPackets() throws IOException {
        DatagramSocket fromDriverStationSocket = new DatagramSocket(FROM_DRIVERSTATION_SOCKET_PORT);
        byte[] buffer = new byte[fromDriverStationSocket.getReceiveBufferSize()];
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
        int i = 0;
        int packetLength = -1;
        ByteArrayInputStream bytesIn = null;
        DataInputStream dataIn = null;
        fromDriverStationSocket.setSoTimeout(2000);
        boolean reportedWaitingForPacket = false;
        while (true) {
            try {
                fromDriverStationSocket.receive(packet);
            } catch (SocketTimeoutException e) {
                if (!reportedWaitingForPacket) {
                    //System.out.println("Waiting for packet from Driver Station");
                    reportedWaitingForPacket = true;
                }
                continue;
            }
            reportedWaitingForPacket = false;
            if (packetLength == -1) {
                packetLength = packet.getLength();
                bytesIn = new ByteArrayInputStream(buffer, 0, packetLength);
                dataIn = new DataInputStream(bytesIn);
            } else {
                if (packet.getLength() != packetLength) {
                    throw new IOException("Expected to get datagram packet of length " + packetLength + ", but got " + packet.getLength());
                }
            }
            dashboardPacket.dashboardSerializableReadFrom(dataIn);
            
            panel.update();
            try {
                //Thread.sleep(100);
                bytesIn.reset();
            } catch (Exception ex) {
                Logger.getLogger(HostDashboard.class.getName()).log(Level.SEVERE, null, ex);
            }
            //bytesIn.reset();
            i++;
        }
    }
    
    public void openUi() {
        JFrame frame = new JFrame("FRC Java Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /*frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        dashboardPacketStateTextArea = new JTextArea(25, 40);
        frame.add(dashboardPacketStateTextArea);
        driveStationStateTextArea = new JTextArea(25, 40);
        frame.add(driveStationStateTextArea);
        analogModule1StateTextArea = new JTextArea(10, 50);
        frame.add(analogModule1StateTextArea);
        digitalModule1StateTextArea = new JTextArea(10, 50);
        frame.add(digitalModule1StateTextArea);
        frame.pack();*/
        dashboardPacket = new DashboardPacket();
        panel = new DashboardPanel(dashboardPacket);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
