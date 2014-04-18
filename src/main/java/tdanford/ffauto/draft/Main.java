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
        DraftHistory d = new DraftHistory(pl.getRoster());
        Comparator<Player> c = d.getComparator(nextDraft, pl.getPlayers());
        Player[] parray = pl.rankPlayers(c);
        for(int i = 0; i < parray.length && i < 10; i++) {
            System.out.println(parray[i]);
        }
        */

        PlayerList pl = new PlayerList();

        Manager[] managers = new Manager[] {
                new Manager("Mojo Risin'"),
                new Manager("Ned Stark"),
                new Manager("Madison"),
                new Manager("A Man for All Seasons"),
                new Manager("Gipsy Dancer"),
                new Manager("Kat"),
                new Manager("Timothy"),
                new Manager("Lucy"),
                new Manager("Robert"),
                new Manager("The Scout Crows")
        };

        Draft d = new Draft(managers);

        DraftManager manager = new DraftManager(d, pl.getPlayers());
        manager.makeVisible();
    }
}
