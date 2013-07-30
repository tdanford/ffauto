package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class DraftHistoryTest {

    @Test
    public void testGetManager() {
        Manager m = new Manager("Timothy");
        DraftHistory d = new DraftHistory(m);
        assertEquals(d.getManager(), m);
    }

    @Test
    public void testGetDraftOrder() {
        DraftHistory d = new DraftHistory(new Manager("Timothy"));
        Player p1 = new Player("Test Player1", Position.QB, null, 1.0);
        Player p2 = new Player("Test Player2", Position.RB, null, 2.0);

        d.addPlayer(p1);
        d.addPlayer(p2);

        Player[] dorder = d.getDraftList().toArray(new Player[0]);
        assertEquals(dorder.length, 2);
        assertEquals(dorder[0], p1);
        assertEquals(dorder[1], p2);
    }
}
