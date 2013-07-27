package tdanford.ffauto.draft.ui;

import java.util.*;

import tdanford.ffauto.draft.*;

import org.testng.annotations.*;
import static org.testng.Assert.*;

import static org.mockito.Mockito.*;

/**
 * User: tdanford
 * Date: 7/17/13
 */
public class PlayerSelectorTest {

    @Test
    public void testPlayerSelectorNameSelection() {
        ArrayList<Player> players = new ArrayList<Player>();
        Player p1 = new Player("Tim Player1", Position.QB, 1.0);
        Player p2 = new Player("Tom Player2", Position.QB, 1.0);
        Player p3 = new Player("John Player3", Position.QB, 1.0);
        players.add(p1);
        players.add(p2);
        players.add(p3);

        PlayerSelector sel = new PlayerSelector(players);

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 3);
        assertEquals(sel.getAvailablePlayer(0), p3);

        sel.setText("T");

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 2);
        assertEquals(sel.getAvailablePlayer(0), p1);

        sel.setText("to");

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 1);
        assertEquals(sel.getAvailablePlayer(0), p2);
    }

    @Test
    public void testPlayerSelectionEvent() {
        ArrayList<Player> players = new ArrayList<Player>();
        Player p1 = new Player("ABC Player1", Position.QB, 1.0);
        Player p2 = new Player("DEF Player2", Position.QB, 1.0);
        players.add(p1);
        players.add(p2);

        PlayerSelector sel = new PlayerSelector(players);

        NameSelectionListener listener = mock(NameSelectionListener.class);
        NameSelectionListener listener2 = mock(NameSelectionListener.class);
        sel.addNameSelectionListener(listener);
        sel.addNameSelectionListener(listener2);

        assertEquals(sel.getAvailablePlayer(0), p1);

        sel.setText("de");

        assertEquals(sel.getAvailablePlayer(0), p2);

        sel.selectName();

        verify(listener).nameSelected(p2);
        verify(listener2).nameSelected(p2);

        sel.removeNameSelectionListener(listener);
        sel.setText("ab");
        sel.selectName();

        verifyNoMoreInteractions(listener);
        verify(listener2).nameSelected(p1);
    }
}
