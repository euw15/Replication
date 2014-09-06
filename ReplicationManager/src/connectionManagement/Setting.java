/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionManagement;

import java.sql.Connection;
import java.sql.Statement;

/**
 *
 * @author Jason
 */
public interface Setting {

    /**
     * Establecer coneccion
     *
     * @param conection
     */
    void setConection(Connection conection);

    /**
     * Retorna la coneccion
     *
     * @return
     */
    Connection getConection();

    /**
     * Establecer statement
     *
     * @param statement
     */
    void setStatement(Statement statement);

    /**
     * Rertorna el statement
     *
     * @return
     */
    public Statement getStatement();

    
}
