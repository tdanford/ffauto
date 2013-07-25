package tdanford.ffauto.draft;

import tdanford.ffauto.draft.ui.DraftManager;

import java.io.IOException;
import java.util.Comparator;

/**
 * User: tdanford
 * Date: 7/24/13
 */
public class Main {
    public static void main(String[] args) throws IOException {
        /*
        PlayerList pl = new PlayerList();
        int nextDraft = pl.getRoster().size() % 2 == 0 ? 6 : 16;
        Draft d = new Draft(pl.getRoster());
        Comparator<Player> c = d.getComparator(nextDraft, pl.getPlayers());
        Player[] parray = pl.rankPlayers(c);
        for(int i = 0; i < parray.length && i < 10; i++) {
            System.out.println(parray[i]);
        }
        */

        PlayerList pl = new PlayerList();
        DraftManager manager = new DraftManager(pl.getPlayers());
        manager.makeVisible();
    }
}
