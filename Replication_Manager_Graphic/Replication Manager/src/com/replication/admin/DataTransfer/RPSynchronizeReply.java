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
        }

    }

}
