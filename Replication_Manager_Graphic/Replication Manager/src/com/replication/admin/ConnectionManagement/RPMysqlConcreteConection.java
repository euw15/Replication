/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.ConnectionManagement;

import com.replication.admin.RPConectionData.RPConection;
import com.replication.user.Error.InfError;
import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;

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
                //      rs = copyResultSet(rs);
                //    conectionMySQL.close();
            }
        } catch (SQLException e) {
            InfError.showInformation(null, "Error al realizar consulta");

        } /*finally {
         try {
         conectionMySQL.close();
         } catch (SQLException ex) {
         System.out.println("Error al cerrar conexion");
         }
         }*/

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
                conectionMySQL.close();
            }
        } catch (SQLException | ClassNotFoundException e) {
            InfError.showInformation(null, "Error al realizar consulta");
        } finally {
            try {
                conectionMySQL.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
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
            conectionMySQL.close();

        } catch (SQLException e) {
            InfError.showInformation(null, "Error al realizar consulta");
        } finally {
            try {
                conectionMySQL.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }
    }

    @Override
    public boolean validConnection() {
        boolean flag = false;
        try {
            Class.forName(this.getConection().getDriver());

            conectionMySQL = DriverManager.getConnection("jdbc:"
                    + "mysql://" + getConection().getIp() + ":"
                    + getConection().getPort() + "/" + getConection().getDatabase(),
                    getConection().getUser(), getConection().getPass());
            flag = true;
            conectionMySQL.close();
        } catch (ClassNotFoundException | SQLException ex) {

            InfError.showInformation(null, "* Error al conectar a Base de Datos");

        } finally {
            try {
                conectionMySQL.close();
            } catch (SQLException ex) {
                System.out.println("Error al cerrar conexion");
            }
        }
        return flag;
    }

    @Override
    public String getTypeConnection() {
        return "MySQL";
    }

    private ResultSet copyResultSet(ResultSet state) {

        CachedRowSet rowset = null;
        try {
            rowset = new CachedRowSetImpl();
            rowset.populate(state);
        } catch (SQLException ex) {
            System.out.println("Error al copiar resultset");
        }
        return rowset;

    }
}
