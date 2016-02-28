package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import config.GAMEConfig;
import config.LANConfig;

public class Ivanhoe {

	private int state;
	private Deck deck;
	private Deck deadwood;
	private int numPlayers;
	private ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<String> tokens = new ArrayList<>();
	
	public Ivanhoe(int num) {
		// TODO Auto-generated constructor stub
		this.state = GAMEConfig.SETUP;
		this.deck = new Deck();
		//this.deck.init();
		this.deadwood = new Deck();
		this.numPlayers = num;
	}
	
	public void addPlayer(int ID){ 
		tokens.add(GAMEConfig.TOKEN_COLORS[tokens.size()]);
		players.add(new Player(ID)); 
	}
	
	public void removePlayer(int ID){
		for (int i = 0; i < players.size(); i++){
			if (players.get(i).getID() == ID){
				players.remove(i);
				tokens.remove(i);
			}
		}
	}
	
	public Player getPlayer(int ID){
		for (Player player: players){
			if (player.getID() == ID)
				return player;
		}
		return null;
	}
	
	public String getData(){
		String data = "";
		if (players.isEmpty()) return data;
		for (Player player: players){
			data += player + "/";
		}
		return data.substring(0, data.length()-1);
	}
	
	public String processInt(String input)	{ 
		if (input.equals(LANConfig.GAME_READY)){
			this.state = GAMEConfig.GAME_READY;
			Collections.shuffle(tokens);
			int index = 0;
			for (Player player: players) { player.setToken(tokens.get(index++)); }
			for (Player player: players) { if (player.checkToken(GAMEConfig.COLOR_PURPLE)) return Integer.toString(player.getID()); }
		}
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
