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

    public int hashCode() { return position.hashCode(); }

    public boolean equals(Object o) {
        if(!(o instanceof PositionSlot)) { return false; }
        PositionSlot ps = (PositionSlot)o;
        return ps.position.equals(position);
    }

}

class RbWrSlot implements Slot {

    public boolean acceptsPlayer(Player p) {
        return p.getPosition().equals(Position.RB) ||
                p.getPosition().equals(Position.WR);
    }

    public String toString() { return "RB/WR"; }

    public int hashCode() { return toString().hashCode(); }

    public boolean equals(Object o) { return o instanceof RbWrSlot; }
}

class FlexSlot implements Slot {
    public boolean acceptsPlayer(Player p) {
        return p.getPosition().equals(Position.RB) ||
                p.getPosition().equals(Position.WR) ||
                p.getPosition().equals(Position.TE);
    }

    public String toString() { return "FLEX"; }

    public int hashCode() { return toString().hashCode(); }

    public boolean equals(Object o) { return o instanceof FlexSlot; }
}