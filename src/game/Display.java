package game;

import java.util.ArrayList;

import config.GAMEConfig;

public class Display {

	private ArrayList<Card> display;
	private String tournament;
	private String status;
	private int total;
	
	public Display() {
		// TODO Auto-generated constructor stub
		this.display = new ArrayList<Card>();
		
		this.tournament = "";
		this.status 	= "";
		this.total 	= 0;
	}

	// Collection of setter 
	public void		setStatus(String status) 		{ this.status += status; 	}
	public void 	setTournament(String tournament) { this.tournament = tournament; }
	
	// Collection of Getter
	public boolean 	isEmpty()			{ return display.isEmpty();										}
	public int 		getTotal() 			{ return ( isGreenTournament() ? display.size() : this.total); 	}
	public Card 	getCard(int index) 	{ return this.display.get(index);								}
	public String	getStatus()			{ return this.status; 											}
	public String 	getTournament() 	{ return this.tournament; 										}
	
	public int		getSize()			{ return display.size();										}
	
	// Add one card to display
	public void addCard(Card card) { 
		this.display.add(card); 
		this.total += card.getValue();
		updateDisplay();
	}
	
	// Remove one card from display
	public void removeCard(Card card){
		this.display.remove(card);
		this.total -= card.getValue();
		updateDisplay();
	}
	
	// Check the supporter maiden
	public void removeMaiden()	{
		for (Card card : display){
			if (card.isMaiden()){
				display.remove(card);
				return;
			}
		}
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
	
	//DISGRACE: Remove all supporter
	public Hand	removeSupport(){
		Hand hand  = new Hand();
		for (Card card : display){
			if (card.isSquire() || card.isMaiden())
				hand.drawCard(card);
		}	
		return hand;
	}
	
	//UNHORSE: The tournament color changes from purple to red, blue or yellow
	public void 	unhorse(String tournament) 		{ this.tournament = tournament; 							}
	
	//CHANGE WEAPON: The tournament color changes from red, blue or yellow to a differ
	public void 	changeWeapon(String tournament) { this.tournament = tournament; 							}
	
	//DROP WEAPON: The tournament color changes from red, blue or yellow to green.
	public void 	dropWeapon() 					{ this.tournament = GAMEConfig.GREEN_CARD; 					}

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
