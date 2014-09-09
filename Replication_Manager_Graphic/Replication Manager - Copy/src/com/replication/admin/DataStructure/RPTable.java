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
public class RPTable {

    protected RPTable succ;
    protected final String name;
    protected RPColumnSLL colums;

    protected String _Script_MYSQL;

    protected String _Script_MSSQL;

    public RPTable(String tableName) {
        this.name = tableName;
        colums = new RPColumnSLL();
    }

    /* public void insertColumn(String nameColumn, String tipo, boolean isPK) {
     colums.insert(nameColumn, tipo, isPK);
     }*/
    public void insertColumns(RPColumnSLL listColums) {
        this.colums = listColums;
    }

    public RPTable getSucc() {
        return succ;
    }

    public String getName() {
        return name;
    }

    public void setScript_MYSQL(String _Script_MYSQL) {
        this._Script_MYSQL = _Script_MYSQL;
    }

    public void setScript_MSSQL(String _Script_MSSQL) {
        this._Script_MSSQL = _Script_MSSQL;
    }

    public RPColumnSLL getColums() {
        return colums;
    }

    public String getScript_MSSQL() {
        return _Script_MSSQL;
    }

}
