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
}
