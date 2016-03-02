package testcases;

import static org.junit.Assert.assertEquals;

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

		Card card = new Card("BreakLance", "Action Card", 0);

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

		Card card = new Card("KnockDown", "Action Card", 0);

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
    
    @Test
	public void testHasActionDisgrace () {
		System.out.println("@Test(): hasActionDisgrace()");

		Card card = new Card("Disgrace", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionDisgrace());
    }
    
    @Test
	public void testHasActionAdapt () {
		System.out.println("@Test(): hasActionAdapt()");

		Card card = new Card("Adapt", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionAdapt());
    }
    
    @Test
	public void testHasActionOutwit () {
		System.out.println("@Test(): hasActionOutwit()");

		Card card = new Card("Outwit", "Action Card", 0);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasActionOutwit());
    }
    
    @Test
	public void testHasPurple () {
		System.out.println("@Test(): hasPurple()");

		Card card = new Card("Jousting", "Purple", 3);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasPurple());
    }
    
    @Test
	public void testHasRed () {
		System.out.println("@Test(): hasRed()");

		Card card = new Card("Sword", "Red", 3);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasRed());
    }
    
    @Test
	public void testHasBlue () {
		System.out.println("@Test(): hasBlue()");

		Card card = new Card("Axe", "Blue", 2);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasBlue());
    }
    
    @Test
	public void testHasYellow () {
		System.out.println("@Test(): hasYellow()");

		Card card = new Card("Morningstar", "Yellow", 2);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasYellow());
    }
    
    @Test
	public void testHasGreen () {
		System.out.println("@Test(): hasGreen()");

		Card card = new Card("No Weapon", "Green", 2);

		hand.drawCard(card);
		
		assertEquals(Boolean.TRUE, hand.hasGreen());
    }
}
