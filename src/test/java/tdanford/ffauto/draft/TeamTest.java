package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

/**
 * User: tdanford
 * Date: 7/26/13
 */
public class TeamTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testExceptionOnNullName() {
        new Team(null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testExceptionOnZeroLengthName() {
        new Team("");
    }

    @Test
    public void testTeamName() {
        Team t = new Team("Hou");
        assertEquals(t.getName(), "Hou");
    }

    @Test
    public void testTeamEquality() {
        Team t1 = new Team("Hou");
        Team t2 = new Team("Hou");
        Team t3 = new Team("NE");

        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
    }

    @Test
    public void testComparison() {
        Team t1 = new Team("Hou");
        Team t2 = new Team("ABC");
        Team t3 = new Team("ZYX");

        assertTrue(t1.compareTo(t2) > 0);
        assertTrue(t1.compareTo(t3) < 0);
        assertTrue(t2.compareTo(t3) < 0);
    }

    @Test
    public void testToString() {
        assertEquals(new Team("Hou").toString(), "Hou");
    }

    @Test
    public void testHashCode() {
        Team t1 = new Team("Hou");
        Team t2 = new Team("Hou");
        Team t3 = new Team("NE");

        assertEquals(t1.hashCode(), t2.hashCode());
        assertNotEquals(t1.hashCode(), t3.hashCode());
    }

}
