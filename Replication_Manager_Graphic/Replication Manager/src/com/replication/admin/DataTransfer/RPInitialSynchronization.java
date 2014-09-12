/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPTableSLL;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class RPInitialSynchronization {

    private String[] colums;
    private Object[][] Informacion;

    public RPInitialSynchronization() {
        colums = null;
        Informacion = null;
    }

    /**
     * Permite hacer una sincronizacion inicial de los datos de una base origen
     * a la base destino
     *
     * @param tablas
     * @param connectionInput
     * @param connectionOutPut
     * @throws SQLException
     */
    public void InitialSynchronization(RPTableSLL tablas,
            RPConnectionInterface connectionInput,
            RPConnectionInterface connectionOutPut) throws SQLException {

        //Obtener las columnas y sus datos para cada tabla en la coneccion input
        for (int i = 0; i < tablas.size(); i++) {

            ResultSet ColumsData
                    = connectionInput.makeQuery("SELECT * FROM "
                            + tablas.getElement(i).getName() + ";");

            //Se obtiene las columnas de las tablas
            setColums(Get_Columnas(ColumsData));

            //Se obtiene los datos de las tablas
            if (connectionInput.getTypeConnection() == "MySQL") {

                setData(ResultSet_ArrayMysql(ColumsData));
            } else {

                setData(ResultSet_ArrayMSSQL(ColumsData));

            }

            //Para cada columnas con sus datos de cada tabla: 
            //escribir los datos en la connecion destino
            for (int j = 0; j < Informacion.length; j++) {
                String datos = "'";
                for (int k = 0; k < colums.length; k++) {
                    //Se obtiene el dato para cada columna
                    datos += Informacion[j][k] + "','";

                }
                //los datos que se van a insertar en la tabla en una pluta
                datos = datos.substring(0, datos.length() - 2);

                if (connectionInput.getTypeConnection() == "MySQL") {
                    connectionOutPut.executeUpdate("INSERT INTO "
                            + tablas.getElement(i).getName() + " VALUES(" + datos + ");");
                } else {
                    connectionOutPut.executeUpdate("INSERT INTO "
                            + tablas.getElement(i).getName() + " VALUES(" + datos + ") "
                            + ";");
                }
            }
        }

    }

    /**
     * Permite obtener los nombres de las columnas de una tabla
     *
     * @param rs
     * @return
     */
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

    /**
     * Permite obtener los datos de un result en forma de matriz esto para el
     * caso de MySQL
     *
     * @param rs
     * @return
     */
    private Object[][] ResultSet_ArrayMysql(ResultSet rs) {
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

    /**
     * Permite obtener los datos de un result en forma de matriz esto para el
     * caso de MSSQL
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private Object[][] ResultSet_ArrayMSSQL(ResultSet rs) throws SQLException {

        ArrayList<Object> tmp = new ArrayList();
        Object[][] lista_datos = null;
        try {

            int count = 0;
            while (rs.next()) {

                int cantidadColumnas = rs.getMetaData().getColumnCount();
                Object[] listaTmp = new Object[cantidadColumnas];
                for (int i = 1; i <= cantidadColumnas; i++) {
                    Object objects = rs.getObject(i);
                    listaTmp[i - 1] = objects;

                }
                count++;
                tmp.add(listaTmp);

            }
            lista_datos = new Object[count][];
            for (int i = 0; i < count; i++) {
                lista_datos[i] = (Object[]) tmp.get(i);
            }

        } catch (Exception e) {

            System.out.println("No se pudo convertir en arreglo");

        }
        return lista_datos;
    }

}
