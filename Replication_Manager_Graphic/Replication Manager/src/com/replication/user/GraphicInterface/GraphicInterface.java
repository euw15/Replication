/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.user.GraphicInterface;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
import com.replication.admin.DataTransfer.RPSynchronizeReply;
import com.replication.admin.RPConectionData.RPConection;
import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Melvin
 */
public class GraphicInterface {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {

//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException |
//                IllegalAccessException | UnsupportedLookAndFeelException e) {
//        }
//
//        Frame a = new Frame();
//        a.setLocationRelativeTo(null);
//        a.setVisible(true);
        
        
        RPConnectionInterface RPconnect;

        RPConection conectionMySql = new RPConection();
        conectionMySql.setDatabase("COMPANY");
        conectionMySql.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conectionMySql.setPort("1433");
        conectionMySql.setUser("sa");
        conectionMySql.setPass("1234");
        conectionMySql.setIp("localhost");

        RPconnect = RPConnectionsFactory.createConnection("SQLMS");
        RPconnect.setConection(conectionMySql);

//------------------Prueba de connection-----------------------------------------////////////////////////////
//        ResultSet makeQuery = RPconnect.makeQuery("SELECT * FROM billing;");
//        makeQuery.next();
//        System.out.println(makeQuery.getString("idBilling"));
        ///-----------------------------------------------------
        
        RPSynchronizeReply prueba = new RPSynchronizeReply();

        prueba.synchronizeData("SQL", "prueba", "INSERT",
                "90", "id", "NULL", "90",
                RPconnect);
        
        
        
    }

}
