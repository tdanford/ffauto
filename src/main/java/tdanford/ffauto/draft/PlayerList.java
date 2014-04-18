package tdanford.ffauto.draft;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerList {



    private Map<String,Team> teams;
	private ArrayList<Player> players;
	private Roster roster;

	public PlayerList() throws IOException {
		
		File f = new File(ClassLoader.getSystemClassLoader().getResource("pr2013c.txt").getFile());
		//File d = new File(ClassLoader.getSystemClassLoader().getResource("drafted.txt").getFile());


		
		roster = new Roster();
		
		Set<String> drafted = new TreeSet<String>();
		Set<String> rostered = new TreeSet<String>();
		
		String line;
		BufferedReader br;

        /*
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
		*/
		
		players = new ArrayList<Player>();
        teams = new TreeMap<String,Team>();

        Pattern pattern2013 = Pattern.compile("([A-Za-z0-9'.\\-\\s]+\\*?,)\\s+([A-Za-z]+)\\s+([A-Za-z]+)\\s*(?:[OPQSPDIR]+\\s+)?(-?\\d+)");
		
		br = new BufferedReader(new FileReader(f));
		while((line = br.readLine()) != null) { 
			if((line = line.trim()).length() > 0) {

                Matcher m = pattern2013.matcher(line);
                if(!m.matches()) { throw new IllegalArgumentException(String.format("Can't match player line \"%s\"", line)); }

                String name = m.group(1), position = m.group(3), teamName = m.group(2);
                Integer value = Integer.parseInt(m.group(4));
                if(name.endsWith(",")) { name = name.substring(0, name.length()-1); }
                if(name.endsWith("*")) { name = name.substring(0, name.length()-1); }
                Position pos = position.equals("DEF") ? Position.DEF : Position.valueOf(position);

                if(!teams.containsKey(teamName)) { teams.put(teamName, new Team(teamName)); }
                Team team = teams.get(teamName);

				Player p = new Player(name, pos, team, (double)value);

				if(rostered.contains(p.name)) { 
					roster.players.get(p.position).add(p);
				}

				if(!drafted.contains(p.name)) {
					players.add(p);
				}
			}
		}
		
		br.close();

		System.out.println(players.size());
	}

    public Collection<Player> getPlayers() { return players; }

    public Collection<Team> getTeams() { return teams.values(); }

    public Roster getRoster() { return roster; }

    public Player[] rankPlayers(Comparator<Player> comp) {
		Player[] array = players.toArray(new Player[0]);
		Arrays.sort(array, comp);
		return array;
	}
}
