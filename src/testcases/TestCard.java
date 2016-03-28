package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;


public class TestCard {
	Card card;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestCard");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestCard");
		card = new Card("Ivanhoe", "Action Card", 0);
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestCard");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestCard");
    }
    
    @Test
	public void testGetName () {
		System.out.println("@Test(): getName()");
		card = new Card("Maiden", "White", 6);
		
		assertEquals("Maiden", card.getName());
    }
    
    @Test
	public void testGetColor () {
		System.out.println("@Test(): getColor()");
		card = new Card("Maiden", "White", 6);
		
		assertEquals("White", card.getColor());
    }
    
    @Test
	public void testGetValue () {
		System.out.println("@Test(): getValue()");
		card = new Card("Maiden", "White", 6);
		
		assertEquals(6, card.getValue());
    }
    
    @Test
	public void testIsAction () {
		System.out.println("@Test(): isAction()");
		card = new Card("Ivanhoe", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isAction());
    }
    
    @Test
	public void testIsPurple () {
		System.out.println("@Test(): isPurple()");
		card = new Card("Jousting", "Purple", 3);
		
		assertEquals(Boolean.TRUE, card.isPurple());
    }
    
    @Test
	public void testIsRed () {
		System.out.println("@Test(): isRed()");
		card = new Card("Sword", "Red", 3);
		
		assertEquals(Boolean.TRUE, card.isRed());
    }
    
    @Test
	public void testIsBlue () {
		System.out.println("@Test(): isBlue()");
		card = new Card("Axe", "Blue", 2);
		
		assertEquals(Boolean.TRUE, card.isBlue());
    }
    
    @Test
	public void testIsYellow () {
		System.out.println("@Test(): isYellow()");
		card = new Card("Morningstar", "Yellow", 2);
		
		assertEquals(Boolean.TRUE, card.isYellow());
    }
    
    @Test
	public void testIsGreen () {
		System.out.println("@Test(): isGreen()");
		card = new Card("No Weapon", "Green", 1);
		
		assertEquals(Boolean.TRUE, card.isGreen());
    }
    
    @Test
	public void testIsMaiden () {
		System.out.println("@Test(): isMaiden()");
		card = new Card("Maiden", "White", 6);
		
		assertEquals(Boolean.TRUE, card.isMaiden());
    }
    
    @Test
	public void testIsSquire () {
		System.out.println("@Test(): isSquire()");
		card = new Card("Squire", "White", 2);
		
		assertEquals(Boolean.TRUE, card.isSquire());
    }
    
    @Test
	public void testIsIvanhoe () {
		System.out.println("@Test(): isIvanhoe()");
		card = new Card("Ivanhoe", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isIvanhoe());
    }
    
    @Test
	public void testIsShield () {
		System.out.println("@Test(): isShield()");
		card = new Card("Shield", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isShield());
    }
    
    @Test
	public void testIsStunned () {
		System.out.println("@Test(): isStunned()");
		card = new Card("Stunned", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isStunned());
    }
    
    @Test
	public void testIsBreakLance () {
		System.out.println("@Test(): isBreakLance()");
		card = new Card("BreakLance", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isBreakLance());
    }
    
    @Test
	public void testIsRiposte () {
		System.out.println("@Test(): isRiposte()");
		card = new Card("Riposte", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isRiposte());
    }
    
    @Test
	public void testIsDodge () {
		System.out.println("@Test(): isDodge()");
		card = new Card("Dodge", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isDodge());
    }
    
    @Test
	public void testIsRetreat () {
		System.out.println("@Test(): isRetreat()");
		card = new Card("Retreat", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isRetreat());
    }
    
    @Test
	public void testIsKnockDown () {
		System.out.println("@Test(): isKnockDown()");
		card = new Card("KnockDown", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isKnockDown());
    }
    
    @Test
	public void testIsOutmaneuver () {
		System.out.println("@Test(): isOutmaneuver()");
		card = new Card("Outmaneuver", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isOutmaneuver());
    }
    
    @Test
	public void testIsCharge () {
		System.out.println("@Test(): isCharge()");
		card = new Card("Charge", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isCharge());
    }
    
    @Test
	public void testIsCountercharge () {
		System.out.println("@Test(): isCountercharge()");
		card = new Card("Countercharge", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isCountercharge());
    }
    
    @Test
	public void testIsDisgrace () {
		System.out.println("@Test(): isDisgrace()");
		card = new Card("Disgrace", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isDisgrace());
    }
    
    @Test
	public void testIsAdapt () {
		System.out.println("@Test(): isAdapt()");
		card = new Card("Adapt", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isAdapt());
    }
    
    @Test
	public void testIsOutwit () {
		System.out.println("@Test(): isOutwitt()");
		card = new Card("Outwit", "Action Card", 0);
		
		assertEquals(Boolean.TRUE, card.isOutwit());
    }
}
