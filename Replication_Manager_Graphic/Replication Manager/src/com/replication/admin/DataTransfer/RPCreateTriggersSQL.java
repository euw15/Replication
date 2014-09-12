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

/**
 *
 * @author Edward
 */
public class RPCreateTriggersSQL {

    RPTableSLL database;   //base de datos a la cual se crea el trigger

    public RPCreateTriggersSQL(RPTableSLL database) {
        this.database = database;
    }

    public void createTriggersOnDataBase(RPTableSLL database, RPConnectionInterface coneccionBaseDatos) {
        String queryCompleto = "";
    }

    public static String createStament(String nombreTabla, String nombreColumna, String Tipo, String ColumnaPK, String Accion) {
        String sqlStatementInsert = "";

        if (Accion.equalsIgnoreCase("UPDATE")) {
            sqlStatementInsert
                    = " insert into Historial (table_name,[action],row_pk,field_name,new_value,old_value)"
                    + " values " + " ('" + nombreTabla + "','" + Accion + "',(select [" + ColumnaPK + "] from inserted),'" + nombreColumna + "',(select [" + nombreColumna + "] from inserted),(select [" + nombreColumna + "] from deleted) ); ";

        }
        if (Accion.equalsIgnoreCase("DELETE")) {
            sqlStatementInsert
                    = " insert into Historial (table_name,[action],row_pk,field_name,new_value)"
                    + " values " + " ('" + nombreTabla + "','" + Accion + "',(select [" + ColumnaPK + "] from deleted),'" + nombreColumna + "',(select [" + nombreColumna + "] from deleted)); ";

        } else {
            sqlStatementInsert
                    = " insert into Historial (table_name,[action],row_pk,field_name,new_value)"
                    + " values " + " ('" + nombreTabla + "','" + Accion + "',(select [" + ColumnaPK + "] from inserted),'" + nombreColumna + "',(select [" + nombreColumna + "] from inserted)); ";

        }

        return sqlStatementInsert;
    }

    public static void CreateInsertTrigger(RPTableSLL database, RPConnectionInterface coneccionBaseDatos) {

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
            String completeTriggerTable = "";
            //String base para trigger de insert
            String insertTrigger = " create TRIGGER insertNotification" + tablaActual.getName() + " ON " + tablaActual.getName() + " FOR INSERT AS Begin begin";
            String finalInsert = " end end ";

            //obtiene columnas de la tablaactual
            RPColumnSLL ColumnasActuales = tablaActual.getColums();

            String statementInsert = "";
            //create el triger para la tabla 
            for (RPColumn columnaActual = ColumnasActuales.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                statementInsert += RPCreateTriggersSQL.createStament(tablaActual.getName(), columnaActual.getColumn_name(), columnaActual.getType(), pkColumna, "INSERT");
            }

            completeTriggerTable = insertTrigger + statementInsert + finalInsert;

            String tiggerName = "insertNotification" + tablaActual.getName();
            String dropInsert = "IF EXISTS (SELECT * FROM sys.objects WHERE [type] = 'TR' AND [name] = '" + tiggerName + "') DROP TRIGGER " + tiggerName + ";";
            coneccionBaseDatos.executeUpdate(dropInsert);

            coneccionBaseDatos.execute(completeTriggerTable);
        }

    }

    public static void CreateDeleteTrigger(RPTableSLL database, RPConnectionInterface coneccionBaseDatos) {

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

            //string para cada tabla 
            String completeTriggerTable = "";

            //String base para trigger de insert
            String insertTrigger = " create TRIGGER deleteNotification" + tablaActual.getName() + " ON " + tablaActual.getName() + " FOR DELETE AS Begin begin";
            String finalInsert = " end end ";

            //obtiene columnas de la tablaactual
            RPColumnSLL ColumnasActuales = tablaActual.getColums();

            //Crea statement de inserts
            String statementDelete = "";

            //create el triger para la tabla 
            for (RPColumn columnaActual = ColumnasActuales.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                statementDelete += createStament(tablaActual.getName(), columnaActual.getColumn_name(), columnaActual.getType(), pkColumna, "DELETE");

            }

            completeTriggerTable = insertTrigger + statementDelete + finalInsert;

            String tiggerName = "deleteNotification" + tablaActual.getName();
            String dropDelete = "IF EXISTS (SELECT * FROM sys.objects WHERE [type] = 'TR' AND [name] = '" + tiggerName + "') DROP TRIGGER " + tiggerName + ";";
            coneccionBaseDatos.executeUpdate(dropDelete);

            coneccionBaseDatos.execute(completeTriggerTable);
        }

    }

    public static void CreateUpdateTrigger(RPTableSLL database, RPConnectionInterface coneccionBaseDatos) {
        //recorre todas las tablas creando trigger insert
        for (RPTable tablaActual = database.getFirst(); tablaActual != null; tablaActual = tablaActual.getSucc()) {
            String completeTriggerTable = "";

            //obtiene el nombre de la columnaPL
            String pkColumna = "";
            RPColumnSLL Columnas = tablaActual.getColums();
            for (RPColumn columnaActual = Columnas.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                if (columnaActual.isIsPK()) {
                    pkColumna = columnaActual.getColumn_name();
                }

            }

            //String base para trigger de insert
            String insertTrigger = " create TRIGGER updateNotification" + tablaActual.getName() + " ON " + tablaActual.getName() + " FOR UPDATE AS Begin begin";
            String finalInsert = " end end ";

            //obtiene columnas de la tablaactual
            RPColumnSLL ColumnasActuales = tablaActual.getColums();

            //Crea statement de inserts
            String statementDelete = "";

            //create el triger para la tabla 
            for (RPColumn columnaActual = ColumnasActuales.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                statementDelete += createStament(tablaActual.getName(), columnaActual.getColumn_name(), columnaActual.getType(), pkColumna, "UPDATE");

            }
            completeTriggerTable = insertTrigger + statementDelete + finalInsert;

            String tiggerName = "updateNotification" + tablaActual.getName();
            String dropUpdate = "IF EXISTS (SELECT * FROM sys.objects WHERE [type] = 'TR' AND [name] = '" + tiggerName + "') DROP TRIGGER " + tiggerName + ";";
            coneccionBaseDatos.executeUpdate(dropUpdate);

            coneccionBaseDatos.executeUpdate(completeTriggerTable);
        }

    }

}
