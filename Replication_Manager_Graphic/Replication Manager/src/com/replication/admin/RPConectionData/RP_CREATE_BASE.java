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

    public void getTablesMySQL() {

//        ArrayList<String> listTables = new ArrayList<>();
//        ResultSet makeQuery = this.connection.makeQuery("select TABLE_NAME from information_schema.tables where TABLE_SCHEMA='" + connection.getConection().getDatabase() + "'");
//
//        if (makeQuery != null) {
//            try {
//                while (makeQuery.next()) {
//                    listTables.add(makeQuery.getString(1));
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(RPBaseInformation.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return listTables;
    }

    public void replicTables(RPTableSLL tableList) {
        String script = "";
        for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {
            script += table.getScript_MSSQL();
        }
        this.connection.executeUpdate(script);
    }

}
