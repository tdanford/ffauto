package tdanford.ffauto.draft.ui;

import tdanford.ffauto.draft.Player;
import tdanford.ffauto.draft.Position;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * User: tdanford
 * Date: 7/24/13
 */
public class DraftManager extends JFrame {

    PlayerSelector selector;
    Map<Position,PositionView> positionViews;

    public DraftManager(Collection<Player> players) {
        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container c = (Container)getContentPane();
        c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

        selector = new PlayerSelector(players);
        positionViews = new HashMap<Position,PositionView>();

        c.add(selector);
        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.X_AXIS));

        c.add(positionPanel);

        for(Position pos : Position.values()) {
            positionViews.put(pos, new PositionView(pos, players));
            positionPanel.add(positionViews.get(pos));
        }
    }

    public void makeVisible() {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    setVisible(true);
                    pack();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        } catch (InvocationTargetException e) {
            e.printStackTrace(System.err);
        }
    }
}
