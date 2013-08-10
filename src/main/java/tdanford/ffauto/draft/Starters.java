package tdanford.ffauto.draft;

/**
 * Starters class represents the assignments of Players to Slots for
 * a particular game.
 */
public class Starters {

    private Slot[] slots;
    private Player[] players;

    public Starters(Slot[] slots) {

        this.slots = slots;
        this.players = new Player[slots.length];
    }
}
