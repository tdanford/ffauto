package tdanford.ffauto.draft.ui;

import tdanford.ffauto.draft.Player;
import tdanford.ffauto.draft.Position;

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

    private JTable table;
    private DefaultTableModel tableModel;

    public PositionView(Position position, Collection<Player> players) {
        super(new BorderLayout());

        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
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
    }


}
