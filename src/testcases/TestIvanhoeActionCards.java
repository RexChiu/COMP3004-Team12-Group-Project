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

public class TestIvanhoeActionCards {
	// Create attribute variable for Ivanhoe
	Ivanhoe rEngine;

	/** This will be processed before the Test Class is instantiated */
	@BeforeClass
	public static void BeforeClass() {
		System.out.println("@BeforeClass: TestIvanhoeActionCards");
	}

	/** This is processed/initialized before each test is conducted */
	@Before
	public void setUp() {
		System.out.println("@Before(): TestIvanhoeActionCards");
		// Set up Ivanhoe
		rEngine = new Ivanhoe();
	}

	/** This is processed/initialized after each test is conducted */
	@After
	public void tearDown () {
		System.out.println("@After(): TestIvanhoeActionCards");
		// Set Ivanhoe to null;
		rEngine = null;
	}

	/** This will be processed after the Test Class has been destroyed */
	@AfterClass
	public static void afterClass () {
		System.out.println("@AfterClass: TestIvanhoeActionCards");
	}

	/** Each unit test is annotated with the @Test Annotation */

	//playing card on unshielded player
	@Test
	public void UnhorseA () {
		System.out.println("\n-------------------Test Unhorse A.------------------------------");

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
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//make sure Unhorse effects are activated, changed colour to red.
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
	}

	//playing card on shielded player
	@Test
	public void UnhorseB () {
		System.out.println("\n-------------------Test Unhorse B.------------------------------");

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
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode giving secondPlayer a shield
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//make sure Unhorse effects are activated, changed colour to red.
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
	}

