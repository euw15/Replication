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
public class RPColumn {

    protected RPColumn succ;

    protected String column_name;
    protected String type;
    protected boolean isPK;

    public RPColumn(String tableName, String tipo, boolean isPK) {
        
        this.column_name = tableName;
        this.type = tipo;
        this.isPK = isPK;
        
    }
}
