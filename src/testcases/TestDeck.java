package testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Deck;
import config.GAMEConfig;

public class TestDeck {
	Deck deck;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestDeck");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestDeck");
		deck = new Deck();
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestDeck");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestDeck");
    }
    
    @Test
	public void testAddCard () 
    {
		System.out.println("@Test(): addCard(Card card)");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		deck.addCard(card);
				
		assertEquals(card, deck.getCard(0));
    }
    
    @Test
	public void testRemoveCard () 
    {
		System.out.println("@Test(): removeCard(Card card)");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		deck.addCard(card);
				
		deck.removeCard(card);
		
		assertTrue(deck.isEmpty());
    }
    
    @Test
	public void testGetSize () 
    {
		System.out.println("@Test(): getSize()");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		deck.addCard(card);
		
		assertEquals(1, deck.getSize());
    }
    
    @Test
	public void testShuffleDeck () 
    {
		System.out.println("@Test(): shuffleDeck()");
		
		Deck deck2 = new Deck();
		
		Card card 	= new Card(GAMEConfig.SQUIRE, 			GAMEConfig.SUPPORTERS_WHITE, 	GAMEConfig.VALUE_SQUIRE_TWO);
		Card card2 	= new Card(GAMEConfig.NO_WEAPON, 		GAMEConfig.COLOR_GREEN, 		GAMEConfig.VALUE_NO_WEAPON_ONE);
		Card card3 	= new Card(GAMEConfig.MAIDEN, 			GAMEConfig.SUPPORTERS_WHITE, 	GAMEConfig.VALUE_MAIDEN_SIX);
		Card card4 	= new Card(GAMEConfig.UNHORSE, 			GAMEConfig.ACTION_CARD, 		GAMEConfig.VALUE_ACTION_CARD_ZERO);
		Card card5 	= new Card(GAMEConfig.CHANGE_WEAPON, 	GAMEConfig.ACTION_CARD, 		GAMEConfig.VALUE_ACTION_CARD_ZERO);
		
		deck.addCard(card);
		deck.addCard(card2);
		deck.addCard(card3);
		deck.addCard(card4);
		deck.addCard(card5);
		
		deck2.addCard(card);
		deck2.addCard(card2);
		deck2.addCard(card3);
		deck2.addCard(card4);
		deck2.addCard(card5);
		
		deck2.shuffleDeck();
		
		assertEquals(deck.getSize(), deck2.getSize());
		assertTrue(!(deck.equals(deck2)));
    }
    
    @Test
	public void testCleanDeck () 
    {
		System.out.println("@Test(): cleanDeck()");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		deck.addCard(card);
		
		deck.cleanDeck();
		
		assertTrue(deck.isEmpty());
    }
    
    @Test
	public void testCloneDeck () throws CloneNotSupportedException 
    {
		System.out.println("@Test(): clone()");
		
		Card card 	= new Card(GAMEConfig.SQUIRE, 			GAMEConfig.SUPPORTERS_WHITE, 	GAMEConfig.VALUE_SQUIRE_TWO);
		Card card2 	= new Card(GAMEConfig.NO_WEAPON, 		GAMEConfig.COLOR_GREEN, 		GAMEConfig.VALUE_NO_WEAPON_ONE);
		Card card3 	= new Card(GAMEConfig.MAIDEN, 			GAMEConfig.SUPPORTERS_WHITE, 	GAMEConfig.VALUE_MAIDEN_SIX);
		Card card4 	= new Card(GAMEConfig.UNHORSE, 			GAMEConfig.ACTION_CARD, 		GAMEConfig.VALUE_ACTION_CARD_ZERO);
		Card card5 	= new Card(GAMEConfig.CHANGE_WEAPON, 	GAMEConfig.ACTION_CARD, 		GAMEConfig.VALUE_ACTION_CARD_ZERO);
		
		deck.addCard(card);
		deck.addCard(card2);
		deck.addCard(card3);
		deck.addCard(card4);
		deck.addCard(card5);
		
		Deck deck2 = new Deck();
		
		deck2 = deck.getDeck();
		
		assertTrue(deck.equals(deck2));
    }
    
    @Test
	public void testAddCards () 
    {
		System.out.println("@Test(): addCards(Card card, int count)");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		deck.addCards(card, 3);
		
		for (int i = 0; i < deck.getSize(); i++)
		{
			assertEquals("Ivanhoe", deck.getCard(i).getName());
			assertEquals("Action Card", deck.getCard(i).getColor());
		}
    }
    
    @Test
	public void testInit () 
    {
		System.out.println("@Test(): init()");
		
		deck.init();
		
		Deck deck2 = new Deck();
		
		deck2.addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_THREE), 		GAMEConfig.NUMBER_PURPLE_THREE	);
		deck2.addCards(new Card(GAMEConfig.JOUSTING, 		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_FOUR), 		GAMEConfig.NUMBER_PURPLE_FOUR	);
		deck2.addCards(new Card(GAMEConfig.JOUSTING,		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_FIVE), 		GAMEConfig.NUMBER_PURPLE_FIVE	);
		deck2.addCards(new Card(GAMEConfig.JOUSTING, 		GAMEConfig.COLOR_PURPLE, 	GAMEConfig.VALUE_JOUSTING_SEVEN), 		GAMEConfig.NUMBER_PURPLE_SEVER	);
		
		deck2.addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_THREE), 			GAMEConfig.NUMBER_RED_THREE		);
		deck2.addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_FOUR), 			GAMEConfig.NUMBER_RED_FOUR		);
		deck2.addCards(new Card(GAMEConfig.SWORD, 		GAMEConfig.COLOR_RED, 		GAMEConfig.VALUE_SWORD_FIVE), 			GAMEConfig.NUMBER_RED_FIVE		);
		
		deck2.addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_TWO), 				GAMEConfig.NUMBER_BLUE_TWO		);
		deck2.addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_THREE), 			GAMEConfig.NUMBER_BLUE_THREE	);
		deck2.addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_FOUR), 			GAMEConfig.NUMBER_BLUE_FOUR		);
		deck2.addCards(new Card(GAMEConfig.AXE, 			GAMEConfig.COLOR_BLUE, 		GAMEConfig.VALUE_AXE_FIVE), 			GAMEConfig.NUMBER_BLUE_FIVE		);
		
		deck2.addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_TWO), 	GAMEConfig.NUMBER_YELLOW_TWO	);
		deck2.addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_THREE), 	GAMEConfig.NUMBER_YELLOW_THREE	);
		deck2.addCards(new Card(GAMEConfig.MORNINGSTAR, 	GAMEConfig.COLOR_YELLOW, 	GAMEConfig.VALUE_MORNINGSTART_FOUR), 	GAMEConfig.NUMBER_YELLOW_FOUR	);
		
		deck2.addCards(new Card(GAMEConfig.NO_WEAPON, 	GAMEConfig.COLOR_GREEN, 	GAMEConfig.VALUE_NO_WEAPON_ONE), 		GAMEConfig.NUMBER_GREEN_ONE		);
		
		deck2.addCards(new Card(GAMEConfig.SQUIRE, 		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_TWO), 			GAMEConfig.NUMBER_SQUIRES_TWO	);
		deck2.addCards(new Card(GAMEConfig.SQUIRE,		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_SQUIRE_THREE),		GAMEConfig.NUMBER_SQUIRES_THREE	);		
		deck2.addCards(new Card(GAMEConfig.MAIDEN, 		GAMEConfig.SUPPORTERS_WHITE, GAMEConfig.VALUE_MAIDEN_SIX), 			GAMEConfig.NUMBER_MAIDENS_SIX	);
		
		deck2.addCards(new Card(GAMEConfig.UNHORSE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_UNHORSE		);
		deck2.addCards(new Card(GAMEConfig.CHANGE_WEAPON, GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_CHANGE_WEAPON	);
		deck2.addCards(new Card(GAMEConfig.DROP_WEAPON, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DROP_WEAPON	);
		deck2.addCards(new Card(GAMEConfig.SHIELD, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_SHIELD		);
		deck2.addCards(new Card(GAMEConfig.STUNNED,		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_STUNNED		);
		deck2.addCards(new Card(GAMEConfig.IVANHOE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_IVANHOE		);
		deck2.addCards(new Card(GAMEConfig.BREAK_LANCE, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_BREAK_LANCE	);
		deck2.addCards(new Card(GAMEConfig.RIPOSTE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_RIPOSTE		);
		deck2.addCards(new Card(GAMEConfig.DODGE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DODGE			);
		deck2.addCards(new Card(GAMEConfig.RETREAT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_RETREAT		);
		deck2.addCards(new Card(GAMEConfig.KNOCK_DOWN, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_KNOCK_DOWN	);
		deck2.addCards(new Card(GAMEConfig.OUTMANEUVER, 	GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_OUTMANEUVER	);
		deck2.addCards(new Card(GAMEConfig.CHARGE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_CHARGE		);
		deck2.addCards(new Card(GAMEConfig.COUNTERCHARGE, GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_COUNTERCHARGE	);
		deck2.addCards(new Card(GAMEConfig.DISGRACE, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_DISGRACE		);
		deck2.addCards(new Card(GAMEConfig.ADAPT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_ADAPT			);
		deck2.addCards(new Card(GAMEConfig.OUTWIT, 		GAMEConfig.ACTION_CARD, 	GAMEConfig.VALUE_ACTION_CARD_ZERO), 	GAMEConfig.NUMBER_OUTFIT		);	
    
		assertTrue(deck.equals(deck2));
    }
}
