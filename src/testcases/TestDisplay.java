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
}
