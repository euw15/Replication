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
public class RPTriggersActionMYSQL {

    private final RPConnectionInterface connection;

    public RPTriggersActionMYSQL(RPConnectionInterface connection) {
        this.connection = connection;

    }

    public void startTriggersMYSQL() {
        connection.executeUpdate("UPDATE `variable` SET `valor`='1' WHERE `idValor`='1';");
    }

    public void stopTriggersMYSQL() {
        connection.executeUpdate("UPDATE `variable` SET `valor`='0' WHERE `idValor`='1';");
         // String path3 = "INSERT INTO `variable` (`valor`) VALUES ('1');";
    }

}
