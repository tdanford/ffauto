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
        players.add(new Player("Tim Player1", Position.QB, 1.0));
        players.add(new Player("Tom Player2", Position.QB, 1.0));
        players.add(new Player("John Player3", Position.QB, 1.0));

        PlayerSelector sel = new PlayerSelector(players);

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 3);
        assertEquals(sel.getAvailablePlayer(0), "John Player3");

        sel.setText("T");

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 2);
        assertEquals(sel.getAvailablePlayer(0), "Tim Player1");

        sel.setText("to");

        assertEquals(sel.getNumTotalPlayers(), 3);
        assertEquals(sel.getNumAvailablePlayers(), 1);
        assertEquals(sel.getAvailablePlayer(0), "Tom Player2");
    }

    @Test
    public void testPlayerSelectionEvent() {
        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("ABC Player1", Position.QB, 1.0));
        players.add(new Player("DEF Player2", Position.QB, 1.0));

        PlayerSelector sel = new PlayerSelector(players);

        PlayerSelector.NameSelectionListener listener = mock(PlayerSelector.NameSelectionListener.class);
        PlayerSelector.NameSelectionListener listener2 = mock(PlayerSelector.NameSelectionListener.class);
        sel.addNameSelectionListener(listener);
        sel.addNameSelectionListener(listener2);

        assertEquals(sel.getAvailablePlayer(0), "ABC Player1");

        sel.setText("de");

        assertEquals(sel.getAvailablePlayer(0), "DEF Player2");

        sel.selectName();

        verify(listener).nameSelected("DEF Player2");
        verify(listener2).nameSelected("DEF Player2");

        sel.removeNameSelectionListener(listener);
        sel.setText("ab");
        sel.selectName();

        verifyNoMoreInteractions(listener);
        verify(listener2).nameSelected("ABC Player1");
    }
}
