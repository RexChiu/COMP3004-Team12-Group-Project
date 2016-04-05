package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Display {

	private ArrayList<Card> display;
	private String tournament;
	private String status;
	private int numPlayed;
	private int total;
	
	public Display() {
		// TODO Auto-generated constructor stub
		this.display = new ArrayList<Card>();
		
		this.tournament = "";
		this.status 	= "";
		this.total 		= 0;
		this.numPlayed  = 0;
	}

	// Collection of setter 
	public void		playCard()							{ this.numPlayed++;				}
	public void		setStatus(String status) 			{ this.status += status; 		}
	public void 	setTournament(String tournament)	{ this.tournament = tournament; }
	public void 	cleanNumPlayed()					{ this.numPlayed = 0;			}
	
	// Collection of Getter
	public boolean 	isEmpty()			{ return display.isEmpty();										}
	public int		getNumPlayed()		{ return this.numPlayed;										}
	public int 		getTotal() 			{ return ( isGreenTournament() ? display.size() : this.total); 	}
	public Card 	getCard(int index) 	{ return this.display.get(index);								}
	public String	getStatus()			{ return this.status; 											}
	public String 	getTournament() 	{ return this.tournament; 										}
	
	public int		getSize()			{ return display.size();										}
	
	//checks for purple colour card
	public boolean hasPurple(){
		for (Card card : display)
			if (card.isPurple())
				return true;
		return false;
	}
	
	//checks for value cards, returns card value
	public int hasValueCard(){
		for (Card card : display)
			if (card.isSupporter() || card.isBlue() || card.isGreen() || card.isPurple() || card.isRed() || card.isYellow())
				return card.getValue();
		return -1;
	}
	
	//checks for specific value
	public boolean hasValue(int value){
		for (Card card : display)
			if (card.getValue() == value)
				return true;
		return false;
	}
	
	//checks for support card
	public boolean hasSupport(){
		for (Card card : display)
			if (card.isSupporter())
				return true;
		return false;
	}
	
	//checks for shield card
	public boolean hasShield(){
		return this.isShield();
	}
	
	//checks for stunned card
	public boolean hasStunned(){
		return this.isStunned();
	}
	
	// Add one card to display
	public void addCard(Card card) { 
		this.display.add(card); 
		this.total += card.getValue();
		playCard();
		updateDisplay();
	}
	
	// Remove one card from display
	public void removeCard(Card card){
		this.display.remove(card);
		this.total -= card.getValue();
		updateDisplay();
	}
	
	// Check the supporter maiden
	public boolean hasMaiden()	{
		for (Card card : display){
			if (card.isMaiden())
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	// Update the status of the display eithe shield or stunned
	public void updateDisplay(){
		if (display.size() == 0) return;
		int index = display.size() - 1;
		if (display.get(index).isShield())	
			this.status += ( isShield() ? "" : GAMEConfig.STATUS_SHIELD);
		
		if (display.get(index).isStunned()) 
			this.status += ( isShield() ? "" : GAMEConfig.STATUS_STUNNED);
	}
	
	// Check the green tournament
	public boolean 	isGreenTournament() { return this.tournament.equalsIgnoreCase(GAMEConfig.COLOR_GREEN); 		}
	
	// Check the status whether is shield of not
	public boolean 	isShield() 			{ return GAMEConfig.containsKey(this.status, GAMEConfig.STATUS_SHIELD); }

	// Check the status whether is stunned of not
	public boolean 	isStunned() 		{ return GAMEConfig.containsKey(this.status, GAMEConfig.STATUS_STUNNED);}
		
	public String toString(){
		String result = "";
		if (display.isEmpty()) return result;
		for (Card card: display){ result += card + ";"; }
		return result.substring(0, result.length()-1);
	}
}
