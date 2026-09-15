package sparkx.sparkshop.knowledge.intent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RuleBasedIntentRouterTest {

    private final RuleBasedIntentRouter router = new RuleBasedIntentRouter();

    @Test
    void shortFollowUpIsNotNewQuestion() {
        assertEquals(QueryIntent.FOLLOW_UP, router.tryClassify("那怎么办"));
        assertEquals(QueryIntent.FOLLOW_UP, router.tryClassify("那怎么办？"));
        assertEquals(QueryIntent.FOLLOW_UP, router.tryClassify("然后呢"));
        assertEquals(QueryIntent.FOLLOW_UP, router.tryClassify("具体怎么做"));
        assertTrue(router.isFollowUp("怎么办"));
        assertTrue(QueryIntent.FOLLOW_UP.needsRetrieval());
    }
}
