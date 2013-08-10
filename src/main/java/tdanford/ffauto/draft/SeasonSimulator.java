package tdanford.ffauto.draft;

import java.util.*;

/**
 * User: tdanford
 * Date: 7/31/13
 */
public class SeasonSimulator {

    /*
     * Parameters
     */
    private static double probInjury = 0.1;

    public static double getProbabilityInjury() {
        return probInjury;
    }

    /*
     * Running values
     */
    private Random rand = new Random();

    private LeagueRules rules;

    private Schedule schedule;

    private ArrayList<String> weeks;

    private int currentWeek;

    private Map<Player,PlayerStats> stats;

    private GameSimulationEngine simEngine = new SimpleGameSimulationEngine();

    public SeasonSimulator(LeagueRules rules, Schedule s, ArrayList<Player> players) {
        this.rules = rules;
        this.schedule = s;
        this.stats = new TreeMap<Player,PlayerStats>();
        this.weeks = new ArrayList<String>(s.getWeeks());
        currentWeek = 0;

        for(Player p : players) {
            stats.put(p, new PlayerStats(p));
        }
    }

    public boolean hasNextWeek() { return currentWeek < weeks.size(); }

    public void simulateNextWeek() {
        String week = weeks.get(currentWeek++);
        Map<Player,Double> values = simEngine.simulateWeekScores(rules, schedule, week, stats, rand);
        for(Player p : values.keySet()) {
            stats.get(p).addValue(values.get(p));
        }

        updatePlayerStatuses();
    }

    private void updatePlayerStatuses() {
        for(Player p : stats.keySet()) {
            stats.get(p).update(rand);
        }
    }

    public void simulateSeason() {
        while(hasNextWeek()) {
            simulateNextWeek();
        }
    }

    public Collection<Player> getPlayers() { return stats.keySet(); }

    public PlayerStats getStats(Player p) { return stats.get(p); }

    public double getTotalValue(Player p) { return stats.get(p).getTotalValue(); }

    public double getLastValue(Player p) { return stats.get(p).getLastValue(); }

}

class PlayerStats {

    private Player player;
    private double points;
    private LinkedList<Double> weekPoints;
    private int injuryWeeksLeft;

    public PlayerStats(Player p) {
        this.player = p;
        this.points = 0.0;
        this.injuryWeeksLeft = 0;
        weekPoints = new LinkedList<Double>();
    }

    public void addValue(double value) {
        points += value;
        weekPoints.addLast(value);
    }

    public double getLastValue() { return weekPoints.getLast(); }

    public Double[] getWeekValues() { return weekPoints.toArray(new Double[0]); }

    public Double getTotalValue() { return points; }

    public boolean isInjured() { return injuryWeeksLeft > 0; }

    public void update(Random rand) {
        if(injuryWeeksLeft > 0) {
            injuryWeeksLeft -= 1;
        } else {
            if(rand.nextDouble() <= SeasonSimulator.getProbabilityInjury()) {
                injuryWeeksLeft = rand.nextInt(3);
            }
        }
    }
}

/**
 * GameSimulationEngine
 */
interface GameSimulationEngine {
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
                values.put(p, 0.0);
            } else {
                double value = simulatePlayerScore(p, rand);
                values.put(p, value);
            }
        }

        return values;
    }

    private static double simulatePlayerScore(Player p, Random rand) {
        Double value = p.getFutureValue();
        double stddev = 10.0;

        double delta = rand.nextGaussian() * stddev;
        delta *= 10.0;
        delta = Math.round(delta);
        delta /= 10.0;

        return Math.max(0.0, value + delta);
    }
}

/**
 * Starters class represents the assignments of Players to Slots for
 * a particular game.
 */
class Starters {

    private Slot[] slots;
    private Player[] players;

    public Starters(Slot[] slots) {

        this.slots = slots;
        this.players = new Player[slots.length];
    }
}
