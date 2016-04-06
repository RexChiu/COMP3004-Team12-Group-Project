package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import config.GAMEConfig;
import config.GUIConfig;

public class PlayerPanel extends JPanel implements ActionListener{

	/**
	 * Player Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -3143066852083636376L;
	private ClientPanel client;
	
	public JButton tokenButton, infoButton, statusOneButton, statusTwoButton, totalButton, displayButton, handButton;
	public HandPanel handPanel;
	public DisplayPanel displayPanel;
	public String display = "";
	public String ID = "";
		
	public PlayerPanel(ClientPanel client, int playerID) { 
		this.client = client;
		setLayout(null);
		
		int key = playerID - 2;
		int offset_x = GUIConfig.PLAYER_X_OFFSET * (key % 2);
		int offset_y = GUIConfig.PLAYER_Y_OFFSET * (key / 2); 
		
		setLocation(GUIConfig.PLAYER_POSITION_X+ offset_x, GUIConfig.PLAYER_POSITION_Y+offset_y);
		setSize(GUIConfig.PLAYER_PANEL_WIDTH, GUIConfig.PLAYER_PANEL_HEIGHT);
		setBackground(Color.WHITE);

		tokenButton = new JButton("Token");
		tokenButton.setLocation(GUIConfig.PLAYER_TOKEN_LOCATION_X, GUIConfig.PLAYER_TOKEN_LOCATION_Y);
		tokenButton.setSize(GUIConfig.PLAYER_TOKEN_WIDTH, GUIConfig.PLAYER_TOKEN_HEIGHT);
		add(tokenButton);
		
		infoButton = new JButton("Info");
		infoButton.setLocation(GUIConfig.PLAYER_INFO_LOCATION_X, GUIConfig.PLAYER_INFO_LOCATION_Y);
		infoButton.setSize(GUIConfig.PLAYER_INFO_WIDTH, GUIConfig.PLAYER_INFO_HEIGHT);
		infoButton.addActionListener(this);
		add(infoButton);
		
		statusOneButton = new JButton("One");
		statusOneButton.setLocation(GUIConfig.PLAYER_STATUS_ONE_LOCATION_X, GUIConfig.PLAYER_STATUS_ONE_LOCATION_Y);
		statusOneButton.setSize(GUIConfig.PLAYER_STATUS_WIDTH, GUIConfig.PLAYER_STATUS_HEIGHT);
		statusOneButton.addActionListener(this);
		add(statusOneButton);
		
		statusTwoButton = new JButton("Two");
		statusTwoButton.setLocation(GUIConfig.PLAYER_STATUS_TWO_LOCATION_X, GUIConfig.PLAYER_STATUS_TWO_LOCATION_Y);
		statusTwoButton.setSize(GUIConfig.PLAYER_STATUS_WIDTH, GUIConfig.PLAYER_STATUS_HEIGHT);
		statusTwoButton.addActionListener(this);
		add(statusTwoButton);

		totalButton = new JButton("Total");
		totalButton.setLocation(GUIConfig.PLAYER_TOTAL_LOCATION_X, GUIConfig.PLAYER_TOTAL_LOCATION_Y);
		totalButton.setSize(GUIConfig.PLAYER_TOTAL_SIZE, GUIConfig.PLAYER_TOTAL_SIZE);
		add(totalButton);

		handPanel = new HandPanel(client, GUIConfig.PLAYER_HAND_LOCATION_X, GUIConfig.PLAYER_HAND_LOCATION_Y, 
				GUIConfig.PLAYER_HAND_WIDTH, GUIConfig.PLAYER_HAND_HEIGHT);
		add(handPanel);
		
		displayPanel = new DisplayPanel(client, GUIConfig.PLAYER_DISPLAY_LOCATION_X, GUIConfig.PLAYER_DISPLAY_LOCATION_Y, 
				GUIConfig.PLAYER_DISPLAY_WIDTH, GUIConfig.PLAYER_DISPLAY_HEIGHT);
		add(displayPanel);	
	}  
	
	public void updateUI(String size, String total, String display){
		this.handPanel.ID = this.ID;
		this.displayPanel.ID = this.ID;
		this.display = display;
		
		totalButton.setText(total);
		handPanel.updateUI(Boolean.FALSE, size);
		if (!display.equals(""))
			displayPanel.updateUI(display);	
	}
	
	public void actionPerformed(ActionEvent e) {
		this.client.dataPacket.put(GAMEConfig.SELECTED_TARGET_ID, this.ID);
		this.client.dataPacket.put(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, e.getActionCommand());	
	}
}
