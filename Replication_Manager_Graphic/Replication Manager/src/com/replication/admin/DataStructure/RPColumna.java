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
public class RPColumna {

    protected RPColumna succ;

    protected String name;
    protected String tipo;
    protected boolean isPK;

    public RPColumna(String tableName, String tipo, boolean isPK) {
        this.name = tableName;
        this.tipo = tipo;
        this.isPK = isPK;
    }
}
