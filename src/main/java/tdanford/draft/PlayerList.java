package tdanford.draft;

import java.util.*;
import java.io.*;

public class PlayerList {
	
	public static void main(String[] args) throws IOException { 
		PlayerList pl = new PlayerList();
		int nextDraft = pl.roster.size() % 2 == 0 ? 6 : 16;
		Draft d = new Draft(pl.roster);
		Comparator<Player> c = d.getComparator(nextDraft, pl.players);
		Player[] parray = pl.players(c);
		for(int i = 0; i < parray.length && i < 10; i++) { 
			System.out.println(parray[i]);
		}
	}
	
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
	
	public Player[] players(Comparator<Player> comp) { 
		Player[] array = players.toArray(new Player[0]);
		Arrays.sort(array, comp);
		return array;
	}
}
