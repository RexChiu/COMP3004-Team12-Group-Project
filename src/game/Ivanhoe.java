package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import config.GAMEConfig;
import message.Message;

public class Ivanhoe {

	private int state;
	private Deck deck;
	private Deck deadwood; // discard
	private int numPlayers;
	private int playersLeft;
	private int firstPlayer;
	private int currentPlayer;
	private int currentID;

	private String prevTournamentColour;
	private String currTournamentColour;

	private int prevState;
	private boolean legal = Boolean.FALSE;
	private boolean allResponded	= Boolean.FALSE;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private ArrayList<Integer> playersOrder = new ArrayList<>();
	private HashMap<Integer, Boolean> confirm = new HashMap<>();

	public Ivanhoe() {
		// TODO Auto-generated constructor stub
		this.state = GAMEConfig.GAME_READY;
	}

	public void addPlayer(int ID)	{ players.put(ID, new Player(ID));	}
	public void removePlayer(int ID){ players.remove(ID); 				}
	public Player getPlayer(int ID)	{ return players.get(ID);			}

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

	public void setup(){
		// Init Deck		
		this.deck = new Deck();
		this.deck.init();		
		this.deck.shuffleDeck();
		this.deadwood = new Deck();
		this.numPlayers = players.size();

		// Init Hands
		for (int i = 0; i < 8; i++){
			for (Integer key: playersOrder){
				Card card = deck.getCard(0);
				this.players.get(key).addCard(card);
				this.deck.removeCard(card);
			}		
		}		

		// Init Player Join Order		
		for (Integer key: players.keySet()){
			this.confirm.put(key, Boolean.FALSE);
			this.playersOrder.add(key);		
		}

		this.currentPlayer = new Random().nextInt(numPlayers);		
		this.currentID = this.playersOrder.get(currentPlayer);
		this.firstPlayer = currentID;
		this.playersLeft = numPlayers;
		
		Card card = deck.getCard(0);
		this.players.get(currentID).addCard(card);
		this.deck.removeCard(card);		

		this.state = GAMEConfig.SELECT_COLOUR;
		this.prevState = GAMEConfig.GAME_SETUP;
	}


	private void dealCard(){
		if (deck.isEmpty()){
			deadwood.shuffleDeck();
			deck = deadwood;
			deadwood.cleanDeck();
		}

		Card newCard = deck.getCard(0);
		players.get(playersOrder.get(currentPlayer)).addCard(newCard);
		deck.removeCard(newCard);
	}

