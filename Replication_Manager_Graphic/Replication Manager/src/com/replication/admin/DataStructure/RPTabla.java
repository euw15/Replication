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
public class RPTabla {

    protected RPTabla succ;
    protected final String name;
    protected RPColumnSLL colums;

    public RPTabla(String tableName) {
        this.name = tableName;
        colums = new RPColumnSLL();
    }

   /* public void insertColumn(String nameColumn, String tipo, boolean isPK) {
        colums.insert(nameColumn, tipo, isPK);
    }*/

    public void insertColumns(RPColumnSLL listColums) {
        this.colums = listColums;
    }

    public RPTabla getSucc() {
        return succ;
    }

    public String getName() {
        return name;
    }
    
    public RPColumnSLL getColumns(){
        return colums;
    }
}
