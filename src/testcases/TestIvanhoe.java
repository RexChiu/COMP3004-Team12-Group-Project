package testcases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.Before;


public class TestIvanhoe {
	// Create attribute variable for Ivanhoe

	/** This will be processed before the Test Class is instantiated */
    @BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: TestIvanhoe");
    }

    /** This is processed/initialized before each test is conducted */
    @Before
    public void setUp() {
		System.out.println("@Before(): TestIvanhoe");
		// Set up Ivanhoe
	}
	
    /** This is processed/initialized after each test is conducted */
    @After
    public void tearDown () {
		System.out.println("@After(): TestIvanhoe");
		// Set Ivanhoe to null;
	}
	
    /** This will be processed after the Test Class has been destroyed */
    @AfterClass
    public static void afterClass () {
    	 System.out.println("@AfterClass: TestIvanhoe");
    }
    
    /** Each unit test is annotated with the @Test Annotation */
    @Test
	public void WaitingState () { }
	
    @Test
	public void Response () { }

}
