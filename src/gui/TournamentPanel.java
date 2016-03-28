package gui;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import config.GUIConfig; 

public class TournamentPanel extends JPanel{

	/**
	 * Tournament Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -6487157126971866036L;

	public HashMap<Integer, JButton> infoLabel 		= new HashMap<Integer, JButton>();
	public HashMap<Integer, JButton> statusLabel	= new HashMap<Integer, JButton>();
	public HashMap<Integer, JButton> totalLabel 	= new HashMap<Integer, JButton>();
	
	
	public TournamentPanel() { 
		setLayout(null);

		for (int i = GUIConfig.USER_PLAYER_ID; i <= GUIConfig.FIFTH_PLAYER_ID; i++){
			infoLabel.put(i, new JButton("INFO"));
			infoLabel.get(i).setLocation(75*(i-1), 0);
			infoLabel.get(i).setSize(GUIConfig.TOURNAMENT_INFO_WIDTH, GUIConfig.TOURNAMENT_INFO_HEIGHT);
			add(infoLabel.get(i));
			
			statusLabel.put(i, new JButton("Status"));
			statusLabel.get(i).setLocation(75*(i-1), 100);
			statusLabel.get(i).setSize(GUIConfig.TOURNAMENT_STATUS_WIDTH, GUIConfig.TOURNAMENT_STATUS_HEIGHT);
			add(statusLabel.get(i));
			
			totalLabel.put(i, new JButton("Total"));
			totalLabel.get(i).setLocation(75*(i-1), 150);
			totalLabel.get(i).setSize(GUIConfig.TOURNAMENT_TOTAL_WIDTH, GUIConfig.TOURNAMENT_TOTAL_HEIGHT);
			add(totalLabel.get(i));
		}

		setLocation(GUIConfig.TOURNAMENT_PANEL_LOCATION_X, GUIConfig.TOURNAMENT_PANEL_LOCATION_Y);
		setSize(GUIConfig.TOURNAMENT_PANEL_WIDTH, GUIConfig.TOURNAMENT_PANEL_HEIGHT);
		setBackground(Color.WHITE);
	}  
}
