/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.gunncs.grsh;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author data
 */
public class ShellComponent extends JComponent implements ActionListener, ShellOutputListener, KeyListener {

	public static int KEY_UP = 38;
	public static int KEY_DOWN = 40;

	private ShellMessenger sm;

	private JEditorPane display;
	private JTextField input;
	private JButton send;
	private JScrollPane scroll;

	public ShellComponent(ShellMessenger sm) {
		this.sm = sm;

		display = new JEditorPane();
		display.setEditable(false);

		input = new JTextField();
		input.addActionListener(this);
		input.addKeyListener(this);

		send = new JButton("Send");
		send.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(input,BorderLayout.CENTER);
		panel.add(send,BorderLayout.EAST);

		scroll = new JScrollPane(display);

		setLayout(new BorderLayout());
		add(scroll,BorderLayout.CENTER);
		add(panel,BorderLayout.SOUTH);
		DefaultCaret caret = (DefaultCaret) display.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
	}

	public void actionPerformed(ActionEvent e) {
		display.setText(display.getText() + ">" + input.getText() + "\n");
		sm.sendMessage(input.getText());
		input.setText("");
	}

	public void receivedLine(String s) {
		display.setText(display.getText() + s +"\n");
	}

	public void keyTyped(KeyEvent e) {}

	public void keyPressed(KeyEvent e) {}

	public void keyReleased(KeyEvent e) {
//		System.out.println(e.getKeyCode());
		if(e.getKeyCode()==KEY_UP) {
			
		} else if(e.getKeyChar()==KEY_DOWN) {
			
		}
	}

}
