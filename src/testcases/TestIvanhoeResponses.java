package testcases;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestIvanhoeResponses {
	// Create attribute variable for Ivanhoe

	/** This will be processed before the Test Class is instantiated */
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestIvanhoeResponses");
    }

    /** This is processed/initialized before each test is conducted */
    @Before
    public void setUp() {
		System.out.println("@Before(): TestIvanhoeResponses");
		// Set up Ivanhoe
	}
	
    /** This is processed/initialized after each test is conducted */
    @After
    public void tearDown () {
		System.out.println("@After(): TestIvanhoeResponses");
		// Set Ivanhoe to null;
	}
	
    /** This will be processed after the Test Class has been destroyed */
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestIvanhoeResponses");
    }

    @Test
	public void testIvanhoeResponses () {
		System.out.println("@Test(): IvanhoeResponses");
		 	
		assertEquals(Boolean.TRUE, Boolean.TRUE);
	}
}
