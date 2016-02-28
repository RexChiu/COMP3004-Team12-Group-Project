package gui;

import java.net.URL;

import javax.swing.*;

import config.GUIConfig;
import config.IMGConfig; 

public class PoolPanel extends JPanel{
	/**
	 * Pool Panel for the Client Ivanhoe
	 */
	private static final long serialVersionUID = 8310057646687805959L;
	JButton deckButton, discardButton, tokenButton, tournamentButton;
	
	
	public PoolPanel() { 
		setLayout(null);		
		setLocation(GUIConfig.POOL_LOCATION_X, GUIConfig.POOL_LOCATION_Y);
		setSize(GUIConfig.POOL_PANEL_WIDTH, GUIConfig.POOL_PANEL_HEIGHT);
		
		URL deck = this.getClass().getResource(IMGConfig.DECK_IVANHOE_TINY);
		deckButton = new JButton(new ImageIcon(deck));
		deckButton.setLocation(GUIConfig.POOL_DECK_LOCATION_X, GUIConfig.POOL_DECK_LOCATION_Y);
		deckButton.setSize(GUIConfig.POOL_DECK_WIDTH, GUIConfig.POOL_DECK_HEIGHT);
		add(deckButton);


		URL discard = this.getClass().getResource(IMGConfig.DECK_IVANHOE_TINY);
		discardButton = new JButton(new ImageIcon(discard));
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
