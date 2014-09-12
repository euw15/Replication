/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.RPConectionData;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPTable;
import com.replication.admin.DataStructure.RPTableSLL;

/**
 *
 * @author Melvin
 */
public class RPCreateBase {

    private final RPConnectionInterface connection;

    public RPCreateBase(RPConnectionInterface connection) {
        this.connection = connection;
    }

    public void replicTables(RPTableSLL tableList, String MotorDestino, String BaseName) {
        switch (MotorDestino) {
            case "SQL SERVER": {        
                for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {    
                    connection.execute(table.getScript_MSSQL());
                }
                break;
            }
            case "MYSQL": {
                String crearBase = "CREATE DATABASE IF NOT EXISTS `" + BaseName + "`;";
                connection.execute(crearBase);
                for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {
                    connection.execute(table.getScript_MYSQL());
                }
                break;
            }
        }
    }

}
