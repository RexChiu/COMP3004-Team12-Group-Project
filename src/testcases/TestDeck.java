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
	public void testCloneDeck () 
    {
		System.out.println("@Test(): cleanDeck()");
		
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
}
