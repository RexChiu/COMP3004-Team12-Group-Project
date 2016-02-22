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
	
	// Count the numeber of same card in the hand
	public int countCard(Card sameCard) {
		int count = 0;
		for (Card card : hand){
			if (card.equals(sameCard))
				count++;
		}
		return count; 
	}
	
	// Check all card are action
	public boolean allAction() {
		for (Card card : hand){
			if (!card.isAction())
				return Boolean.FALSE;	
		}
		return Boolean.TRUE;
	}
	
	// Check the hand is there an Ivanhoe
	public boolean hasIvanhoe(){
		for (Card card : hand){
			if (card.isIvanhoe())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a Maiden
	public boolean hasMaiden(){
		for (Card card : hand){
			if (card.isMaiden())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}
}