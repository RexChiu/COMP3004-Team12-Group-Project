package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Hand {

	private ArrayList<Card> hand;
	
	public Hand() {
		// TODO Auto-generated constructor stub
		hand = new ArrayList<Card>();
	}
	
	// Draw a card into the hands
	public void drawCard(Card card) { hand.add(card); }
	
	// Get the information of card in the hand
	public Card getCard(int index) { return hand.get(index); }
}