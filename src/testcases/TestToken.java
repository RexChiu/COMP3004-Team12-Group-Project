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
	Tokens token;
	
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestToken");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestToken");
		token = new Tokens("Purple");
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
		
		assertEquals(1, token.getSize());
    }

    @Test
	public void testGetToken () {
		System.out.println("@Test(): getToken(String token)");
		
		Token purpleToken = new Token("Purple");
		
		assertEquals(purpleToken.getName(), token.getToken("Purple").getName());
    }

    @Test
	public void testCheckToken () {
		System.out.println("@Test(): checkToken(String token)");
				
		assertEquals(Boolean.TRUE, token.checkToken("Purple"));
    }

    @Test
	public void testAddToken () {
		System.out.println("@Test(): addToken(String token)");
		
		token.addToken("Blue");

		assertEquals(Boolean.TRUE, token.checkToken("Blue"));
    }

    @Test
	public void testRemoveToken () {
		System.out.println("@Test(): removeToken(String token)");
		
		token.removeToken("Blue");

		assertEquals(Boolean.FALSE, token.checkToken("Blue"));
    }

    @Test
	public void testHasPurple () {
		System.out.println("@Test(): hasPurple()");
		
		assertEquals(Boolean.TRUE, token.hasPurple());
    }

    @Test
	public void testHasRed () {
		System.out.println("@Test(): hasRed()");
		
		token.addToken("Red");
		
		assertEquals(Boolean.TRUE, token.hasRed());
    }

    @Test
	public void testHasBlue () {
		System.out.println("@Test(): hasBlue()");
		
		token.addToken("Blue");
		
		assertEquals(Boolean.TRUE, token.hasBlue());
    }

    @Test
	public void testHasYellow () {
		System.out.println("@Test(): hasYellow()");
		
		token.addToken("Yellow");
		
		assertEquals(Boolean.TRUE, token.hasYellow());
    }

    @Test
	public void testHasWhite () {
		System.out.println("@Test(): hasWhite()");
		
		token.addToken("White");
		
		assertEquals(Boolean.TRUE, token.hasWhite());
    }
}
