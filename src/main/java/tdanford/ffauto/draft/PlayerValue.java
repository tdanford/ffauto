package tdanford.ffauto.draft;

import java.util.Comparator;

/**
 * User: tdanford
 * Date: 8/11/13
 */
public interface PlayerValue {

    public double value(Player p);

    public Comparator<Player> comparator();

    public static class Ranking implements Comparator<Player> {

        private PlayerValue evaluator;

        public Ranking(PlayerValue v) {
            evaluator = v;
        }

        public int compare(Player p1, Player p2) {
            double v1 = evaluator.value(p1), v2 = evaluator.value(p2);
            if(v1 > v2) { return -1; }
            if(v1 < v2) { return 1; }
            return 0;
        }
    }
}

class PlayerFutureValue implements PlayerValue {

    public double value(Player p) {
        return p.getFutureValue();
    }

    public Comparator<Player> comparator() { return new PlayerValue.Ranking(this); }
}
