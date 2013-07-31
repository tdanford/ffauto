package tdanford.ffauto.draft;

import tdanford.ffauto.draft.events.RosterListener;

import java.util.*;

public class Roster extends EventSource<RosterListener> {
	
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
        super(RosterListener.class);
		players = new HashMap<Position,Set<Player>>();
		for(Position p : Position.values()) { 
			players.put(p, new TreeSet<Player>());
		}
		injuryFactor = 0.25;
	}
	
	public Roster(Roster r, Player p) {
        super(RosterListener.class);
		players = new HashMap<Position,Set<Player>>();
		for(Position pos : r.players.keySet()) { 
			players.put(pos, new TreeSet<Player>(r.players.get(pos)));
		}
		injuryFactor = r.injuryFactor;
		players.get(p.position).add(p);
	}

    public Collection<Player> getPlayers() {
        ArrayList<Player> ps = new ArrayList<Player>();
        for(Position p : players.keySet()) {
            ps.addAll(players.get(p));
        }
        return ps;
    }

    public void addPlayer(Player p) {
        if(!players.get(p.getPosition()).contains(p)) {
            players.get(p.getPosition()).add(p);
            fireEvent("playerAdded", this, p);
        }
    }

    public void removePlayer(Player p) {
        if(players.get(p.getPosition()).contains(p)) {
            players.get(p.getPosition()).remove(p);
            fireEvent("playerRemoved", this, p);
        }
    }

    public int size() {
        int s = 0;
        for(Position p : players.keySet()) {
            s += players.get(p).size();
        }
        return s;
    }

    public double value() {
        double sum = 0.0;

        for(Position pos : players.keySet()) {
            double factor = 1.0;
            int depth = 0;

            for(Player p : players.get(pos)) {
                sum += (factor * p.getFutureValue());
                depth += 1;

                // This isn't correct, but I need to do something simple for now.
                // Really, we need a substitution model, in order to understand
                // how likely a player is to be played, given the possibility of injuries
                // in front of him.
                factor *= injuryFactor;
            }
        }

        return sum;
    }

    public double factor(Player p) {
		int rem = remaining(p.getPosition());
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
