package tdanford.ffauto.draft;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class PlayerTest {
	
	public static final double eps = 0.000001;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testThrowsExceptionIfNullLine() { 
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Null line string");
		new Player(null);
	}
	
	@Test
	public void testNameUpperCase() { 
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1");
		assertEquals(p.name, "Foo D. Bar".toUpperCase());
	}
	
	@Test
	public void testPositionParseRB() { 
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1");
		assertEquals(p.position, Position.RB);
	}
	
	@Test
	public void testFutureValueParse() { 
		Player p = new Player("1\tFoo D. Bar, Hou RB\t5.1");
		assertTrue(String.format("p.futureValue should be 5.1"), p.futureValue-5.1 <= eps);
	}
}
