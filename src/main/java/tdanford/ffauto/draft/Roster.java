package tdanford.ffauto.draft;

import java.util.*;

public class Roster {
	
	public static Map<Position,Integer> startingSpots;
	
	static { 
		startingSpots = new HashMap<Position,Integer>();
		startingSpots.put(Position.QB, 1);
		startingSpots.put(Position.RB, 2);
		startingSpots.put(Position.WR, 2);
		startingSpots.put(Position.TE, 1);
		startingSpots.put(Position.DEF, 1);
		startingSpots.put(Position.K, 1);
	}

	public Map<Position,Set<Player>> players;
	public double injuryFactor;
	
	public Roster() { 
		players = new HashMap<Position,Set<Player>>();
		for(Position p : Position.values()) { 
			players.put(p, new TreeSet<Player>());
		}
		injuryFactor = 0.25;
	}
	
	public int size() { 
		int s = 0; 
		for(Position p : players.keySet()) { 
			s += players.get(p).size();
		}
		return s;
	}
	
	public Roster(Roster r, Player p) { 
		players = new HashMap<Position,Set<Player>>();
		for(Position pos : r.players.keySet()) { 
			players.put(pos, new TreeSet<Player>(r.players.get(pos)));
		}
		injuryFactor = r.injuryFactor;
		players.get(p.position).add(p);
	}
	
	public double factor(Player p) { 
		int rem = remaining(p.position);
		double factor = 1.0;
		if(rem <= 0) { 
			factor = Math.pow(injuryFactor, 1-rem);
		}
		return factor;
	}
	
	public int remaining(Position p) { 
		if(p.equals(Position.RB) || p.equals(Position.WR) || p.equals(Position.TE)) { 
			int starts = players.get(Position.RB).size() + players.get(Position.WR).size() + players.get(Position.TE).size();
			return 6 - starts;
		} else { 
			return startingSpots.get(p) - players.get(p).size();
		}
	}
	
}
