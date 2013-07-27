package tdanford.ffauto.draft;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class Team implements Comparable<Team> {

    private String name;

    public Team(String n) {
        if(n == null || n.length() == 0) { throw new IllegalArgumentException(); }
        this.name = n;
    }

    public String getName() { return name; }

    public String toString() { return name; }

    public int hashCode() { return name.hashCode(); }

    public boolean equals(Object o) {
        if(!(o instanceof Team)) { return false; }
        Team t = (Team)o;
        return name.equals(t.name);
    }

    public int compareTo(Team t) {
        return name.compareTo(t.name);
    }
}
