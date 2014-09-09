/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.user.GraphicInterface;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Melvin
 */
public class CellPassword extends AbstractCellEditor implements TableCellEditor {

    private TableCellEditor current;

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {


        JPasswordField pass = new JPasswordField();
        pass.setText("holamundo");
        current = new DefaultCellEditor(pass);
        return pass;

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
