package tdanford.ffauto.draft;

import java.util.ArrayList;

/**
 * Manager represents a player in the fantasy football league,
 * who has a draft, manages a Roster, etc.
 *
 * User: tdanford
 * Date: 7/24/13
 */
public class Manager {

    private String name;

    public Manager(String n) {
        if(n == null) { throw new IllegalArgumentException("null name"); }
        this.name = n;
    }

    public String getName() { return name; }

    public String toString() { return name; }

    public int hashCode() { return name.hashCode(); }

    public boolean equals(Object o) {
        if(!(o instanceof Manager)) { return false; }
        Manager m = (Manager)o;
        return m.name.equals(name);
    }
}
