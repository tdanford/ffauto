package tdanford.ffauto.draft;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class Draft {

    private int next;
    private ArrayList<Manager> draftOrder;
    private LinkedHashMap<Manager,DraftHistory> draftHistories;

    public Draft(Manager... ms) {
        next = 0;
        draftOrder = new ArrayList<Manager>();
        draftHistories = new LinkedHashMap<Manager,DraftHistory>();
        for(Manager m : ms) {
            draftOrder.add(m);
            draftHistories.put(m, new DraftHistory(m));
        }
    }

    public int size() { return draftOrder.size(); }

    public int getNextIndex() { return next; }

    public Manager getNextManager() { return draftOrder.get(next); }

    public Manager getManager(int i) { return draftOrder.get(i); }

    public DraftHistory getDraftHistory(int i) { return draftHistories.get(draftOrder.get(i)); }

    public void draftPlayer(Player p) {
        draftHistories.get(getNextManager()).addPlayer(p);
        next = (next+1) % draftOrder.size();
    }

}
