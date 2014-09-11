package com.replication.admin.TreadsReplication;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
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
public class RPTreadHistorical extends Thread{

    List<RPConection> dataBaseConections;   //conexion con todas la bases de datos que son origenes 

    RPConection conexionBaseAplicion;
    RPConnectionInterface conexionBaseDatosSQL;
    boolean Pausar;

    public RPTreadHistorical() {

        RPConection connection = new RPConection();
        connection.setDatabase("MotorBase");
        connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        connection.setIp("localhost");
        connection.setPass("123456");
        connection.setPort("1433");
        connection.setUser("sa");

        dataBaseConections = new ArrayList<>();
        conexionBaseAplicion = connection;
        conexionBaseDatosSQL = RPConnectionsFactory.createConnection("SQLMS");
        conexionBaseDatosSQL.setConection(conexionBaseAplicion);
    }

    //rellena la lista dataBaseConections
    public void askForConections() {

        RPConnectionInterface baseConexion = RPConnectionsFactory.createConnection("SQLMS");
        baseConexion.setConection(conexionBaseAplicion);

        ResultSet basesDatoDataSet = baseConexion.makeQuery("select DBMSInput, ipInput, DBNameInput,userInput, passwordInput FROM connections where stateConnection = 1;");

        setdataBaseConections(basesDatoDataSet);

    }

    public void setdataBaseConections(ResultSet basesOrigenes) {
        try {
            while (basesOrigenes.next()) {
                if (basesOrigenes != null) {
                    String tipodbms = basesOrigenes.getString("DBMSInput");

                    RPConection connection = new RPConection();
                    connection.setDatabase(basesOrigenes.getString("DBNameInput"));

                    if (tipodbms.equals("SQLMS")) {
                        connection.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        connection.setPort("1433");
                        connection.setTypeDatabase("SQLMS");
                    } else {
                        connection.setDriver("com.mysql.jdbc.Driver");
                        connection.setPort("3306");
                        connection.setTypeDatabase("MySQL");
                    }
             
                    connection.setIp(basesOrigenes.getString("ipInput"));
                    connection.setPass(basesOrigenes.getString("passwordInput"));
                    connection.setUser(basesOrigenes.getString("userInput"));

                    this.dataBaseConections.add(connection);

                }
            }
        } 
        catch (SQLException ex) {
            Logger.getLogger(RPTreadHistorical.class.getName()).log(Level.SEVERE, null, ex);
        }       
        finally {

        }
    }

    public boolean isPausar() {
        return Pausar;
    }

    public void setPausar(boolean Pausar) {
        this.Pausar = Pausar;
    }
    
    public void hasNewData() throws SQLException 
    {
        //crea la conexion
        //seleciona la base
        for (RPConection conexionActual : this.dataBaseConections) {
            //crea la conexion
            RPConnectionInterface baseConexion = RPConnectionsFactory.createConnection(conexionActual.getTypeDatabase());
            baseConexion.setConection(conexionBaseAplicion);

            //crea el query
            String query = "SELECT TOP 1000 [idHistorial],[table_name],[action],[row_pk],[field_name],[old_value],[new_value],"
                    + "[timestamp],[consultado] FROM [" + conexionActual.getDatabase() + "].[dbo].[Historial] where [consultado] = 0";
            ResultSet historialResultSet = baseConexion.makeQuery(query);

            //si es nula no hace nada
            if (historialResultSet != null) {

                //crea el metodo de insert
                while (historialResultSet.next()) {

                    String nombreTabla = historialResultSet.getString("table_name");
                    String action = historialResultSet.getString("action");
                    String rowPk = historialResultSet.getString("row_pk");
                    String fieldName = historialResultSet.getString("field_name");
                    String oldValue = historialResultSet.getString("old_value");
                    String newValue = historialResultSet.getString("new_value");
                    String tiempo = historialResultSet.getString("timestamp");

                    String nombreBaseOrigen = conexionActual.getDatabase();

                    String a = "'" + nombreTabla + "','" + action + "','" + rowPk + "','" + fieldName + "','" + oldValue + "','" + newValue + "','" + nombreBaseOrigen + "'";

                    String replaceAll = a.replaceAll("\\s", "");

                    String queryInsert = "INSERT INTO [MotorBase].[dbo].[Log] ([table_name],[action],[row_pk],[field_name],[old_value],[new_value],[nombreBaseOrigen]) VALUES (" + replaceAll + ")";
                    System.out.println(queryInsert);
                    conexionBaseDatosSQL.execute(queryInsert);

                ResultSet historicalResultSet = baseConexion.makeQuery(query);
                //si es nula no hace nada
                if (historicalResultSet != null) {
                       
                    //crea el metodo de insert
                    while (historicalResultSet.next()) 
                    {
                       
                        String idHistorial = historicalResultSet.getString("idHistorial");
                        String nombreTabla = historicalResultSet.getString("table_name");
                        String action = historicalResultSet.getString("action");
                        String rowPk = historicalResultSet.getString("row_pk");
                        String fieldName = historicalResultSet.getString("field_name");
                        String oldValue = historicalResultSet.getString("old_value");
                        String newValue = historicalResultSet.getString("new_value");
                        String tiempo = historicalResultSet.getString("timestamp");
                        String nombreBaseOrigen = conexionActual.getDatabase();

                        String valores = "'" + nombreTabla + "','" + action + "','" + rowPk + "','" + fieldName + "','" + oldValue + "','" + newValue + "','" + nombreBaseOrigen + "'";

                        String valoresSinEspacio = valores.replaceAll("\\s", "");

                        String queryInsert = "INSERT INTO [MotorBase].[dbo].[Log] ([table_name],[action],[row_pk],[field_name],[old_value],[new_value],[nombreBaseOrigen]) VALUES (" + valoresSinEspacio + ")";
                        String queryUpdateHistorical= "";
                         if (conexionActual.getTypeDatabase().equals("SQLMS")) 
                         {
                            queryUpdateHistorical = "UPDATE [" + conexionActual.getDatabase() + "].[dbo].[Historial] SET [consultado] = 1 WHERE [idHistorial] = " + idHistorial;
                         }
                         else
                         {
                            queryUpdateHistorical = "update Historical set consultado = 1 where idHistorial=" + idHistorial;
                         }
                         System.out.println(queryUpdateHistorical);
                         System.out.println(queryInsert);
                        baseConexion.executeUpdate(queryUpdateHistorical);
                        conexionBaseDatosSQL.executeUpdate(queryInsert);

                    }
                }
            }
        }
    }
    @Override
     public void run()
     {
       while(Pausar)
       {
           try {
               askForConections();
               hasNewData();
               sleep(1000);
              
           } catch (SQLException ex) {
               Logger.getLogger(RPTreadHistorical.class.getName()).log(Level.SEVERE, null, ex);
           
               
            }   catch (InterruptedException ex) {
               Logger.getLogger(RPTreadHistorical.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
     }

}
