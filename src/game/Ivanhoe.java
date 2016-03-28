package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import config.GAMEConfig;
import message.Message;

public class Ivanhoe {

	private Deck deck;
	private Deck deadwood; // discard
	private int state;
	private int prevState;
	private int numPlayers;
	private int playersLeft;
	private int firstPlayer;

	private int currentID;
	private int currentPlayer;
	private String prevTournamentColour;
	private String currTournamentColour;	

	private HashMap<Integer, Player> players 	= new HashMap<Integer, Player>();
	private ArrayList<Integer> playersOrder 	= new ArrayList<Integer>();
	private HashMap<Integer, Boolean> confirm 	= new HashMap<Integer, Boolean>();

	public Ivanhoe() {
		this.updateState(GAMEConfig.GAME_READY);
	}

	public void init(){
		this.deck = new Deck();
		this.deck.init();		
		this.deck.shuffleDeck();
		this.deadwood = new Deck();
		this.numPlayers = players.size();		
	}

	public void initTestCase(){
		this.deck = new Deck();
		this.deck.initTestCase();
		//this.deck.shuffleDeck();
		this.deadwood = new Deck();
		this.numPlayers = players.size();

		// Initialize Player Order		
		this.initPlayerOrder();
		// Initialize and store first player
		this.initFirstPlayer();
		// Initialize Hands	
		this.initHand();
		// Deal card to first player
		this.dealCard();
		// Update store to select color
		this.updateState(GAMEConfig.SELECT_COLOUR);
	}

	private void initHand(){
		for (int i = 0; i < 8; i++){
			for (Integer key: playersOrder){
				Card card = deck.getCard(0);
				this.players.get(key).addCard(card);
				this.deck.removeCard(card);

				// Test Purpose DELETE after Test
				/*card = deck.getCard(0);
				this.players.get(key).addDisplay(card);
				this.deck.removeCard(card);*/
			}		
		}			
	}

	private void initPlayerOrder(){
		for (Integer key: players.keySet()){
			this.confirm.put(key, Boolean.FALSE);
			this.playersOrder.add(key);		
		}		
	}

	private void initFirstPlayer(){
		this.currentPlayer = new Random().nextInt(numPlayers);		
		this.currentID = this.playersOrder.get(currentPlayer);
		this.firstPlayer = currentID;
		this.playersLeft = numPlayers;		
	}

	public void setup(){
		// Initialize Deck		
		this.init();
		// Initialize Player Order		
		this.initPlayerOrder();
		// Initialize and store first player
		this.initFirstPlayer();
		// Initialize Hands	
		this.initHand();
		// Deal card to first player
		this.dealCard();
		// Update store to select color
		this.updateState(GAMEConfig.SELECT_COLOUR);
	}

	private void dealCard(){
		// Check deck is empty refill by discards deck and shuffle
		if (this.deck.isEmpty()){
			this.deadwood.shuffleDeck();
			this.deck = this.deadwood;
			this.deadwood.cleanDeck();
		}

		// Deal card to player
		Card card = this.deck.getCard(0);
		this.players.get(playersOrder.get(currentPlayer)).addCard(card);
		this.deck.removeCard(card);
	}

	private Message selectColor(String color){
		System.out.println("Color Info:");
		System.out.println(this.currTournamentColour + " - " + this.prevTournamentColour);
		System.out.println(color);
		if (color.equalsIgnoreCase(GAMEConfig.COLOR_PURPLE) && this.prevTournamentColour != null){
			if (this.prevTournamentColour.equalsIgnoreCase(GAMEConfig.COLOR_PURPLE))
				return Data.selectColor(this.players, this.currentID, GAMEConfig.NUMBER_COLOR_FOUR);
		}
		
		// Update tournament colors	for all displays	
		this.updateTournament(color);

		// Update store to play or withdraw
		this.updateState(GAMEConfig.PLAY_OR_WITHDRAW);
		System.out.println("SELECTED COLOR: " + color);
		return Data.playOrWithdraw(this.players, this.currentID);
	}

	private Message playOrWithdraw(String choice, String color){
		// Check current Player's Choice if player is withdrawn
		if (choice.equalsIgnoreCase(GAMEConfig.POW_WITHDRAW)){
			if (this.players.get(this.currentID).getDisplayer().hasMaiden() &&
					this.players.get(this.currentID).getTokens().getSize() > 0){
				return checkToken();
			}
			return checkWinner();
		}

		// Update state to play card
		this.updateState(GAMEConfig.PLAY_CARD);
		return null;
	}
	
	private Message checkToken(){
		Tokens tokens = this.players.get(this.currentID).getTokens();
		if (tokens.getSize() == 1){
			tokens.cleanToken();
			return checkWinner();
		}
		
		this.updateState(GAMEConfig.MAIDEN_PUNISH);
		return Data.maidenPunish(this.players, this.currentID, tokens.toString());
	}
	

