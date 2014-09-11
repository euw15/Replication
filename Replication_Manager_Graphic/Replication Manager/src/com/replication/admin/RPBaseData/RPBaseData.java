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
 *
 * @author Melvin
 */
public class RPBaseData {

    public RPBaseData() {

    }

    public void setActiveOrPause(int idConnection) {
        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("RPDataBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);

            RPconnect.executeUpdate("exec setActiveOrPause   @idConnection =" + idConnection + ";");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso setActiveOrPause");

        }

    }

    public void insertHistoryEvent(int typeEvent, String description) {
        try {
            RPConnectionInterface RPconnect;

            RPConection connection = new RPConection();
            connection.setDatabase("RPDataBase");
            connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection.setIp("localhost");
            connection.setPass("1234");
            connection.setPort("1433");
            connection.setUser("sa");

            RPconnect = RPConnectionsFactory.createConnection("SQLMS");
            RPconnect.setConection(connection);

            RPconnect.executeUpdate("exec InsertHistoryEvent  @typeEvent = " + typeEvent + ","
                    + " @description  	= '" + description + "';");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso InsertTable");

        }

    }

    public void RPSaveConnecton(String dbmsInput, String ipInput,
            String dbNameInput, String userInput, String passwordInput,
            String dbmsOutput, String ipOutput, String dbNameOutput,
            String userOutput, String passwordOutput) {

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

            RPconnect.executeUpdate("InsertConnection  '" + dbmsInput + "','" + ipInput + "','" + dbNameInput
                    + "','" + userInput + "','" + passwordInput + "','" + dbmsOutput + "','" + ipOutput + "','" + dbNameOutput + "','"
                    + userOutput + "','"
                    + passwordOutput + "'" + ",'" + 1 + "'" + ";");

        } catch (Exception e) {
            System.out.println("Error al ejecutar proceso InsertTable");

        }

    }

    public ArrayList<String[]> getConnection() {
        ArrayList<String[]> data = new ArrayList<>();
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
                    dbNameOutput, userOutput, passwordOutput, "", "", idConnection};

                data.add(datos);
            }

        } catch (SQLException e) {
            System.out.println("Error al ejecutar proceso de obtencion de connexiones");

        }
        return data;
    }
}
