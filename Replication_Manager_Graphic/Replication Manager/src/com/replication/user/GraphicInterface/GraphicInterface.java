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
    public static void main(String[] args)  {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException e) {
        }

        Frame a = new Frame();
        a.setLocationRelativeTo(null);
        a.setVisible(true);   
    }

}
