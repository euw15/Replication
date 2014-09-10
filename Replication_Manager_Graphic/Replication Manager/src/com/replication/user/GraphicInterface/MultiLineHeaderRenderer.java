package com.replication.user.GraphicInterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class MultiLineHeaderRenderer extends JPanel implements TableCellRenderer {

    public MultiLineHeaderRenderer() {

        Color color = new Color(76, 0, 153);
        setBackground(color);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label;
       
        removeAll();
        String[] header = ((String) value).split("\n");
        setLayout(new GridLayout(header.length, 1));
        for (String s : header) {
            
            label = new JLabel(s, JLabel.CENTER);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Khmer UI", Font.PLAIN, 14));
            label.setBorder(null);
          
            add(label);
        }
        return this;
    }
}
