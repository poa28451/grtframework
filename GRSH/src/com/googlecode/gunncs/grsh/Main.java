/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.googlecode.gunncs.grsh;

import com.grt192.prototyper.Prototyper;
import com.grt192.prototyper.PrototyperFactory;
import java.awt.BorderLayout;
import javax.swing.JFrame;

/**
 *
 * @author data
 */
public class Main {

    public static void main(String[] args) {
        PrototyperFactory.debugedStart();
        JFrame j = new JFrame("GRSH");
        ShellMessenger sm = new ShellMessenger();
        ShellComponent sc = new ShellComponent(sm);
        j.add(sc, BorderLayout.CENTER);
        j.setSize(300, 400);
        j.setVisible(true);
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}
