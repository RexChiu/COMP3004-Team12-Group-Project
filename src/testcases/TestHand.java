package testcases;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Hand;

public class TestHand {
	Hand hand;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestHand");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestHand");
		hand = new Hand();
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestHand");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestHand");
    }
    
    @Test
	public void testGetCard () {
		System.out.println("@Test(): getCard(int index)");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		hand.drawCard(card);
				
		assertEquals(card, hand.getCard(0));
    }
    
    @Test
	public void testLastCard () {
		System.out.println("@Test(): lastCard()");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		hand.drawCard(card);
				
		assertEquals(Boolean.TRUE, hand.lastCard());
    }
    
    @Test
	public void testPlayCard () {
		System.out.println("@Test(): playCard(Card card)");

		Card card = new Card("Ivanhoe", "Action Card", 0);

		assertEquals(Boolean.FALSE, hand.playCard(card));
		
		hand.drawCard(card);
		
		Card newCard = new Card("Maiden", "White", 6);
		hand.drawCard(newCard);
				
		assertEquals(Boolean.TRUE, hand.playCard(newCard));
    }
    
    @Test
	public void testCountCard () {
		System.out.println("@Test(): countCard(Card sameCard)");

		Card card = new Card("Ivanhoe", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(1, hand.countCard(card));
    }
    
    @Test
	public void testAllAction () {
		System.out.println("@Test(): allAction()");

		Card card = new Card("Ivanhoe", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.allAction());
    }
    
    @Test
	public void testHasIvanhoe () {
		System.out.println("@Test(): hasIvanhoe()");

		Card card = new Card("Ivanhoe", "Action Card", 0);

		hand.drawCard(card);
		
		assertNotEquals(-1, hand.hasIvanhoe());
    }
    
    @Test
	public void testHasMaiden () {
		System.out.println("@Test(): hasMaiden()");

		Card card = new Card("Maiden", "White", 6);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasMaiden());
    }
}
