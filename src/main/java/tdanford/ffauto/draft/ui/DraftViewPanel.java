package tdanford.ffauto.draft.ui;

import tdanford.ffauto.draft.Draft;
import tdanford.ffauto.draft.DraftHistory;
import tdanford.ffauto.draft.Manager;
import tdanford.ffauto.draft.Player;

import java.net.URL;
import java.util.*;

import javax.swing.*;
import java.awt.*;

/**
 * A view of the draft order (among managers), along with an icon indicating
 * which manager is next to make a selection.
 *
 * User: tdanford
 * Date: 7/25/13
 */
public class DraftViewPanel extends JPanel {

    private Draft draft;
    private ArrayList<CurrentSelectionPanel> selections;

    public DraftViewPanel(Draft d) {
        URL currentIconURL = getClass().getClassLoader().getResource("arrow.png");
        Icon currentIcon = new ImageIcon(currentIconURL);
        this.draft = d;

        setLayout(new GridLayout(draft.size(), 2));
        selections = new ArrayList<CurrentSelectionPanel>();

        for(int i = 0; i < draft.size(); i++) {
            CurrentSelectionPanel selPanel = new CurrentSelectionPanel(currentIcon);
            selections.add(selPanel);
            add(selPanel);
            add(new JLabel(draft.getManager(i).getName()));
        }

        selections.get(draft.getNextIndex()).setSelected(true);
    }

    public DraftHistory getCurrentDraftHistory() {
        return draft.getDraftHistory(draft.getNextIndex());
    }

    public Manager getCurrentManager() {
        return draft.getNextManager();
    }

    public void draftPlayer(Player p) {
        selections.get(draft.getNextIndex()).setSelected(false);
        draft.draftPlayer(p);
        selections.get(draft.getNextIndex()).setSelected(true);
    }

    /**
     * A panel that displays either an icon, or nothing, depending on whether
     * it is "selected" or not.  There's one of these panels to the left of each
     * Manager's JLabel object in the GridLayout, above.
     */
    private static class CurrentSelectionPanel extends JPanel {

        private Icon icon;
        private boolean isSelected;

        public CurrentSelectionPanel(Icon i) {
            super(new BorderLayout());
            icon = i;
            setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
            isSelected = false;
        }

        public void setSelected(boolean value) {
            isSelected = value;
            repaint();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(isSelected) {
                int w = getWidth(), h = getHeight();
                double wscale = (double)w / (double)icon.getIconWidth();
                double hscale = (double)h / (double)icon.getIconHeight();
                double scale = Math.min(wscale, hscale);

                Graphics2D g2 = (Graphics2D)g;
                g2.scale(scale, scale);
                icon.paintIcon(this, g2, 0, 0);
            }
        }
    }

}
