package tdanford.ffauto.sim;

import tdanford.ffauto.draft.*;

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

    public int getNumWeeks() { return weeks.size(); }

    public String getWeekName(int i) { return weeks.get(i); }

    public boolean hasNextWeek() { return currentWeek < weeks.size(); }

    public void reset() {
        currentWeek = 0;
        for(Player p : stats.keySet()) {
            stats.get(p).reset();
        }
    }

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

    /**
     * Scores a given set of Starters in the given week.
     *
     * @param starters The Starters is the set of Players whose values are to be totalled.
     * @param week The name of the week in which to score the Starters
     * @throws IllegalArgumentException if the 'week' parameter is the name of an unknown week.
     * @return The total score of all Players starting in the given week.
     */
    public double teamScore(Starters starters, String week) {
        int weekIndex = weeks.indexOf(week);
        if(weekIndex == -1) { throw new IllegalArgumentException("Unknown week " + week); }

        double totalPoints = 0.0;
        for(int i = 0; i < starters.getNumSlots(); i++) {
            Player p = starters.getPlayer(i);
            Double value = stats.get(p).getValue(weekIndex);
            if(value != null) {
                totalPoints += value;
            }
        }
        return totalPoints;
    }

    /**
     * For a given Roster chooses the best available (non-injured) players in the current
     * week to fit into the complete set of Slots provided by the LeagueRules, and returns
     * a Starters object matching those players to the slots.
     *
     * @param r The Roster to choose the non-injured players from.
     * @param week The week in which to choose the Starters
     * @return A Starters object containing the Players matched to Slots.
     */
    public Starters chooseStarters(Roster r, String week) {
        Slot[] slots = rules.getSlots();
        Player[] players = new Player[slots.length];
        Set<Player> chosen = new TreeSet<Player>();

        for(int i = 0; i < slots.length; i++) {
            Slot slot = slots[i];
            Player p = findBestPlayer(week, slot, r, chosen);
            players[i] = p;

            // 'null' indicates that no player was available, and this slot
            // will go un-filled from this Roster.
            if(players[i] != null) {
                chosen.add(p);
            }
        }

        return new Starters(slots, players);
    }

    /**
     * This chooses, for the given Slot and from the given Roster, the best available player
     * who hasn't already been chosen.  It also ignores players that are _currently_ injured,
     * which is why it's a method of the SeasonSimulator, because it requires access to the
     * player's current status.
     *
     * @param week
     * @param slot
     * @param roster
     * @param chosen
     * @return
     */
    public Player findBestPlayer(String week, Slot slot, Roster roster, Set<Player> chosen) {
        int weekIndex = weeks.indexOf(week);
        if(weekIndex == -1) { throw new IllegalArgumentException("Unknown week " + week); }

        TreeSet<Player> available = new TreeSet<Player>();
        for(Player p : roster.getPlayers()) {
            if(!chosen.contains(p) && slot.acceptsPlayer(p) && !stats.get(p).isInjured(weekIndex)) {
                available.add(p);
            }
        }

        if(available.isEmpty()) { return null; }
        return available.first();
    }
}

/**
 * The PlayerStats encapsulates the state of a (simulated or real) season for a given Player.
 *
 * The reason I don't embed a lot of this into the Player object itself is because I want to make
 * it easier to write multi-threaded parallel sampling (simulation) frameworks.
 */
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

    public void addValue(Double value) {
        points += (value != null ? value : null);
        weekPoints.addLast(value);
    }

    public void reset() {
        points = 0;
        weekPoints.clear();
        injuryWeeksLeft = 0;
    }

    public double getLastValue() { return weekPoints.getLast(); }

    public Double[] getWeekValues() { return weekPoints.toArray(new Double[0]); }

    public Double getTotalValue() { return points; }

    public Double getValue(int i) { return weekPoints.get(i); }

    public boolean isInjured() { return injuryWeeksLeft > 0; }

    public boolean isInjured(int week) {
        if(week < weekPoints.size()) {
            return weekPoints.get(week) == null;
        } else {
            return isInjured();
        }
    }

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


