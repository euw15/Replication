/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.RPBaseData;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
import com.replication.admin.RPConectionData.RPConection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Se encarga de administrar la base del proyecto Repication Manager
 *
 * @author Melvin
 */
public class RPBaseData {

    public RPBaseData() {

    }

    /**
     * Permite cambiar el estado pausa/Active(0/1) de una replicacion
     *
     * @param idConnection
     */
    public void setActiveOrPause(int idConnection) {
        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("MotorBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver"
            );
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);

            // Llama el preceso almacenado encargado de cambiar el estado de la
            // replica(idConnection)
            RPconnect.executeUpdate("exec setActiveOrPause   @idConnection ="
                    + idConnection + ";");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso setActiveOrPause");

        }

    }

    /**
     * Permite insertar un evento/error ocurrido en el Replication Manager,
     * en la tabla historial
     * @param typeEvent
     * @param description 
     */
    public void insertHistoryEvent(int typeEvent, String description) {
        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("MotorBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);
            
            //Llama al proceso almacenado encargado de insertar eventos
            RPconnect.executeUpdate("exec InsertHistoryEvent  @typeEvent = "
                    + typeEvent + ","
                    + " @description  	= '" + description + "';");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso InsertTable");

        }

    }
 
    /**
     * Permiete guardar una nueva replica(coneccion) hecha entre una base de 
     * entrada y una base destino 
     * @param dbmsInput
     * @param ipInput
     * @param dbNameInput
     * @param userInput
     * @param passwordInput
     * @param dbmsOutput
     * @param ipOutput
     * @param dbNameOutput
     * @param userOutput
     * @param passwordOutput 
     */
    public void RPSaveConnecton(String dbmsInput, String ipInput,
            String dbNameInput, String userInput, String passwordInput,
            String dbmsOutput, String ipOutput, String dbNameOutput,
            String userOutput, String passwordOutput) {

        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("MotorBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver"
            );
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);
    
            //Llama al proceso almacenado para insertar una nueva coneccion en 
            // la tabla de conecciones
            RPconnect.executeUpdate("InsertConnection  '" + dbmsInput + "','"
                    + ipInput + "','" + dbNameInput
                    + "','" + userInput + "','" + passwordInput + "','"
                    + dbmsOutput + "','" + ipOutput + "','" + dbNameOutput
                    + "','"
                    + userOutput + "','"
                    + passwordOutput + "'" + ",'" + 1 + "'" + ";");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso InsertTable");

        }

    }
 
    /**
     * Permite consultar las conecciones(replicas) existentes en Replication 
     * Manager
     * @return 
     */
    public ArrayList<String[]> getConnection() {
        ArrayList<String[]> data = new ArrayList<>();
        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("MotorBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver"
            );
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);

            ResultSet makeQuery = RPconnect.makeQuery("SELECT [idConnection]\n"
                    + "      ,[DBMSInput]\n"
                    + "      ,[ipInput]\n"
                    + "      ,[DBNameInput]\n"
                    + "      ,[userInput]\n"
                    + "      ,[passwordInput]\n"
                    + "      ,[DBMSOutput]\n"
                    + "      ,[ipOutput]\n"
                    + "      ,[DBNameOutput]\n"
                    + "      ,[userOutput]\n"
                    + "      ,[passwordOutput]\n"
                    + "  FROM [MotorBase].[dbo].[connections]");

            while (makeQuery.next()) {
                String dbmsInput = makeQuery.getString("DBMSInput");
                String ipInput = makeQuery.getString("ipInput");
                String dbNameInput = makeQuery.getString("DBNameInput");
                String userInput = makeQuery.getString("userInput");
                String passwordInput = makeQuery.getString("passwordInput");
                String dbmsOutput = makeQuery.getString("DBMSOutput");
                String ipOutput = makeQuery.getString("ipOutput");
                String dbNameOutput = makeQuery.getString("DBNameOutput");
                String userOutput = makeQuery.getString("userOutput");
                String passwordOutput = makeQuery.getString("passwordOutput");

                String idConnection = makeQuery.getString("idConnection");

                String[] datos = {dbmsInput, ipInput, dbNameInput, userInput,
                    passwordInput, "Ver Tablas", dbmsOutput, ipOutput,
                    dbNameOutput, userOutput, passwordOutput, "", "",
                    idConnection};

                data.add(datos);
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar proceso de obtencion"
                    + " de connexiones");

        }
        return data;
    }
}
