/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPColumn;
import com.replication.admin.DataStructure.RPColumnSLL;
import com.replication.admin.DataStructure.RPTable;
import com.replication.admin.DataStructure.RPTableSLL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Melvin
 */
public class RPCreateTriggersMYSQL {

    private final RPTableSLL database;
    private final RPConnectionInterface connection;

    public RPCreateTriggersMYSQL(RPConnectionInterface connection, RPTableSLL database) {
        this.connection = connection;
        this.database = database;
    }

    public void CreateInsertTrigger() {

        //recorre todas las tablas creando trigger insert
        for (RPTable tablaActual = database.getFirst(); tablaActual != null; tablaActual = tablaActual.getSucc()) {
            //obtiene el nombre de la columnaPL

            String pkColumna = "";
            RPColumnSLL Columnas = tablaActual.getColums();

            for (RPColumn columnaActual = Columnas.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                if (columnaActual.isIsPK()) {
                    pkColumna = columnaActual.getColumn_name();
                }
            }

            ResultSet makeQuery = connection.makeQuery("call addLogTrigger('" + tablaActual.getName() + "','" + pkColumna + "');");
            if (makeQuery != null) {
                try {
                    while (makeQuery.next()) {

                        String triggersUPDATE = makeQuery.getString("_outputUPDATE");
                        String triggersINSERT = makeQuery.getString("_outputINSERT");
                        String triggersDELETE = makeQuery.getString("_outputDELETE");

                        //  System.out.println(triggersINSERT);
                        connection.executeUpdate(triggersUPDATE);
                        connection.executeUpdate(triggersINSERT);
                        connection.executeUpdate(triggersDELETE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(RPCreateTriggersMYSQL.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}
