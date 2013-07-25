package tdanford.ffauto.draft;

import java.util.*;

public class DraftHistory {

    private Manager manager;
	private Roster roster;
    private LinkedList<Player> draftList;
	
	public DraftHistory(Manager m, Roster r) {
        this.manager = m;
		roster = r;
        draftList = new LinkedList<Player>();
	}

    public Collection<Player> getDraftList() { return draftList; }
    public Manager getManager() { return manager; }
	
	public void addPlayer(Player p) { 
		roster.players.get(p.position).add(p);
        draftList.addLast(p);
	}
	
	public double futureValue(Roster r, Player p) {
		return r.factor(p) * p.futureValue; 
	}
	
	public double score(Player p, Collection<Player> total, int nextDrafts) { 
		Roster r = new Roster(roster, p);
		
		TreeSet<Player> remaining = new TreeSet<Player>(total);
		remaining.remove(p);
		Player[] ordered = remaining.toArray(new Player[0]);
		Player replacement = ordered[nextDrafts];
		
		return futureValue(r, p) - futureValue(r, replacement);
	}
	
	public Comparator<Player> getComparator(final int nextDrafts, final Collection<Player> board) {  
		return new Comparator<Player>() { 
			public int compare(Player p1, Player p2) { 
				double s1 = score(p1, board, nextDrafts);
				double s2 = score(p2, board, nextDrafts);
				if(s1 > s2) { return -1; }
				if(s1 < s2) { return 1; }
				return p1.compareTo(p2);
			}
		};
	}
}
