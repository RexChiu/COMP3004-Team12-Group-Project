package gui;

import java.awt.*; 
import javax.swing.*;

import config.GUIConfig; 

public class TournamentPanel extends JPanel{

	/**
	 * Tournament Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = -6487157126971866036L;

	public TournamentPanel() { 
		setLayout(null);

		for (int i = 0; i < 5; i ++){
			JButton infoLabel = new JButton("INFO");
			infoLabel.setLocation(75*i, 0);
			infoLabel.setSize(GUIConfig.TOURNAMENT_INFO_WIDTH, GUIConfig.TOURNAMENT_INFO_HEIGHT);
			add(infoLabel);
			
			JButton statusLabel = new JButton("Status");
			statusLabel.setLocation(75*i, 100);
			statusLabel.setSize(GUIConfig.TOURNAMENT_STATUS_WIDTH, GUIConfig.TOURNAMENT_STATUS_HEIGHT);
			add(statusLabel);
			
			JButton totalLabel = new JButton("Total");
			totalLabel.setLocation(75*i, 150);
			totalLabel.setSize(GUIConfig.TOURNAMENT_TOTAL_WIDTH, GUIConfig.TOURNAMENT_TOTAL_HEIGHT);
			add(totalLabel);
		}

		setLocation(GUIConfig.TOURNAMENT_PANEL_LOCATION_X, GUIConfig.TOURNAMENT_PANEL_LOCATION_Y);
		setSize(GUIConfig.TOURNAMENT_PANEL_WIDTH, GUIConfig.TOURNAMENT_PANEL_HEIGHT);
		setBackground(Color.WHITE);
	}  
}
