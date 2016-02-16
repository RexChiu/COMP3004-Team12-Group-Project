package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import config.GUIConfig;
import config.LANConfig;
public class HostPanel extends JFrame {
	
	private static final String HOST_TITLE = "Host of Ivanhoe";
	private static JButton startServer, stopServer;
	private static JTextArea displayMessage;
	private static HashMap<String, JComponent> hostComponents;
		
	public HostPanel(){
		super(HOST_TITLE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);
		
		hostComponents = new HashMap<String, JComponent>();
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setLocation(GUIConfig.TITLE_PANEL_LOCATION_X, GUIConfig.TITLE_PANEL_LOCATION_Y);
		titlePanel.setSize(GUIConfig.TITLE_PANEL_WIDTH, GUIConfig.TITLE_PANEL_HEIGHT);
		titlePanel.setBackground(Color.BLACK);
		addTitle(titlePanel);
		add(titlePanel);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(null);
		controlPanel.setLocation(GUIConfig.CONTROL_PANEL_LOCATION_X, GUIConfig.CONTROL_PANEL_LOCATION_Y);
		controlPanel.setSize(GUIConfig.CONTROL_PANEL_WIDTH, GUIConfig.CONTROL_PANEL_HEIGHT);
		controlPanel.setBackground(Color.BLACK);
		addStart(controlPanel);
		addStop(controlPanel);
		add(controlPanel);

		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(null);
		messagePanel.setLocation(GUIConfig.MESSAGE_PANEL_LOCATION_X, GUIConfig.MESSAGE_PANEL_LOCATION_Y);
		messagePanel.setSize(GUIConfig.MESSAGE_PANEL_WIDTH, GUIConfig.MESSAGE_PANEL_HEIGHT);
		messagePanel.setBackground(Color.LIGHT_GRAY);
		addDisplay(messagePanel);
		add(messagePanel);

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(null);
		addServer(infoPanel);
		for (int i = 1; i <= LANConfig.MAX_CLIENTS; ++i){
			addClient(infoPanel, i);
		}
		
		infoPanel.setLocation(GUIConfig.INFO_PANEL_LOCATION_X, GUIConfig.INFO_PANEL_LOCATION_Y);
		infoPanel.setSize(GUIConfig.INFO_PANEL_WIDTH, GUIConfig.INFO_PANEL_HEIGHT);
		infoPanel.setBackground(Color.LIGHT_GRAY);
		add(infoPanel);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	    setSize(GUIConfig.HOST_WINDOW_WIDTH, GUIConfig.HOST_WINDOW_HEIGHT);
	    setResizable(Boolean.FALSE);	
	}
	
	private void addServer(JPanel panel){
		JPanel serverPanel = new JPanel();
		serverPanel.setLayout(null);
		serverPanel.setLocation(GUIConfig.SERVER_PANEL_LOCATION_X, GUIConfig.SERVER_PANEL_LOCATION_Y);
		serverPanel.setSize(GUIConfig.SERVER_PANEL_WIDTH, GUIConfig.SERVER_PANEL_HEIGHT);
		serverPanel.setBackground(Color.BLACK);
		
		JButton serverTitle = new JButton("Server");
		serverTitle.setLocation(GUIConfig.SERVER_TITLE_LOCATION_X, GUIConfig.SERVER_TITLE_LOCATION_Y);
		serverTitle.setSize(GUIConfig.SERVER_TITLE_WIDTH, GUIConfig.SERVER_TITLE_HEIGHT);
		serverPanel.add(serverTitle);
		
		JButton serverStatus = new JButton("Status");
		serverStatus.setLocation(GUIConfig.SERVER_STATUS_LOCATION_X, GUIConfig.SERVER_STATUS_LOCATION_Y);
		serverStatus.setSize(GUIConfig.SERVER_STATUS_WIDTH, GUIConfig.SERVER_STATUS_HEIGHT);
		serverPanel.add(serverStatus);
		
		JButton serverConnection = new JButton("No Connection");
		serverConnection.setLocation(GUIConfig.SERVER_CONNECTION_LOCATION_X, GUIConfig.SERVER_CONNECTION_LOCATION_Y);
		serverConnection.setSize(GUIConfig.SERVER_CONNECTION_WIDTH, GUIConfig.SERVER_CONNECTION_HEIGHT);
		serverPanel.add(serverConnection);
		
		JButton serverDetail = new JButton("Detail");
		serverDetail.setLocation(GUIConfig.SERVER_DETAIL_LOCATION_X, GUIConfig.SERVER_DETAIL_LOCATION_Y);
		serverDetail.setSize(GUIConfig.SERVER_DETAIL_WIDTH, GUIConfig.SERVER_DETAIL_HEIGHT);
		serverPanel.add(serverDetail);
		panel.add(serverPanel);
	}
	
