package com.replication.user.GraphicInterface;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

public class ComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    private TableCellEditor current;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("MYSQL");
        comboBox.addItem("SQL SERVER");
        comboBox.addItem("ORACLE DB");
        current = new DefaultCellEditor(comboBox);
        return comboBox;

    }

    @Override
    public Object getCellEditorValue() {
        return (current == null ? null : current.getCellEditorValue());
    }

    @Override
    public void cancelCellEditing() {
        if (current != null) {
            current.cancelCellEditing();
        }
    }

    @Override
    public boolean isCellEditable(EventObject evt) {
        if (evt instanceof MouseEvent) {
            return ((MouseEvent) evt).getClickCount() >= 1;
        }
        return true;
    }
}
