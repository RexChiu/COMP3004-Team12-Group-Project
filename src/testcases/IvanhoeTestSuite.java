package testcases;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
   TestIvanhoe.class,
   TestIvanhoeRequests.class,
   TestIvanhoeResponses.class,
   TestToken.class,
   TestCard.class,
   TestHand.class,
   TestDisplay.class,
   TestDeck.class,
   TestIvanhoeTournament.class,
   TestIvanhoeActionCards.class,
   TestIvanhoeScenarios.class
})

public class IvanhoeTestSuite {

}