	// Check winner is not completed
	private Message checkWinner(){
		System.out.println("Check Winnter");

		// Decrement the remaining players
		if (!this.players.get(currentID).isWithdrawn()){
			this.playersLeft--;
		}

		// Set player to withdraw	
		this.players.get(currentID).setWithdrawn(Boolean.TRUE);
		System.out.println(currentID + " Withdraws");

		// Discard display
		int displaySize = players.get(this.currentID).getDisplayer().getSize();
		System.out.println("Display Size: " + displaySize);
		for (int i = 0; i < displaySize; i++){
			System.out.println("Removing Card");
			Card card = players.get(this.currentID).getDisplayer().getCard(0);
			System.out.println("Card: " + card.toString());
			players.get(this.currentID).getDisplayer().removeCard(card);
			System.out.println("Discard from player");
			this.deadwood.addCard(card);
			System.out.println("Discard to Deadwood");
		}
		System.out.println(currentID + " Discard");

		// Check the player left to determine the winner
		if (playersLeft == 1){
			// Find the winner
			for (Integer key : this.players.keySet()){
				nextPlayer();
				if (!this.players.get(this.currentID).isWithdrawn())
					break;
			}
			System.out.println("Tournament Winner: " + this.currentID);
			if (this.currTournamentColour.equalsIgnoreCase(GAMEConfig.COLOR_PURPLE)){
				// Update state to win purple tournament
				this.updateState(GAMEConfig.WIN_TOURNAMENT);
				return Data.winTournament(this.players, this.currentID); 
			}else{
				// Add token to current player
				this.players.get(this.currentID).addToken(this.currTournamentColour); // Display all token currently had 
				System.out.println(this.players.get(this.currentID).getTokens().toString());

				// Game Over if players could win the whole game
				if ((this.players.get(this.currentID).getTokens().hasFour() && this.numPlayers < GAMEConfig.FIVE_TOKEN_GAME ) ||
						(this.players.get(this.currentID).getTokens().hasFive() && this.numPlayers >= GAMEConfig.FIVE_TOKEN_GAME )){
					// Update state to game over
					this.updateState(GAMEConfig.GAME_OVER);
					return Data.gameOver(this.players, this.currentID);
				}

				// Discard Display of all players and update to discard deck
				for (Integer key : this.players.keySet()){
					Display display = this.players.get(key).getDisplayer();
					while (!display.isEmpty()){
						Card card = display.getCard(0);
						this.deadwood.addCard(card);
						display.removeCard(card);
					}
					this.players.get(key).cleanDisplay();
					this.players.get(key).setWithdrawn(Boolean.FALSE);
				}

				// Reset Data
				this.playersLeft = this.numPlayers;
				System.out.println("Players Left: " +  this.playersLeft);

				// Update state to select color for next tournament
				this.updateState(GAMEConfig.SELECT_COLOUR);
				return Data.selectColor(this.players, this.currentID, GAMEConfig.NUMBER_COLOR_FIVE);
			}
		}else{
			// Increment current player until non withdraw player (do while loop)
			System.out.println(this.currentID + " is withdrawn then next player.");
			do {
				System.out.print("");
				this.nextPlayer();
				this.dealCard();			
			} while (players.get(currentID).isWithdrawn());		
		
			// Update state to play or withdraw
			this.updateState(GAMEConfig.PLAY_OR_WITHDRAW);
			return Data.playOrWithdraw(this.players, this.currentID);		
		}
	}

	private Message playCard(Card card){
		// Limit so the first play cannot play action card as first card
		if (this.currentID == this.firstPlayer && this.players.get(this.currentID).getDisplayer().isEmpty()){
			// Update if the card is simple card in first time
			if (!card.isAction()){
				this.playerPlayCard(this.currentID, card);
			}
		} else if (!card.isAction()) {			
			// Current player play a simple card to display
			if (card.getColor().equalsIgnoreCase(this.currTournamentColour) || card.isSupporter()){
				this.playerPlayCard(this.currentID, card);
			}
		}else{
			// If action card is valid
			//   handle action cards (many cases)
		}
		return Data.getMessage(this.players, this.currentID);
	}

	private Message endTurn(String color){	
		System.out.println("Current Player: " + this.currentID);
		// Check winner with given color if exist
		if (!color.equalsIgnoreCase(""))
			return this.checkWinner();
		// Check if total value of current player display is not max
		if (!this.checkMax()){
			System.out.println("Check Winner due to no Max: " + color);
			return this.checkWinner();		
		}
		// Increment current player until non withdraw player (do while loop)
		do {		
			System.out.print("");
			this.nextPlayer();
			System.out.print("");
			this.dealCard();
			System.out.println("Curreng ID:" + this.currentID + " State: " + this.players.get(this.currentID).isWithdrawn());
		} while (this.players.get(currentID).isWithdrawn());				

		System.out.println("Next Player: " + this.currentID);
		// Update state to play or withdraw
		this.updateState(GAMEConfig.PLAY_OR_WITHDRAW);
		return Data.playOrWithdraw(this.players, this.currentID);
	}

