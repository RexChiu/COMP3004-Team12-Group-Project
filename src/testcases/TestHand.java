package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Hand;
import game.Tokens;

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
	public void testgetCard () {
		System.out.println("@Test(): getCard()");
		
		Card card = new Card("Ivanhoe", "Action Card", 0);
		
		hand.drawCard(card);
		
		assertEquals(card, card.getCard(0));
    }
}
