package testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Data;
import game.Ivanhoe;
import config.GAMEConfig;
import message.Message;

import org.junit.Before;

public class TestIvanhoeScenarios {
	// Create attribute variable for Ivanhoe
	Ivanhoe rEngine;

	/** This will be processed before the Test Class is instantiated */
	@BeforeClass
	public static void BeforeClass() {
		System.out.println("@BeforeClass: TestIvanhoeScenarios");
	}

	/** This is processed/initialized before each test is conducted */
	@Before
	public void setUp() {
		System.out.println("@Before(): TestIvanhoeScenarios");
		// Set up Ivanhoe
		rEngine = new Ivanhoe();
	}

	/** This is processed/initialized after each test is conducted */
	@After
	public void tearDown () {
		System.out.println("@After(): TestIvanhoeScenarios");
		// Set Ivanhoe to null;
		rEngine = null;
	}

	/** This will be processed after the Test Class has been destroyed */
	@AfterClass
	public static void afterClass () {
		System.out.println("@AfterClass: TestIvanhoeScenarios");
	}

	/** Each unit test is annotated with the @Test Annotation */

	//the player who should start cannot start a tournament
	//*IF FAILED, RUN AGAIN, bug on eclipse side, not code*
	@Test
	public void A1() {
		System.out.println("\n-------------------Test A.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase2();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int ivanhoeFirstPlayer = rEngine.getFirstPlayer();

		assertNotEquals(ivanhoeFirstPlayer, firstPlayer);
	}

	//last tournament was purple, cannot be purple again
	@Test
	public void B1() {
		System.out.println("\n-------------------Test B.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();

		//hardcode previous tournament colour to purple
		rEngine.setPrevColor(GAMEConfig.COLOR_PURPLE);

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure selection is invalid (Ivanhoe sends choices that do not contain purple, if tries to choose purple, idiot-proofing)
		assertEquals("Red,Blue,Yellow,Green", reply.getBody().getField("Select Colors"));
	}

	//trying to play invalid cards
	@Test
	public void D1() {
		System.out.println("\n-------------------Test D.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, GAMEConfig.VALUE_JOUSTING_SEVEN);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure card is invalid
		assertEquals(null, reply);
		assertEquals(0, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//coming up to end of deck
	@Test
	public void E1() {
		System.out.println("\n-------------------Test E.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//deal all of 110 deck cards directly to deadwood.
		for (int i = 0; i < 110; i++){
			card = rEngine.getDeck().getCard(0);
			rEngine.getDeck().removeCard(card);
			rEngine.getDeadwood().addCard(card);
		}

		//make sure deck is empty.
		assertEquals(0, rEngine.getDeck().getSize());
		assertEquals(110, rEngine.getDeadwood().getSize());

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = Data.newMessage(firstPlayer +"", GAMEConfig.END_TURN);
		rEngine.processMessage(msg);

		//make sure deck isnt empty anymore, and that deadwood is empty.
		assertEquals(109, rEngine.getDeck().getSize());
		assertEquals(0, rEngine.getDeadwood().getSize());
		//make sure the missing card from deadwood is in secondPlayer
		assertEquals(1, rEngine.getPlayer(secondPlayer).getHand().getSize());
	}

	//using charge in a green tournament with every player with only green 1s
	@Test
	public void F1() {
		System.out.println("\n-------------------Test F.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };
		int thirdPlayer = (secondPlayer+1) % 6;
		if (thirdPlayer == 0) { thirdPlayer++; };
		int fourthPlayer = (thirdPlayer+1) % 6;
		if (fourthPlayer == 0) { fourthPlayer++; };
		int fifthPlayer = (fourthPlayer+1) % 6;
		if (fifthPlayer == 0) { fifthPlayer++; };

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);
		card = new Card(GAMEConfig.CHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode green 1s on every display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.NO_WEAPON, GAMEConfig.COLOR_GREEN, GAMEConfig.VALUE_NO_WEAPON_ONE);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(thirdPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fourthPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fifthPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_GREEN);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card effects are activated, 1 card left in all displays
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize()); 
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize()); 
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//Using charge in a green tournament with every player with only green 1s, one card must remain
	@Test
	public void G1 () {
		System.out.println("\n-------------------Test G.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };
		int thirdPlayer = (secondPlayer+1) % 6;
		if (thirdPlayer == 0) { thirdPlayer++; };
		int fourthPlayer = (thirdPlayer+1) % 6;
		if (fourthPlayer == 0) { fourthPlayer++; };
		int fifthPlayer = (fourthPlayer+1) % 6;
		if (fifthPlayer == 0) { fifthPlayer++; };

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SWORD, GAMEConfig.COLOR_RED, GAMEConfig.VALUE_SWORD_THREE);
		rEngine.getPlayer(firstPlayer).addCard(card);
		card = new Card(GAMEConfig.DISGRACE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some supporter cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(thirdPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fourthPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fifthPlayer).getDisplayer().addCard(card);
		}

		//make sure each player has 5 cards
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure firstPlayer has 6 cards
		assertEquals(6, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card effects are activated, 1 card is left for all players
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//Winning the game with 5 players
	@Test
	public void H1 () {
		System.out.println("\n-------------------Test H.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };
		int thirdPlayer = (secondPlayer+1) % 6;
		if (thirdPlayer == 0) { thirdPlayer++; };
		int fourthPlayer = (thirdPlayer+1) % 6;
		if (fourthPlayer == 0) { fourthPlayer++; };
		int fifthPlayer = (fourthPlayer+1) % 6;
		if (fifthPlayer == 0) { fifthPlayer++; };

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		for (int i = 1; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
				rEngine.getPlayer(i).addCard(card);
			}
		}

		//give firstPlayer all tokens except red.
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_BLUE);
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_GREEN);
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_PURPLE);

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
		//make sure state is changed to "Play or Withdraw"
		assertEquals(GAMEConfig.PLAY_OR_WITHDRAW, rEngine.getState());
		//make sure firstPlayer is dealt a card
		assertEquals(9, rEngine.getPlayer(firstPlayer).getHand().getSize());

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure player has not withdrawn
		assertFalse(rEngine.getPlayer(firstPlayer).isWithdrawn());
		//make sure state is changed properly to PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(8, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure state is changed properly to playOrWithdraw for next player
		assertEquals(GAMEConfig.PLAY_OR_WITHDRAW, rEngine.getState());

		//hardcode withdraw for all other players
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
		msg = Data.newMessage(thirdPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
		msg = Data.newMessage(fourthPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
		msg = Data.newMessage(fifthPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a red token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
		//make sure game is over
		assertEquals(GAMEConfig.GAME_OVER, rEngine.getState());
	}

	//Winning the game with 3 players
	@Test
	public void H2 () {
		System.out.println("\n-------------------Test H2.------------------------------");

		//add 3 players
		for (int i = 1; i <= 3; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();

		//calculate player IDs based on player order
		int firstPlayer = rEngine.getCurrentID();
		int secondPlayer = (firstPlayer+1) % 4;
		if (secondPlayer == 0) { secondPlayer++; };
		int thirdPlayer = (secondPlayer+1) % 4;
		if (thirdPlayer == 0) { thirdPlayer++; };

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		for (int i = 1; i <= 3; i++) {
			for (int j = 0; j < 8; j++) {
				card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
				rEngine.getPlayer(i).addCard(card);
			}
		}

		//give firstPlayer all tokens except red.
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_BLUE);
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_GREEN);
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_PURPLE);
		rEngine.getPlayer(firstPlayer).addToken(GAMEConfig.COLOR_YELLOW);

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
		//make sure state is changed to "Play or Withdraw"
		assertEquals(GAMEConfig.PLAY_OR_WITHDRAW, rEngine.getState());
		//make sure firstPlayer is dealt a card
		assertEquals(9, rEngine.getPlayer(firstPlayer).getHand().getSize());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure player has not withdrawn
		assertFalse(rEngine.getPlayer(firstPlayer).isWithdrawn());
		//make sure state is changed properly to PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(8, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer endTurn response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.END_TURN);
		reply = rEngine.processMessage(msg);
		
		//make sure state is changed properly to playOrWithdraw for next player
		assertEquals(GAMEConfig.PLAY_OR_WITHDRAW, rEngine.getState());

		//hardcode withdraw for all other players
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
		msg = Data.newMessage(thirdPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a red token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
		//make sure game is over
		assertEquals(GAMEConfig.GAME_OVER, rEngine.getState());
	}


	//make sure the deck uses 110 cards
	@Test
	public void I1(){
		System.out.println("\n-------------------Test I.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.init();

		//make sure deck is using 110 cards
		assertEquals(110, rEngine.getDeck().getSize());
	}
}