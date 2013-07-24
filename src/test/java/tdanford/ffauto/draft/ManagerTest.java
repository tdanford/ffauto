package tdanford.ffauto.draft;

import static org.testng.Assert.*;
import org.testng.annotations.*;

/**
 * User: tdanford
 * Date: 7/24/13
 */
public class ManagerTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testManagerConstructorExceptOnNull() {
        new Manager(null);
    }

    @Test
    public void testEquality() {
        assertEquals(new Manager("Manager1"), new Manager("Manager1"));
        assertNotEquals(new Manager("Manager1"), new Manager("Manager2"));
        assertNotEquals(new Manager("Manager1"), new Manager("manager1"));
    }

    @Test
    public void testHashCode() {
        assertEquals(new Manager("Manager1").hashCode(), new Manager("Manager1").hashCode());
        assertNotEquals(new Manager("Manager1").hashCode(), new Manager("Manager2").hashCode());
    }

    @Test
    public void testToString() {
        assertEquals(new Manager("manager person").toString(), "manager person");
    }

    @Test
    public void testAccessor() {
        assertEquals(new Manager("manager").getName(), "manager");
    }
}
