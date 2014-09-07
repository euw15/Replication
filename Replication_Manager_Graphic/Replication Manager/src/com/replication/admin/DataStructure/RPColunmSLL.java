/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataStructure;

/**
 *
 * @author Melvin
 */
public class RPColunmSLL {

    private RPColumna first;

    protected RPColunmSLL() {
        this.first = null;
    }

    public void insert(String columnName, String tipo, boolean isPK) {

        RPColumna newly = new RPColumna(columnName, tipo, isPK);

        if (first == null) {
            first = newly;
            first.succ = null;
        }
        for (RPColumna curr = first; curr != null; curr = curr.succ) {
            if (curr.succ == null) {
                curr.succ = newly;
                newly.succ = null;
            }
        }
    }

    void insert(RPColumna column) {

        if (first == null) {
            first = column;
            first.succ = null;
        }
        for (RPColumna curr = first; curr != null; curr = curr.succ) {
            if (curr.succ == null) {
                curr.succ = column;
                column.succ = null;
            }
        }
    }

    public void clear() {
        first = null;
    }

    void deletePosition(int position) {
        int i = 0;
        if (position == 0 & this.getNodes() == 0) {
            first = null;
        } else if (position == 0) {
            RPColumna aux = first.succ;
            first = aux;
        } else {
            position--;
            for (RPColumna curr = first; curr != null; curr = curr.succ, i++) {
                if (i == position) {
                    RPColumna aux = curr.succ;
                    curr.succ = aux.succ;
                }
            }
        }
    }

    private int getNodes() {

        int count = 0;
        for (RPColumna curr = first; curr != null; curr = curr.succ, count++) {
        }
        return count--;
    }

    private RPColumna getElement(int pos) {

        RPColumna aux = null;
        int i = 0;
        for (RPColumna curr = first; curr != null; curr = curr.succ, i++) {
            if (i == pos) {
                aux = curr;
                break;
            }
        }
        return aux;
    }

    boolean isEmpty() {
        return getNodes() == 0;
    }

}
