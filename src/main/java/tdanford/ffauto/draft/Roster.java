package tdanford.ffauto.draft;

import tdanford.ffauto.draft.events.RosterListener;
import tdanford.ffauto.sim.SeasonSimulator;

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

    /**
     * Calculates the value of the Roster over the course of an entire (simulated)
     * season.
     *
     * This works by calculating, for each week of the season, the 'best' set of
     * Starters for that week (given the Players' injury status), and then calculating
     * the score of that Starter set.
     *
     * @param sim The SeasonSimulator which holds the stats of an entire season; this simulation must be complete
     * @throws IllegalArgumentException if sim.hasNextWeek() == true, meaning that the simulation is incomplete
     * @return the total score, summed across all the Starters from this Roster in all the weeks of the season.
     */
    public double rosterScore(SeasonSimulator sim) {
        if(sim.hasNextWeek()) {
            throw new IllegalArgumentException("Roster value can only be calculated over 'completed' season simulations");
        }
        double totalValue = 0.0;
        for(int i = 0; i < sim.getNumWeeks(); i++) {
            String week = sim.getWeekName(i);
            Starters starters = sim.chooseStarters(this, week);
            double starterValue = sim.teamScore(starters, week);
            totalValue += starterValue;
        }

        return totalValue;
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
