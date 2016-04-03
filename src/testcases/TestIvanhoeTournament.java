package testcases;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Ivanhoe;
import game.Player;
import config.GAMEConfig;
import message.Message;

import org.junit.Before;

public class TestIvanhoeTournament {
	// Create attribute variable for Ivanhoe
	Ivanhoe rEngine;

	/** This will be processed before the Test Class is instantiated */
	@BeforeClass
	public static void BeforeClass() {
		System.out.println("@BeforeClass: TestIvanhoeTournament");
	}

	/** This is processed/initialized before each test is conducted */
	@Before
	public void setUp() {
		System.out.println("@Before(): TestIvanhoeTournament");
		// Set up Ivanhoe
		rEngine = new Ivanhoe();
	}

	/** This is processed/initialized after each test is conducted */
	@After
	public void tearDown () {
		System.out.println("@After(): TestIvanhoeTournament");
		// Set Ivanhoe to null;
		rEngine = null;
	}

	/** This will be processed after the Test Class has been destroyed */
	@AfterClass
	public static void afterClass () {
		System.out.println("@AfterClass: TestIvanhoeTournament");
	}

	/** Each unit test is annotated with the @Test Annotation */

	//one player draws/starts, others draw but do not participate
	@Test
	public void TestA1 () {
		System.out.println("\n-------------------Test A.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);

			//make sure all players have been dealt a card
			assertEquals(9, rEngine.getPlayer(id).getHand().getSize());
			//makes sure all players has withdrawn, cept last withdrawn player
			if (id != beforeFirstPlayer)
				assertTrue(rEngine.getPlayer(id).isWithdrawn());
		}

		//make sure firstPlayer won a red token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
	}

	//one player draws/starts, others draw but only one participates by playing a card
	@Test
	public void TestB1 () {
		System.out.println("\n-------------------Test B. i)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//calculate secondPlayer ID
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };

		//make sure next Player is secondPlayer
		assertEquals(secondPlayer, rEngine.getCurrentID());
		//make sure secondPlayer is dealt a card
		assertEquals(9, rEngine.getPlayer(secondPlayer).getHand().getSize());

		//hard code secondPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure player has not withdrawn
		assertFalse(rEngine.getPlayer(secondPlayer).isWithdrawn());
		//make sure state is changed properly to PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(8, rEngine.getPlayer(secondPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());

		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure state is changed properly to playOrWithdraw for next player
		assertEquals(GAMEConfig.PLAY_OR_WITHDRAW, rEngine.getState());

		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);

