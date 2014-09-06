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
import java.util.ArrayList;
import java.util.List;
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
        
        
        RPBaseInformation prueba = new RPBaseInformation(test);
         ArrayList<String> listTables = prueba.getTables();//getMetaData("billing");//test.makeQuery("select * from Billing");
        List<List<String>> metaData = prueba.getMetaData(listTables);
        
        for (int i = 0; i < metaData.size(); i++) {
            for (int j = 0; j < metaData.get(i).size(); j++) {
                System.out.print(metaData.get(i).get(j) + " - ");
            }
            System.out.println("");
            
        }
    }

}
