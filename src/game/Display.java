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
	public int 		getTotal() 			{ return this.total; 				}
	public Card 	getCard(int index) 	{ return this.display.get(index);	}
	public String	getStatus()			{ return this.status; 				}
	public String 	getTournament() 	{ return this.tournament; 			}
	
	// Add one card to display
	public void addCard(Card card) { 
		this.display.add(card); 
		this.total += card.getValue();
	}

	//UNHORSE: The tournament color changes from purple to red, blue or yellow
	public void 	unhorse(String tournament) 		{ this.tournament = tournament; }

	// Check the green tournament
	public boolean 	isGreenTournament() { return this.tournament.equalsIgnoreCase(GAMEConfig.COLOR_GREEN); 		}
	
	// Check the status whether is shield of not
	public boolean 	isShield() 			{ return GAMEConfig.containsKey(this.status, GAMEConfig.STATUS_SHIELD); }

	// Check the status whether is stunned of not
	public boolean 	isStunned() 		{ return GAMEConfig.containsKey(this.status, GAMEConfig.STATUS_STUNNED);}
}
