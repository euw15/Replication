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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class RPMysqlConcreteConection implements RPConnectionInterface {

    public RPConection conection;

    public RPMysqlConcreteConection() {

    }

    @Override
    public ResultSet makeQuery(String query) {
        ResultSet rs = null;
        try {

            Class.forName(this.getConection().getDriver());

            Connection conectionMySQL = DriverManager.getConnection("jdbc:"
                    + "mysql://" + getConection().getIp() + ":"
                    + getConection().getPort() + "/" + getConection().getDatabase(),
                    getConection().getUser(), getConection().getPass());

            Statement statement = conectionMySQL.createStatement();
            rs = statement.executeQuery(query);

        } catch (SQLException e) {
            InfError.showInformation(null, "Error al recuperar la conexion");

        } catch (ClassNotFoundException ex) {
            InfError.showInformation(null, "Error al conectar a la base de datos");
        }
        return rs;
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

}
