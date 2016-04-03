package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.Display;

public class TestDisplay {
	Display display;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestDisplay");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestDisplay");
		display = new Display();
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestDisplay");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestDisplay");
    }
    
    @Test
	public void testGetTotal () {
		System.out.println("@Test(): getTotal()");

		Card card = new Card("Ivanhoe", "Action Card", 0);
		display.addCard(card);
		assertEquals(0, display.getTotal());
		
		Card newCard = new Card("Jousting", "Purple", 3);
		display.addCard(newCard);
		assertEquals(3, display.getTotal());
    }
    
    @Test
	public void testGetCard () {
		System.out.println("@Test(): getCard(int index)");

		Card card = new Card("Ivanhoe", "Action Card", 0);
		display.addCard(card);
		
		assertEquals(card, display.getCard(0));
    }
    
    @Test
	public void testGetStatus () {
		System.out.println("@Test(): getStatus()");

		assertEquals("", display.getStatus());

		display.setStatus("Shield");
		assertEquals("Shield", display.getStatus());
		display.setStatus("Stunned");
		assertEquals("ShieldStunned", display.getStatus());
    }
    
    @Test
	public void testGetTournament () {
		System.out.println("@Test(): getTournament()");

		display.setTournament("Purple");
		
		assertEquals("Purple", display.getTournament());
    }
    
    @Test
	public void testIsGreenTournament () {
		System.out.println("@Test(): isGreenTournament()");

		display.setTournament("Green");
		
		assertEquals(Boolean.TRUE, display.isGreenTournament());
    }
    
    @Test
	public void testIsShield () {
		System.out.println("@Test(): isShield()");

		display.setStatus("Shield");
		
		assertEquals(Boolean.TRUE, display.isShield());
    }
    
    @Test
	public void testIsStunned () {
		System.out.println("@Test(): isStunned()");

		display.setStatus("Stunned");
		
		assertEquals(Boolean.TRUE, display.isStunned());
    }
    
    @Test
	public void testHasMaiden () {
		System.out.println("@Test(): hasMaiden()");
		
		Card card = new Card("Maiden", "White", 6);
		
		display.addCard(card);
		
		assertEquals(Boolean.TRUE, display.hasMaiden());
    }
}
