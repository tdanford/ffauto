package tdanford.ffauto.draft;

import java.util.*;

/**
 * User: tdanford
 * Date: 8/2/13
 */
public class LeagueRules {

    private Slot[] slots;

    private int rosterSize;

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
    }


    public Slot[] getSlots() {
        return slots;
    }

    public int getRosterSize() {
        return rosterSize;
    }

    public Double getEventValue(GameEvent e) {
        return eventValues.get(e);
    }


}

enum GameEvent {
    RUSHING_YARDS,
    PASSING_YARDS,
    PASSES_ATTEMPTED,
    PASSES_COMPLETED,
    TOUCHDOWNS,
    PAT,
    FG_MADE,
    FG_MISSED,
    RECEPTIONS,
    SAFETIES,
    YARDS_ALLOWED,
    POINTS_ALLOWED,
    INTERCEPTIONS,
    BLOCKED_FG,
    BLOCKED_PAT
}

