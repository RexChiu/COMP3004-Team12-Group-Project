package app;


import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import config.GUIConfig;
import config.LANConfig;
public class HostIvanhoe extends JFrame {
	
	private static final String HOST_TITLE = "Host of Ivanhoe";
		
	public HostIvanhoe(){
		super(HOST_TITLE);
		getContentPane().setLayout(null);

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);
		GridBagConstraints constraints = new GridBagConstraints();

		JPanel mainPanel = new JPanel();

		mainPanel.setLayout(new GridLayout(6, 3));
		
		addServer(mainPanel);
		addClient(mainPanel);
		addClient(mainPanel);
		addClient(mainPanel);
		addClient(mainPanel);
		addClient(mainPanel);

		constraints.gridx = 0;
		constraints.gridy = 0;
		layout.setConstraints(mainPanel, constraints); 
		add(mainPanel);

		JPanel controlPanel = new JPanel();
		addStart(controlPanel);
		addStop(controlPanel);

		constraints.gridx = 0;
		constraints.gridy = 1; 
		layout.setConstraints(controlPanel, constraints); 
		add(controlPanel);
		
	    setSize(GUIConfig.HOST_WINDOW_WIDTH, GUIConfig.HOST_WINDOW_HEIGHT);
	    setResizable(Boolean.FALSE);	
	}
	
	public void addServer(JPanel panel){
		JLabel server = new JLabel(GUIConfig.HOST_SERVER);
		server.setHorizontalAlignment(SwingConstants.CENTER);
		JButton status = new JButton("Status");
		JButton	setting	= new JButton("Setting");
		JButton	detail	= new JButton("Detail");

		panel.add(server);
		panel.add(status);	
		panel.add(setting);
		panel.add(detail);
	}
	
	public void addClient(JPanel panel){
		JLabel client = new JLabel(GUIConfig.HOST_CLIENT);
		client.setHorizontalAlignment(SwingConstants.CENTER);
		JButton status = new JButton("Status");
		JButton	setting	= new JButton("Disconnect");
		JButton	detail	= new JButton("Detail");

		panel.add(client);		
		panel.add(status);	
		panel.add(setting);
		panel.add(detail);
	}
	
	public void addStart(JPanel panel){
		JButton startButton = new JButton(GUIConfig.HOST_START);
		 
		startButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){ 
				 
			 	}});
		
		panel.add(startButton);
	}
	
	public void addStop(JPanel panel){
		JButton cancelButton = new JButton(GUIConfig.HOST_STOP);
		
		cancelButton.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){ 
				 
				 }});
		 		
		panel.add(cancelButton);
	}
	
	public static void main(String args[]) { new HostIvanhoe().setVisible(true); }
}
