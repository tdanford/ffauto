package tdanford.ffauto.draft;

import java.util.*;
import java.util.regex.*;

public class Player implements Comparable<Player> {
	
	public String name;
	public double futureValue;
	public Position position;
	
	private static Pattern pattern = Pattern.compile("^\\d+\\s+([^,]+),\\s+(\\w+)\\s+(\\w+)\\s(.+)$");

    public Player(String name, Position pos, double value) {
        this.name = name;
        this.futureValue = value;
        this.position = pos;
    }

	public Player(String line) { 
		if(line == null) { throw new IllegalArgumentException("Null line string"); }
		
		Matcher m = pattern.matcher(line);
		if(!m.matches()) { throw new IllegalArgumentException(line); }
		name = m.group(1).replaceAll("\t", " ").toUpperCase();
		position = Position.valueOf(m.group(3).toUpperCase());
		futureValue = Double.parseDouble(m.group(4));
	}

    public Double getFutureValue() { return futureValue; }
    public Position getPosition() { return position; }
    public String getName() { return name; }

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
