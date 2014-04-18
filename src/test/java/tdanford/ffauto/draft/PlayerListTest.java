package tdanford.ffauto.draft;

import org.testng.annotations.Test;
import tdanford.ffauto.draft.PlayerList;
import tdanford.ffauto.draft.Schedule;
import tdanford.ffauto.draft.Team;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * User: tdanford
 * Date: 7/27/13
 */
public class PlayerListTest {

    @Test
    public void testPlayerList() throws IOException {
        PlayerList list = new PlayerList();
        assertEquals(list.getPlayers().size(), 1376);
    }
}
