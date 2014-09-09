/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.DataStructure.RPColumn;
import com.replication.admin.DataStructure.RPColumnSLL;
import com.replication.admin.DataStructure.RPTable;
import com.replication.admin.DataStructure.RPTableSLL;

/**
 *
 * @author Edward
 */
public class RPCreateTriggersSQL {

    RPTableSLL database;   //base de datos a la cual se crea el trigger

    public RPCreateTriggersSQL(RPTableSLL database) {
        this.database = database;
    }

    public void createTriggersOnDataBase(RPTableSLL database) {
        String queryCompleto = "";

        String insertsTrigger = CreateInsertTrigger(database);
        String deleteTrigger = CreateDeleteTrigger(database);

        System.out.println(insertsTrigger);
        System.out.println(deleteTrigger);

    }

    public String createStament(String nombreTabla, String nombreColumna, String Tipo, String ColumnaPK, String Accion) {
        String sqlStatementInsert
                = " insert into Historial (NombreColumna,NombreTabla,Dato,Tipo,TuplaPK,Accion,Consultado)  "
                + "values" + " ('" + nombreTabla + "','" + nombreColumna + "',(select " + nombreColumna + " from inserted),'" + Tipo + "',(select '" + ColumnaPK + "' from inserted),'" + Accion + "',0); ";

        return sqlStatementInsert;
    }

    public String CreateInsertTrigger(RPTableSLL database) {
        String completeTriggerTable = "";

        //recorre todas las tablas creando trigger insert
        for (RPTable tablaActual = database.getFirst(); tablaActual != null; tablaActual = tablaActual.getSucc()) {
            System.out.println("hola si entre");
            //String base para trigger de insert
            String insertTrigger = "go Create TRIGGER insertNotification ON " + tablaActual.getName() + " FOR INSERT AS Begin begin";
            String finalInsert = "end end";

            //obtiene columnas de la tablaactual
            RPColumnSLL ColumnasActuales = tablaActual.getColums();

            //Crea statement de inserts
            String statementInsert = "";

            //create el triger para la tabla 
            for (RPColumn columnaActual = ColumnasActuales.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                statementInsert += createStament(tablaActual.getName(), columnaActual.getColumn_name(), columnaActual.getType(), "id", "insert");
            }

            completeTriggerTable += insertTrigger + statementInsert + finalInsert;

        }

        return completeTriggerTable;
    }

    public String CreateDeleteTrigger(RPTableSLL database) {
        String completeTriggerTable = "";

        //recorre todas las tablas creando trigger insert
        for (RPTable tablaActual = database.getFirst(); tablaActual != null; tablaActual = tablaActual.getSucc()) {
            //String base para trigger de insert
            String insertTrigger = "go Create TRIGGER deleteNotification ON " + tablaActual.getName() + " FOR DELETE AS Begin begin";
            String finalInsert = "end end ";

            //obtiene columnas de la tablaactual
            RPColumnSLL ColumnasActuales = tablaActual.getColums();

            //Crea statement de inserts
            String statementDelete = "";

            //create el triger para la tabla 
            for (RPColumn columnaActual = ColumnasActuales.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                statementDelete += createStament(tablaActual.getName(), columnaActual.getColumn_name(), columnaActual.getType(), "id", "deleted");
            }

            completeTriggerTable += insertTrigger + statementDelete + finalInsert;

        }

        return completeTriggerTable;
    }
}
