package testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Deck;

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
		
		Card card 	= new Card("Squire", "White", 2);
		Card card2 	= new Card("NoWeapon",	"Action Card", 1);
		Card card3 	= new Card("Maiden", "Action Card", 6);
		Card card4 	= new Card("Unhorse", "Action Card", 0);
		Card card5 	= new Card("ChangeWeapon", "Action Card", 0);
		
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

		Card card 	= new Card("Squire", 		"White", 2);
		Card card2 	= new Card("NoWeapon",		"Action Card", 1);
		Card card3 	= new Card("Maiden", 		"Action Card", 6);
		Card card4 	= new Card("Unhorse", 		"Action Card", 0);
		Card card5 	= new Card("Change Weapon", "Action Card", 0);
		
		deck.addCard(card);
		deck.addCard(card2);
		deck.addCard(card3);
		deck.addCard(card4);
		deck.addCard(card5);
		
		Deck deck2 = new Deck();
		
		deck2 = deck;
		
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
			assertEquals("Ivanhoe", 	deck.getCard(i).getName());
			assertEquals("Action Card", deck.getCard(i).getColor());
		}
    }
    
    @Test
	public void testInit () 
    {
		System.out.println("@Test(): init()");
		
		deck.init();
		
		Deck deck2 = new Deck();

		deck2.addCards(new Card("Jousting",	"Purple", 3), 4);
		deck2.addCards(new Card("Jousting",	"Purple", 4), 4);
		deck2.addCards(new Card("Jousting",	"Purple", 5), 4);
		deck2.addCards(new Card("Jousting",	"Purple", 7), 2);

		deck2.addCards(new Card("Sword", "Red", 3), 6);
		deck2.addCards(new Card("Sword", "Red", 4), 6);
		deck2.addCards(new Card("Sword", "Red", 5), 2);

		deck2.addCards(new Card("Axe", "Blue", 2), 4);
		deck2.addCards(new Card("Axe", "Blue", 3), 4);
		deck2.addCards(new Card("Axe", "Blue", 4), 4);
		deck2.addCards(new Card("Axe", "Blue", 5), 2);
		
		deck2.addCards(new Card("Morningstar", "Yellow", 2), 4);
		deck2.addCards(new Card("Morningstar", "Yellow", 3), 8);
		deck2.addCards(new Card("Morningstar", "Yellow", 4), 2);

		deck2.addCards(new Card("NoWeapon", "Green", 1), 14);

		deck2.addCards(new Card("Squire", "White", 2), 8);
		deck2.addCards(new Card("Squire", "White", 3), 8);
		deck2.addCards(new Card("Maiden", "White", 6), 4);
		
		deck2.addCards(new Card("Unhorse", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("ChangeWeapon", 	"Action Card", 0), 	1);
		deck2.addCards(new Card("DropWeapon", 		"Action Card", 0), 	1);
		deck2.addCards(new Card("Shield", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("Stunned",			"Action Card", 0), 	1);
		deck2.addCards(new Card("Ivanhoe", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("BreakLance", 		"Action Card", 0), 	1);
		deck2.addCards(new Card("Riposte", 			"Action Card", 0), 	3);
		deck2.addCards(new Card("Dodge", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("Retreat", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("KnockDown", 		"Action Card", 0), 	2);
		deck2.addCards(new Card("Outmaneuver", 		"Action Card", 0), 	1);
		deck2.addCards(new Card("Charge", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("Countercharge", 	"Action Card", 0), 	1);
		deck2.addCards(new Card("Disgrace", 		"Action Card", 0), 	1);
		deck2.addCards(new Card("Adapt", 			"Action Card", 0), 	1);
		deck2.addCards(new Card("Outwit", 			"Action Card", 0), 	1);
		
		assertTrue(deck.equals(deck2));
    }
}