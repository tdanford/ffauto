package tdanford.ffauto.draft;

import org.testng.annotations.*;
import tdanford.ffauto.draft.events.RosterListener;

import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * User: tdanford
 * Date: 7/17/13
 */
public class RosterTest {

    @Test
    public void testRosterCreationAndSize() {
        Roster r = new Roster();

        assertEquals(r.size(), 0);
        assertEquals(r.remaining(Position.QB), 1);

        Player p = new Player("Test Player", Position.QB, 0.0);
        Roster r2 = new Roster(r, p);

        assertEquals(r2.size(), 1);
        assertEquals(r2.remaining(Position.QB), 0);
    }

    @Test
    public void testRosterFactor() {
        Player p = new Player("Test Player", Position.RB, 1.0);
        Player p2 = new Player("Second Player", Position.RB, 1.0);
        Roster r = new Roster(new Roster(), p);

        assertEquals(r.remaining(Position.RB), 5, "There should be five RB remaining");
        assertEquals(r.factor(p2), 1.0, "The injury factor for that running back should be 1.0");

        Player qb = new Player("Test QB", Position.QB, 1.0);
        Player qb2 = new Player("Second QB", Position.QB, 1.0);
        Roster r2 = new Roster(r, qb);

        assertEquals(r2.remaining(Position.QB), 0, "Should be no QB remaining");
        assertEquals(r2.factor(qb2), 0.25, "Injury factor on a second QB should be 0.25");
    }

    @Test
    public void testRosterEvents() {
        Player p1 = new Player("Test QB", Position.QB, 1.0);
        Player p2 = new Player("Test RB", Position.RB, 1.0);

        Roster r = new Roster();

        RosterListener listener = mock(RosterListener.class);
        r.addListener(listener);

        r.addPlayer(p1);
        verify(listener).playerAdded(r, p1);

        // adding a player twice, shouldn't fire an event on the second add!
        r.addPlayer(p1);
        verifyNoMoreInteractions(listener);

        r.addPlayer(p2);
        verify(listener).playerAdded(r, p2);

        r.removePlayer(p1);
        verify(listener).playerRemoved(r, p1);

        // removing a player twice, or removing a player who isn't in the roster,
        // shouldn't fire an event!
        r.removePlayer(p1);
        verifyNoMoreInteractions(listener);

        r.removeListener(listener);
        r.removePlayer(p2);
        verifyNoMoreInteractions(listener);
    }
}
