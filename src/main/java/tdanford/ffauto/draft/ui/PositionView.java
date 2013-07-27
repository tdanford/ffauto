package tdanford.ffauto.draft.ui;

import tdanford.ffauto.draft.Player;
import tdanford.ffauto.draft.Position;
import tdanford.ffauto.draft.utils.EventSource;

import java.util.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * User: tdanford
 * Date: 7/25/13
 */
public class PositionView extends JPanel {

    private EventSource<NameSelectionListener> listeners;

    private JTable table;
    private DefaultTableModel tableModel;

    public PositionView(Position position, Collection<Player> players) {
        super(new BorderLayout());

        tableModel = new NonEditableTableModel();
        table = new JTable(tableModel);
        listeners = new EventSource<NameSelectionListener>(NameSelectionListener.class);

        add(new JScrollPane(table));

        setBorder(new TitledBorder(position.toString()));

        tableModel.addColumn("Name");
        tableModel.addColumn("Value");
        for(Player p : players) {
            if(p.getPosition().equals(position)) {
                tableModel.addRow(new Object[] { p.getName(), p.getFutureValue() });
            }
        }

        setPreferredSize(new Dimension(200, 400));

        table.setShowHorizontalLines(true);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int x = evt.getX(), y = evt.getY();
                int row = table.rowAtPoint(new Point(x, y));
                listeners.fireEvent("nameSelected", (String)tableModel.getValueAt(row, 0));
            }
        });
    }

    public void addNameSelectionListener(NameSelectionListener listener) {
        listeners.addListener(listener);
    }

    public void removeNameSelectionListener(NameSelectionListener listener) {
        listeners.removeListener(listener);
    }

    private static class NonEditableTableModel extends DefaultTableModel {
        public NonEditableTableModel() {
            super();
        }

        public boolean isCellEditable(int row, int col) { return false; }
    }


}
