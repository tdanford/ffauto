package tdanford.ffauto.draft.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class EventSource<Listener> {

    private Class<Listener> cls;
    private ArrayList<Listener> listeners;

    public EventSource(Class<Listener> c) {
        this.cls = c;
        this.listeners = new ArrayList<Listener>();
    }

    public void addListener(Listener l) {
        synchronized(listeners) {
            listeners.add(l);
        }
    }

    public void removeListener(Listener l) {
        synchronized(listeners) {
            listeners.remove(l);
        }
    }

    public void fireEventInThread(String methodName, Object... args) {
        Thread t = new Thread(new FireEventRunnable(methodName, args));
        t.start();
    }

    private class FireEventRunnable implements Runnable {
        private String methodName;
        private Object[] args;
        public FireEventRunnable(String methodName, Object... args) {
            this.methodName = methodName;
            this.args = args;
        }
        public void run() {
            fireEvent(methodName, args);
        }
    }

    public void fireEvent(String methodName, Object... args) {
        try {
            Method m = cls.getMethod(methodName, argTypes(args));
            ArrayList<Listener> toFire = new ArrayList<Listener>();
            synchronized(listeners) {
                toFire.addAll(listeners);
            }
            for(Listener listener : toFire) {
                m.invoke(listener, args);
            }

        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Unknown method %s in class %s", methodName, cls.getSimpleName()), e);

        } catch (InvocationTargetException e) {
            e.printStackTrace(System.err);
        } catch (IllegalAccessException e) {
            e.printStackTrace(System.err);
        }
    }

    public static Class[] argTypes(Object... args) {
        Class[] types = new Class[args.length];
        for(int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }
        return types;
    }

}
