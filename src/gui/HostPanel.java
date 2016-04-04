package gui;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
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
	
	/**
	 * Host Panel for the Server Ivanhoe
	 */
	private static final long serialVersionUID = -2462830087150288101L;
	private static final String HOST_TITLE = "Host of Ivanhoe";
	private boolean start = Boolean.FALSE;
	private JTextArea displayMessage;
		
	public HostPanel(){
		super(HOST_TITLE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.DARK_GRAY);
				
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
		addSetting(controlPanel);
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
		displayMessage = new JTextArea("\t\t      Display LOG of Ivanhoe");
		displayMessage.setForeground(Color.WHITE);
		displayMessage.setBackground(Color.BLACK);
		displayMessage.setOpaque(Boolean.TRUE);
		displayMessage.setLineWrap(Boolean.TRUE);
		displayMessage.setEditable(Boolean.FALSE);
		displayMessage.append("\n >> Welcomd to the Ivanhoe");
		JScrollPane display;display = new JScrollPane(displayMessage);
		display.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) { 
	        	//e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    }); 
		display.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
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
		JButton startServer = new JButton(GUIConfig.HOST_START);
		startServer.setLocation(GUIConfig.CONTROL_START_LOCATION_X, GUIConfig.CONTROL_START_LOCATION_Y);
		startServer.setSize(GUIConfig.CONTROL_START_WIDTH, GUIConfig.CONTROL_START_HEIGHT);
		
		startServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (!start){
					if (JOptionPane.showConfirmDialog(null, LANConfig.START_SERVER_REQUEST,
							LANConfig.START_SERVER, JOptionPane.YES_NO_OPTION) == 0) { startHost(); } 
				}else{
					JOptionPane.showMessageDialog(null, LANConfig.SERVER_RUNNING, LANConfig.SERVER_ERROR, JOptionPane.INFORMATION_MESSAGE);
				}
			}});
				
		panel.add(startServer);
	}
	
	private void addStop(JPanel panel){
		JButton stopServer = new JButton(GUIConfig.HOST_STOP);
		stopServer.setLocation(GUIConfig.CONTROL_STOP_LOCATION_X, GUIConfig.CONTROL_STOP_LOCATION_Y);
		stopServer.setSize(GUIConfig.CONTROL_STOP_WIDTH, GUIConfig.CONTROL_STOP_HEIGHT);
		
		stopServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if (start){
					if (JOptionPane.showConfirmDialog(null, LANConfig.STOP_SERVER_REQUEST,
							LANConfig.STOP_SERVER, JOptionPane.YES_NO_OPTION) == 0) { stopHost(); }
				}else{
					JOptionPane.showMessageDialog(null, LANConfig.SERVER_NOT_RUNNING, LANConfig.SERVER_ERROR, JOptionPane.INFORMATION_MESSAGE);
				}
			}});
		 		
		panel.add(stopServer);
	}
	
	private void addSetting(JPanel panel){
		JButton settingServer = new JButton(GUIConfig.HOST_SETTING);
		settingServer.setLocation(GUIConfig.CONTROL_SETTING_LOCATION_X, GUIConfig.CONTROL_SETTING_LOCATION_Y);
		settingServer.setSize(GUIConfig.CONTROL_SETTING_WIDTH, GUIConfig.CONTROL_SETTING_HEIGHT);
		
		settingServer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new NetworkSetting("Server Status").setVisible(Boolean.TRUE);
			}});
		 		
		panel.add(settingServer);
	}

	public void 	writeMessage(String message){ 
		displayMessage.append("\n >> " + message);	
		displayMessage.setCaretPosition(displayMessage.getDocument().getLength());
	}
	public Boolean	isStart()					{ return this.start; 			}
	public void 	startHost()					{ this.start = Boolean.TRUE;	}
	public void 	stopHost()					{ this.start = Boolean.FALSE;	}
}
