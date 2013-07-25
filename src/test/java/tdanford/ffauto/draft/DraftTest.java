package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class DraftTest {

    @Test
    public void testGetManager() {
        Manager m = new Manager("Timothy");
        Draft d = new Draft(m, new Roster());
        assertEquals(d.getManager(), m);
    }

    @Test
    public void testGetDraftOrder() {
        Draft d = new Draft(new Manager("Timothy"), new Roster());
        Player p1 = new Player("Test Player1", Position.QB, 1.0);
        Player p2 = new Player("Test Player2", Position.RB, 2.0);

        d.addPlayer(p1);
        d.addPlayer(p2);

        Player[] dorder = d.getDraftOrder().toArray(new Player[0]);
        assertEquals(dorder.length, 2);
        assertEquals(dorder[0], p1);
        assertEquals(dorder[1], p2);
    }
}
