/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.RPConectionData;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPColumna;
import com.replication.admin.DataStructure.RPColunmSLL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class RPBaseInformation {

    RPConnectionInterface connection;

    public RPBaseInformation(RPConnectionInterface connection) {
        this.connection = connection;
    }

    public ArrayList<String> getTablesMySQL() {

        ArrayList<String> listTables = new ArrayList<>();
        ResultSet makeQuery = this.connection.makeQuery("select TABLE_NAME from information_schema.tables where TABLE_SCHEMA='" + connection.getConection().getDatabase() + "'");

        if (makeQuery != null) {
            try {
                while (makeQuery.next()) {
                    listTables.add(makeQuery.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(RPBaseInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listTables;
    }

    public ArrayList<String> getTablesMSSQL() {

        ArrayList<String> listTables = new ArrayList<>();
        ResultSet makeQuery = this.connection.makeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES");
        try {
            while (makeQuery.next()) {
                listTables.add(makeQuery.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(RPBaseInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listTables;
    }

    public List<List<String>> getColumnsNames(ArrayList<String> listTableName) {

        List<List<String>> result = new ArrayList<>();

        for (String listTableName1 : listTableName) {
            try {

                ResultSet resultset = this.connection.makeQuery("SHOW COLUMNS FROM " + connection.getConection().getDatabase() + "." + listTableName1 + ";");
                int numcols = resultset.getMetaData().getColumnCount();
                while (resultset.next()) {

                    List<String> row = new ArrayList<>(numcols); // new list per row
                    row.add(listTableName1);

                    for (int i = 1; i <= numcols; i++) {  // don't skip the last column, use <=
                        row.add(resultset.getString(i));
                    }
                    result.add(row); // add it to the result

                }
            } catch (SQLException ex) {
                Logger.getLogger(RPBaseInformation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;

    }

    public ResultSet getTriggers() {
        return this.connection.makeQuery("SELECT * FROM INFORMATION_SCHEMA.TRIGGERS WHERE TRIGGER_SCHEMA='" + connection.getConection().getDatabase() + "';");
    }

    public RPColunmSLL getColumnsMYSQL(String Tabla) {

        RPColunmSLL listColums = new RPColunmSLL();
        try {
            ResultSet resultset = this.connection.makeQuery("SHOW COLUMNS FROM " + connection.getConection().getDatabase() + "." + Tabla + ";");
            while (resultset.next()) {

                String name = resultset.getString("Field");
                String tipo = resultset.getString("Type");
                String key = resultset.getString("Key");
                boolean isPk = false;
                if (key.equals("PRI")) {
                    isPk = true;
                }

                listColums.insert(name, tipo, isPk);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RPBaseInformation.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listColums;

    }

}
