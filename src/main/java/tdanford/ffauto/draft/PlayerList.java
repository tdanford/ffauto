package tdanford.ffauto.draft;

import java.util.*;
import java.io.*;

public class PlayerList {

	private ArrayList<Player> players;
	private Roster roster;

	public PlayerList() throws IOException {
		
		File f = new File(ClassLoader.getSystemClassLoader().getResource("player-rankings.txt").getFile());
		File d = new File(ClassLoader.getSystemClassLoader().getResource("drafted.txt").getFile());
		
		roster = new Roster();
		
		Set<String> drafted = new TreeSet<String>();
		Set<String> rostered = new TreeSet<String>();
		
		String line;
		BufferedReader br;
		
		br = new BufferedReader(new FileReader(d));
		while((line = br.readLine()) != null) { 
			if((line = line.trim()).length() > 0) { 
				line = line.toUpperCase();
				if(line.startsWith("*")) { 
					line = line.substring(1, line.length()).trim();
					rostered.add(line);
				}
				drafted.add(line);
			}
		}
		
		br.close();
		
		players = new ArrayList<Player>();
		
		br = new BufferedReader(new FileReader(f));
		while((line = br.readLine()) != null) { 
			if((line = line.trim()).length() > 0) { 
				Player p = new Player(line);
				if(rostered.contains(p.name)) { 
					roster.players.get(p.position).add(p);
				}
				if(p.futureValue > 0.0 && !drafted.contains(p.name)) { 
					players.add(p);
				}
			}
		}
		
		br.close();

		System.out.println(players.size());
	}

    public Collection<Player> getPlayers() { return players; }

    public Roster getRoster() { return roster; }

    public Player[] rankPlayers(Comparator<Player> comp) {
		Player[] array = players.toArray(new Player[0]);
		Arrays.sort(array, comp);
		return array;
	}
}
