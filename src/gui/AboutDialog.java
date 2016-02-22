package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import config.GUIConfig;

public class AboutDialog extends JDialog {

	/**
	 * About Dialog for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -3538502224097519592L;

	public AboutDialog(JFrame owner, String title, String msg){
		super(owner, title);
		
		 setLayout(new BorderLayout());

		 JTextField aboutText = new JTextField("\t"+msg); 
		 aboutText.setEditable(Boolean.FALSE);
		 aboutText.setSize(GUIConfig.ABOUT_TEXT_HEIGHT*2, GUIConfig.ABOUT_TEXT_WIDTH*2); 
		 
		 JButton okButton = new JButton("OK");
		 okButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){ dispose(); }});
		 
		 add(BorderLayout.SOUTH, okButton); 
		 add(BorderLayout.CENTER, aboutText);

		 setResizable(Boolean.FALSE);
		 setSize(GUIConfig.ABOUT_WINDOW_HEIGHT, GUIConfig.ABOUT_WINDOW_WIDTH);
		 setLocationRelativeTo(owner);
	}
}
