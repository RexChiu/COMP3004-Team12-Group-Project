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

	private Message storedMessage = new Message();

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
		this.deck.shuffleDeck();
		this.deadwood = new Deck();
		this.numPlayers = players.size();

		// Initialize Player Order		
		this.initPlayerOrder();
		// Initialize and store first player
		this.initFirstPlayer();
		// Initialize Hands	
		//this.initHand();
		// Deal card to first player
		//this.dealCard();
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

	private Message playCard(Message message, Boolean checkIvanhoe){
		int targetID = -1, ownDisplayIndex = -1, targetDisplayIndex = -1;

		int selectedCardIndex = Integer.parseInt(message.getBody().getField(GAMEConfig.SELECTED_HAND_INDEX).toString());
		Card card = this.players.get(this.currentID).getHand().getCard(selectedCardIndex);

		// Limit cards played to 1, if stunned
		Display display = this.players.get(this.currentID).getDisplayer();
		if (display.isStunned() && display.getNumPlayed() != 0)
			return null;

		// Update if the card is simple card in first time
		if (this.currentID == this.firstPlayer && this.players.get(this.currentID).getDisplayer().isEmpty()){
			if (!card.isAction()){
				this.playerPlayCard(this.currentID, card);
				this.players.get(this.currentID).getDisplayer().playCard();
			}
		} else if (!card.isAction()) {			
			// Current player play a simple card to display
			if (card.getColor().equalsIgnoreCase(this.currTournamentColour) || card.isSupporter()){
				//does not allow multiple maidens to be played
				if (this.players.get(this.currentID).getDisplayer().hasMaiden() && card.isMaiden())
					return null;

				this.playerPlayCard(this.currentID, card);
				this.players.get(this.currentID).getDisplayer().playCard();
			}
		}else{
			// Check if anyone has Ivanhoe, if a player didnt already refuse to play Ivanhoe
			for (Integer key: playersOrder){
				if (this.players.get(key).getHand().hasIvanhoe() != -1 && checkIvanhoe){
					this.storedMessage = message;
					System.out.println(key + " has Ivanhoe, sending message.");
					//sets currentPlayer to person who has Ivanhoe
					this.currentID = key;
					this.currentPlayer = playersOrder.indexOf(key);
					return Data.checkIvanhoe(players, key, Integer.parseInt(message.getHeader().sender.toString()), card.getName());
				}
			}

			// Get all required info for action cards.
			if (message.getBody().hasField(GAMEConfig.SELECTED_DISPLAY_INDEX))
				ownDisplayIndex = Integer.parseInt(message.getBody().getField(GAMEConfig.SELECTED_DISPLAY_INDEX).toString());
			if (message.getBody().hasField(GAMEConfig.SELECTED_TARGET_ID))
				targetID = Integer.parseInt(message.getBody().getField(GAMEConfig.SELECTED_TARGET_ID).toString());
			if (message.getBody().hasField(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX))
				targetDisplayIndex = Integer.parseInt(message.getBody().getField(GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX).toString());

			if (card.getName().equalsIgnoreCase(GAMEConfig.UNHORSE)){
				//tournament color changes from purple to red, blue or yellow
				//can only play if tournament colour is purple
				
				if (this.currTournamentColour == GAMEConfig.COLOR_PURPLE){
					// sending available choices to player
					String colors = "";		
					for (String tokenColor : GAMEConfig.TOKEN_COLORS_THREE){
						colors += tokenColor + ","; 
					}
					//solicit a reply from the player
					this.storedMessage = message;
					this.state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
					return Data.changeTournamentColor(players, this.currentID, colors);
				}
				
				System.out.println(this.currentID + " Cannot play " + GAMEConfig.UNHORSE);
				return null;
				
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.CHANGE_WEAPON)){
				//tournament color changes from red, blue, or yellow to a diff colour
				//can only play if tournament colour is red, blue, or yellow
				
				if (this.currTournamentColour == GAMEConfig.COLOR_RED ||
					this.currTournamentColour == GAMEConfig.COLOR_BLUE ||
					this.currTournamentColour == GAMEConfig.COLOR_YELLOW) {
					
					// sending available choices to player
					String colors = "";		
					for (String tokenColor : GAMEConfig.TOKEN_COLORS_THREE){
						//does not add current tournament colour to choice
						if (!tokenColor.equalsIgnoreCase(this.currTournamentColour))
							colors += tokenColor + ","; 
					}
					//solicit a reply from the player
					this.storedMessage = message;
					this.state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
					return Data.changeTournamentColor(players, this.currentID, colors);
				}
				
				return null;
				
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.DROP_WEAPON)){	
				//change tournament from red, blue, or yellow to green
				//can only play if tournament colour is not green or purple
				
				if (this.currTournamentColour == GAMEConfig.COLOR_PURPLE ||
					this.currTournamentColour == GAMEConfig.COLOR_GREEN){
					
					return null;
				}
				this.currTournamentColour = GAMEConfig.COLOR_GREEN;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.BREAK_LANCE)) {	
				//remove all purples
				//can only play if > 1 card left, and has a purple, and does not have shield
				
				if (players.get(targetID).getDisplayer().getSize() <= 1 ||
					!players.get(targetID).getDisplayer().hasPurple() ||
				  	players.get(targetID).getDisplayer().hasShield()) {
					
					return null;
				}
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);

				//removing the player's purple cards
				ArrayList<Card> tempArr = new ArrayList<Card>();
				Card tempCard;
				for (int i = 0; i < players.get(targetID).getDisplayer().getSize(); i++)
				{
					tempCard = players.get(targetID).getDisplayer().getCard(i);
					//remove all purple cards, leave only one card
					if (tempCard.isPurple() && (players.get(targetID).getDisplayer().getSize() - tempArr.size() > 1))
					{
						tempArr.add(tempCard);
					}
				}
				for (int i = 0; i < tempArr.size(); i++) {
					System.out.println("Removing: " + tempArr.get(i).toString());
					players.get(targetID).getDisplayer().removeCard(tempArr.get(i));
					this.deadwood.addCard(tempArr.get(i));
				}

			} else if (card.getName().equalsIgnoreCase(GAMEConfig.RIPOSTE)) {	
				//take last card played on any opponent's display and add it to own
				//can only play if > 2 cards left and does not have shield
				
				if (players.get(targetID).getDisplayer().getSize() < 2 ||
					players.get(targetID).getDisplayer().hasShield()) {
					
					return null;
				}
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				int cardIndex = players.get(targetID).getDisplayer().getSize() - 1;
				Card tempCard = players.get(targetID).getDisplayer().getCard(cardIndex);
				players.get(targetID).getDisplayer().removeCard(tempCard);
				players.get(this.currentID).getDisplayer().addCard(tempCard);
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.DODGE)) {	
				//discard any one card from opponent's display
				//can only play if > 1 card left and does not have shield
				
				if (players.get(targetID).getDisplayer().getSize() <= 1 ||
					players.get(targetID).getDisplayer().hasShield()) {
					
					return null;
				}
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				// discard last card of target's display
				Card tempCard = players.get(targetID).getDisplayer().getCard(targetDisplayIndex);
				players.get(targetID).getDisplayer().removeCard(tempCard);
				this.deadwood.addCard(tempCard);
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.RETREAT)) {	
				//take any one card from your own display back into your hand
				//can only play if > 1 card left
				
				if (players.get(this.currentID).getDisplayer().getSize() <= 1) {
					return null;
				}
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//take back any card from own display
				Card tempCard = players.get(this.currentID).getDisplayer().getCard(ownDisplayIndex);
				players.get(this.currentID).getDisplayer().removeCard(tempCard);
				players.get(this.currentID).getHand().drawCard(tempCard);
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.KNOCK_DOWN)) {	
				//draw at random one card from target's display and add to hand
				//can only play if > 1 card left and does not have shield
				
				if (players.get(targetID).getDisplayer().getSize() <= 1 ||
					players.get(targetID).getDisplayer().hasShield()) {
					
					return null;
				}

				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//draws random card and adds to own hand
				int targetDisplaySize = players.get(targetID).getDisplayer().getSize();
				int cardIndex = new Random().nextInt(targetDisplaySize);
				Card tempCard = players.get(targetID).getDisplayer().getCard(cardIndex);
				players.get(targetID).getDisplayer().removeCard(tempCard);
				this.deadwood.addCard(tempCard);
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.OUTMANEUVER)) {	
				//all players discard last card
				//can only play if at least one player has > 1 card left and does not have shield
				
				//validity check
				boolean valid = false;
				
				//iterate through players, find a player who has > 1 card in display and does not have shield
				for (int i = 0; i < this.numPlayers; i++) {
					if (players.get(playersOrder.get(i)).getDisplayer().getSize() > 1 &&
						!players.get(i).getDisplayer().hasShield()) {
						
						valid = true;
						break;
					}
				}
				
				// card cant be played
				if (!valid)
					return null;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//all players discard last card if possible
				for (int key : this.players.keySet()) {
					if (players.get(key).getDisplayer().getSize() > 1 &&
						!players.get(key).getDisplayer().hasShield()) {
						
						int cardIndex = players.get(key).getDisplayer().getSize() - 1;
						Card tempCard = players.get(key).getDisplayer().getCard(cardIndex);
						players.get(key).getDisplayer().removeCard(tempCard);
						this.deadwood.addCard(tempCard);
					}
				}
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.CHARGE)) {	
				//identify lowest value card, discard all cards of this value
				//can only play if one player has > 1 card left, 
				//and one person has a simple/value card and does not have shield
				
				//validity check
				boolean valid = false;
				int cardValue;
				
				//iterate through players, find a player who has a simple/value card
				for (int i = 0; i < this.numPlayers; i++) {	
					cardValue = players.get(playersOrder.get(i)).getDisplayer().hasValueCard();
					if (cardValue != -1) {
						valid = true;
						break;
					}
				}
				
				//failed first check, do not need to go further
				if (!valid) 
					return null;
				else
					valid = false;
				
				//find lowest card value
				int minValue = 999;
				int displaySize, tempCardValue;
				
				for (int key : this.players.keySet()) {
					displaySize = players.get(key).getDisplayer().getSize();
					for (int i = 0; i < displaySize; i++) {
						tempCardValue = players.get(key).getDisplayer().getCard(i).getValue();
						if (tempCardValue < minValue) {
							minValue = tempCardValue;
						}
					}
				}
				
				//iterate through players, find a player who has > 1 card in display
				//and has the value card, and does not have shield
				for (int i = 0; i < this.numPlayers; i++) {	
					if (players.get(playersOrder.get(i)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(i)).getDisplayer().hasShield() &&
						players.get(playersOrder.get(i)).getDisplayer().hasValue(minValue)) {
						
						valid = true;
						break;
					}
				}
				
				// card cant be played
				if (!valid)
					return null;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//remove lowest card value of all players if possible
				ArrayList<Card> tempArr = new ArrayList<Card>();
				Card tempCard;
				//loop through players
				for (int key : this.players.keySet()) {
					//if can discard at all
					if (players.get(playersOrder.get(key)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(key)).getDisplayer().hasShield()) {
						
						for (int i = 0; i < players.get(targetID).getDisplayer().getSize(); i++)
						{
							tempCard = players.get(targetID).getDisplayer().getCard(i);
							//remove cards equal to minValue, leave only one card
							if (tempCard.getValue() == minValue && (players.get(targetID).getDisplayer().getSize() - tempArr.size() > 1))
							{
								tempArr.add(tempCard);
							}
						}
						for (int i = 0; i < tempArr.size(); i++) {
							System.out.println("Removing: " + tempArr.get(i).toString());
							players.get(targetID).getDisplayer().removeCard(tempArr.get(i));
							this.deadwood.addCard(tempArr.get(i));
						}
					}
				}
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.COUNTERCHARGE)) {	
				//identify highest value card, discard all cards of this value
				//can only play if one player has > 1 card left, 
				//and one person has a simple/value card and does not have shield
				
				//validity check
				boolean valid = false;
				int cardValue;
				
				//iterate through players, find a player who has a simple/value card
				for (int i = 0; i < this.numPlayers; i++) {	
					cardValue = players.get(playersOrder.get(i)).getDisplayer().hasValueCard();
					if (cardValue != -1) {
						valid = true;
						break;
					}
				}
				
				//failed first check, do not need to go further
				if (!valid) 
					return null;
				else
					valid = false;
				
				//find highest card value
				int maxValue = -1;
				int displaySize, tempCardValue;
				
				for (int key : this.players.keySet()) {
					displaySize = players.get(key).getDisplayer().getSize();
					for (int i = 0; i < displaySize; i++) {
						tempCardValue = players.get(key).getDisplayer().getCard(i).getValue();
						if (tempCardValue > maxValue) {
							maxValue = tempCardValue;
						}
					}
				}
				
				//iterate through players, find a player who has > 1 card in display
				//and has the value card, and does not have shield
				for (int i = 0; i < this.numPlayers; i++) {	
					if (players.get(playersOrder.get(i)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(i)).getDisplayer().hasShield() &&
						players.get(playersOrder.get(i)).getDisplayer().hasValue(maxValue)) {
						
						valid = true;
						break;
					}
				}
				
				// card cant be played
				if (!valid)
					return null;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//remove lowest card value of all players if possible
				ArrayList<Card> tempArr = new ArrayList<Card>();
				Card tempCard;
				//loop through players
				for (int key : this.players.keySet()) {
					//if can discard at all
					if (players.get(playersOrder.get(key)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(key)).getDisplayer().hasShield()) {
						
						for (int i = 0; i < players.get(targetID).getDisplayer().getSize(); i++)
						{
							tempCard = players.get(targetID).getDisplayer().getCard(i);
							//remove cards equal to minValue, leave only one card
							if (tempCard.getValue() == maxValue && (players.get(targetID).getDisplayer().getSize() - tempArr.size() > 1))
							{
								tempArr.add(tempCard);
							}
						}
						for (int i = 0; i < tempArr.size(); i++) {
							System.out.println("Removing: " + tempArr.get(i).toString());
							players.get(targetID).getDisplayer().removeCard(tempArr.get(i));
							this.deadwood.addCard(tempArr.get(i));
						}
					}
				}
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.DISGRACE)) {	
				//all players discard all supporters
				//can only play if a player has > 1 card AND has a simple card AND does not have shield
				
				//validity check
				boolean valid = false;
				for (int i = 0; i < this.numPlayers; i++) {	
					//iterate through players, find a player who has a supporter card and has >1 cards and no shield
					if (players.get(playersOrder.get(i)).getDisplayer().hasSupport() &&
						players.get(playersOrder.get(i)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(i)).getDisplayer().hasShield()) {
						
						valid = true;
						break;
					}
				}
				
				if (!valid)
					return null;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				//remove all supporters
				ArrayList<Card> tempArr = new ArrayList<Card>();
				Card tempCard;
				for (int key : this.players.keySet()) {
					if (players.get(playersOrder.get(key)).getDisplayer().getSize() > 1 &&
						!players.get(playersOrder.get(key)).getDisplayer().hasShield()) {
						
						for (int i = 0; i < players.get(targetID).getDisplayer().getSize(); i++)
						{
							tempCard = players.get(targetID).getDisplayer().getCard(i);
							//remove all purple cards, leave only one card
							if (tempCard.isSupporter() && (players.get(targetID).getDisplayer().getSize() - tempArr.size() > 1))
							{
								tempArr.add(tempCard);
							}
						}
						for (int i = 0; i < tempArr.size(); i++) {
							System.out.println("Removing: " + tempArr.get(i).toString());
							players.get(targetID).getDisplayer().removeCard(tempArr.get(i));
							this.deadwood.addCard(tempArr.get(i));
						}
					}
				}
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.ADAPT)) {	
				//each player keep one card of each value, all other card values must be discarded
				//can only play if one person has two different value cards
				
				//NOT IMPLEMENTED
			} else if (card.getName().equalsIgnoreCase(GAMEConfig.OUTWIT)) {	
				//give target a card from own display, take a card from target display
				//can only play if both players have >1 cards, and target does not have shield
				
				if (players.get(targetID).getDisplayer().hasShield())
					return null;
				
				//discard action card
				players.get(this.currentID).getHand().drawCard(card);
				this.deadwood.addCard(card);
				
				// swap cards
				Card ownCard = players.get(this.currentID).getDisplayer().getCard(ownDisplayIndex);
				Card targetCard = players.get(targetID).getDisplayer().getCard(targetDisplayIndex);
				
				players.get(this.currentID).getDisplayer().removeCard(ownCard);
				players.get(targetID).getDisplayer().addCard(ownCard);
				
				//catches special case of you giving target a shield
				if (!ownCard.isShield())
				{
					players.get(targetID).getDisplayer().removeCard(targetCard);
					players.get(this.currentID).getDisplayer().addCard(targetCard);
				}
			}
		}
		return Data.getMessage(players, this.currentID);
	}

	private Message checkIvanhoe(String choice){
		if (choice.equalsIgnoreCase(GAMEConfig.IVANHOE_NO))
			return playCard(storedMessage, Boolean.FALSE);
		else {
			//remove ivanhoe from player's hand
			int ivanhoeIndex = this.players.get(this.currentID).getHand().hasIvanhoe();
			Card card = this.players.get(this.currentID).getHand().getCard(ivanhoeIndex);
			this.players.get(this.currentID).getHand().playCard(card);
			this.deadwood.addCard(card);

			// resets states and variables back to original
			this.currentID = Integer.parseInt(storedMessage.getHeader().sender.toString());
			this.updateState(storedMessage.getHeader().state);
			System.out.println("Reset ID to " + this.currentID + ", State to " + GAMEConfig.STATE[this.getState()]);

			// removes action card from original player
			int selectedCardIndex = Integer.parseInt(storedMessage.getBody().getField(GAMEConfig.SELECTED_HAND_INDEX).toString());
			card = this.players.get(this.currentID).getHand().getCard(selectedCardIndex);
			this.players.get(this.currentID).getHand().playCard(card);
			this.deadwood.addCard(card);

			//maybe send a message instead of just deleting?
			return Data.getMessage(players, this.currentID);
		}

	}

	private Message changeTournamentColor(String choice){
		this.currTournamentColour = choice;
		int selectedCardIndex = Integer.parseInt(storedMessage.getBody().getField(GAMEConfig.SELECTED_HAND_INDEX).toString());
		Card card = this.players.get(this.currentID).getHand().getCard(selectedCardIndex);
		
		//discard action card
		players.get(this.currentID).getHand().drawCard(card);
		this.deadwood.addCard(card);
		
		this.updateState(GAMEConfig.PLAY_CARD);
		return Data.getMessage(players, this.currentID);
	}

	private Message endTurn(String color){	
		this.players.get(this.currentID).getDisplayer().cleanNumPlayed();

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
		System.out.println("Ivanhoe Before: " + GAMEConfig.STATE[message.getHeader().getState()]);
		
		int 	sender 	= Integer.parseInt(message.getHeader().getSender());
		int 	state 	= message.getHeader().getState();

		if (this.currentID != sender)
			return null;

		String 	choice  			= "";
		String  color 				= "";
		String  token 				= "";
		String 	maiden				= "";
		String 	tournamentChoice 	= "";
		String  ivanhoeChoice		= "";

		if (message.getBody().hasField("POW Choice"))
			choice = message.getBody().getField("POW Choice").toString();
		if (message.getBody().hasField("Tournament Color"))
			color = message.getBody().getField("Tournament Color").toString();
		if (message.getBody().hasField("Token Color"))
			token = message.getBody().getField("Token Color").toString();
		if (message.getBody().hasField("Maiden Punish"))
			maiden = message.getBody().getField("Maiden Punish").toString();
		if (message.getBody().hasField("Change Tournament Color"))
			tournamentChoice = message.getBody().getField("Change Tournament Color").toString();
		if (message.getBody().hasField("Ivanhoe Choice"))
			ivanhoeChoice = message.getBody().getField("Ivanhoe Choice").toString();

		System.out.println("ProcessMessage: " + message.toString());

		switch (state){
		case GAMEConfig.SELECT_COLOUR:
			// Purple Tournament and no winning will also choose the different color ? is it problem?
			System.out.println("COLOR:" + color);
			return this.selectColor(color);
		case GAMEConfig.PLAY_OR_WITHDRAW:
			return this.playOrWithdraw(choice, color);
		case GAMEConfig.PLAY_CARD:
			return this.playCard(message, Boolean.TRUE);
		case GAMEConfig.CHECK_IVANHOE:
			return this.checkIvanhoe(ivanhoeChoice);
		case GAMEConfig.CHANGE_TOURNAMENT_COLOR:
			return this.changeTournamentColor(tournamentChoice);
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
	public Deck		getDeck()			{ return this.deck;								}
	public Deck		getDeadwood()		{ return this.deadwood;							}
}
