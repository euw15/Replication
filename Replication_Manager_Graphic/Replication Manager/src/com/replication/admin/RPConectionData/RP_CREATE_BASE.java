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
public class RP_CREATE_BASE {

    private final RPConnectionInterface connection;

    public RP_CREATE_BASE(RPConnectionInterface connection) {
        this.connection = connection;
    }

    public void replicTables(RPTableSLL tableList, String MotorDestino, String BaseName) {
        switch (MotorDestino) {
            case "SQL SERVER": {
                String script = "";
                for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {
                    script += table.getScript_MSSQL();
                }

                //    System.out.println(script);
                //   this.connection.executeUpdate(script);
                break;
            }
            case "MYSQL": {

                String crearBase = "CREATE DATABASE IF NOT EXISTS `" + BaseName + "`;";
                System.out.println(crearBase);
                connection.execute(crearBase);

                for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {
                    System.out.println(table.getScript_MYSQL());
                    connection.execute(table.getScript_MYSQL());
                }
                break;
            }
        }
    }

}

