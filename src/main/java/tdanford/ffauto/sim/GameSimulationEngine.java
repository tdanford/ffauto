package tdanford.ffauto.sim;

import tdanford.ffauto.draft.LeagueRules;
import tdanford.ffauto.draft.Player;
import tdanford.ffauto.draft.Schedule;

import java.util.*;

/**
 * GameSimulationEngine
 *
 * User: tdanford
 * Date: 8/10/13
 */
/**
 */
public interface GameSimulationEngine {
    /**
     * This method is the core of the simulation -- it generates, for a particular week in the season,
     * and for a particular profile of player statuses, a value for each player.
     *
     * @param schedule
     * @param week
     * @param stats
     * @return
     */
    public Map<Player,Double> simulateWeekScores(LeagueRules rules, Schedule schedule, String week, Map<Player,PlayerStats> stats, Random rand);
}

class SimpleGameSimulationEngine implements GameSimulationEngine {

    @Override
    public Map<Player, Double> simulateWeekScores(LeagueRules rules, Schedule schedule, String week, Map<Player, PlayerStats> stats, Random rand) {
        Map<Player,Double> values = new TreeMap<Player,Double>();

        for(Player p : stats.keySet()) {
            if(stats.get(p).isInjured()) {
                values.put(p, null);  // nulls represent players who didn't play.
            } else {
                double value = simulatePlayerScore(p, rand);
                values.put(p, value);
            }
        }

        return values;
    }

    public static double simulatePlayerScore(Player p, Random rand) {
        Double value = p.getFutureValue();
        double stddev = 10.0;

        double delta = rand.nextGaussian() * stddev;
        delta *= 10.0;
        delta = Math.round(delta);
        delta /= 10.0;

        return Math.max(0.0, value + delta);
    }
}