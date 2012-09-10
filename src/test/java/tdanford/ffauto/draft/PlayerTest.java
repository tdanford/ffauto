package tdanford.ffauto.draft;

import org.junit.*;
import org.junit.rules.ExpectedException;

public class PlayerTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Test
	public void testThrowsExceptionIfNullLine() { 
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("Null line string");
		new Player(null);
	}
}