	private void addClient(JPanel panel, int clientID){
		JPanel clientPanel = new JPanel();
		clientPanel.setLayout(null);
		clientPanel.setLocation(GUIConfig.SERVER_PANEL_LOCATION_X, GUIConfig.SERVER_PANEL_LOCATION_Y+clientID*GUIConfig.SEREVER_PANEL_OFFSET);
		clientPanel.setSize(GUIConfig.SERVER_PANEL_WIDTH, GUIConfig.SERVER_PANEL_HEIGHT);
		clientPanel.setBackground(Color.BLACK);
		
		JButton clientTitle = new JButton("Client "+clientID);
		clientTitle.setLocation(GUIConfig.SERVER_TITLE_LOCATION_X, GUIConfig.SERVER_TITLE_LOCATION_Y);
		clientTitle.setSize(GUIConfig.SERVER_TITLE_WIDTH, GUIConfig.SERVER_TITLE_HEIGHT);
		clientPanel.add(clientTitle);
		
		JButton clientStatus = new JButton("Status");
		clientStatus.setLocation(GUIConfig.SERVER_STATUS_LOCATION_X, GUIConfig.SERVER_STATUS_LOCATION_Y);
		clientStatus.setSize(GUIConfig.SERVER_STATUS_WIDTH, GUIConfig.SERVER_STATUS_HEIGHT);
		clientPanel.add(clientStatus);
		
		JButton clientConnection = new JButton("No Connection");
		clientConnection.setLocation(GUIConfig.SERVER_CONNECTION_LOCATION_X, GUIConfig.SERVER_CONNECTION_LOCATION_Y);
		clientConnection.setSize(GUIConfig.SERVER_CONNECTION_WIDTH, GUIConfig.SERVER_CONNECTION_HEIGHT);
		clientPanel.add(clientConnection);
		
		JButton clientDetail = new JButton("Detail");
		clientDetail.setLocation(GUIConfig.SERVER_DETAIL_LOCATION_X, GUIConfig.SERVER_DETAIL_LOCATION_Y);
		clientDetail.setSize(GUIConfig.SERVER_DETAIL_WIDTH, GUIConfig.SERVER_DETAIL_HEIGHT);
		clientPanel.add(clientDetail);
		
		panel.add(clientPanel);
	}
	
	
	private void addDisplay(JPanel panel){
		displayMessage = new JTextArea("\t\tDisplay Message");
		displayMessage.setForeground(Color.WHITE);
		displayMessage.setBackground(Color.BLACK);
		displayMessage.setOpaque(Boolean.TRUE);
		displayMessage.setLineWrap(Boolean.TRUE);
		displayMessage.setEditable(Boolean.FALSE);
		for (int i = 0; i < 20; i++)
			displayMessage.append("\n >> Display Message" + i);
		JScrollPane display = new JScrollPane(displayMessage);
		display.setLocation(GUIConfig.DISPLAY_LOCATION_X, GUIConfig.DISPLAY_LOCATION_Y);
		display.setSize(GUIConfig.DISPLAY_WIDTH, GUIConfig.DISPLAY_HEIGHT);
		panel.add(display);
	}
	
	private void addTitle(JPanel panel){
		JLabel hostTitle = new JLabel(HOST_TITLE);
		hostTitle.setLocation(GUIConfig.TITLE_LOCATION_X, GUIConfig.TITLE_LOCATION_Y);
		hostTitle.setSize(GUIConfig.TITLE_WIDTH, GUIConfig.TITLE_HEIGHT);
		hostTitle.setHorizontalAlignment(SwingConstants.CENTER);
		hostTitle.setFont(new Font("Arial", Font.BOLD, 24));
		hostTitle.setForeground(Color.WHITE);
		hostTitle.setBackground(Color.DARK_GRAY);
		hostTitle.setOpaque(Boolean.TRUE);
		panel.add(hostTitle);
	}
	
	private void addStart(JPanel panel){
		startServer = new JButton(GUIConfig.HOST_START);
		startServer.setLocation(GUIConfig.CONTROL_START_LOCATION_X, GUIConfig.CONTROL_START_LOCATION_Y);
		startServer.setSize(GUIConfig.CONTROL_START_WIDTH, GUIConfig.CONTROL_START_HEIGHT);
		
		startServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
				                            "Do you want start the host of Ivanhoe?",
				                            "Start Ivanhoe",
				                            JOptionPane.YES_NO_OPTION) == 0) startHost();
			}});
				
		panel.add(startServer);
	}
	
	private void addStop(JPanel panel){
		stopServer = new JButton(GUIConfig.HOST_STOP);
		stopServer.setLocation(GUIConfig.CONTROL_STOP_LOCATION_X, GUIConfig.CONTROL_STOP_LOCATION_Y);
		stopServer.setSize(GUIConfig.CONTROL_STOP_WIDTH, GUIConfig.CONTROL_STOP_HEIGHT);
		
		stopServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(null,
				                            "Do you want stop the host of Ivanhoe?",
				                            "Stop Ivanhoe",
				                            JOptionPane.YES_NO_OPTION) == 0) stopHost();
			}});
		 		
		panel.add(stopServer);
	}

	public void addMessage(String message)	{ displayMessage.append("\n >> " + message);	}
	private void startHost()				{ addMessage("Hello World!");	}
	private void stopHost()					{ dispose(); 					}
}