	// Win purple tournament
	private Message winTournament(String color){
		this.players.get(this.currentID).addToken(color);

		// Game Over if players could win the whole game
		if ((this.players.get(this.currentID).getTokens().hasFour() && this.numPlayers < GAMEConfig.FIVE_TOKEN_GAME ) ||
				(this.players.get(this.currentID).getTokens().hasFive() && this.numPlayers >= GAMEConfig.FIVE_TOKEN_GAME )){
			// Update state to game over
			this.updateState(GAMEConfig.GAME_OVER);
			return Data.gameOver(this.players, this.currentID);
		}

		// Discard Display of all players and update to discard deck
		this.discardDisplay();
		
		// Reset all players not withdrawn
		System.out.println("Players Left: " +  this.playersLeft);
		this.playersLeft = this.numPlayers;
		for (Integer key : this.players.keySet()){
			this.players.get(key).setWithdrawn(Boolean.FALSE);
		}
				
		// Update state to select color
		this.updateState(GAMEConfig.SELECT_COLOUR);
		return Data.selectColor(this.players, this.currentID, GAMEConfig.NUMBER_COLOR_FOUR);
	}
	
	public Message processMessage(Message message){
		int 	sender 	= Integer.parseInt(message.getHeader().getSender());
		int 	state 	= message.getHeader().getState();

		if (this.currentID != sender)
			return null;

		String 	choice  = "";
		String  color 	= "";
		String  token 	= "";
		String 	maiden	= "";

		if (message.getBody().hasField("POW Choice"))
			choice = message.getBody().getField("POW Choice").toString();
		if (message.getBody().hasField("Tournament Color"))
			color = message.getBody().getField("Tournament Color").toString();
		if (message.getBody().hasField("Token Color"))
			token = message.getBody().getField("Token Color").toString();
		if (message.getBody().hasField("Maiden Punish"))
			maiden = message.getBody().getField("Maiden Punish").toString();
		System.out.println("ProcessMessage: " + message.toString());
		switch (state){
		case GAMEConfig.SELECT_COLOUR:
			System.out.println("COLOR:" + color);
			return this.selectColor(color);
		case GAMEConfig.PLAY_OR_WITHDRAW:
			return this.playOrWithdraw(choice, color);
		case GAMEConfig.PLAY_CARD:
			int selectedCardIndex = Integer.parseInt(message.getBody().getField("Selected Card Index").toString());
			Card card = this.players.get(this.currentID).getHand().getCard(selectedCardIndex);
			return this.playCard(card);
		case GAMEConfig.MAIDEN_PUNISH:
			this.players.get(this.currentID).getTokens().removeToken(maiden);
			return this.checkWinner();
		case GAMEConfig.END_TURN:
			return this.endTurn(token);
		case GAMEConfig.WIN_TOURNAMENT:
			return this.winTournament(token);
		default:
			return null;
		}		
	}

	private void nextPlayer(){
		this.currentPlayer = (this.currentPlayer + 1) % this.numPlayers;
		this.currentID = this.playersOrder.get(this.currentPlayer);	
	}

	private void discardDisplay(){
		for (Integer key : this.players.keySet()){
			Display display = this.players.get(key).getDisplayer();
			this.players.get(key).cleanDisplay();
			while (!display.isEmpty()){
				Card card = display.getCard(0);
				this.deadwood.addCard(card);
				display.removeCard(card);
			}
		}
	}

	private boolean checkMax(){
		// Search the total value of current player display
		int total = this.players.get(this.currentID).getDisplayer().getTotal();
		int max = 0;

		for (Integer key : this.players.keySet()){
			int tempTotal = this.players.get(key).getDisplayer().getTotal();
			if (tempTotal > max)
				max = tempTotal;
		}

		return total == max;
	}

	private void playerPlayCard(int playerID, Card card){
		this.players.get(playerID).getDisplayer().addCard(card);
		this.players.get(playerID).getHand().playCard(card);
	}

	private void updateState(int state)	{ 
		this.prevState = this.state; 
		this.state = state;
	}

	private void updateTournament(String color){
		this.prevTournamentColour = this.currTournamentColour;
		this.currTournamentColour = color;

		for (int key : this.players.keySet())
			this.players.get(key).getDisplayer().setTournament(this.currTournamentColour);
	}

	public HashMap<Integer, Player> getPlayers() { return this.players; }

	public void 	addPlayer(int ID)	{ players.put(ID, new Player(ID));	}
	public void 	removePlayer(int ID){ players.remove(ID); 				}
	public Player 	getPlayer(int ID)	{ return players.get(ID);			}

	public int 		getState()			{ return this.state; 							}
	public int		getPrevState()		{ return this.prevState;						}
	public int		getCurrentPlayer()	{ return this.currentPlayer;					}
	public int 		getCurrentID()		{ return this.playersOrder.get(currentPlayer);	}
	public String	getCurrColour()		{ return this.currTournamentColour;				}
}
