package tdanford.ffauto.draft.utils;

import org.testng.annotations.*;
import static org.testng.Assert.*;

import static org.mockito.Mockito.*;

/**
 * User: tdanford
 * Date: 7/26/13
 */
public class EventSourceTest {

    @Test
    public void testMockListener() {
        EventSource<MockListener> source = new EventSource<MockListener>(MockListener.class);
        MockListener l1 = mock(MockListener.class);
        MockListener l2 = mock(MockListener.class);

        source.addListener(l1);
        source.addListener(l2);
        source.fireEvent("signalEvent", "foo");

        verify(l1).signalEvent("foo");
        verify(l2).signalEvent("foo");

        source.removeListener(l2);
        source.fireEvent("signalEvent", "bar");

        verify(l1).signalEvent("bar");
        verifyNoMoreInteractions(l2);

        source.removeListener(l1);
        source.fireEvent("signalEvent", "quux");

        verifyNoMoreInteractions(l1);
        verifyNoMoreInteractions(l2);
    }

    public static interface MockListener {
        public void signalEvent(String event);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testMockListenerExceptOnWrongMethod() {
        EventSource<MockListener> source = new EventSource<MockListener>(MockListener.class);
        MockListener l1 = mock(MockListener.class);
        source.addListener(l1);
        source.fireEvent("noSuchMethod", "foo");
    }
}
