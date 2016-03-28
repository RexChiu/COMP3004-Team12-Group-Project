package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import config.GUIConfig;

public class StartServerPanel extends JDialog {

	/**
	 * Start Serevr Panel for the Server Ivanhoe
	 */
	private static final long serialVersionUID = 4474735495503707285L;

	public StartServerPanel(JFrame owner, String title){
		super(owner, title);
		
		 setLayout(new BorderLayout());

		 JTextField aboutText = new JTextField("\tStart Server"); 
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
