package testcases;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestIvanhoeRequests {
	// Create attribute variable for Ivanhoe

	/** This will be processed before the Test Class is instantiated */
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestIvanhoeRequests");
    }

    /** This is processed/initialized before each test is conducted */
    @Before
    public void setUp() {
		System.out.println("@Before(): TestIvanhoeRequests");
		// Set up Ivanhoe
	}
	
    /** This is processed/initialized after each test is conducted */
    @After
    public void tearDown () {
		System.out.println("@After(): TestIvanhoeRequests");
		// Set Ivanhoe to null;
	}
	
    /** This will be processed after the Test Class has been destroyed */
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestIvanhoeRequests");
    }
    
    @Test
	public void testIvanhoeRequests () {
		System.out.println("@Test(): TestIvanhoeRequests");
		assertEquals(Boolean.TRUE, Boolean.TRUE);
	}
}
