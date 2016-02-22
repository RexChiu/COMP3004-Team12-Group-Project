package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Hand {

	private ArrayList<Card> hand;
	
	public Hand() {
		// TODO Auto-generated constructor stub
		hand = new ArrayList<Card>();
	}
	
	// Check there is only last card in the hand
	public boolean lastCard() { return this.hand.size() == GAMEConfig.MIN_CARD; }
	
	// Draw a card into the hands
	public void drawCard(Card card) { hand.add(card); }
	
	// Play a card to the display
	public boolean playCard(Card card){ return ( !lastCard() ? hand.remove(card) : Boolean.FALSE); }
	
	// Get the information of card in the hand
	public Card getCard(int index) { return hand.get(index); }
}