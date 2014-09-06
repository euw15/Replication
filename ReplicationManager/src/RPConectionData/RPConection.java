/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package RPConectionData;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Jason
 */
public class RPConection {
     //Parametros de conexion
     public String driver ;
     public String database ;
     public String user ;
     public String pass ;
 
     public RPConection(RPConection mConection)
     {
         this.driver   = mConection.driver;
         this.database = mConection.database;
         this.user     = mConection.user;
         this.pass     = mConection.pass;
     }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }
     
     
     
}
