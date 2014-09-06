/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package replicationmanager;

import RPConectionData.RPConection;
import connectionManagement.RPConnectionInterface;
import connectionManagement.RPConnectionsFactory;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class ReplicationManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RPConection conectionMySql = new RPConection();
        conectionMySql.setDatabase("cargodispatcher");
        conectionMySql.setDriver("com.mysql.jdbc.Driver");
        conectionMySql.setUser("root");
        conectionMySql.setPass("123456");
        conectionMySql.setIp("localhost");
        conectionMySql.setPort("3306");
       

        RPConnectionInterface test = RPConnectionsFactory.createConnection("MySQL");
        test.setConection(conectionMySql);
        
        ResultSet makeQuery = test.makeQuery("select * from Billing");
        try {
            while (makeQuery.next()) {
                System.out.println(makeQuery.getInt("tax"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReplicationManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
