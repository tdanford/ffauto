package tdanford.ffauto.draft.events;

import tdanford.ffauto.draft.Player;
import tdanford.ffauto.draft.Position;
import tdanford.ffauto.draft.Roster;

/**
 * User: tdanford
 * Date: 7/18/13
 */
public interface RosterListener {
    public void playerAdded(Roster r, Player p);
    public void playerRemoved(Roster r, Player p);
}
