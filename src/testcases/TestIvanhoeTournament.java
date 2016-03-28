package testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Ivanhoe;
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
	@Test
	public void TestA1 () //First Player starts, others withdraw immediately
	{ 
		System.out.println("-------------------Test A------------------------------");
		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		/*
    	//hard code firstPlayer playCard response
    	msg = new Message();
    	msg.getHeader().sender = firstPlayer + "";
    	msg.getHeader().state = GAMEConfig.PLAY_CARD;
    	msg.getBody().addField("Selected Card Index", 0+"");
    	reply = rEngine.processMessage(msg);
		 */

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());
		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//check if first player has won red token
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
	}

	@Test
	public void TestB1 () //first player draws and plays one card, others withdraw
	{ 
		System.out.println("-------------------Test B1------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

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

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());
		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestB2 () //first player draws and plays multiple cards, others withdraw
	{ 
		System.out.println("-------------------Test B2------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

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

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());
		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestC1 () //first player draws and plays a card, second player also plays a card others withdraw
	{ 
		System.out.println("-------------------Test C1------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

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

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());

		int secondPlayer = rEngine.getCurrentID();

		//hard code secondPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			if (i != secondPlayer)
			{
				msg = new Message();
				msg.getHeader().sender = i + "";
				msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
				msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
				reply = rEngine.processMessage(msg);
				//make sure withdraw message is valid
				//assertNotNull(reply);
			}
		}

		//hardcode withdraw first player
		msg = new Message();
		msg.getHeader().sender = firstPlayer +"";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//true if secondPlayer won a token
		boolean wonToken = rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestC2 () //first player draws and plays cards, second player also plays cards, others withdraw
	{ 
		System.out.println("-------------------Test C2------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

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

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());

		int secondPlayer = rEngine.getCurrentID();

		//hard code secondPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = secondPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			if (i != secondPlayer)
			{
				msg = new Message();
				msg.getHeader().sender = i + "";
				msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
				msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
				reply = rEngine.processMessage(msg);
				//make sure withdraw message is valid
				//assertNotNull(reply);
			}
		}

		//hardcode withdraw first player
		msg = new Message();
		msg.getHeader().sender = firstPlayer +"";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
		reply = rEngine.processMessage(msg);

		//true if secondPlayer won a token
		boolean wonToken = rEngine.getPlayer(secondPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestD1 () //first player draws and plays one card, others play 1 card, then withdraws
	{ 
		System.out.println("-------------------Test D1------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}
		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestD2 () //first player draws and plays cards, others play cards, then withdraws
	{ 
		System.out.println("-------------------Test D2------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}
		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestE1 () //starting with a single supporter (all players)
	{ 
		System.out.println("-------------------Test E1------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}
		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestE2 () //starting with multiple supporters (all players)
	{ 
		System.out.println("-------------------Test E2------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}
		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestF1 () //plays one, then plays several supporters
	{ 
		System.out.println("-------------------Test F------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}


		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}

	@Test
	public void TestG1() //plays one, then plays several supporters
	{ 
		System.out.println("-------------------Test G------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//first player plays two cards
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code playCard response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code endTurn response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);


		int secondPlayer = rEngine.getCurrentID();
		//hardcoded second player play or withdraw response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer playCard response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.PLAY_CARD;
		msg.getBody().addField("Selected Card Index", 0+"");
		reply = rEngine.processMessage(msg);

		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = rEngine.getCurrentID() + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure secondPlayer is withdrawn
		assertTrue(rEngine.getPlayer(secondPlayer).isWithdrawn());
	}
	
	@Test
	public void TestI1 () //First Player starts, others withdraw immediately
	{ 
		System.out.println("-------------------Test I------------------------------");
		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_RED, rEngine.getCurrColour());

		//hard code firstPlayer playorwithdraw response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
		msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
		reply = rEngine.processMessage(msg);

		//make sure playOrWithdraw is valid, and state is PlayCard
		assertEquals(GAMEConfig.PLAY_CARD, rEngine.getState());

		/*
    	//hard code firstPlayer playCard response
    	msg = new Message();
    	msg.getHeader().sender = firstPlayer + "";
    	msg.getHeader().state = GAMEConfig.PLAY_CARD;
    	msg.getBody().addField("Selected Card Index", 0+"");
    	reply = rEngine.processMessage(msg);
		 */

		//hard code firstPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//make sure after first player ends turn, that the REngine incremented
		assertNotEquals(firstPlayer, rEngine.getCurrentID());
		System.out.println("First Player = " + firstPlayer);

		//loops through all chars except firstPlayer, and withdraws
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}

		//check if first player has won red token
		assertTrue(rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED));
	}
	
	@Test
	public void TestJ1 () //starting with a single supporter (all players)
	{ 
		System.out.println("-------------------Test J------------------------------");

		//add 5 players
		rEngine.addPlayer(1);
		rEngine.addPlayer(2);
		rEngine.addPlayer(3);
		rEngine.addPlayer(4);
		rEngine.addPlayer(5);

		//init game
		rEngine.initTestCase();

		int firstPlayer = rEngine.getCurrentID();

		//hard code firstPlayer select colour response
		Message msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.SELECT_COLOUR;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_PURPLE);
		Message reply = rEngine.processMessage(msg);

		//make sure select colour is valid
		assertNotNull(reply);
		//make sure selected colour is proper
		assertEquals(GAMEConfig.COLOR_PURPLE, rEngine.getCurrColour());

		//loops through all chars, plays a card, and ends turn
		for (int i = 0; i < 5; i++)
		{
			int oldPlayer = rEngine.getCurrentID();
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_PLAY);
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer playCard response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.PLAY_CARD;
			msg.getBody().addField("Selected Card Index", 0+"");
			reply = rEngine.processMessage(msg);

			//hard code secondPlayer endTurn response
			msg = new Message();
			msg.getHeader().sender = rEngine.getCurrentID() + "";
			msg.getHeader().state = GAMEConfig.END_TURN;
			reply = rEngine.processMessage(msg);

			assertNotEquals(oldPlayer, rEngine.getCurrentID());
		}
		assertEquals(firstPlayer, rEngine.getCurrentID());
		//hard code secondPlayer endTurn response
		msg = new Message();
		msg.getHeader().sender = firstPlayer + "";
		msg.getHeader().state = GAMEConfig.END_TURN;
		reply = rEngine.processMessage(msg);

		//withdraw all players except firstPlayer
		for (int i = rEngine.getCurrentID(); i != firstPlayer; i = (i+1)%6)
		{
			msg = new Message();
			msg.getHeader().sender = i + "";
			msg.getHeader().state = GAMEConfig.PLAY_OR_WITHDRAW;
			msg.getBody().addField("POW Choice", GAMEConfig.POW_WITHDRAW);
			reply = rEngine.processMessage(msg);
			//make sure withdraw message is valid
			//assertNotNull(reply);
		}
		
		//assertEquals(GAMEConfig.WIN_TOURNAMENT, rEngine.getState());
		
		System.out.println("Current ID = " + rEngine.getCurrentID() + " First ID = " + firstPlayer);
		
		msg = new Message();
		msg.getHeader().sender = firstPlayer +"";
		msg.getHeader().state = GAMEConfig.WIN_TOURNAMENT;
		msg.getBody().addField("Tournament Color", GAMEConfig.COLOR_RED);
		reply = rEngine.processMessage(msg);

		//true if firstPlayer won a token
		boolean wonToken = rEngine.getPlayer(firstPlayer).checkToken(GAMEConfig.COLOR_RED);
		assertTrue(wonToken);
	}
}
