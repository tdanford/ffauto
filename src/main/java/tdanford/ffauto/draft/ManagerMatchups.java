package tdanford.ffauto.draft;

import java.util.*;

/**
 * ManagerMatchups tracks the head-to-head matching of the managers
 * in each round of the fantasy football league, which will be useful
 * in assessing the future value of each draft class.
 *
 * User: tdanford
 * Date: 8/8/13
 */
public class ManagerMatchups {

    public ArrayList<Round> rounds;

    public ManagerMatchups() {
        rounds = new ArrayList<Round>();
    }

    public void addMatchup(int round, Manager m1, Manager m2) {
        while(round >= rounds.size()) { rounds.add(new Round()); }
        rounds.get(round).addMatchup(m1, m2);
    }

    public Round getRound(int i) {
        return rounds.get(i);
    }

    public int getNumRounds() { return rounds.size(); }

    public static class Round {

        private Map<Manager, Manager> matchups;

        public Round() {
            matchups = new HashMap<Manager,Manager>();
        }

        /**
         * Adds a matchup between managers to the given round.  All matchups are
         * symmetric, so after invoking this method, both of the following constraints
         * will be true:
         *   getMatchup(m1).equals(m2)
         *   getMatchup(m2).equals(m1)
         *
         * @param m1
         * @param m2
         * @throws IllegalArgumentException if either m1 or m2 have been matched with other,
         * different managers already.
         */
        public void addMatchup(Manager m1, Manager m2) {
            if(matchups.containsKey(m1)) {
                if(!matchups.get(m1).equals(m2)) {
                    throw new IllegalArgumentException("Already have a matchup for manager m1");
                }
            }

            if(matchups.containsKey(m2)) {
                if(!matchups.get(m2).equals(m1)) {
                    throw new IllegalArgumentException("Already have a matchup for manager m2");
                }
            }

            matchups.put(m1, m2);
            matchups.put(m2, m1);
        }

        public Manager getMatchup(Manager m) { return matchups.get(m); }
    }
}
