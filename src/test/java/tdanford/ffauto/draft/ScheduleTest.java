package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * User: tdanford
 * Date: 7/27/13
 */
public class ScheduleTest {

    @Test
    public void parse2012Schedule() throws IOException {
        Reader r = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("schedule-2012.txt"),
                "UTF-8");
        Schedule s = new Schedule(r);

        Team ari = new Team("ARI");
        Team sea = new Team("SEA");
        Team sf = new Team("SF");

        assertTrue(s.hasTeam(ari));
        assertTrue(s.hasTeam(sea));
        assertTrue(s.hasTeam(sf));

        assertEquals(s.getMatchup(ari, "1"), sea);
        assertEquals(s.getMatchup(sea, "1"), ari);

        assertEquals(s.getMatchup(ari, "17"), sf);
        assertEquals(s.getMatchup(sf, "17"), ari);
    }
}
