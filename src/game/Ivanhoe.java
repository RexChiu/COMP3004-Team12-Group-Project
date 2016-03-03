package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import config.GAMEConfig;
import config.LANConfig;
import message.Message;

public class Ivanhoe {

	private int state;
	private Deck deck;
	private Deck deadwood; // discard
	private int numPlayers;
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	ArrayList<String> tokens = new ArrayList<>();
	
	public Ivanhoe(int num) {
		// TODO Auto-generated constructor stub
		this.state = GAMEConfig.GAME_SETUP;
		this.deck = new Deck();
		this.deck.init();
		this.deck.shuffleDeck();
		this.deadwood = new Deck();
		this.numPlayers = num;
	}
	
	public void addPlayer(int ID){ 
		tokens.add(GAMEConfig.TOKEN_COLORS[tokens.size()]);
		players.put(ID, new Player(ID));
	}
	
	public void removePlayer(int ID){	
		players.remove(ID);
	}
	
	public Player getPlayer(int ID){
		return players.get(ID);
	}
	
	public Message getMessage(int ID){
		Message message = new Message();
		Player user = players.get(ID);
		message.getHeader().setType("Setup");
		message.getBody().addField("UserID",ID);
		message.getBody().addField("UserHand", user.getHand().toString());
		message.getBody().addField("UserTokens", user.getTokens().toString());
		message.getBody().addField("UserDisplay", user.getDisplayer().toString());
		message.getBody().addField("UserStatus", user.getDisplayer().getStatus());
		message.getBody().addField("UserTotal", user.getDisplayer().getTotal());
		
		String playersID = "";
		String tournamentInfo = "";
		for (Integer key: players.keySet()){
			if (key != ID){
				message.getBody().addField("Player " + key + " Tokens", players.get(key).getTokens().toString());
				message.getBody().addField("Player " + key + " Hand", players.get(key).getHand().getSize());
				message.getBody().addField("Player " + key + " Status", players.get(key).getDisplayer().getStatus());
				message.getBody().addField("Player " + key + " Display", players.get(key).getDisplayer().toString());
				message.getBody().addField("Player " + key + " Total", players.get(key).getDisplayer().getTotal());
				playersID += key + ",";
			}
			tournamentInfo += key + ":" + players.get(key).getDisplayer().getStatus() + ":" + players.get(key).getDisplayer().getTotal() + ";"; 
		}
		message.getBody().addField("PlayersID", playersID.substring(0, playersID.length()-1));
		message.getBody().addField("TournamentInfo", tournamentInfo.substring(0, tournamentInfo.length()-1));
		return message;
	}
	
	public String getData(){
		String data = "";
		if (players.isEmpty()) return data;
		for (Integer key: players.keySet()){
			data += players.get(key).toString() + "/";
		}
		return data.substring(0, data.length()-1);
	}
	
	public String processInt(String input)	{ 
		if (input.equals(LANConfig.GAME_READY)){
			this.state = GAMEConfig.GAME_READY;
			Collections.shuffle(tokens);
			int index = 0;
			for (Integer key: players.keySet()){
				players.get(key).setToken(tokens.get(0)); 
				tokens.remove(0);
			}
			for (Integer key: players.keySet()){
				for (int i = 0; i < 8; i++){
					Card card = deck.getCard(0);
					deck.removeCard(card);
					players.get(key).addCard(card); 
					card = deck.getCard(0);
					deck.removeCard(card);
					players.get(key).addDisplay(card);
				}
			}
			for (Integer key: players.keySet()){ 
				if (players.get(key).checkToken(GAMEConfig.COLOR_PURPLE)) 
					return Integer.toString(players.get(key).getID());
			}
			
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
