package tdanford.ffauto.draft;

import org.testng.annotations.*;

import static org.testng.Assert.*;


/**
 * User: tdanford
 * Date: 7/26/13
 */
public class StartersTest {

    @Test
    public void testEquality() {
        Player p1 = new Player("Test Player", Position.QB, new Team("Team"), 1.0);
        Player p2 = new Player("Test2 Player", Position.QB, new Team("Team"), 1.0);

        Slot qbSlot = new PositionSlot(Position.QB);

        Starters s1 = new Starters(new Slot[] { qbSlot }, new Player[] { p1 });
        Starters s2 = new Starters(new Slot[] { qbSlot }, new Player[] { p1 });
        Starters s3 = new Starters(new Slot[] { qbSlot }, new Player[] { p2 });

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertNotEquals(s2, s3);
    }

    @Test
    public void testAccessors() {
        Player qb = new Player("Test Quarterback", Position.QB, new Team("Team"), 1.0);
        Player rb = new Player("Test Runningback", Position.RB, new Team("Team"), 1.0);

        Slot qbSlot = new PositionSlot(Position.QB);
        Slot rbSlot = new PositionSlot(Position.RB);

        Starters s = new Starters(new Slot[] { qbSlot, rbSlot }, new Player[] { qb, rb });

        assertEquals(s.getNumSlots(), 2);
        assertEquals(s.getSlot(0), qbSlot);
        assertEquals(s.getSlot(1), rbSlot);
        assertEquals(s.getPlayer(0), qb);
        assertEquals(s.getPlayer(1), rb);
    }

    @Test
    public void testHasUnmatchedSlots() {
        Player qb = new Player("Test Quarterback", Position.QB, new Team("Team"), 1.0);
        Player rb = new Player("Test Runningback", Position.RB, new Team("Team"), 1.0);

        Slot qbSlot = new PositionSlot(Position.QB);
        Slot rbSlot = new PositionSlot(Position.RB);

        Starters s1 = new Starters(new Slot[] { qbSlot, rbSlot, rbSlot }, new Player[] { qb, rb, null });
        Starters s2 = new Starters(new Slot[] { qbSlot, rbSlot }, new Player[] { qb, rb });

        assertEquals(s1.getNumSlots(), 3);
        assertTrue(s1.hasUnmatchedSlot());

        assertEquals(s2.getNumSlots(), 2);
        assertFalse(s2.hasUnmatchedSlot());
    }
}
