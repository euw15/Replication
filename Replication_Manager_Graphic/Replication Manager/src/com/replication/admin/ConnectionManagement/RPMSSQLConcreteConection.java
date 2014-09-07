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

    RPConection conection;

    public RPMSSQLConcreteConection() {
    }

    @Override
    public ResultSet makeQuery(String query) {
        ResultSet rs = null;
        try {
            Class.forName(this.conection.getDriver());

            Connection conectionMySQL = DriverManager.getConnection("jdbc:"
                    + "sqlserver://"
                    + conection.getIp() + ":"
                    + conection.getPort() + ";dataBaseName="
                    + conection.getDatabase(), conection.getUser(),
                    conection.getPass());

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

    @Override
    public RPConection getConection() {
        return conection;
    }
}
