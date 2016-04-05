package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Hand {

	private ArrayList<Card> hand;
	
	public Hand() {
		// TODO Auto-generated constructor stub
		this.hand = new ArrayList<Card>();
	}
	
	public Hand(String data){
		String [] hand = data.split(";");
		this.hand = new ArrayList<Card>();
		for (int i = 0; i < hand.length; i++){
			drawCard(new Card(hand[i]));		
		}
	}
	
	// Check there is only last card in the hand
	public boolean lastCard() { return this.hand.size() == GAMEConfig.MIN_CARD; }
	
	// Draw a card into the hands
	public void drawCard(Card card) { hand.add(card); }
	
	// Play a card to the display
	public boolean playCard(Card card){ return hand.remove(card); }
	
	// Get the information of card in the hand
	public Card getCard(int index) { return hand.get(index); }
	
	// Get the size of the hand
	public int 	getSize() { return this.hand.size(); }
	
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
	public int hasIvanhoe(){
		for (int i = 0; i < hand.size(); i++){
			if (hand.get(i).isIvanhoe())
				return i;	
		}
		return -1;
	}

	// Check the hand is there a Maiden
	public boolean hasMaiden(){
		for (Card card : hand){
			if (card.isMaiden())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}
	
	public String toString(){
		String result = "";
		if (hand.isEmpty()) return result;
		for (Card card: hand){ result += card + ";"; }
		
		return result.substring(0, result.length()-1);
	}
}