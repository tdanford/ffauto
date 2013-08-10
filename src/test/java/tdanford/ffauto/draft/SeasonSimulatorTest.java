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

        ArrayList<Player> players = new ArrayList<Player>();
        Player p1 = new Player("Test Player", Position.QB, new Team("Team1"), 10.0);
        players.add(p1);

        LeagueRules rules = new LeagueRules();
        SeasonSimulator sim = new SeasonSimulator(rules, schedule, players);
        sim.simulateSeason();

        assertTrue(sim.getPlayers().contains(p1), "Player set does not contain the player p1");
        assertTrue(sim.getTotalValue(p1) >= 0.0, String.format("Player p1's score %f is negative", sim.getTotalValue(p1)));
    }
}
