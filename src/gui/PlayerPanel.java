package gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import config.GUIConfig;

public class PlayerPanel extends JPanel{

	public JButton tokenButton, infoButton, statusOneButton, statusTwoButton, totalButton, displayButton, handButton;
	
	public PlayerPanel(int playerID) { 
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
		add(infoButton);
		
		statusOneButton = new JButton("One");
		statusOneButton.setLocation(GUIConfig.PLAYER_STATUS_ONE_LOCATION_X, GUIConfig.PLAYER_STATUS_ONE_LOCATION_Y);
		statusOneButton.setSize(GUIConfig.PLAYER_STATUS_WIDTH, GUIConfig.PLAYER_STATUS_HEIGHT);
		add(statusOneButton);
		
		statusTwoButton = new JButton("Two");
		statusTwoButton.setLocation(GUIConfig.PLAYER_STATUS_TWO_LOCATION_X, GUIConfig.PLAYER_STATUS_TWO_LOCATION_Y);
		statusTwoButton.setSize(GUIConfig.PLAYER_STATUS_WIDTH, GUIConfig.PLAYER_STATUS_HEIGHT);
		add(statusTwoButton);

		totalButton = new JButton("Total");
		totalButton.setLocation(GUIConfig.PLAYER_TOTAL_LOCATION_X, GUIConfig.PLAYER_TOTAL_LOCATION_Y);
		totalButton.setSize(GUIConfig.PLAYER_TOTAL_SIZE, GUIConfig.PLAYER_TOTAL_SIZE);
		add(totalButton);
		
		displayButton = new JButton("Dispaly");
		displayButton.setLocation(GUIConfig.PLAYER_DISPLAY_LOCATION_X, GUIConfig.PLAYER_DISPLAY_LOCATION_Y);
		displayButton.setSize(GUIConfig.PLAYER_DISPLAY_WIDTH, GUIConfig.PLAYER_DISPLAY_HEIGHT);
		add(displayButton);		
		
		handButton = new JButton("Hand");
		handButton.setLocation(GUIConfig.PLAYER_HAND_LOCATION_X, GUIConfig.PLAYER_HAND_LOCATION_Y);
		handButton.setSize(GUIConfig.PLAYER_HAND_WIDTH, GUIConfig.PLAYER_HAND_HEIGHT);
		add(handButton);
	}  

}
