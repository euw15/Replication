/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.RPConectionData;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPColumnSLL;
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

    public RPColumnSLL getColumnsMYSQL(String Tabla) {

        RPColumnSLL listColums = new RPColumnSLL();
        try {
            ResultSet resultset = this.connection.makeQuery("SHOW COLUMNS FROM " + connection.getConection().getDatabase() + "." + Tabla + ";");
            while (resultset.next()) {

                String _column_Name = resultset.getString("Field");
                String _type = resultset.getString("Type");
                String _null = resultset.getString("Null");
                String _key = resultset.getString("Key");
                String _default = resultset.getString("Default");
                String _extra = resultset.getString("Extra");

                listColums.insert(_column_Name, _type, _null, _key, _default, _extra);
            }

        } catch (SQLException ex) {
            Logger.getLogger(RPBaseInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return listColums;

    }

    public RPColumnSLL getColumnsSQLMS(String Tabla) {

        RPColumnSLL listColums = new RPColumnSLL();
        try {
            String sql = "SELECT Columna.COLUMN_NAME, Llave.CONSTRAINT_NAME as [Key], Columna.IS_NULLABLE, COLUMN_DEFAULT as [Default] ,Iden.is_identity as [Extra], Columna.DATA_TYPE, Columna.CHARACTER_MAXIMUM_LENGTH FROM INFORMATION_SCHEMA.COLUMNS as Columna left join information_schema.key_column_usage as Llave on Columna.COLUMN_NAME = Llave.COLUMN_NAME left join sys.columns as Iden on object_id = object_id(Columna.TABLE_NAME) and name = Columna.COLUMN_NAME WHERE Columna.TABLE_NAME = '" + Tabla + "'";
            System.out.println(sql);
            ResultSet resultset = this.connection.makeQuery(sql);

            while (resultset.next()) {
                String _column_Name = resultset.getString("COLUMN_NAME");
                String _type = resultset.getString("DATA_TYPE");
                String _null = resultset.getString("IS_NULLABLE");
                String _key = resultset.getString("Key");
                String _default = resultset.getString("Default");
                int _extra = resultset.getInt("Extra");
                String largoString= resultset.getString("CHARACTER_MAXIMUM_LENGTH");
                
                if(largoString!=null)
                {
                    _type+="("+largoString+")";
                }
                String keyString = "no ";

                if (_key != null) {
                    if (_key.contains("PK")) {
                        keyString = "PRI";
                    }
                }
                String extraString = null;
                if (_extra == 1) {
                    extraString = "auto_increment";
                }

                listColums.insert(_column_Name, _type, _null, keyString, _default, extraString);

            }

        } catch (SQLException ex) {
            Logger.getLogger(RPBaseInformation.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return listColums;

    }

}
