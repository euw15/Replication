/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.ConnectionManagement;

import com.replication.admin.RPConectionData.RPConection;
import com.replication.user.Error.InfError;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason
 */
public class RPMSSQLConcreteConection implements RPConnectionInterface {

    private RPConection conection;
    private Connection conectionMS_SQL;

    public RPMSSQLConcreteConection() {
    }

    @Override
    public ResultSet makeQuery(String query) {
        ResultSet rs = null;
        try {
            makeConnection();
            if (conectionMS_SQL != null) {
                Statement statement = conectionMS_SQL.createStatement();
                rs = statement.executeQuery(query);
            }
        } catch (SQLException e) {
            //InfError.showInformation(null, "Error al realizar consulta");
        }
        return rs;

    }

    @Override
    public void executeUpdate(String query) {

        try {
            makeConnection();

            if (conectionMS_SQL != null) {
                Statement statement = conectionMS_SQL.createStatement();
                statement.executeUpdate(query);
            }

        } catch (SQLException e) {
            //InfError.showInformation(null, "Error al realizar consulta");
        }

    }

    @Override
    public void execute(String query) {
        try {
            makeConnection();

            if (conectionMS_SQL != null) {
                Statement statement = conectionMS_SQL.createStatement();
                statement.execute(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
           // InfError.showInformation(null, "Error al realizar consulta");
        }

    }

    @Override
    public void setConection(RPConection mConection) {
        this.conection = mConection;
    }

    @Override
    public RPConection getConection() {
        return conection;
    }

    @Override
    public void makeConnection() {
        try {
            Class.forName(this.conection.getDriver());

            conectionMS_SQL = DriverManager.getConnection("jdbc:"
                    + "sqlserver://"
                    + conection.getIp() + ":"
                    + conection.getPort() + ";dataBaseName="
                    + conection.getDatabase(), conection.getUser(),
                    conection.getPass());
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
            //InfError.showInformation(null, "Error al conectar a Base de Datos");
        }
    }

    @Override
    public boolean validConnection() {
        boolean flag = false;
        try {
            Class.forName(this.conection.getDriver());

            conectionMS_SQL = DriverManager.getConnection("jdbc:"
                    + "sqlserver://"
                    + conection.getIp() + ":"
                    + conection.getPort() + ";dataBaseName="
                    + conection.getDatabase(), conection.getUser(),
                    conection.getPass());
            flag = true;

        } catch (SQLException | ClassNotFoundException ex) {
            //InfError.showInformation(null, "*Error al conectar a Base de Datos");
        }
        return flag;
    }

}
