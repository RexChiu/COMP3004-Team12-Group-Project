package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Token;
import game.Tokens;

public class TestToken {
	Tokens tokens;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestToken");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestToken");
		tokens = new Tokens("Purple");
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestToken");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestToken");
    }

    @Test
	public void testGetSize () {
		System.out.println("@Test(): getSize()");
		
		assertEquals(1, tokens.getSize());
    }

    @Test
	public void testGetToken () {
		System.out.println("@Test(): getToken(String token)");
		
		Token purpleToken = new Token("Purple");
		
		assertEquals(purpleToken.getName(), tokens.getToken("Purple").getName());
    }

    @Test
	public void testCheckToken () {
		System.out.println("@Test(): checkToken(String token)");
				
		assertEquals(Boolean.TRUE, tokens.checkToken("Purple"));
    }

    @Test
	public void testAddToken () {
		System.out.println("@Test(): addToken(String token)");
		
		tokens.addToken("Blue");

		assertEquals(Boolean.TRUE, tokens.checkToken("Blue"));
    }

    @Test
	public void testRemoveToken () {
		System.out.println("@Test(): removeToken(String token)");
		
		tokens.removeToken("Blue");

		assertEquals(Boolean.FALSE, tokens.checkToken("Blue"));
    }

    @Test
	public void testHasPurple () {
		System.out.println("@Test(): hasPurple()");
		
		assertEquals(Boolean.TRUE, tokens.hasPurple());
    }

    @Test
	public void testHasRed () {
		System.out.println("@Test(): hasRed()");
		
		tokens.addToken("Red");
		
		assertEquals(Boolean.TRUE, tokens.hasRed());
    }

    @Test
	public void testHasBlue () {
		System.out.println("@Test(): hasBlue()");
		
		tokens.addToken("Blue");
		
		assertEquals(Boolean.TRUE, tokens.hasBlue());
    }

    @Test
	public void testHasYellow () {
		System.out.println("@Test(): hasYellow()");
		
		tokens.addToken("Yellow");
		
		assertEquals(Boolean.TRUE, tokens.hasYellow());
    }

    @Test
	public void testHasWhite () {
		System.out.println("@Test(): hasWhite()");
		
		tokens.addToken("White");
		
		assertEquals(Boolean.TRUE, tokens.hasWhite());
    }
    
    @Test
	public void testHasFour () {
		System.out.println("@Test(): hasFour()");
		
		assertEquals(Boolean.FALSE, tokens.hasFour());

		tokens.addToken("Red");
		tokens.addToken("Blue");
		tokens.addToken("Yellow");
		
		assertEquals(Boolean.TRUE, tokens.hasFour());
    }
    
    @Test
	public void testHasFive () {
		System.out.println("@Test(): hasFive()");
		
		assertEquals(Boolean.FALSE, tokens.hasFive());

		tokens.addToken("Red");
		tokens.addToken("Blue");
		tokens.addToken("Yellow");
		tokens.addToken("White");
		
		assertEquals(Boolean.TRUE, tokens.hasFive());
    }
}
