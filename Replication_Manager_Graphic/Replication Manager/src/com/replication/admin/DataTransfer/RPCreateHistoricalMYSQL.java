/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.user.Error.InfError;
import com.replication.user.GraphicInterface.Frame;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Melvin
 */
public class RPCreateHistoricalMYSQL {

    private final RPConnectionInterface connection;

    public RPCreateHistoricalMYSQL(RPConnectionInterface connection) {
        this.connection = connection;
    }

    public void createHistorical() {
        try {

            String historial = "/com/replication/admin/Scripts/InsertHistorical.sql";
            String variable = "/com/replication/admin/Scripts/InsertTableVariable.sql";
            String dropProcedure = "DROP PROCEDURE IF EXISTS addLogTrigger;";
            String procedure = "/com/replication/admin/Scripts/InsertStoreProcedureMYSQL.sql";
            String valor = "INSERT INTO `variable` (`valor`) VALUES ('1');";

            this.connection.executeUpdate(readQuery(historial));
            this.connection.executeUpdate(readQuery(variable));
            this.connection.executeUpdate(dropProcedure);
            this.connection.executeUpdate(readQuery(procedure));
            this.connection.executeUpdate((valor));

        } catch (Exception ex) {
            InfError.showInformation(null, "No se pudo crear la tabla historial en la base de datos MYSQL");
        }
    }

    private static String readQuery(String path) throws Exception {

        InputStream in = Frame.class.getClass().getResourceAsStream(path);
        StringBuilder sb;
        try (BufferedReader input = new BufferedReader(new InputStreamReader(in))) {
            String str;
            sb = new StringBuilder();
            while ((str = input.readLine()) != null) {
                sb.append(str).append("\n");
            }
        }
        return sb.toString();
    }

}
