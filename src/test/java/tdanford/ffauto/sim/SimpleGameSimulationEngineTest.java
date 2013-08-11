package tdanford.ffauto.sim;

import org.testng.annotations.*;
import tdanford.ffauto.draft.Position;

import tdanford.ffauto.draft.*;

import static org.testng.Assert.*;

import java.util.*;

/**
 * User: tdanford
 * Date: 8/10/13
 */
public class SimpleGameSimulationEngineTest {

    @Test
    public void testNonNegativeScores() {
        Random rand = new Random(1001L);

        Player p1 = new Player("Test Player", Position.QB, new Team("Team"), 10.0);

        for(int i = 0; i < 1000; i++) {
            assertTrue(SimpleGameSimulationEngine.simulatePlayerScore(p1, rand) >= 0.0);
        }
    }

    @Test
    public void testScoreFractions() {
        /*
        Check that, to within five decimal places, all scores have at most one nonzero fractional digit.
         */
        Set<String> fractions = new TreeSet<String>(Arrays.asList(
                "0.00000",
                "0.10000",
                "0.20000",
                "0.30000",
                "0.40000",
                "0.50000",
                "0.60000",
                "0.70000",
                "0.80000",
                "0.90000"
        ));
        Player p1 = new Player("Test Player", Position.QB, new Team("Team"), 10.0);
        Random rand = new Random(1002L);

        for(int i = 0; i < 1000; i++) {
            double score = SimpleGameSimulationEngine.simulatePlayerScore(p1, rand);
            double fraction = score - Math.floor(score);
            String fstring = String.format("%.5f", fraction);
            assertTrue(fractions.contains(fstring), String.format("%f fraction %s", score, fstring));
        }

    }
}
