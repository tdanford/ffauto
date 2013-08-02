package tdanford.ffauto.draft;

/**
 * User: tdanford
 * Date: 8/2/13
 */
public interface Slot {
    public boolean acceptsPlayer(Player p);
}

class PositionSlot implements Slot {
    private Position position;
    public PositionSlot(Position p) { this.position = p; }

    public boolean acceptsPlayer(Player p) {
        return position.equals(p.getPosition());
    }

    public String toString() { return position.name(); }
}

class RbWrSlot implements Slot {

    public boolean acceptsPlayer(Player p) {
        return p.getPosition().equals(Position.RB) ||
                p.getPosition().equals(Position.WR);
    }

    public String toString() { return "RB/WR"; }
}

class FlexSlot implements Slot {
    public boolean acceptsPlayer(Player p) {
        return p.getPosition().equals(Position.RB) ||
                p.getPosition().equals(Position.WR) ||
                p.getPosition().equals(Position.TE);
    }

    public String toString() { return "FLEX"; }
}