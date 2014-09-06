/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionManagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @authors: Daniel Canessa, Melvin Gutierrez , Edwar Umana, Jason Salazar
 *
 * Estable la coneccion con la base de datos Mysql
 */
public abstract class SettingBD implements Setting {

    //Parametros de conexion
    final String driver = "com.mysql.jdbc.Driver";
    final String database = "jdbc:mysql://localhost:3306/replication";
    final String user = "root";
    final String pass = "123456";
    Connection conection ;
    Statement statement ;
   // DateFormat dateFormat = new SimpleDateFormat("EEEE");//formato fecha act

    public SettingBD() {
        try {
            Class.forName(driver);
            setConection(DriverManager.getConnection(database, user, pass));
            setStatement(conection.createStatement());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al recuperar conexion "
                    + e.toString());
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar a la base de datos.");

        }
    }

    @Override
    public void setConection(Connection conection2) {
        this.conection = conection2;
    }

    @Override
    public Connection getConection() {
        return conection;
    }

    @Override
    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public Statement getStatement() {
        return statement;
    }

}
