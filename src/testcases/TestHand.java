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
		
		assertEquals(Boolean.TRUE, hand.hasIvanhoe());
    }
    
    @Test
	public void testHasMaiden () {
		System.out.println("@Test(): hasMaiden()");

		Card card = new Card("Maiden", "White", 6);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasMaiden());
    }
    
    @Test
	public void testHasSquire () {
		System.out.println("@Test(): hasSquire()");

		Card card = new Card("Squire", "White", 2);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasSquire());
    }
    
    @Test
	public void testHasShield () {
		System.out.println("@Test(): hasShield()");

		Card card = new Card("Shield", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasShield());
    }
    
    @Test
	public void testHasStunned () {
		System.out.println("@Test(): hasStunned()");

		Card card = new Card("Stunned", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasStunned());
    }
    
    @Test
	public void testHasActionBraekLance () {
		System.out.println("@Test(): hasActionBraekLance()");

		Card card = new Card("Break Lance", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionBraekLance());
    }
    
    @Test
	public void testHasActionRiposte () {
		System.out.println("@Test(): hasActionRiposte()");

		Card card = new Card("Riposte", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionRiposte());
    }
    
    @Test
	public void testHasActionDodge () {
		System.out.println("@Test(): hasActionDodge()");

		Card card = new Card("Dodge", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionDodge());
    }
    
    @Test
	public void testHasActionRetreat () {
		System.out.println("@Test(): hasActionRetreat()");

		Card card = new Card("Retreat", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionRetreat());
    }
    
    @Test
	public void testHasActionKnockDown () {
		System.out.println("@Test(): hasActionKnockDown()");

		Card card = new Card("Knock Down", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionKnockDown());
    }
    
    @Test
	public void testHasActionOutmaneuver () {
		System.out.println("@Test(): hasActionOutmaneuver()");

		Card card = new Card("Outmaneuver", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionOutmaneuver());
    }
    
    @Test
	public void testHasActionCharge () {
		System.out.println("@Test(): hasActionCharge()");

		Card card = new Card("Charge", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionCharge());
    }
    
    @Test
	public void testHasActionCountercharge () {
		System.out.println("@Test(): hasActionCountercharge()");

		Card card = new Card("Countercharge", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionCountercharge());
    }
}
