package gui;

import javax.swing.*;

import config.GUIConfig; 

public class UserPanel extends JPanel{

	/**
	 * User Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -449738241414461419L;

	public UserPanel() { 
		setLocation(GUIConfig.USER_PANEL_LOCATION_X, GUIConfig.USER_PANEL_LOCATION_Y);
		setSize(GUIConfig.USER_PANEL_WIDTH, GUIConfig.USER_PANEL_HEIGHT);
		setLayout(null);	
		
		JButton tokenButton = new JButton("Token");
		tokenButton.setLocation(GUIConfig.USER_TOKEN_LOCATION_X, GUIConfig.USER_TOKEN_LOCATION_Y);
		tokenButton.setSize(GUIConfig.USER_TOKEN_WIDTH, GUIConfig.USER_TOKEN_HEIGHT);
		add(tokenButton);
		
		JButton infoButton = new JButton("Info");
		infoButton.setLocation(GUIConfig.USER_INFO_LOCATION_X, GUIConfig.USER_INFO_LOCATION_Y);
		infoButton.setSize(GUIConfig.USER_INFO_WEIGTH, GUIConfig.USER_INFO_HEIGHT);
		add(infoButton);
		
		JButton handButton = new JButton("Hand");
		handButton.setLocation(GUIConfig.USER_HAND_LOCATION_X, GUIConfig.USER_HAND_LOCATION_Y);
		handButton.setSize(GUIConfig.USER_HAND_WIDTH, GUIConfig.USER_HAND_HEIGHT);
		add(handButton);
		
		
		JButton statusOneButton = new JButton("One");
		statusOneButton.setLocation(GUIConfig.USER_STATUS_ONE_LOCATION_X, GUIConfig.USER_STATUS_ONE_LOCATION_Y);
		statusOneButton.setSize(GUIConfig.USER_STATUS_WIDTH, GUIConfig.USER_STATUS_HEIGHT);
		add(statusOneButton);
		
		JButton statusTwoButton = new JButton("Two");
		statusTwoButton.setLocation(GUIConfig.USER_STATUS_TWO_LOCATION_X, GUIConfig.USER_STATUS_TWO_LOCATION_Y);
		statusTwoButton.setSize(GUIConfig.USER_STATUS_WIDTH, GUIConfig.USER_STATUS_HEIGHT);
		add(statusTwoButton);
		
		JButton totalButton = new JButton("Total");
		totalButton.setLocation(GUIConfig.USER_TOTAL_LOCATION_X, GUIConfig.USER_TOTAL_LOCATION_Y);
		totalButton.setSize(GUIConfig.USER_TOTAL_SIZE, GUIConfig.USER_TOTAL_SIZE);
		add(totalButton);
		
		JButton displayButton = new JButton("Dispaly");
		displayButton.setLocation(GUIConfig.USER_DISPLAY_LOCATION_X, GUIConfig.USER_DISPLAY_LOCATION_Y);
		displayButton.setSize(GUIConfig.USER_DISPLAY_WIDTH, GUIConfig.USER_DISPLAY_HEIGHT);
		add(displayButton);
	}  
}
