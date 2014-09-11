/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.replication.admin.TreadsReplication;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
import com.replication.admin.RPConectionData.RPConection;
import java.sql.ResultSet;

/**
 *
 * @author Edward
 */
public class RPTreadIndividualBase {
  
    RPConnectionInterface conexionBaseDatosSQL;  //conexion para hacer consultas
   
   public RPTreadIndividualBase()
   {
       RPConection connection = new RPConection();
        connection.setDatabase("MotorBase");
        connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection.setIp("localhost");
        connection.setPass("123456");
        connection.setPort("1433");
        connection.setUser("sa");
        
        conexionBaseDatosSQL = RPConnectionsFactory.createConnection("SQLMS");
        conexionBaseDatosSQL.setConection(connection);
   }
   
   public ResultSet consultarHistorial()
   {
        ResultSet historial = conexionBaseDatosSQL.makeQuery("SELECT TOP 1000 [idLog],[table_name],[action],[row_pk],[field_name],[old_value],[new_value],[timestamp],[consultado],[nombreBaseOrigen] FROM [MotorBase].[dbo].[Log] where consultado = 0");
        return historial;
   }
   
   public void replicate()
   {
       //llama al metodoConsultarHistorial
       
       //analiza el ResultSet
       
       //Busca que tipo de accion es
       
       //LLAMA METODO QUE TIENE LISTA CON TODAS LAS BASES A LAS QUE TIENE QUE REPLICAR
            //dentro de for para cada base
            //1.desabilita triggers
            //2.llama a los metodos para crear insersion dependiendo de la base
            //3.ejecuta la insercion
            //4.habilita triggers
     
   }
   
}
