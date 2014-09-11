/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;

/**
 *
 * @author Melvin
 */
public class RPTriggersActionMSQL {

    private final RPConnectionInterface connection;

    public RPTriggersActionMSQL(RPConnectionInterface connection) {
        this.connection = connection;

    }

    public void startTriggersMSQL() {
        connection.executeUpdate("EXEC sp_msforeachtable \"ALTER TABLE ? ENABLE TRIGGER ALL\";");
    }

    public void stopTriggersMSQL() {
        connection.executeUpdate("EXEC sp_msforeachtable \"ALTER TABLE ? DISABLE TRIGGER ALL\";");
    }

}
