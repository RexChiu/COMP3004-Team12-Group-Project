package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import config.GUIConfig;
import config.LANConfig;

public class NetworkSetting extends JFrame {
	
	/**
	 * Host Setting for the Server Ivanhoe
	 */
	private static final long serialVersionUID = 507060002943025833L;
	private JTextField numPlayerText;
	private JTextField IPText;
	private JTextField portText;
	
	public NetworkSetting(String title){
		super(title);
		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(3, 2));
		
		addNumPlayer(mainPanel);
		addIP(mainPanel);
		addPort(mainPanel);
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.setConstraints(mainPanel, constraints); 
		add(mainPanel);

		JPanel controlPanel = new JPanel();
		addStart(controlPanel);
		addCancel(controlPanel);
		
		constraints.gridx = 0;
		constraints.gridy = 1;
		layout.setConstraints(controlPanel, constraints); 
		add(controlPanel);

		setResizable(Boolean.FALSE);		 
		setSize(GUIConfig.START_SERVER_WINDOW_HEIGHT, GUIConfig.START_SERVER_WINDOW_WIDTH);
	}
		
	public void addNumPlayer(JPanel panel){
		JLabel numPlayer = new JLabel(GUIConfig.NUMBER_PLAYER);
		numPlayerText = new JTextField(Integer.toString(LANConfig.NUM_CLIENTS));
		
		numPlayerText.setHorizontalAlignment(JTextField.RIGHT);
		
		panel.add(numPlayer);
		panel.add(numPlayerText);
	}
	
	public void addIP(JPanel panel){
		JLabel IP = new JLabel(GUIConfig.IP);
		IPText = new JTextField(LANConfig.DEFAULT_HOST);

		IPText.setHorizontalAlignment(JTextField.RIGHT);
		
		panel.add(IP);
		panel.add(IPText);
	}
	
	public void addPort(JPanel panel){
		JLabel port = new JLabel(GUIConfig.PORT);
		portText = new JTextField(Integer.toString(LANConfig.DEFAULT_PORT));

		portText.setHorizontalAlignment(JTextField.RIGHT);
		
		panel.add(port);
		panel.add(portText);
	}
	
	public void addStart(JPanel panel){
		JButton startButton = new JButton(GUIConfig.CONFIRM);
		 
		startButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){ 
				 if (valid_input()) { 
					 LANConfig.NUM_CLIENTS 	= Integer.parseInt(numPlayerText.getText());
					 dispose(); } 
		}});
		
		panel.add(startButton);
	}
	
	public void addCancel(JPanel panel){
		JButton cancelButton = new JButton(GUIConfig.CANCEL);
		
		cancelButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){ dispose(); }});
		 		
		panel.add(cancelButton);
	}
	
	public boolean valid_input(){
		int num = Integer.parseInt(numPlayerText.getText());
		if (num < LANConfig.MIN_CLIENTS || num > LANConfig.MAX_CLIENTS)
			return Boolean.FALSE;
		
		int port = Integer.parseInt(portText.getText());
		if (port < LANConfig.MIN_PORT || port > LANConfig.MAX_PORT)
			return Boolean.FALSE;
		
		LANConfig.NUM_CLIENTS  	= num;
		LANConfig.DEFAULT_HOST 	= IPText.getText();
		LANConfig.DEFAULT_PORT 	= port;
				
		return Boolean.TRUE;
	}
}