			//make sure all players have been dealt a card
			assertEquals(9, rEngine.getPlayer(id).getHand().getSize());
			//makes sure all players has withdrawn, cept last withdrawn player
			if (id != beforeFirstPlayer)
				assertTrue(rEngine.getPlayer(id).isWithdrawn());
		}
	}

	//one player draws/starts, others draw but only one participates by playing several cards (3)
	@Test
	public void TestB2 () {
		System.out.println("\n-------------------Test B. ii)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//calculate secondPlayer ID
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };

		//hard code secondPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(8, rEngine.getPlayer(secondPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(7, rEngine.getPlayer(secondPlayer).getHand().getSize());
		assertEquals(2, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure player has played a card
		assertEquals(6, rEngine.getPlayer(secondPlayer).getHand().getSize());
		assertEquals(3, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());

		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);

			//make sure all players have been dealt a card
			assertEquals(9, rEngine.getPlayer(id).getHand().getSize());
			//makes sure all players has withdrawn, cept last withdrawn player
			if (id != beforeFirstPlayer)
				assertTrue(rEngine.getPlayer(id).isWithdrawn());
		}
	}

	//one player draws/starts, others draw but only some (three others) participates by playing a card
	@Test
	public void TestC1 () {
		System.out.println("\n-------------------Test C. i)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hardcode playCard and endTurn on first 4 players
		for (int i = -1; i < 3; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played one card
			assertEquals(8, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(1, rEngine.getPlayer(id).getDisplayer().getSize());
		}


		//info needed for next part
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hard code lastPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = beforeFirstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
	}

	//one player draws/starts, others draw but only some (three others) participates by playing several cards (3)
	@Test
	public void TestC2 () {
		System.out.println("\n-------------------Test C. ii)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hardcode playCard and endTurn on first 4 players
		for (int i = -1; i < 3; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played three cards
			assertEquals(6, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(3, rEngine.getPlayer(id).getDisplayer().getSize());
		}


		//info needed for next part
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hard code lastPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = beforeFirstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);
	}

	//one player draws/starts, others draw and all plays one card
	@Test
	public void TestD1 () {
		System.out.println("\n-------------------Test D. i)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hardcode playCard and endTurn on all players
		for (int i = -1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played a cards
			assertEquals(8, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(1, rEngine.getPlayer(id).getDisplayer().getSize());
		}
	}

	//one player draws/starts, others draw and all play several cards (3)
	@Test
	public void TestD2 () {
		System.out.println("\n-------------------Test D. ii)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hardcode playCard and endTurn on all players
		for (int i = -1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played three cards
			assertEquals(6, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(3, rEngine.getPlayer(id).getDisplayer().getSize());
		}
	}

	//starting with a supporter
	@Test
	public void TestE1 () {
		System.out.println("\n-------------------Test E. i)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

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
	}

	//starting with several supporters (5 cards)
	@Test
	public void TestE2 () {
		System.out.println("\n-------------------Test E. ii)------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		for (int i = 0; i < 5; i++) {
			//hard code firstPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = firstPlayer + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);
		}

		//make sure player has played 5 cards
		assertEquals(4, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
	}

	//several rounds where each player plays one and then several supporters
	//round 1: 1 card, round 2: 2 cards, round 3: 3 cards
	@Test
	public void TestF1 () {
		System.out.println("\n-------------------Test F.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hardcode playCard and endTurn on all players
		//round 1, each player plays 1 card
		for (int i = -1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played a card
			assertEquals(8, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(1, rEngine.getPlayer(id).getDisplayer().getSize());
		}

		//hardcode playCard and endTurn on all players
		//round 2, each player plays 2 cards
		for (int i = -1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played two cards
			assertEquals(7, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(3, rEngine.getPlayer(id).getDisplayer().getSize());
		}

		//hardcode playCard and endTurn on all players
		//round 3, each player plays 3 cards
		for (int i = -1; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they all played three cards
			assertEquals(5, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(6, rEngine.getPlayer(id).getDisplayer().getSize());
		}
	}

	//trying to play cards that do not get the current player to beat the tournament originator
	//2nd, 3rd, and 4th player will be withdrawn when they do not play enough
	@Test
	public void TestG1 () {
		System.out.println("\n-------------------Test G.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//making firstPlayer play 5 cards
		for (int i = 0; i < 5; i++)
		{
			//hard code firstPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = firstPlayer + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);
		}

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//have 2nd,3rd,4th player force withdraw because insufficient total
		for (int i = 0; i < 3; i++) {
			int id = ((firstPlayer + i) % 5) + 1;

			//hard code player play or withdraw response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code player playCard response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//make sure they all played one card only
			assertEquals(8, rEngine.getPlayer(id).getHand().getSize());
			assertEquals(1, rEngine.getPlayer(id).getDisplayer().getSize());
			//make sure they have lower totals than firstPlayer
			int firstPlayerTotal = rEngine.getPlayer(firstPlayer).getDisplayer().getTotal();
			int playerTotal = rEngine.getPlayer(id).getDisplayer().getTotal();
			assertTrue(firstPlayerTotal > playerTotal);

			//hard code player endTurn response
			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			//make sure they are forced to withdraw
			assertTrue(rEngine.getPlayer(id).isWithdrawn());
		}
	}

	//Restriction to 1 maiden per player per tournament (testing 2 tournaments)
	@Test
	public void TestH1 () {
		System.out.println("\n-------------------Test H.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.MAIDEN, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		for (int i = 1; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				card = new Card(GAMEConfig.MAIDEN, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX);
				rEngine.getPlayer(i).addCard(card);
			}
		}

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//making firstPlayer play 5 maiden cards
		for (int i = 0; i < 5; i++)
		{
			//hard code firstPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = firstPlayer + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);
		}

		//make sure firstPlayer played one maiden card only in first tournament
		assertEquals(8, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hard code firstPlayer select colour response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//making firstPlayer play 5 maiden cards
		for (int i = 0; i < 5; i++)
		{
			//hard code firstPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = firstPlayer + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);
		}

		//make sure firstPlayer played one maiden card only in 2nd tournament
		assertEquals(8, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//Winning and getting a token
	@Test
	public void TestI1 () {
		System.out.println("\n-------------------Test I.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//make sure firstPlayer won a red token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
	}

	//Winning and choosing a green token when purple tournament
	@Test
	public void TestJ1 () {
		System.out.println("\n-------------------Test J1.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hardcode firstPlayer choosing token message
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Token Color", GAMEConfig.COLOR_GREEN);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a green token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_GREEN));
	}

	//Winning and choosing a blue token when purple tournament
	@Test
	public void TestJ2 () {
		System.out.println("\n-------------------Test J2.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hardcode firstPlayer choosing token message
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Token Color", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a blue token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_BLUE));
	}

	//Winning and choosing a red token when purple tournament
	@Test
	public void TestJ3 () {
		System.out.println("\n-------------------Test J3.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hardcode firstPlayer choosing token message
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Token Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a red token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
	}

	//Winning and choosing a purple token when purple tournament
	@Test
	public void TestJ4 () {
		System.out.println("\n-------------------Test J4.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hardcode firstPlayer choosing token message
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Token Color", GAMEConfig.COLOR_PURPLE);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a purple token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_PURPLE));
	}

	//Winning and choosing a yellow token when purple tournament
	@Test
	public void TestJ5 () {
		System.out.println("\n-------------------Test J5.------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

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

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);
		//info needed for next loop
		int beforeFirstPlayer = (firstPlayer + 5 - 1) % 5;
		if (beforeFirstPlayer == 0) { beforeFirstPlayer = 5; }

		//hardcode withdraw for all other players
		for (int i = 0; i < 4; i++){
			int id = ((firstPlayer + i) % 5) + 1;

			System.out.println("Hardcoding Message: " + id);

			msg = new Message();
			msg.getHeader().sender = id + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
		}

		//hardcode firstPlayer choosing token message
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Token Color", GAMEConfig.COLOR_YELLOW);
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer won a yellow token because everyone withdrew
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_YELLOW));
	}

	//withdrawing with maiden and losing a token
	//2nd Player has 1 token, loses token
	@Test
	public void TestK1 () {
		System.out.println("\n-------------------Test K1------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);


		//calculate secondPlayer ID
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		// add a maiden to secondPlayer's display
		card = new Card(GAMEConfig.MAIDEN, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//add a blue token to secondPlayer's display
		rEngine.getPlayer(secondPlayer).addToken(GAMEConfig.COLOR_BLUE);

		//make sure secondPlayer has a token
		assertEquals(1, rEngine.getPlayer(secondPlayer).getTokens().getSize());
		assertTrue(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_BLUE));

		//hard code secondPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//make sure secondPlayer loses blue token
		assertFalse(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_BLUE));
	}

	//withdrawing with maiden and losing a token
	//2nd Player has 4 tokens, chooses to lose blue token
	@Test
	public void TestK2 () {
		System.out.println("\n-------------------Test K2------------------------------");

		//add 5 players
		for (int i = 1; i <= 5; i++) {
			rEngine.addPlayer(i);
		}

		//init game
		rEngine.initTestCase();
		int firstPlayer = rEngine.getCurrentID();

		//hardcode dealing card to firstPlayer
		Card card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(firstPlayer).addCard(card);


		//calculate secondPlayer ID
		int secondPlayer = (firstPlayer+1) % 6;
		if (secondPlayer == 0) { secondPlayer++; };

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		// add a maiden to secondPlayer's display
		card = new Card(GAMEConfig.MAIDEN, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//add a blue token and red token to secondPlayer's display
		rEngine.getPlayer(secondPlayer).addToken(GAMEConfig.COLOR_BLUE);
		rEngine.getPlayer(secondPlayer).addToken(GAMEConfig.COLOR_RED);
		rEngine.getPlayer(secondPlayer).addToken(GAMEConfig.COLOR_YELLOW);
		rEngine.getPlayer(secondPlayer).addToken(GAMEConfig.COLOR_GREEN);

		//make sure secondPlayer has two tokens
		assertEquals(4, rEngine.getPlayer(secondPlayer).getTokens().getSize());
		assertTrue(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_BLUE));
		assertTrue(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_RED));
		assertTrue(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_YELLOW));
		assertTrue(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_GREEN));

		//hard code secondPlayer withdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer losing blue token
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.MAIDEN_PUNISH;
		msg.getBody().addField("Maiden Punish", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//make sure secondPlayer loses blue token
		assertEquals(3, rEngine.getPlayer(secondPlayer).getTokens().getSize());
		assertFalse(rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_BLUE));
	}
}
