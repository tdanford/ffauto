package tdanford.ffauto.draft;

import java.util.*;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class Schedule {

    private ArrayList<Team> teams;
    private Map<String,Week> weeks;

    public Schedule(Collection<Team> ts) {
        weeks = new TreeMap<String,Week>();
        teams = new ArrayList<Team>(new TreeSet<Team>(ts));
    }

    public Collection<String> getWeeks() { return weeks.keySet(); }

    public Collection<String> findByeWeeks(Team t) {
        Map<String,Team> matchups = matchups(t);
        ArrayList<String> byes = new ArrayList<String>();
        for(String week : matchups.keySet()) {
            if(matchups.get(week) == null) {
                byes.add(week);
            }
        }
        return byes;
    }

    public Map<String,Team> matchups(Team t) {
        Map<String,Team> matchups = new TreeMap<String,Team>();
        for(String week : weeks.keySet()) {
            matchups.put(week, weeks.get(week).matchup(t));
        }
        return matchups;
    }

    public void addMatchup(String week, Team t1, Team t2) {
        if(!weeks.containsKey(week)) {
            weeks.put(week, new Week());
        }
        weeks.get(week).addMatchup(t1, t2);
    }

    public static class Week {

        private Map<Team,Team> matchups;

        public Week() {
            matchups = new TreeMap<Team,Team>();
        }

        public Team matchup(Team t) {
            return matchups.get(t);
        }

        public Collection<Team> findByeTeams(Collection<Team> ts) {
            TreeSet<Team> byes = new TreeSet<Team>();
            for(Team t : ts) {
                if(!matchups.containsKey(t)) {
                    byes.add(t);
                }
            }

            return byes;
        }

        public void addMatchup(Team t1, Team t2) {
            matchups.put(t1, t2);
            matchups.put(t2, t1);
        }
    }
}
