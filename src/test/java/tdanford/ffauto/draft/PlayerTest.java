package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

import java.util.Map;
import java.util.TreeMap;

/**
 * User: tdanford
 */
public class PlayerTest {
	
	public static final double eps = 0.000001;

    @Test
    public void testAccessors() {
        Team team = new Team("TEST");
        Player p = new Player("Timothy Player", Position.DEF, team, 1.0);

        assertEquals(p.getName(), "Timothy Player");
        assertEquals(p.getPosition(), Position.DEF);
        assertEquals(p.getTeam(), team);
        assertEquals(p.getFutureValue(), 1.0);
    }
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testThrowsExceptionIfNullLine() { 
		new Player(null, null);
	}

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testThrowsExceptionIfNullTeams() {
        Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1", null);
    }

    @Test
	public void testNameUpperCase() {
        Map<String,Team> teams = new TreeMap<String,Team>();
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1", teams);

		assertEquals(p.name, "Foo D. Bar".toUpperCase());
        assertTrue(teams.containsKey("Hou"));
	}
	
	@Test
	public void testPositionParseRB() {
        Map<String,Team> teams = new TreeMap<String,Team>();
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1", teams);
		assertEquals(p.position, Position.RB);
        assertTrue(teams.containsKey("Hou"));
	}
	
	@Test
	public void testFutureValueParse() {
        Map<String,Team> teams = new TreeMap<String,Team>();
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1", teams);

		assertTrue(p.futureValue-5.1 <= eps, String.format("p.futureValue should be 5.1"));
	}
}
