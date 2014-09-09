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

    private RPTable first;
    protected String Identifier;

    public String getIdentifier() {
        return Identifier;
    }
   

    public RPTableSLL(String Identifier) {
        this.first = null;
        this.Identifier = Identifier;
    }

    public void insert(String tableName) {

        RPTable newly = new RPTable(tableName);

        if (first == null) {
            first = newly;
            first.succ = null;
        }
        for (RPTable curr = first; curr != null; curr = curr.succ) {
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
            RPTable aux = first.succ;
            first = aux;
        } else {
            position--;
            for (RPTable curr = first; curr != null; curr = curr.succ, i++) {
                if (i == position) {
                    RPTable aux = curr.succ;
                    curr.succ = aux.succ;
                }
            }
        }
    }

    private int getNodes() {

        int count = 0;
        for (RPTable curr = first; curr != null; curr = curr.succ, count++) {
        }
        return count--;
    }

    private RPTable getElement(int pos) {

        RPTable aux = null;
        int i = 0;
        for (RPTable curr = first; curr != null; curr = curr.succ, i++) {
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

    public RPTable getFirst() {
        return first;
    }

    public void printTables() {
        
        for (RPTable curr = first; curr != null; curr = curr.succ) {
           
            System.out.println("-----------------");
            System.out.println(curr.name);
            System.out.println("-----------------");
            curr.colums.printColums();
            
        }
        System.out.println("");
    }

}
