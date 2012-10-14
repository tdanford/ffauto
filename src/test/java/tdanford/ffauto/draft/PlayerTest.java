package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

public class PlayerTest {
	
	public static final double eps = 0.000001;
	
	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testThrowsExceptionIfNullLine() { 
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
		assertTrue(p.futureValue-5.1 <= eps, String.format("p.futureValue should be 5.1"));
	}
}
