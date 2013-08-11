package tdanford.ffauto.draft;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * User: tdanford
 * Date: 7/26/13
 */
public class SlotTest {

    @Test
    public void testEquality() {
        Slot qb1 = new PositionSlot(Position.QB);
        Slot qb2 = new PositionSlot(Position.QB);
        Slot rb = new PositionSlot(Position.RB);
        Slot flex = new FlexSlot();
        Slot rbwr = new RbWrSlot();

        assertEquals(qb1, qb2);
        assertNotEquals(qb1, rb);
        assertNotEquals(rb, flex);
        assertNotEquals(rb, rbwr);
        assertNotEquals(flex, rbwr);
    }

    @Test
    public void testHashCodes() {
        Slot qb1 = new PositionSlot(Position.QB);
        Slot qb2 = new PositionSlot(Position.QB);
        Slot rb = new PositionSlot(Position.RB);
        Slot flex = new FlexSlot();
        Slot rbwr = new RbWrSlot();

        assertEquals(qb1.hashCode(), qb2.hashCode());
        assertNotEquals(qb1.hashCode(), rb.hashCode());
        assertNotEquals(rb.hashCode(), flex.hashCode());
        assertNotEquals(rb.hashCode(), rbwr.hashCode());
        assertNotEquals(flex.hashCode(), rbwr.hashCode());
    }
}
