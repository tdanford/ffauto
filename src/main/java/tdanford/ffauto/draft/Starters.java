package tdanford.ffauto.draft;

import java.util.*;

/**
 * Starters class represents the assignments of Players to Slots for
 * a particular game.
 */
public class Starters {

    private Slot[] slots;
    private Player[] players;

    public Starters(Slot[] ss, Collection<Player> available, PlayerValue evaluator) {
        // TODO fill me in with a matching algorithm.
    }

    public Starters(Slot[] slots, Player[] players) {
        if(players.length != slots.length) {
            throw new IllegalArgumentException("slots.length must match players.length");
        }
        this.slots = slots;
        this.players = players.clone();
    }

    public boolean hasUnmatchedSlot() {
        for(int i = 0; i < players.length; i++) {
            if(players[i] == null) { return true; }
        }
        return false;
    }

    public int getNumSlots() { return slots.length; }

    public Slot getSlot(int i) { return slots[i]; }

    public Player getPlayer(int i) { return players[i]; }

    public int hashCode() {
        int code = 17;
        for(int i = 0; i < slots.length; i++) {
            code += slots[i].hashCode(); code *= 37;
            code += players[i].hashCode(); code *= 37;
        }
        return code;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Starters)) { return false; }
        Starters s = (Starters)o;
        if(slots.length != s.slots.length) { return false; }
        for(int i = 0; i < slots.length; i++) {
            if(!slots[i].equals(s.slots[i])) { return false; }
            if(!players[i].equals(s.players[i])) { return false; }
        }
        return true;
    }
}