	//undoing this card using Ivanhoe
	@Test
	public void UnhorseC () {
		System.out.println("\n-------------------Test Unhorse C.------------------------------");

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
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to secondPlayer
		assertEquals(GAMEConfig.CHECK_IVANHOE, reply.getHeader().state);
		assertEquals(String.valueOf(secondPlayer), reply.getHeader().receiver);
		assertEquals(secondPlayer, rEngine.getCurrentID());

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		reply = rEngine.processMessage(msg);

		//make sure Unhorse is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure current tournament colour is still the same
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void UnhorseD () {
		System.out.println("\n-------------------Test Unhorse D.------------------------------");

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
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.END_TURN);
		reply = rEngine.processMessage(msg);

		//make sure Unhorse is discarded
		assertEquals(1, rEngine.getDeadwood().getSize());
		//make sure card discarded is Unhorse
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void UnhorseE () {
		System.out.println("\n-------------------Test Unhorse D.------------------------------");

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
		card = new Card(GAMEConfig.UNHORSE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is red
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response (trying to play unhorse)
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure no response from RE (invalid card, didnt play)
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void ChangeWeaponA () {
		System.out.println("\n-------------------Test ChangeWeapon A.------------------------------");

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
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//confirm current tournament colour
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to blue.
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());
	}

	//playing card on shielded player
	@Test
	public void ChangeWeaponB () {
		System.out.println("\n-------------------Test ChangeWeapon B.------------------------------");

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
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode giving secondPlayer a shield
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_BLUE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to red.
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
	}

	//undoing this card using Ivanhoe
	@Test
	public void ChangeWeaponC () {
		System.out.println("\n-------------------Test ChangeWeapon C.------------------------------");

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
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure current tournament colour is still same
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void ChangeWeaponD () {
		System.out.println("\n-------------------Test ChangeWeapon D.------------------------------");

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
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.CHANGE_TOURNAMENT_COLOR, "Change Tournament Color", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.END_TURN);
		reply = rEngine.processMessage(msg);

		//make sure card is discarded
		assertEquals(1, rEngine.getDeadwood().getSize());
		//make sure card discarded is Unhorse
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void ChangeWeaponE () {
		System.out.println("\n-------------------Test ChangeWeapon D.------------------------------");

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
		card = new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response (trying to play changeWeapon)
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure no response from RE (invalid card, didnt play)
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void DropWeaponA () {
		System.out.println("\n-------------------Test DropWeapon A.------------------------------");

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
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//confirm current tournament colour
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to green.
		assertEquals(GAMEConfig.COLOR_GREEN, rEngine.getCurrColour());
	}

	//playing card on shielded player
	@Test
	public void DropWeaponB () {
		System.out.println("\n-------------------Test DropWeapon B.------------------------------");

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
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode giving secondPlayer a shield
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_BLUE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to green.
		assertEquals(GAMEConfig.COLOR_GREEN, rEngine.getCurrColour());
	}

	//undoing this card using Ivanhoe
	@Test
	public void DropWeaponC () {
		System.out.println("\n-------------------Test DropWeapon C.------------------------------");

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
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure current tournament colour is still same
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void DropWeaponD () {
		System.out.println("\n-------------------Test DropWeapon D.------------------------------");

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
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card is discarded
		assertEquals(1, rEngine.getDeadwood().getSize());
		//make sure card discarded is correct
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void DropWeaponE () {
		System.out.println("\n-------------------Test DropWeapon D.------------------------------");

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
		card = new Card(GAMEConfig.DROP_WEAPON, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response (trying to play changeWeapon)
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure no response from RE (invalid card, didnt play)
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void BreakLanceA () {
		System.out.println("\n-------------------Test BreakLance A.------------------------------");

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
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		System.out.println(rEngine.getPlayer(secondPlayer).getDisplayer().toString());
		//make sure card effects are activated.
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void BreakLanceB () {
		System.out.println("\n-------------------Test BreakLance B.------------------------------");

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
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure action card cannot be played
		assertEquals(null, reply);

		System.out.println(rEngine.getPlayer(secondPlayer).getDisplayer().toString());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void BreakLanceC () {
		System.out.println("\n-------------------Test BreakLance C.------------------------------");

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
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void BreakLanceD () {
		System.out.println("\n-------------------Test BreakLance D.------------------------------");

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
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void BreakLanceE () {
		System.out.println("\n-------------------Test BreakLance E.------------------------------");

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
		card = new Card(GAMEConfig.BREAK_LANCE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_RED, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void RiposteA () {
		System.out.println("\n-------------------Test Riposte A.------------------------------");

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
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure firstPlayer only has one card in display
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		System.out.println("Second Player: " + rEngine.getPlayer(secondPlayer).getDisplayer().toString());
		System.out.println("First Player: " + rEngine.getPlayer(firstPlayer).getDisplayer().toString());
		//make sure card effects are activated.
		assertEquals(4, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void RiposteB () {
		System.out.println("\n-------------------Test Riposte B.------------------------------");

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
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer only has one card in display
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure action card cannot be played
		assertEquals(null, reply);

		System.out.println(rEngine.getPlayer(secondPlayer).getDisplayer().toString());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void RiposteC () {
		System.out.println("\n-------------------Test Riposte C.------------------------------");

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
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void RiposteD () {
		System.out.println("\n-------------------Test Riposte D.------------------------------");

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
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void RiposteE () {
		System.out.println("\n-------------------Test Riposte E.------------------------------");

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
		card = new Card(GAMEConfig.RIPOSTE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void DodgeA () {
		System.out.println("\n-------------------Test Dodge A.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 2; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//make sure secondPlayer display has two cards
		assertEquals(2, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure firstPlayer only has one card in display
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		System.out.println("Second Player: " + rEngine.getPlayer(secondPlayer).getDisplayer().toString());
		//make sure card effects are activated.
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void DodgeB () {
		System.out.println("\n-------------------Test Dodge B.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer only has one card in display
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		//make sure action card cannot be played
		assertEquals(null, reply);

		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void DodgeC () {
		System.out.println("\n-------------------Test Dodge C.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void DodgeD () {
		System.out.println("\n-------------------Test Dodge D.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void DodgeE () {
		System.out.println("\n-------------------Test Dodge E.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void RetreatA () {
		System.out.println("\n-------------------Test Retreat A.------------------------------");

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
		card = new Card(GAMEConfig.RETREAT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//make sure firstPlayer has correct # of cards in hand
		assertEquals(2, rEngine.getPlayer(firstPlayer).getHand().getSize());

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in firstPlayer's display
		for (int i = 0; i < 3; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
		}

		//make sure firstPlayer's display has three cards
		assertEquals(3, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure firstPlayer has 4 cards in display, and 1 card in hand.
		assertEquals(4, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getHand().getSize());

		System.out.println("First Player: " + rEngine.getPlayer(firstPlayer).getHand().toString());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 1+"");
		reply = rEngine.processMessage(msg);

		//make sure card is played properly
		assertNotEquals(null, reply);

		System.out.println("First Player: " + rEngine.getPlayer(firstPlayer).getDisplayer().toString());
		System.out.println("First Player: " + rEngine.getPlayer(firstPlayer).getHand().toString());

		//make sure card effects are activated.
		assertEquals(3, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getHand().getSize());
	}

	//playing card on shielded player
	@Test
	public void RetreatB () {
		System.out.println("\n-------------------Test Retreat B.------------------------------");

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
		card = new Card(GAMEConfig.RETREAT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto firstPlayer
		rEngine.getPlayer(firstPlayer).getDisplayer().setStatus("Shield");

		//hardcode some purple cards in firstPlayer's display
		for (int i = 0; i < 3; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure first player display size is 4.
		assertEquals(4, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		//make sure action card can still be played
		assertNotEquals(null, reply);

		//make sure card effects are activated
		assertEquals(3, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(firstPlayer).getHand().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void RetreatC () {
		System.out.println("\n-------------------Test Retreat C.------------------------------");

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
		card = new Card(GAMEConfig.RETREAT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in firstPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(6, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void RetreatD () {
		System.out.println("\n-------------------Test Retreat D.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void RetreatE () {
		System.out.println("\n-------------------Test Retreat E.------------------------------");

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
		card = new Card(GAMEConfig.DODGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure there is only one card in firstPlayer's display
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void KnockDownA () {
		System.out.println("\n-------------------Test KnockDown A.------------------------------");

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
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		assertNotEquals(null, reply);

		System.out.println("firstPlayer: " + rEngine.getPlayer(firstPlayer).getHand().toString());
		System.out.println("secondPlayer: " + rEngine.getPlayer(secondPlayer).getHand().toString());

		//make sure card effects are activated.
		assertEquals(1, rEngine.getPlayer(firstPlayer).getHand().getSize());
		assertEquals(0, rEngine.getPlayer(secondPlayer).getHand().getSize());
	}

	//playing card on shielded player
	@Test
	public void KnockDownB () {
		System.out.println("\n-------------------Test KnockDown B.------------------------------");

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
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure action card cannot be played
		assertEquals(null, reply);

		//make sure card effects are not activated
		assertEquals(1, rEngine.getPlayer(secondPlayer).getHand().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void KnockDownC () {
		System.out.println("\n-------------------Test KnockDown C.------------------------------");

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
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void KnockDownD () {
		System.out.println("\n-------------------Test KnockDown D.------------------------------");

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
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void KnockDownE () {
		System.out.println("\n-------------------Test KnockDown E.------------------------------");

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
		card = new Card(GAMEConfig.KNOCK_DOWN, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		//rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}


	//playing card on unshielded player
	@Test
	public void OutmaneuverA () {
		System.out.println("\n-------------------Test Outmaneuver A.------------------------------");

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
		card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 2; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(thirdPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fourthPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fifthPlayer).getDisplayer().addCard(card);
		}

		//make sure each player has 2 cards in display
		assertEquals(2, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure firstPlayer has 3 cards in display
		assertEquals(3, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure card effects are activated.
		assertEquals(2, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void OutmaneuverB () {
		System.out.println("\n-------------------Test Outmaneuver B.------------------------------");

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
		card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 2; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(thirdPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fourthPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fifthPlayer).getDisplayer().addCard(card);
		}

		//make sure each player has 2 cards in display
		assertEquals(2, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
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

		//make sure card effects are activated, except for player with shield
		assertEquals(2, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(2, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void OutmaneuverC () {
		System.out.println("\n-------------------Test Outmaneuver C.------------------------------");

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
		card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void OutmaneuverD () {
		System.out.println("\n-------------------Test Outmaneuver D.------------------------------");

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
		card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
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

		//make sure card discarded is correct
		card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void OutmaneuverE () {
		System.out.println("\n-------------------Test Outmaneuver E.------------------------------");

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

		//hardcode dealing card to secondPlayer
		Card card = new Card(GAMEConfig.OUTMANEUVER, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode shield onto firstPlayer
		rEngine.getPlayer(firstPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer play or withdraw response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard playing action card
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void ChargeA () {
		System.out.println("\n-------------------Test Charge A.------------------------------");

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

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//make sure card effects are activated.
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void ChargeB () {
		System.out.println("\n-------------------Test Charge B.------------------------------");

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

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

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

		//make sure card effects are activated.
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void ChargeC () {
		System.out.println("\n-------------------Test Charge C.------------------------------");

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

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void ChargeD () {
		System.out.println("\n-------------------Test Charge D.------------------------------");

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

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//make sure card discarded is correct
		card = new Card(GAMEConfig.CHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void ChargeE () {
		System.out.println("\n-------------------Test Charge E.------------------------------");

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

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void CounterchargeA () {
		System.out.println("\n-------------------Test Countercharge A.------------------------------");

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
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//make sure card effects are activated.
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void CounterchargeB () {
		System.out.println("\n-------------------Test Countercharge B.------------------------------");

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
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//hardcode shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

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

		//make sure card effects are activated.
		assertEquals(5, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(4, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void CounterchargeC () {
		System.out.println("\n-------------------Test Countercharge C.------------------------------");

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
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some purple cards in secondPlayer's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void CounterchargeD () {
		System.out.println("\n-------------------Test Countercharge D.------------------------------");

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
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hardcode some purple cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.JOUSTING, GAMEConfig.COLOR_PURPLE, i);
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

		//make sure card discarded is correct
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void CounterchargeE () {
		System.out.println("\n-------------------Test Countercharge E.------------------------------");

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
		card = new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void DisgraceA () {
		System.out.println("\n-------------------Test Disgrace A.------------------------------");

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

		System.out.println("FirstPlayer: " + rEngine.getPlayer(firstPlayer).getDisplayer().toString());

		//make sure card effects are activated.
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//playing card on shielded player
	@Test
	public void DisgraceB () {
		System.out.println("\n-------------------Test Disgrace B.------------------------------");

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

		//hard code a shield onto secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

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

		System.out.println("FirstPlayer: " + rEngine.getPlayer(firstPlayer).getDisplayer().toString());

		//make sure card effects are activated, except for shielded player
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(thirdPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fourthPlayer).getDisplayer().getSize());
		assertEquals(1, rEngine.getPlayer(fifthPlayer).getDisplayer().getSize());
	}

	//undoing this card using Ivanhoe
	@Test
	public void DisgraceC () {
		System.out.println("\n-------------------Test Disgrace C.------------------------------");

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
		card = new Card(GAMEConfig.DISGRACE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hardcode some supporter cards in each player's display
		for (int i = 0; i < 5; i++){
			card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
			rEngine.getPlayer(firstPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(thirdPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fourthPlayer).getDisplayer().addCard(card);
			rEngine.getPlayer(fifthPlayer).getDisplayer().addCard(card);
		}

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(5, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void DisgraceD () {
		System.out.println("\n-------------------Test Disgrace D.------------------------------");

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

		//make sure card discarded is correct
		card = new Card(GAMEConfig.DISGRACE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void DisgraceE () {
		System.out.println("\n-------------------Test Disgrace E.------------------------------");

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
		card = new Card(GAMEConfig.DISGRACE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void OutwitA () {
		System.out.println("\n-------------------Test Outwit A.------------------------------");

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
		Card card = new Card(GAMEConfig.SWORD,GAMEConfig.COLOR_RED,GAMEConfig.VALUE_SWORD_THREE);
		rEngine.getPlayer(firstPlayer).addCard(card);
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//add squire to secondPlayer display
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		assertNotEquals(null, reply);

		//make sure card effects are activated.
		assertEquals(1, rEngine.getPlayer(firstPlayer).getDisplayer().getSize());
		assertEquals(GAMEConfig.SQUIRE, rEngine.getPlayer(firstPlayer).getDisplayer().getCard(0).getName());
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(GAMEConfig.SWORD, rEngine.getPlayer(secondPlayer).getDisplayer().getCard(0).getName());
	}

	//playing card on shielded player
	@Test
	public void OutwitB () {
		System.out.println("\n-------------------Test Outwit B.------------------------------");

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
		Card card = new Card(GAMEConfig.SWORD,GAMEConfig.COLOR_RED,GAMEConfig.VALUE_SWORD_THREE);
		rEngine.getPlayer(firstPlayer).addCard(card);
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//add squire to secondPlayer display
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//add shield to secondPlayer display
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		reply = rEngine.processMessage(msg);

		//make sure action card cannot be played
		assertEquals(null, reply);

		//make sure card effects are not activated
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(GAMEConfig.SQUIRE, rEngine.getPlayer(secondPlayer).getDisplayer().getCard(0).getName());
	}

	//undoing this card using Ivanhoe
	@Test
	public void OutwitC () {
		System.out.println("\n-------------------Test Outwit C.------------------------------");

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
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//add squire to secondPlayer display
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_YELLOW);
		rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure card is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
		//make sure card effects are not activated
		assertEquals(1, rEngine.getPlayer(secondPlayer).getDisplayer().getSize());
		assertEquals(GAMEConfig.SQUIRE, rEngine.getPlayer(secondPlayer).getDisplayer().getCard(0).getName());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void OutwitD () {
		System.out.println("\n-------------------Test Outwit D.------------------------------");

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
		Card card = new Card(GAMEConfig.SWORD,GAMEConfig.COLOR_RED,GAMEConfig.VALUE_SWORD_THREE);
		rEngine.getPlayer(firstPlayer).addCard(card);
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//add squire to secondPlayer display
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		rEngine.getPlayer(secondPlayer).getDisplayer().addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"", GAMEConfig.SELECTED_DISPLAY_INDEX, 0+"", GAMEConfig.SELECTED_TARGET_DISPLAY_INDEX, 0+"");
		rEngine.processMessage(msg);

		//make sure card discarded is correct
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void OutwitE () {
		System.out.println("\n-------------------Test Outwit E.------------------------------");

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
		card = new Card(GAMEConfig.OUTWIT, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hardcode player hands
		card = new Card(GAMEConfig.SQUIRE, GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO);
		//rEngine.getPlayer(secondPlayer).addCard(card);
		rEngine.getPlayer(thirdPlayer).addCard(card);
		rEngine.getPlayer(fourthPlayer).addCard(card);
		rEngine.getPlayer(fifthPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard playing action card
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void ShieldA () {
		System.out.println("\n-------------------Test Shield A.------------------------------");

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
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure firstPlayer has shield
		assertTrue(rEngine.getPlayer(firstPlayer).getDisplayer().hasShield());
	}

	//playing card on shielded player
	@Test
	public void ShieldB () {
		System.out.println("\n-------------------Test Shield B.------------------------------");

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
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//add shield to firstPlayer
		rEngine.getPlayer(firstPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//undoing this card using Ivanhoe
	@Test
	public void ShieldC () {
		System.out.println("\n-------------------Test Shield C.------------------------------");

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
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure Unhorse is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void ShieldD () {
		System.out.println("\n-------------------Test Shield D.------------------------------");

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
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//make sure action card is discarded
		assertEquals(1, rEngine.getDeadwood().getSize());
		//make sure card discarded is correct
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void ShieldE () {
		System.out.println("\n-------------------Test Shield D.------------------------------");

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
		card = new Card(GAMEConfig.SHIELD, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//add shield to firstPlayer
		rEngine.getPlayer(firstPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//playing card on unshielded player
	@Test
	public void StunnedA () {
		System.out.println("\n-------------------Test Stunned A.------------------------------");

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
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//make sure secondPlayer has stunned
		assertTrue(rEngine.getPlayer(secondPlayer).getDisplayer().hasStunned());
	}

	//playing card on shielded player
	@Test
	public void StunnedB () {
		System.out.println("\n-------------------Test Stunned B.------------------------------");

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
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//add shield to secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Shield");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}

	//undoing this card using Ivanhoe
	@Test
	public void StunnedC () {
		System.out.println("\n-------------------Test Stunned C.------------------------------");

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
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//Giving secondPlayer an Ivanhoe Card
		card = new Card(GAMEConfig.IVANHOE, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(secondPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);

		//hard code secondPlayer play Ivanhoe response
		msg = Data.newMessage(secondPlayer + "", GAMEConfig.CHECK_IVANHOE, "Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		rEngine.processMessage(msg);

		//make sure Unhorse is discarded because of Ivanhoe
		assertEquals(2, rEngine.getDeadwood().getSize());
	}

	//checking a used action card is indeed thrown away
	@Test
	public void StunnedD () {
		System.out.println("\n-------------------Test Stunned D.------------------------------");

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
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		rEngine.processMessage(msg);
		
		//make sure action card is discarded
		assertEquals(1, rEngine.getDeadwood().getSize());
		//make sure card discarded is correct
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		assertTrue(rEngine.getDeadwood().hasCard(card));
	}

	//making sure the card cannot be played in some circumstances
	@Test
	public void StunnedE () {
		System.out.println("\n-------------------Test Stunned D.------------------------------");

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
		card = new Card(GAMEConfig.STUNNED, GAMEConfig.ACTION_CARD, GAMEConfig.VALUE_ACTION_CARD_ZERO);
		rEngine.getPlayer(firstPlayer).addCard(card);

		//add stunned to secondPlayer
		rEngine.getPlayer(secondPlayer).getDisplayer().setStatus("Stunned");

		//hard code firstPlayer select colour response
		Message msg = Data.newMessage(firstPlayer + "", GAMEConfig.SELECT_COLOR, "Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//hard code firstPlayer play or withdraw response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_OR_WITHDRAW, "POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer playCard response
		msg = Data.newMessage(firstPlayer + "", GAMEConfig.PLAY_CARD, "Selected Card Index", 0+"", GAMEConfig.SELECTED_TARGET_ID, secondPlayer+"");
		reply = rEngine.processMessage(msg);

		//make sure card cannot be played
		assertEquals(null, reply);
	}
}