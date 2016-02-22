package game;

import java.util.ArrayList;

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
	
	// Collection of Getter
	public int 		getTotal() 			{ return this.total; 	}
	
	// Add one card to display
	public void addCard(Card card) { 
		this.display.add(card); 
		this.total += card.getValue();
	}
	
}