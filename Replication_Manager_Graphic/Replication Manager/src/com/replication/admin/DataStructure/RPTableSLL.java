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
public class RPTableSLL {

    private RPTabla first;
    protected String Identifier;

    public RPTableSLL(String Identifier) {
        this.first = null;
        this.Identifier = Identifier;
    }

    public void insert(String tableName) {

        RPTabla newly = new RPTabla(tableName);

        if (first == null) {
            first = newly;
            first.succ = null;
        }
        for (RPTabla curr = first; curr != null; curr = curr.succ) {
            if (curr.succ == null) {
                curr.succ = newly;
                newly.succ = null;
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
            RPTabla aux = first.succ;
            first = aux;
        } else {
            position--;
            for (RPTabla curr = first; curr != null; curr = curr.succ, i++) {
                if (i == position) {
                    RPTabla aux = curr.succ;
                    curr.succ = aux.succ;
                }
            }
        }
    }

    private int getNodes() {

        int count = 0;
        for (RPTabla curr = first; curr != null; curr = curr.succ, count++) {
        }
        return count--;
    }

    private RPTabla getElement(int pos) {

        RPTabla aux = null;
        int i = 0;
        for (RPTabla curr = first; curr != null; curr = curr.succ, i++) {
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

    public RPTabla getFirst() {
        return first;
    }

    public void printTables() {
        
        for (RPTabla curr = first; curr != null; curr = curr.succ) {
           
            System.out.println("-----------------");
            System.out.println(curr.name);
            System.out.println("-----------------");
            curr.colums.printColums();
        }
        System.out.println("");
    }

}
