package testcases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

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
}
