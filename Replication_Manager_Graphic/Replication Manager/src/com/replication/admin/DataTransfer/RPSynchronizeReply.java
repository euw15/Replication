/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Jason
 */
public class RPSynchronizeReply {

    public void synchronizeData(String DBMS, String tableName, String action,
            String rowPK, String fieldName, String oldValue, String newValue,
            RPConnectionInterface connection) throws SQLException {

        if (DBMS.equals("MySQL")) {

            ResultSet query = connection.makeQuery("SHOW KEYS FROM "
                    + tableName + " WHERE Key_name = 'PRIMARY';");
            query.next();
            String namePK = query.getString("Column_name");

            if ("INSERT".equals(action)) {

                query = connection.makeQuery("SELECT COUNT((SELECT " + namePK + "  FROM " + tableName + " AS C "
                        + "WHERE C." + namePK + " = '" + rowPK + "' )) AS C");
                query.next();
                int exist = query.getInt("C");

                if (exist == 1) {//Update

                    connection.executeUpdate("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK + " = '" + rowPK + "';");

                } else {//Insert
                    connection.executeUpdate("INSERT INTO " + tableName
                            + " (" + namePK + ") VALUES('" + newValue + "');");
                }

            } else if ("UPDATE".equals(action)) {
                connection.executeUpdate("UPDATE " + tableName + " SET "
                        + fieldName + "='" + newValue + "' WHERE " + namePK + " = '" + rowPK + "';");

            } //DELETE
            else {
                connection.executeUpdate("DELETE  FROM " + tableName
                        + " WHERE " + namePK + " = '" + rowPK + "';");

            }
        } else {//SQL SERVER

            ResultSet query = connection.makeQuery("SELECT COL_NAME(ic.OBJECT_ID,ic.column_id) AS ColumnName \n"
                    + "FROM sys.indexes AS i INNER JOIN sys.index_columns AS ic ON i.OBJECT_ID = ic.OBJECT_ID AND i.index_id = ic.index_id\n"
                    + " WHERE i.is_primary_key = 1 AND OBJECT_NAME(ic.object_id)='" + tableName + "';");
            query.next();
            String namePK = query.getString("ColumnName");

            if ("INSERT".equals(action)) {

                query = connection.makeQuery("SELECT COUNT(" + namePK + ") AS C FROM " + tableName + " WHERE " + namePK + "='" + rowPK + "';");

                query.next();
                int exist = query.getInt("C");

                if (exist == 1) {//Update

                    connection.executeUpdate("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK + " = '" + rowPK + "';");

                } else {//Insert

                    connection.executeUpdate("SET IDENTITY_INSERT " + tableName
                                + " ON  "+  "INSERT INTO " + tableName + "(" + namePK
                            + ") VALUES('" + newValue + "') "+" SET IDENTITY_INSERT "
                                + tableName + " OFF"+";");
                   
                }

            } else if ("UPDATE".equals(action)) {
                connection.executeUpdate("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK + " = '" + rowPK + "';");

            } //DELETE
            else {

                 connection.executeUpdate("DELETE  FROM " + tableName
                        + " WHERE " + namePK + " = '" + rowPK + "';");
            }

        }

    }

}
