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

    private static Slot[] slots =
            new Slot[] {
                    new PositionSlot(Position.QB),
                    new PositionSlot(Position.RB),
                    new PositionSlot(Position.RB),
                    new PositionSlot(Position.WR),
                    new PositionSlot(Position.WR),
                    new RbWrSlot(),
                    new PositionSlot(Position.TE),
                    new FlexSlot(),
                    new PositionSlot(Position.K),
                    new PositionSlot(Position.DEF)
            };


    /*
     * Running values
     */
    private Roster roster;

    private Schedule schedule;

    private Map<Player,PlayerStatus> status;

    private double totalScore;

    public SeasonSimulator(Roster r, Schedule s) {
        this.roster = r;
        this.schedule = s;
        this.status = new TreeMap<Player,PlayerStatus>();
        for(Player p : roster.getPlayers()) {
            status.put(p, new PlayerStatus(p));
        }
    }

    public void simulateSeason() {
        Collection<String> weeks = schedule.getWeeks();
        for(String week : weeks) {
            simulateWeek(week);
        }
    }

    private void simulateWeek(String week) {
        Starters starters = findStarters();
    }

    public Starters findStarters() {
        Starters starters = new Starters(slots);

        return starters;
    }

    public double getTotalScore() {
        return totalScore;
    }
}

class PlayerStatus {

    private boolean injured;
    private Player player;

    public PlayerStatus(Player p) {
        this.player = p;
        this.injured = false;
    }
}



class Starters {

    private Slot[] slots;
    private Player[] players;

    public Starters(Slot[] slots) {

        this.slots = slots;
        this.players = new Player[slots.length];
    }

}
