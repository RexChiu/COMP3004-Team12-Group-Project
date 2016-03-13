package gui;

import javax.swing.*;

import config.GUIConfig;

public class UserPanel extends JPanel{

	/**
	 * User Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -449738241414461419L;

	public JButton tokenButton, infoButton, statusOneButton, statusTwoButton, totalButton;
	public HandPanel handPanel;
	public DisplayPanel displayPanel;
	public String hand;
	
	public UserPanel(ClientPanel client) { 		
		setLocation(GUIConfig.USER_PANEL_LOCATION_X, GUIConfig.USER_PANEL_LOCATION_Y);
		setSize(GUIConfig.USER_PANEL_WIDTH, GUIConfig.USER_PANEL_HEIGHT);
		setLayout(null);	
		
		tokenButton = new JButton("Token");
		tokenButton.setLocation(GUIConfig.USER_TOKEN_LOCATION_X, GUIConfig.USER_TOKEN_LOCATION_Y);
		tokenButton.setSize(GUIConfig.USER_TOKEN_WIDTH, GUIConfig.USER_TOKEN_HEIGHT);
		add(tokenButton);
		
		infoButton = new JButton("Info");
		infoButton.setLocation(GUIConfig.USER_INFO_LOCATION_X, GUIConfig.USER_INFO_LOCATION_Y);
		infoButton.setSize(GUIConfig.USER_INFO_WEIGTH, GUIConfig.USER_INFO_HEIGHT);
		add(infoButton);
		
		handPanel = new HandPanel(client, GUIConfig.USER_HAND_LOCATION_X, GUIConfig.USER_HAND_LOCATION_Y, GUIConfig.USER_HAND_WIDTH, GUIConfig.USER_HAND_HEIGHT);
		add(handPanel);
		
		statusOneButton = new JButton("One");
		statusOneButton.setLocation(GUIConfig.USER_STATUS_ONE_LOCATION_X, GUIConfig.USER_STATUS_ONE_LOCATION_Y);
		statusOneButton.setSize(GUIConfig.USER_STATUS_WIDTH, GUIConfig.USER_STATUS_HEIGHT);
		add(statusOneButton);
		
		statusTwoButton = new JButton("Two");
		statusTwoButton.setLocation(GUIConfig.USER_STATUS_TWO_LOCATION_X, GUIConfig.USER_STATUS_TWO_LOCATION_Y);
		statusTwoButton.setSize(GUIConfig.USER_STATUS_WIDTH, GUIConfig.USER_STATUS_HEIGHT);
		add(statusTwoButton);
		
		totalButton = new JButton("Total");
		totalButton.setLocation(GUIConfig.USER_TOTAL_LOCATION_X, GUIConfig.USER_TOTAL_LOCATION_Y);
		totalButton.setSize(GUIConfig.USER_TOTAL_SIZE, GUIConfig.USER_TOTAL_SIZE);
		add(totalButton);
		
		displayPanel = new DisplayPanel(client, GUIConfig.USER_DISPLAY_LOCATION_X, GUIConfig.USER_DISPLAY_LOCATION_Y, GUIConfig.USER_DISPLAY_WIDTH, GUIConfig.USER_DISPLAY_HEIGHT);
		add(displayPanel);
	}  
	
	public void updateHand(String hand)			{ handPanel.updateUI(hand); 		}
	public void updateDisplay(String display)	{ displayPanel.updateUI(display); 	}
	public void updateTotal(String total)		{ totalButton.setText(total);;		}
	
	public void updateUI(String hand, String total, String card){ 
		this.hand = hand;
		updateTotal(total);
		updateHand(hand);
		if (!card.equals("")) 
			updateDisplay(card);
	}
}
