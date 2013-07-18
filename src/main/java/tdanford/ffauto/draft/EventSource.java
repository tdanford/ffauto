package tdanford.ffauto.draft;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * User: tdanford
 * Date: 7/18/13
 */
public abstract class EventSource<Listener> {

    protected Class<Listener> listenerClass;
    protected ArrayList<Listener> listeners;

    public EventSource(Class<Listener> cls) {
        listenerClass = cls;
        listeners = new ArrayList<Listener>();
    }

    public void addListener(Listener listener) { listeners.add(listener); }
    public void removeListener(Listener listener) { listeners.remove(listener); }

    protected void fireEvent(String eventName, Object... args) {
        Class[] argTypes = new Class[args.length];
        for(int i = 0; i < argTypes.length; i++) { argTypes[i] = args[i].getClass(); }
        try {
            Method m = listenerClass.getMethod(eventName, argTypes);

            for(Listener listener : listeners) {
                m.invoke(listener, args);
            }

        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format("Can't find method %s in listener class %s with argument " +
                    "types %s", eventName, listenerClass.getCanonicalName(), classesToNames(argTypes)), e);

        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(String.format("Can't invoke method %s in listener class %s with argument " +
                    "types %s", eventName, listenerClass.getCanonicalName(), classesToNames(argTypes)), e);

        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException(String.format("Can't access method %s in listener class %s with argument " +
                    "types %s", eventName, listenerClass.getCanonicalName(), classesToNames(argTypes)), e);
        }
    }

    private static ArrayList<String> classesToNames(Class... clsList) {
        ArrayList<String> typeNames = new ArrayList<String>();
        for(Class cls : clsList) {
            typeNames.add(cls.getCanonicalName());
        }
        return typeNames;
    }
}
