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
public class RPColumnSLL {

    private RPColumn first;

    public RPColumnSLL() {
        this.first = null;
    }

    public void insert(String columnName, String tipo, String _null, String _key, String _default, String extra) {

        if (!isOnList(columnName)) {

            RPColumn newly = new RPColumn(columnName, tipo, _null, _key, _default, extra);

            if (first == null) {
                first = newly;
                first.succ = null;
            }
            for (RPColumn curr = first; curr != null; curr = curr.succ) {
                if (curr.succ == null) {
                    curr.succ = newly;
                    newly.succ = null;
                }
            }
        }
    }

    void insert(RPColumn column) {

        if (first == null) {
            first = column;
            first.succ = null;
        }
        for (RPColumn curr = first; curr != null; curr = curr.succ) {
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
            RPColumn aux = first.succ;
            first = aux;
        } else {
            position--;
            for (RPColumn curr = first; curr != null; curr = curr.succ, i++) {
                if (i == position) {
                    RPColumn aux = curr.succ;
                    curr.succ = aux.succ;
                }
            }
        }
    }

    private int getNodes() {

        int count = 0;
        for (RPColumn curr = first; curr != null; curr = curr.succ, count++) {
        }
        return count--;
    }

    private boolean isOnList(String column) {

        for (RPColumn curr = first; curr != null; curr = curr.succ) {
            if (curr._column_name.equals(column)) {
                return true;
            }
        }
        return false;
    }

    public void printColums() {
        for (RPColumn curr = first; curr != null; curr = curr.succ) {
            System.out.println(curr._column_name + " " + curr._default + " " + curr._extra + " " + curr._key + " " + curr._isPK + " " + curr._null + " " + curr._type);
        }
        System.out.println("");
    }

    boolean isEmpty() {
        return getNodes() == 0;
    }

    public RPColumn getFirst() {
        return first;
    }
}
