/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.replication.admin.TreadsReplication;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
import com.replication.admin.DataTransfer.RPSynchronizeReply;
import com.replication.admin.DataTransfer.RPTriggersActionMSQL;
import com.replication.admin.DataTransfer.RPTriggersActionMYSQL;
import com.replication.admin.RPConectionData.RPConection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edward
 */
public class RPTreadIndividualBase extends Thread {
  
    RPConnectionInterface conexionBaseDatosSQL;  //conexion para hacer consultas
    List<RPConection> dataBaseConectionsOrigenes;   //conexion con todas la bases de datos que son origenes
    boolean pausa;
     
   public RPTreadIndividualBase()
   {
       //crea la conexion con la base
       RPConection connection = new RPConection();
        connection.setDatabase("MotorBase");
        connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection.setIp("localhost");
        connection.setPass("1234");
        connection.setPort("1433");
        connection.setUser("sa");
        
        //estado del hilo
        pausa= true;
        
        //setea algunas variables
        conexionBaseDatosSQL = RPConnectionsFactory.createConnection("SQLMS");
        conexionBaseDatosSQL.setConection(connection);
   }
   
   //consulto 
   public ResultSet consultarHistorial()
   {
        ResultSet historial = conexionBaseDatosSQL.makeQuery("SELECT TOP 1000 [idLog],[table_name],[action],[row_pk],[field_name],[old_value],[new_value],[timestamp],[consultado],[nombreBaseOrigen] FROM [MotorBase].[dbo].[Log] where consultado = 0");
     
        return historial;
   }
   
   public List<RPConection> getBasesAReplicar(String nombreBaseOrigen)
   {
        ResultSet conexionesAReplicar = conexionBaseDatosSQL.makeQuery("SELECT TOP 1000 [idConnection],[DBMSInput],[ipInput],[DBNameInput],[userInput],[passwordInput],[DBMSOutput],[ipOutput],[DBNameOutput],[userOutput],[passwordOutput],[stateConnection] FROM [MotorBase].[dbo].[connections] where DBNameInput ='"+ nombreBaseOrigen+"'");
        List<RPConection> basesAReplicar= new ArrayList<>();
        
        try {
            //analiza el ResultSet
             while (conexionesAReplicar.next()) 
            {
                if (conexionesAReplicar != null) {
                    String tipodbms = conexionesAReplicar.getString("DBMSInput");

                    RPConection connection = new RPConection();
                    connection.setDatabase(conexionesAReplicar.getString("DBNameInput"));

                    if (tipodbms.equals("SQLMS")) {
                        connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        connection.setPort("1433");
                        connection.setTypeDatabase("SQLMS");
                    } else {
                        connection.setDriver("com.mysql.jdbc.Driver");
                        connection.setPort("3306");
                        connection.setTypeDatabase("MySQL");
                    }

                    connection.setIp(conexionesAReplicar.getString("ipInput"));
                    connection.setPass(conexionesAReplicar.getString("passwordInput"));
                    connection.setUser(conexionesAReplicar.getString("userInput"));

                    basesAReplicar.add(connection);

                }
            }
          return basesAReplicar;
          
        } catch (SQLException ex) {
            Logger.getLogger(RPTreadIndividualBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            return basesAReplicar;
        }
     
   }
   
   public void desactivarTriggers(String dbms,RPConnectionInterface conexionBaseActual ){
       if (dbms.equals("SQLMS")) {
           RPTriggersActionMSQL controlTriggersSQL = new RPTriggersActionMSQL(conexionBaseActual);
           controlTriggersSQL.stopTriggersMSQL();
       } else {
           RPTriggersActionMYSQL controlTriggersMySql = new RPTriggersActionMYSQL(conexionBaseActual);
           controlTriggersMySql.stopTriggersMYSQL();
       }
   }
   
    public void activarTriggers(String dbms,RPConnectionInterface conexionBaseActual ){
       if (dbms.equals("SQLMS")) {
           RPTriggersActionMSQL controlTriggersSQL = new RPTriggersActionMSQL(conexionBaseActual);
           controlTriggersSQL.startTriggersMSQL();
       } else {
           RPTriggersActionMYSQL controlTriggersMySql = new RPTriggersActionMYSQL(conexionBaseActual);
           controlTriggersMySql.startTriggersMYSQL();
       }
   }

    public boolean isPausa() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }
   
    
   public void replicate()
   {
        //llama al metodoConsultarHistorial
        ResultSet Historial = consultarHistorial();
       
        if(Historial!=null){
        try 
        {
           
            //consulta las bases a la que este destino
            String nombreBaseOrigen = "ReadingDBLog";//Historial.getString("nombreBaseOrigen");
            List<RPConection> basesAReplicar = getBasesAReplicar(nombreBaseOrigen);
            
            for(RPConection conexionActual: basesAReplicar)
            {
                while(Historial.next())
                {
                   String dbms      = conexionActual.getTypeDatabase();
                   String tableName = Historial.getString("table_name");
                   String action    = Historial.getString("action");
                   String rowPK     = Historial.getString("row_pk");
                   String fieldName = Historial.getString("field_name");
                   String oldValue  = "null";
                   String newValue  = Historial.getString("new_value");
                   
                  //crea una conexionBASE con la conexion actual
                  RPConnectionInterface conexionBaseActual= RPConnectionsFactory.createConnection(dbms);
                  conexionBaseActual.setConection(conexionActual);
                 
                     //desactiva los triggers
                 //   desactivarTriggers(dbms,conexionBaseActual);
                    //ejecuta el query
                   // RPSynchronizeReply.synchronizeData(dbms,tableName,action,rowPK,fieldName,oldValue,newValue,conexionBaseActual);
                    //re-activa los triggers
                 //   activarTriggers(dbms,conexionBaseActual);
                
                }
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(RPTreadIndividualBase.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
   }
   
     @Override
    public void run() {
        while (pausa) {
            try {
                replicate();
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(RPTreadHistorical.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
   
}
