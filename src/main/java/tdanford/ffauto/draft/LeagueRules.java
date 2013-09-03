package tdanford.ffauto.draft;

import java.util.*;

/**
 * User: tdanford
 * Date: 8/2/13
 */
public class LeagueRules {

    private Slot[] slots;

    private int benchSize;
    private int reserveSize;

    private Map<GameEvent,Double> eventValues;

    public LeagueRules() {
        slots = new Slot[] {
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

        benchSize = 7;
        reserveSize = 1;
    }


    public Slot[] getSlots() {
        return slots;
    }

    public int getRosterSize() { return benchSize + slots.length; }

    public int getBenchSize() {
        return benchSize;
    }

    public int getReserveSize() {
        return reserveSize;
    }

    public Double getEventValue(GameEvent e) {
        return eventValues.get(e);
    }
}

class GameSummary {

    private Map<GameEvent,Integer> gameEvents;

    public GameSummary() {
        gameEvents = new HashMap<GameEvent,Integer>();
    }

    public double score(ScoringRule rule) {
        double score = 0.0;

        return score;
    }
}

interface ScoringRule {
    public double score(GameEvent e, Integer value);
}

enum GameEvent {
    /*
    Passing statistics
     */
    PASSES_ATTEMPTED,
    PASSES_COMPLETED,
    TOUCHDOWN_PASSES,
    PASSING_YARDS,
    INTERCEPTIONS_THROWN,
    TWOPT_PASSING_CONVERSIONS,

    /*
    Rushing Statistics
     */
    RUSHING_YARDS,
    TWOPT_RUSHING_CONVERSIONS,
    TOUCHDOWN_RUSHES,

    /*
    Receiving Statistics
     */
    RECEPTIONS,
    YARDS_RECEIVING,
    TOUCHDOWN_RECEPTIONS,
    TWOPT_RECEIVING_CONVERSIONS,

    /*
    Miscellaneous Offense
     */
    KICKOFF_RETURN_TOUCHDOWNS,
    PUNT_RETURN_TOUCHDOWNS,
    FUMBLE_RECOVERY_TOUCHDOWNS,
    FUMBLES_LOST,

    /*
    Kicking Statistics
     */
    PAT,
    FG_MADE,
    FG_MISSED,

    /*
    Defensive Statistics
     */
    SAFETIES,
    SACKS,
    INTERCEPTIONS_MADE,
    FUMBLES_RECOVERED,
    BLOCKED_PUNTS,
    BLOCKED_FGS,
    YARDS_ALLOWED,
    POINTS_ALLOWED,
}

