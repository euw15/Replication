/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectionManagement;

import RPConectionData.RPConection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Jason
 */
public class RPMysqlConcreteConection implements RPConnectionRepository{
    
    RPConection conection;
    
    public RPMysqlConcreteConection(RPConection conection){
        this.conection.driver = conection.driver;
        this.conection.pass = conection.pass;
        this.conection.user = conection.user;
        this.conection.database = conection.database;
    }

    
    @Override
    public ResultSet makeQuery() {
        ResultSet rs = null;
        try {
            Class.forName(conection.driver);
            Connection conectionMySQL = DriverManager.getConnection(conection.database, conection.user, conection.pass);
            Statement statement = conectionMySQL.createStatement();
             rs = statement.executeQuery("SELECT * FROM mierda");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al recuperar conexion "
                    + e.toString());
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos.");

        }
        return rs;
         
            
        
        //falta return
        
    }

    
    
}