	public Message processMessage(Message message){
		int userID = Integer.parseInt(message.getBody().getField("UserID").toString());
		int userState = message.getHeader().getState();

		if (userID == this.currentID && userState == this.state){
			if (userState == GAMEConfig.SELECT_COLOUR){
				this.prevTournamentColour = this.currTournamentColour;
				this.currTournamentColour = message.getBody().getField("Colour").toString();
				for (int key : this.players.keySet()){
					this.players.get(key).getDisplayer().setTournament(this.currTournamentColour);
				}
				this.state = GAMEConfig.PLAY_OR_WITHDRAW;
				this.prevState = GAMEConfig.SELECT_COLOUR;
				return Data.playOrWithdraw(this.players, userID);
			} else if (userState == GAMEConfig.PLAY_OR_WITHDRAW){
				String response = message.getBody().getField("POW Choice").toString();
				if (response.equalsIgnoreCase(GAMEConfig.POW_PLAY)){
					this.state = GAMEConfig.PLAY_CARD;
					this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
					return null; 
				} else {
					this.players.get(currentID).setWithdrawn(Boolean.TRUE);
					this.currentPlayer = (currentPlayer+1)&numPlayers;
					this.currentID = playersOrder.get(currentPlayer);
					this.playersLeft--;
					
					dealCard();
					
					if (playersLeft == 1){
						for (Integer key : this.players.keySet()){
							if (!this.players.get(key).isWithdrawn()){
								currentID = key;
								break;								
							}	
						}	
						if (this.currTournamentColour.equalsIgnoreCase(GAMEConfig.COLOR_PURPLE)){
							
						}else{		
							if (!this.players.get(currentID).getTokens().checkToken(this.currTournamentColour)){
								this.players.get(currentID).addToken(this.currTournamentColour);
							}
							this.state = GAMEConfig.WIN_TOURNAMENT;
							this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
							return Data.winTournament(players, currentID);								
						}					
					}else{
						this.state = GAMEConfig.PLAY_OR_WITHDRAW;
						this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
						return Data.playOrWithdraw(this.players, userID);		
					}
				}				
			} else if (userState == GAMEConfig.PLAY_CARD){
				String selectedCard = message.getBody().getField("Selected Card").toString();
				Card card = new Card(selectedCard);
				if (userID == this.firstPlayer && players.get(userID).getDisplayer().isEmpty()) {
					if (!card.isAction()){
						this.players.get(userID).getDisplayer().addCard(card);
						this.players.get(userID).getHand().playCard(card);
					}
				} else if (!card.isAction()){
					if (card.getColor().equalsIgnoreCase(this.currTournamentColour) || card.isSupporter()){
						this.players.get(userID).getDisplayer().addCard(card);
						this.players.get(userID).getHand().playCard(card);
					}
				}else{
					// Action Card					
				}
			} else if (userState == GAMEConfig.END_TURN){
				do {
					this.currentPlayer = (currentPlayer+1)&numPlayers;
					this.currentID = playersOrder.get(currentPlayer);					
					dealCard();			
				} while (players.get(currentID).isWithdrawn());				
				
				this.state = GAMEConfig.PLAY_OR_WITHDRAW;
				this.prevState = GAMEConfig.END_TURN;	
				return Data.playOrWithdraw(this.players, userID);					
			} else if (userState == GAMEConfig.WIN_TOURNAMENT){
				if (!confirm.get(userID)){
					confirm.put(userID, Boolean.TRUE);
				}
				

				// Catch ALl Conifrm
					
			}



	}





	return new Message();
}

/*public int processInput(Message message){
		int state = message.getHeader().getState();
		if (playersOrder.size() > 0)
			currentID = getCurrentID();
		System.out.println(String.format("Before Ivanhoe State[%5d]: %20s", currentID, GAMEConfig.STATE[state]));

		if (state == GAMEConfig.GAME_SETUP){
			setup();
			this.state = GAMEConfig.START_TOURNAMENT;
			this.prevState = GAMEConfig.GAME_SETUP;
		} else if (state == GAMEConfig.START_TOURNAMENT){
			this.currentID = this.playersOrder.get(this.currentPlayer);
			this.state = GAMEConfig.DEAL_CARD;
			this.prevState = GAMEConfig.START_TOURNAMENT;
		} else if (state == GAMEConfig.DEAL_CARD){
			dealCard();			
			if (prevState == GAMEConfig.START_TOURNAMENT) {
				this.state = GAMEConfig.SELECT_COLOUR;
			} else if (prevState == GAMEConfig.PLAY_CARD) {
				this.state = GAMEConfig.CONFIRM_TOURNAMENT;
			} else {
				this.state = GAMEConfig.CONFIRM_TOURNAMENT;
			}
			this.prevState = GAMEConfig.DEAL_CARD;
		} else if (state == GAMEConfig.SELECT_COLOUR){
			this.currentID = playersOrder.get(currentPlayer);
			if (players.get(currentID).getHand().allAction()){
				System.out.println(players.get(currentID).getHand().toString()+"\n");
				int index = 0;
				int ID = 0;
				for (int i = 0; i < this.numPlayers; i++){
					index = (i+currentPlayer)%numPlayers;
					ID = playersOrder.get(index);
					if (!players.get(ID).getHand().allAction()){
						currentPlayer = index;
						this.currentID = playersOrder.get(currentPlayer);
						this.state = GAMEConfig.SELECT_COLOUR;
					}else{
						System.out.println(players.get(ID).getHand().toString()+"\n");						
					}
				}
			} else { 
				this.state = GAMEConfig.CONFIRM_COLOUR;
			}
			this.prevState = GAMEConfig.SELECT_COLOUR;
		} else if (state == GAMEConfig.CONFIRM_COLOUR){

			String currColour = message.getBody().getField("Colour").toString();
			this.prevTournamentColour = currColour;
			this.currTournamentColour = currColour;
			for (int key : this.players.keySet()){
				this.players.get(key).getDisplayer().setTournament(this.currTournamentColour);
			}
			this.state = GAMEConfig.CONFIRM_TOURNAMENT;
			this.prevState = GAMEConfig.CONFIRM_COLOUR;
		} else if (state == GAMEConfig.CONFIRM_TOURNAMENT){
			this.currentID = playersOrder.get(currentPlayer);

			int playerLeft = this.numPlayers;
			for (Integer key : this.players.keySet()){
				if (this.players.get(key).isWithdrawn())
					playerLeft--;
			}

			if (playerLeft == 1){ // send msg to every body for winner
				// Find the winner
				for (Integer key : playersOrder){
					if (!this.players.get(key).isWithdrawn()){
						this.currentPlayer = playerLeft-1;
						this.currentID = playersOrder.get(currentPlayer);
					}
					playerLeft++;						
				}
				this.state = GAMEConfig.WIN_TOURNAMENT;
				this.prevState = GAMEConfig.CONFIRM_TOURNAMENT;
			} else if (!players.get(currentID).isWithdrawn()){ // Send request of play or withdraw
				this.prevState = GAMEConfig.CONFIRM_TOURNAMENT;
				this.state = GAMEConfig.PLAY_OR_WITHDRAW;
			} else {
				currentPlayer = (currentPlayer+1) % numPlayers;	
				this.prevState = GAMEConfig.CONFIRM_TOURNAMENT;	
				this.state = GAMEConfig.CONFIRM_TOURNAMENT;
			}
		} else if (state == GAMEConfig.PLAY_OR_WITHDRAW){
			this.currentID = playersOrder.get(currentPlayer);
			String response = message.getBody().getField("POW Choice").toString();
			if (response.equalsIgnoreCase(GAMEConfig.POW_PLAY)){ 		
				this.state = GAMEConfig.CONFIRM_REQUEST;
				this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
			} else {
				players.get(currentID).setWithdrawn(Boolean.TRUE);
				currentPlayer = (currentPlayer+1) % numPlayers;
				dealCard();
				if (players.get(currentID).getDisplayer().hasMaiden() && players.get(currentID).getTokens().getSize() != 0){  
					this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
					this.state = GAMEConfig.CONFIRM_TOKEN;
				}else{
					this.prevState = GAMEConfig.PLAY_OR_WITHDRAW;
					this.state = GAMEConfig.CONFIRM_TOURNAMENT;
				}				
			}
		} else if (state == GAMEConfig.CONFIRM_TOKEN){
			this.currentID = playersOrder.get(currentPlayer);

			String tokenColour =  message.getBody().getField("Token Colour").toString();			
			if (prevState == GAMEConfig.WIN_TOURNAMENT){
				players.get(currentID).addToken(tokenColour); 
				this.state = GAMEConfig.GAME_OVER; 
			} else { 
				players.get(currentID).getTokens().removeToken(tokenColour);
				currentPlayer = (currentPlayer+1) % numPlayers;	
				this.state = GAMEConfig.CONFIRM_TOURNAMENT;
			}
			this.prevState = GAMEConfig.CONFIRM_TOKEN;
		} else if (state == GAMEConfig.CONFIRM_REQUEST){
			this.currentID = playersOrder.get(currentPlayer);

			String selectedCard = "";
			if (message.getBody().hasField("Selected Card")){
				selectedCard = message.getBody().getField("Selected Card").toString();
				Hand hands = players.get(currentID).getHand();
				if (!selectedCard.contains(",")){ // Simple Card Selected
					Card card = hands.getCard(Integer.parseInt(selectedCard));
					if (!card.isAction()){
						// Add more case such as supporter
						if (currTournamentColour.equals(card.getColor()) || card.isSquire()){
							this.legal = Boolean.TRUE;
							this.state = GAMEConfig.PLAY_CARD;
							this.prevState = GAMEConfig.CONFIRM_REQUEST;
							return this.state;
						}else{
							this.legal = Boolean.FALSE;
							this.state = GAMEConfig.CONFIRM_REQUEST;
							this.prevState = GAMEConfig.CONFIRM_REQUEST;
							return this.state;
						}
					}else{
						this.legal = Boolean.FALSE;
					}
				}else{ // Many cards Selected
					this.legal = Boolean.TRUE;
					String[] cards = selectedCard.split(", ");
					for (String index : cards){
						System.out.println("Index: " + index + " - " + hands.getCard(Integer.parseInt(index)));
						Card card = hands.getCard(Integer.parseInt(index));
						if (!card.isAction()){
							// Add more case such as supporter
							if (!currTournamentColour.equals(card.getColor())){
								if (!card.isMaiden() && !card.isSquire()){
									this.legal = Boolean.FALSE;
									break;
								}
							}
						}else{							
							if (card.isDropWeapon() && (currTournamentColour.equals(GAMEConfig.COLOR_PURPLE)
													||	currTournamentColour.equals(GAMEConfig.COLOR_GREEN))){
								this.legal = Boolean.FALSE;
								break;
							}
						}
					}		

					if (this.legal){
						this.state = GAMEConfig.PLAY_CARD;
						this.prevState = GAMEConfig.CONFIRM_REQUEST;
						return this.state;
					}else{
						this.state = GAMEConfig.CONFIRM_REQUEST;
						this.prevState = GAMEConfig.CONFIRM_REQUEST;
						return this.state;
					}					
				}
			}			
			this.prevState = GAMEConfig.CONFIRM_REQUEST;
		} else if (state == GAMEConfig.PLAY_CARD){
			this.currentID = playersOrder.get(currentPlayer);

			if (message.getBody().hasField("Selected Card")){
				String selectedCard = message.getBody().getField("Selected Card").toString();
				Hand hands = players.get(currentID).getHand();				
				if (!selectedCard.contains(",")){ // Simple Card Selected
					Card card = hands.getCard(Integer.parseInt(selectedCard));
					if (!card.isAction()){
						players.get(currentID).getDisplayer().addCard(card);
						players.get(currentID).getHand().playCard(card);
						currentPlayer = (currentPlayer+1)%numPlayers;
						dealCard();		
						this.state = GAMEConfig.CONFIRM_TOURNAMENT;
						this.prevState = GAMEConfig.PLAY_CARD;
						return this.state;
					}					
				}else{ // Many cards Selected
					String[] selectedIndex = selectedCard.split(", ");
					System.out.println(selectedCard);
					Hand selectedHand = new Hand();
					for (String index:selectedIndex){
						selectedHand.drawCard(hands.getCard(Integer.parseInt(index)));
					}
					for (int i = 0; i < selectedIndex.length; i++){
						Card card = selectedHand.getCard(i);
						if (card.isStunned()){
							if( !this.players.get(currentID).getDisplayer().isShield()){
								players.get(currentID).getDisplayer().addCard(card);
								players.get(currentID).getHand().playCard(card);	
							}else{
								players.get(currentID).getHand().playCard(card);
								deadwood.addCard(card);							
							}
						} else{
							players.get(currentID).getDisplayer().addCard(card);
							players.get(currentID).getHand().playCard(card);		
						}
						if (card.isDropWeapon()){
							this.currTournamentColour = GAMEConfig.COLOR_GREEN;
							for (int key : this.players.keySet()){
								this.players.get(key).getDisplayer().setTournament(this.currTournamentColour);
							}
						}
						System.out.println("Card: " + card.toString());
					}
					currentPlayer = (currentPlayer+1)%numPlayers;
					dealCard();		
					this.state = GAMEConfig.CONFIRM_TOURNAMENT;
					this.prevState = GAMEConfig.PLAY_CARD;
					return this.state;
				}
			}
			this.state = GAMEConfig.CONFIRM_REQUEST;
			this.prevState = GAMEConfig.PLAY_CARD;
		} else if (state == GAMEConfig.WIN_TOURNAMENT){
			this.currentID = playersOrder.get(currentPlayer);
			System.out.println("Prev Tournament Colour: " + this.prevTournamentColour);

			if (!players.get(currentID).getTokens().checkToken(currTournamentColour))
				players.get(currentID).addToken(currTournamentColour);
			this.state = GAMEConfig.GAME_OVER;						
			this.prevState = GAMEConfig.WIN_TOURNAMENT;
		}else{
			this.state = GAMEConfig.GAME_OVER;
			this.prevState = GAMEConfig.WIN_TOURNAMENT;
		}

		return this.state;
	}*/

public String 	selectedTournament(String input)	{ return input; }

public HashMap<Integer, Player> getPlayers() { return this.players; }

public boolean 	playTournament()	{ return Boolean.TRUE; }
public void 	playCards()			{	}	
public void 	nextPlayer()		{	}
public void 	nextTournament()	{	}

public int		getPrevState()		{ return this.prevState;						}
public int		getCurrentPlayer()	{ return this.currentPlayer;					}
public int 		getCurrentID()		{ return this.playersOrder.get(currentPlayer);	}
public String	getCurrColour()		{ return this.currTournamentColour;				}
public boolean	isLegal()			{ return this.legal;							}
public boolean	isAllReponded()		{ return this.allResponded;						}

public void 	setState(int state)	{ this.state = state; }
public int 		getState()			{ return this.state; }
public boolean 	gameReady()			{ return this.state == 1; }//GAMEConfig.GAME_READY;	}
public boolean 	withdraw()			{ return this.state == 6; }//GAMEConfig.WITHDAWAL;	}
public boolean 	gameOver()			{ return this.state == 9; }//GAMEConfig.GAME_OVER;	}
}
