package testcases;

import static org.junit.Assert.assertEquals;

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
}
