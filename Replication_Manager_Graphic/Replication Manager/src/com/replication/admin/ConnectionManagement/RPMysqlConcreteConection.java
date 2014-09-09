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
public class RPMysqlConcreteConection implements RPConnectionInterface {

    private RPConection conection;
    private Connection conectionMySQL;

    public RPMysqlConcreteConection() {

    }

    @Override
    public ResultSet makeQuery(String query) {
        ResultSet rs = null;
        try {
            makeConnection();
            if (conectionMySQL != null) {
                Statement statement = conectionMySQL.createStatement();
                rs = statement.executeQuery(query);
            }
        } catch (SQLException e) {
            InfError.showInformation(null, "Error al realizar consulta");

        }
        return rs;
    }

    @Override
    public void executeUpdate(String query) {

        try {
            Class.forName(this.conection.getDriver());

            makeConnection();

            if (conectionMySQL != null) {
                Statement statement = conectionMySQL.createStatement();
                statement.executeUpdate(query);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            InfError.showInformation(null, "Error al realizar consulta");
        }

    }

    @Override
    public void setConection(RPConection mConection) {
        this.conection = mConection;
    }

    /**
     * @return the conection
     */
    @Override
    public RPConection getConection() {
        return conection;
    }

    @Override
    public void makeConnection() {
        try {
            Class.forName(this.getConection().getDriver());

            conectionMySQL = DriverManager.getConnection("jdbc:"
                    + "mysql://" + getConection().getIp() + ":"
                    + getConection().getPort() + "/" + getConection().getDatabase(),
                    getConection().getUser(), getConection().getPass());

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            InfError.showInformation(null, "Error al conectar a Base de Datos");
        }
    }

   @Override
     public void execute(String query) {

        try {
            makeConnection();
            if (conectionMySQL != null) {
                
                Statement statement = conectionMySQL.createStatement();
                statement.execute(query);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            InfError.showInformation(null, "Error al realizar consulta");
        }

    } 
}
