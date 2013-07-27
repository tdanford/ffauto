package tdanford.ffauto.draft;

import java.util.*;
import java.io.*;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class Schedule {

    private Map<String,Team> teams;
    private Map<String,Week> weeks;

    public Schedule(Reader r) throws IOException {
        BufferedReader br = new BufferedReader(r);
        String[] header = null;

        teams = new TreeMap<String,Team>();
        weeks = new TreeMap<String,Week>();

        Map<String,Team> teamsByName = new TreeMap<String,Team>();

        try {
            String line = null;
            while((line = br.readLine()) != null) {
                if(!line.startsWith("#")) {
                    String[] array = line.split("\\s+");
                    if(header == null) {
                        header = array;
                        for(int i = 1; i < header.length; i++) {
                            String weekName = header[i];
                            weeks.put(weekName, new Week());
                        }

                    } else {

                        String teamName = array[0];
                        Team team = getTeam(teamName);

                        for(int i = 1; i < array.length; i++) {
                            String atTeamName = array[i];
                            boolean awayGame = atTeamName.startsWith("@");
                            String otherTeamName = awayGame ?
                                    atTeamName.substring(1) : atTeamName;

                            Team otherTeam = getTeam(otherTeamName);

                            if(awayGame) {
                                addMatchup(header[i], otherTeam, team);
                            } else {
                                addMatchup(header[i], team, otherTeam);
                            }
                        }
                    }
                }
            }
        } finally {
            br.close();
        }
    }

    public Schedule(Collection<Team> ts) {
        weeks = new TreeMap<String,Week>();
        teams = new TreeMap<String,Team>();
        for(Team t : ts) { teams.put(t.getName(), t); }
    }

    public Collection<String> getWeeks() { return weeks.keySet(); }

    public boolean hasTeam(Team t) { return teams.containsKey(t.getName()); }

    public Team getTeam(String teamName) {
        if(!teams.containsKey(teamName)) { teams.put(teamName, new Team(teamName)); }
        return teams.get(teamName);
    }

    public Team getMatchup(Team t, String week) {
        return weeks.get(week).matchup(t);
    }

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
