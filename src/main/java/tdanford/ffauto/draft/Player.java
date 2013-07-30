package tdanford.ffauto.draft;

import java.util.*;
import java.util.regex.*;

public class Player implements Comparable<Player> {
	
	public String name;
	public double futureValue;
	public Position position;
    private Team team;
	
	private static Pattern pattern = Pattern.compile(
            "^\\d+" +           // rank
            "\\s+([^,]+)," +    // player's name
            "\\s+(\\w+)" +      // team
            "\\s+(\\w+)" +      // position
            "\\s(.+)$");        // value

    public Player(String name, Position pos, Team team, double value) {
        this.name = name;
        this.futureValue = value;
        this.position = pos;
        this.team = team;
    }

	public Player(String line, Map<String,Team> teams) {
		if(line == null) { throw new IllegalArgumentException("Null line string"); }
        if(teams == null) { throw new IllegalArgumentException("Null teams map"); }
		
		Matcher m = pattern.matcher(line);
		if(!m.matches()) { throw new IllegalArgumentException(line); }
		name = m.group(1).replaceAll("\t", " ").toUpperCase();
        String teamName = m.group(2);
		position = Position.valueOf(m.group(3).toUpperCase());
		futureValue = Double.parseDouble(m.group(4));

        if(!teams.containsKey(teamName)) {
            teams.put(teamName, new Team(teamName));
        }
        team = teams.get(teamName);
	}

    public Double getFutureValue() { return futureValue; }
    public Position getPosition() { return position; }
    public String getName() { return name; }
    public Team getTeam() { return team; }

    public int compareTo(Player p){
		if(futureValue > p.futureValue) { return -1; }
		if(futureValue < p.futureValue) { return 1; }
		return name.compareTo(p.name);
	}
	
	public int hashCode() { return name.hashCode(); }

    public boolean equals(Object o) {
		if(!(o instanceof Player)) { return false; }
		Player p = (Player)o;
		return p.name.equals(name);
	}
	
	public String toString() { 
		return String.format("%s %.1f %s", position.toString(), futureValue, name);
	}


}
