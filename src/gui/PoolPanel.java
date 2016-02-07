package gui;

import java.awt.*; 
import javax.swing.*;

import config.GUIConfig; 

public class PoolPanel extends JPanel{
	JButton deckButton, discardButton, tokenButton, tournamentButton;
	
	
	public PoolPanel() { 
		setLayout(null);		
		setLocation(GUIConfig.POOL_LOCATION_X, GUIConfig.POOL_LOCATION_Y);
		setSize(GUIConfig.POOL_PANEL_WIDTH, GUIConfig.POOL_PANEL_HEIGHT);

		deckButton = new JButton("Deck");
		deckButton.setLocation(GUIConfig.POOL_DECK_LOCATION_X, GUIConfig.POOL_DECK_LOCATION_Y);
		deckButton.setSize(GUIConfig.POOL_DECK_WIDTH, GUIConfig.POOL_DECK_HEIGHT);
		add(deckButton);

		discardButton = new JButton("Discard");
		discardButton.setLocation(GUIConfig.POOL_DISCARD_LOCATION_X, GUIConfig.POOL_TOKEN_LOCATION_Y);
		discardButton.setSize(GUIConfig.POOL_DISCARD_WIDTH, GUIConfig.POOL_DISCARD_HEIGHT);
		add(discardButton);

		tokenButton = new JButton("Token");
		tokenButton.setLocation(GUIConfig.POOL_TOKEN_LOCATION_X, GUIConfig.POOL_TOKEN_LOCATION_Y);
		tokenButton.setSize(GUIConfig.POOL_TOKEN_WIDTH, GUIConfig.POOL_TOKEN_HEIGHT);
		add(tokenButton);
		
		tournamentButton = new JButton("Tournment");
		tournamentButton.setLocation(GUIConfig.POOL_TOURNAMENT_LOCATION_X, GUIConfig.POOL_TOURNAMENT_LOCATION_Y);
		tournamentButton.setSize(GUIConfig.POOL_TOURNAMENT_WIDTH, GUIConfig.POOL_TOURNAMENT_HEIGHT);
		add(tournamentButton);
		
	}  
}
