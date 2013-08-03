package tdanford.ffauto.draft;

import java.util.*;

import org.testng.annotations.*;
import static org.testng.Assert.*;

/**
 * User: tdanford
 * Date: 7/31/13
 */
public class SeasonSimulatorTest {

    @Test
    public void testTotalScoreIsNonZero() {
        Collection<Team> teams = new ArrayList<Team>();
        teams.add(new Team("Team1"));
        teams.add(new Team("Team2"));

        Schedule schedule = new Schedule(teams);
        schedule.addMatchup("1", new Team("Team1"), new Team("Team2"));

        LeagueRules rules = new LeagueRules();
        SeasonSimulator sim = new SeasonSimulator(rules, new Roster(), schedule);
        sim.simulateSeason();

        assertTrue(sim.getTotalScore() >= 0.0);
    }
}
