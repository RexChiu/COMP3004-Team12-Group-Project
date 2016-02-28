package game;

import config.GAMEConfig;

public class Ivanhoe {

	private int state;
	private Deck deck;
	private Deck deadwood;
	
	public Ivanhoe() {
		// TODO Auto-generated constructor stub
		this.state = 0;
		this.deck = new Deck();
		this.deck.init();
		this.deadwood = new Deck();
	}
	
	public static String processInt(String input)	{ 
		return input; 
	}
	
	public String 	selectedTournament(String input)	{ return input; }
	
	public boolean 	playTournament()	{ return Boolean.TRUE; }
	public void 	playCards()			{	}	
	public void 	nextPlayer()		{	}
	public void 	nextTournament()	{	}

	public void 	setState(int state)	{ this.state = state; }
	public int 		getStstae()			{ return this.state; }
	public boolean 	gameReady()			{ return this.state == GAMEConfig.GAME_READY;	}
	public boolean 	withdraw()			{ return this.state == GAMEConfig.WITHDAWAL;	}
	public boolean 	gameOver()			{ return this.state == GAMEConfig.GAME_OVER;	}
}
