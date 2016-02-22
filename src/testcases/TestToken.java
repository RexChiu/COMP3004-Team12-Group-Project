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
}
