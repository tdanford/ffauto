package tdanford.ffauto.draft.ui;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import tdanford.ffauto.draft.*;

/**
 * A UI panel which allows for interactive player selections.
 *
 * User: tdanford
 * Date: 7/13/13
 */
public class PlayerSelector extends JPanel {

    public static void main(String[] args) {
        final JFrame frame = new JFrame("Test Frame");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = frame.getContentPane();
        c.setLayout(new BorderLayout());

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("Timothy Danford", Position.DEF, 0.0));
        players.add(new Player("Tim Johnson", Position.K, 0.0));
        players.add(new Player("Peter Johnson", Position.K, 0.0));

        PlayerSelector selector = new PlayerSelector(players);

        c.add(selector, BorderLayout.CENTER);
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    frame.setVisible(true);
                    frame.pack();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private String currentRestriction;
    private Map<String,Player> players;
    private NameListModel listModel;
    private JList playerList;
    private JTextArea nameArea;

    private ArrayList<NameSelectionListener> nameListeners;

    public PlayerSelector(Collection<Player> ps) {
        super();

        /*
        Setup internal data structures
         */
        players = new TreeMap<String,Player>();
        for(Player p : ps) {
            players.put(p.getName(), p);
        }
        currentRestriction = "";
        nameListeners = new ArrayList<NameSelectionListener>();

        /*
        Setup Swing layout UI stuff.
         */
        setLayout(new BorderLayout());

        listModel = new NameListModel();
        playerList = new JList(listModel);
        nameArea = new JTextArea();

        nameArea.setColumns(50);

        add(nameArea, BorderLayout.NORTH);
        add(new JScrollPane(playerList), BorderLayout.CENTER);

        nameArea.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(keyEvent.getKeyChar() == '\n') {  // On 'return', select the top name in the box.
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            selectName();
                        }});
                    keyEvent.consume(); // consume the event, so that we don't put a newline in the text area.
                }
            }

            @Override
            public void keyTyped(KeyEvent keyEvent) {
                SwingUtilities.invokeLater(new Runnable() { public void run() { updateNames(); } });
            }
        });
    }

    public void setText(final String name) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    // We can't interact with the JTextArea except in the Swing thread...
                    nameArea.setText(name);
                }
            });

            // We don't update the selected names, unless we were able to successfully
            // update the JTextArea text field (i.e. an exception wasn't thrown).
            updateNames();

        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        } catch (InvocationTargetException e) {
            e.printStackTrace(System.err);
        }
    }

    public void selectName() {
        String name = listModel.getTopName();
        System.out.println(String.format("SELECTED: %s", name));
        for(NameSelectionListener listener : nameListeners) {
            listener.nameSelected(name);
        }
    }

    public void updateNames() {
        listModel.update();
    }

    public int getNumTotalPlayers() { return players.size(); }
    public int getNumAvailablePlayers() { return listModel.getSize(); }
    public String getAvailablePlayer(int i) { return (String)listModel.getElementAt(i); }

    public void addNameSelectionListener(NameSelectionListener listener) {
        nameListeners.add(listener);
    }

    public void removeNameSelectionListener(NameSelectionListener listener) {
        nameListeners.remove(listener);
    }

    /**
     * Models the list of remaining names, and implements (case-insensitive) name prefix
     * searching.
     */
    public class NameListModel implements ListModel {

        private ArrayList<String> names;
        private ArrayList<ListDataListener> listeners;

        public NameListModel() {
            names = new ArrayList<String>();
            listeners = new ArrayList<ListDataListener>();
            for(String name : players.keySet()) {
                names.add(name);
            }
        }

        public String getTopName() { return names.get(0); }

        public void update() {
            update(nameArea.getText());
        }

        public void update(String text) {
            System.out.println(String.format("updating: \"%s\"", text));
            ListDataEvent removed = new ListDataEvent(NameListModel.this, ListDataEvent.CONTENTS_CHANGED, 0, names.size());
            names.clear();
            fireEvent(removed);

            for(String name : players.keySet()) {
                if(name.startsWith(text) || name.toLowerCase().startsWith(text)) {
                    names.add(name);
                }
            }

            ListDataEvent added = new ListDataEvent(NameListModel.this, ListDataEvent.CONTENTS_CHANGED, 0, names.size());
            fireEvent(added);
        }

        private void fireEvent(ListDataEvent evt) {
            for(ListDataListener listener : listeners) {
                switch(evt.getType()) {
                    case ListDataEvent.CONTENTS_CHANGED:
                        listener.contentsChanged(evt);
                        break;
                    case ListDataEvent.INTERVAL_ADDED:
                        listener.intervalAdded(evt);
                        break;
                    case ListDataEvent.INTERVAL_REMOVED:
                        listener.intervalRemoved(evt);
                        break;
                }
            }
        }

        @Override
        public int getSize() {
            return names.size();
        }

        @Override
        public Object getElementAt(int i) {
            return names.get(i);
        }

        @Override
        public void addListDataListener(ListDataListener ldl) {
            listeners.add(ldl);
        }

        @Override
        public void removeListDataListener(ListDataListener ldl) {
            listeners.remove(ldl);
        }
    }

    public static interface NameSelectionListener {
        public void nameSelected(String name);
    }
}
