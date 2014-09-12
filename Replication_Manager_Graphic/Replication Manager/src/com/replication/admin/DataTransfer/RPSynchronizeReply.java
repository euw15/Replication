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

    /**
     * Dada una connecion, se encarga de insertar, actualizar o borrar una tupla
     * o algun atributo de una tupla, de la table indicada en la base
     * relacionada con la coneccion
     *
     * @param DBMS
     * @param tableName
     * @param action
     * @param rowPK
     * @param fieldName
     * @param oldValue
     * @param newValue
     * @param connection
     * @param pk
     * @throws SQLException
     */
    public static void synchronizeData(String DBMS, String tableName, String action,
            String rowPK, String fieldName, String oldValue, String newValue,
            RPConnectionInterface connection,boolean pk) throws SQLException {

        action = action.replace(" ", "");
        if (DBMS.equals("MySQL")) {// Caso en el motor es MySQL

            //Se obtiene  la llave primaria de la tupla en la cual
            //se va a realizar la accion
            ResultSet query = connection.makeQuery("SHOW KEYS FROM "
                    + tableName + " WHERE Key_name = 'PRIMARY';");
            query.next();
            String namePK = query.getString("Column_name");
               
            if ("INSERT".equals(action)) {
                //Para saber si lo que se va a insertar es un atributo en una
                //tupla ya existente o insertar una nueva, se consulta si ya 
                //existe la  PK
                query = connection.makeQuery("SELECT COUNT((SELECT " + namePK
                        + "  FROM " + tableName + " AS C "
                        + "WHERE C." + namePK + " = '" + rowPK + "' )) AS C");
                query.next();
                int exist = query.getInt("C");

                if (exist == 1) {//Update
                    //Si ya existe una tupla con esa PK entonces lo que 
                    //se hace es una actualizacion del atributo indicado
                    connection.executeUpdate("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK
                            + " = '" + rowPK + "';");

                } else {//Insert
                    //Si aun no existe la tupla con esa PK, entonces, se insert
                    //como una nueva
                    
                    connection.executeUpdate("INSERT INTO " + tableName
                            + " (" + namePK + ") VALUES('" + newValue + "');");
                }

            } else if ("UPDATE".equals(action)) {
                //Actualizar un atributo en una tupla dada
                connection.executeUpdate("UPDATE " + tableName + " SET "
                        + fieldName + "='" + newValue + "' WHERE " + namePK
                        + " = '" + rowPK + "';");

            } //DELETE
            else {
                //Borrar una tupla de la tabla
                connection.executeUpdate("DELETE  FROM " + tableName
                        + " WHERE " + namePK + " = '" + rowPK + "';");

            }
        } else {//Caso en el motor MSSQL 

            ResultSet query = connection.makeQuery("SELECT COL_NAME"
                    + "(ic.OBJECT_ID,ic.column_id) AS ColumnName \n"
                    + "FROM sys.indexes AS i INNER JOIN "
                    + "sys.index_columns AS ic ON i.OBJECT_ID = ic.OBJECT_ID"
                    + " AND i.index_id = ic.index_id\n"
                    + " WHERE i.is_primary_key = 1 AND"
                    + " OBJECT_NAME(ic.object_id)"
                    + "='" + tableName + "';");
            query.next();
            String namePK = query.getString("ColumnName");
           
            if ("INSERT".equals(action)) {
                //Para saber si lo que se va a insertar es un atributo en una
                //tupla ya existente o insertar una nueva, se consulta si ya 
                //existe la  PK
                System.out.println("SELECT COUNT(" + namePK
                        + ") AS C FROM " + tableName + " WHERE " + namePK
                        + "='" + rowPK + "';");
                query = connection.makeQuery("SELECT COUNT(" + namePK
                        + ") AS C FROM " + tableName + " WHERE " + namePK
                        + "='" + rowPK + "';");

                query.next();
                int exist = query.getInt("C");
             
                if (pk) {//Update
                  
                    //Si ya existe una tupla con esa PK entonces lo que 
                    //se hace es una actualacion de el atributo indicado
                    connection.executeUpdate("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK
                            + " = '" + rowPK + "';");
                    System.out.println("UPDATE " + tableName + " SET "
                            + fieldName + "='" + newValue + "' WHERE " + namePK
                            + " = '" + rowPK + "';");
                } else {//Insert
                    //Si aun no existe la tupla con esa PK, entonces, se insert
                    //como una nueva 
//                    connection.executeUpdate("SET IDENTITY_INSERT " + tableName
//                            + " ON  " + "INSERT INTO " + tableName + "("
//                            + namePK + ") VALUES('" + newValue + "') "
//                            + " SET IDENTITY_INSERT " + tableName + " OFF"
//                            + ";");
                    System.out.println("INSERT INTO [" + tableName + "] ("
                            + namePK + ") VALUES('" + newValue + "') "
                            + ";");
                    System.out.println("tipo conexion "+connection.getConection().getTypeDatabase());
                    System.out.println("nombrebase "+connection.getConection().getDatabase());
                    connection.executeUpdate("INSERT INTO [" + tableName + "] ("
                            + namePK + ") VALUES('" + newValue + "') "
                            + ";");
                   

                }

            } else if ("UPDATE".equals(action)) {
                //Actualizar un atributo en una tupla dada
                connection.executeUpdate("UPDATE " + tableName + " SET "
                        + fieldName + "='" + newValue + "' WHERE " + namePK
                        + " = '" + rowPK + "';");

            } //DELETE
            else {
                //Borrar una tupla de la tabla
                connection.executeUpdate("DELETE  FROM " + tableName
                        + " WHERE " + namePK + " = '" + rowPK + "';");
            }

        }

    }

}
