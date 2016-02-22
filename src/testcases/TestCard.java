package testcases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


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

}
