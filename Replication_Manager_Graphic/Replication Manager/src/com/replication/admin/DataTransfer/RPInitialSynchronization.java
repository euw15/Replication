/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class RPInitialSynchronization {

    private String[] colums;
    private Object[][] Informacion;

    public  RPInitialSynchronization() {
        colums = null;
        Informacion = null;
    }

    public void InitialSynchronization(ArrayList<String> tablas,
            RPConnectionInterface connectionInput,
            RPConnectionInterface connectionOutPut) {

        //Obtener las columnas vs datos para cada tabla en la coneccion input
        for (int i = 0; i < tablas.size(); i++) {
           
            ResultSet ColumsData
                    = connectionInput.makeQuery("SELECT * FROM "
                            + tablas.get(i));
            //Se obtiene las columnas de las tablas
            setColums(Get_Columnas(ColumsData));
            //Se obtiene los datos de las tablas
            setData(ResultSet_Array(ColumsData));
            
            //////////////****pruebas print************
//            for (int j = 0; j < Informacion.length; j++) {
//                for (int k = 0; k < colums.length; k++) {
//                   
//                    System.out.println(Informacion[j][k]);
//                    
//                }
//                 
//                
//            }
            //////////////////////////////////////
           
            
            ///////////////////////////
            /////para cada columnas vs datos de cada tabla: escribir los datos en la connecion destino
            for (int j = 0; j < Informacion.length; j++) {
                String datos = "";
                for (int k = 0; k < colums.length; k++) {
                    //Se obtiene el dato para cada columna
                    datos += Informacion[j][k] + ",";

                }
                datos = datos.substring(0, datos.length() - 1);
                //System.out.println("INSERT INTO " + tablas.get(i) + " VALUES(" + datos + ");");
                connectionOutPut.executeUpdate("INSERT INTO " + tablas.get(i) + " VALUES(" + datos + ");");

            }
        }

        ///////////////////////////////////////////////////////////
    }

    private String[] Get_Columnas(ResultSet rs) {
        String[] etiquetas = null;
        try {
            ResultSetMetaData metaDatos = rs.getMetaData();
            int numeroColumnas = metaDatos.getColumnCount();
            etiquetas = new String[numeroColumnas];
            for (int i = 0; i < numeroColumnas; i++) {
                etiquetas[i] = metaDatos.getColumnLabel(i + 1);
            }
        } catch (Exception e) {
            System.out.println("No se pudo obtener las columnas");

        }
        return etiquetas;
    }

    public void setColums(String[] colums) {
        this.colums = colums;
    }

    private Object[][] ResultSet_Array(ResultSet rs) {
        Object[][] lista_datos = null;
        try {
            rs.last();
            ResultSetMetaData rsmd = rs.getMetaData();
            int numCols = rsmd.getColumnCount();
            int numFils = rs.getRow();
            lista_datos = new Object[numFils][numCols];
            int j = 0;
            rs.beforeFirst();
            while (rs.next()) {
                for (int i = 0; i < numCols; i++) {
                    lista_datos[j][i] = rs.getObject(i + 1);
                }
                j++;

            }
        } catch (Exception e) {
            System.out.println("No se pudo convertir en arreglo");

        }
        return lista_datos;
    }

    public void setData(Object[][] data) {
        this.Informacion = data;
    }

}
