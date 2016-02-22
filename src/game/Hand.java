package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Hand {

	private ArrayList<Card> hand;
	
	public Hand() {
		// TODO Auto-generated constructor stub
		this.hand = new ArrayList<Card>();
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
	
	// Check the hand is there a Square
	public boolean hasSquire(){
		for (Card card : hand){
			if (card.isSquire())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a shield
	public boolean hasShield(){
		for (Card card : hand){
			if (card.isShield())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a stunned
	public boolean hasStunned(){
		for (Card card : hand){
			if (card.isStunned())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a Break Lance
	public boolean hasActionBraekLance(){
		for (Card card : hand){
			if (card.isBreakLance())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a Riposte
	public boolean hasActionRiposte(){
		for (Card card : hand){
			if (card.isRiposte())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a Dodge
	public boolean hasActionDodge(){
		for (Card card : hand){
			if (card.isDodge())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a Retreat
	public boolean hasActionRetreat(){
		for (Card card : hand){
			if (card.isRetreat())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a knock down
	public boolean hasActionKnockDown(){
		for (Card card : hand){
			if (card.isKnockDown())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a outmaneuver
	public boolean hasActionOutmaneuver(){
		for (Card card : hand){
			if (card.isOutmaneuver())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a charge
	public boolean hasActionCharge(){
		for (Card card : hand){
			if (card.isCharge())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a countercharge
	public boolean hasActionCountercharge(){
		for (Card card : hand){
			if (card.isCountercharge())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a disgrace
	public boolean hasActionDisgrace(){
		for (Card card : hand){
			if (card.isDisgrace())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a adapt
	public boolean hasActionAdapt(){
		for (Card card : hand){
			if (card.isAdapt())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there a outwit
	public boolean hasActionOutwit(){
		for (Card card : hand){
			if (card.isOutwit())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there any purple card
	public boolean hasPurple(){
		for (Card card : hand){
			if (card.isPurple())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there any red card card
	public boolean hasRed(){
		for (Card card : hand){
			if (card.isRed())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there any blue card
	public boolean hasBlue(){
		for (Card card : hand){
			if (card.isBlue())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there any yellow card
	public boolean hasYellow(){
		for (Card card : hand){
			if (card.isYellow())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}

	// Check the hand is there any green card
	public boolean hasGreen(){
		for (Card card : hand){
			if (card.isGreen())
				return Boolean.TRUE;	
		}
		return Boolean.FALSE;
	}
}