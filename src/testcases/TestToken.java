package testcases;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestToken {

    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestToken");
    }
    
    @Before
    public void setUp() {
		System.out.println("@Before: TestToken");
		Tokens token = new Tokens("Purple");
	}
	
    @After
    public void tearDown () {
		System.out.println("@After(): TestToken");
	}
	
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestToken");
    }
}
