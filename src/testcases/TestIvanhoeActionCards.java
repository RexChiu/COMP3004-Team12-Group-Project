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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_RED);
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_RED);
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to secondPlayer
		assertEquals(GAMEConfig.CHECK_IVANHOE, reply.getHeader().state);
		assertEquals(String.valueOf(secondPlayer), reply.getHeader().receiver);
		assertEquals(secondPlayer, rEngine.getCurrentID());

		//hard code secondPlayer play Ivanhoe response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.CHECK_IVANHOE;
		msg.getBody().addField("Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure Unhorse effects are activated, changed colour to red.
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is red
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response (trying to play unhorse)
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//confirm current tournament colour
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_BLUE);
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_BLUE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_RED);
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_YELLOW);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to secondPlayer
		assertEquals(GAMEConfig.CHECK_IVANHOE, reply.getHeader().state);
		assertEquals(String.valueOf(secondPlayer), reply.getHeader().receiver);
		assertEquals(secondPlayer, rEngine.getCurrentID());

		//hard code secondPlayer play Ivanhoe response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.CHECK_IVANHOE;
		msg.getBody().addField("Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		reply = rEngine.processMessage(msg);

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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to player
		assertEquals(GAMEConfig.CHANGE_TOURNAMENT_COLOR, reply.getHeader().state);

		//hard code firstPlayer changeTournamentColor response to Red
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.CHANGE_TOURNAMENT_COLOR;
		msg.getBody().addField("Change Tournament Color", GAMEConfig.COLOR_BLUE);
		reply = rEngine.processMessage(msg);

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to blue.
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response (trying to play changeWeapon)
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//confirm current tournament colour
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_BLUE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_BLUE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_YELLOW);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is correct
		assertEquals(GAMEConfig.COLOR_YELLOW, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure RE sends correct response back to secondPlayer
		assertEquals(GAMEConfig.CHECK_IVANHOE, reply.getHeader().state);
		assertEquals(String.valueOf(secondPlayer), reply.getHeader().receiver);
		assertEquals(secondPlayer, rEngine.getCurrentID());

		//hard code secondPlayer play Ivanhoe response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.CHECK_IVANHOE;
		msg.getBody().addField("Ivanhoe Choice", GAMEConfig.IVANHOE_YES);
		reply = rEngine.processMessage(msg);

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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure card effects are activated, changed colour to green.
		assertEquals(GAMEConfig.COLOR_GREEN, rEngine.getCurrColour());
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
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure current tournament colour is purple
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

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

		//hard code firstPlayer playCard response (trying to play changeWeapon)
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//make sure no response from RE (invalid card, didnt play)
		assertEquals(null, reply);
	}
}