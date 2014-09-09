/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataTransfer;

import com.replication.admin.DataStructure.RPColumn;
import com.replication.admin.DataStructure.RPColumnSLL;
import com.replication.admin.DataStructure.RPTable;
import com.replication.admin.DataStructure.RPTableSLL;

/**
 *
 * @author Melvin
 */
public class RPCreateTriggersMYSQL {

    RPTableSLL database;

    public RPCreateTriggersMYSQL() {
        System.out.println("klgs");

    }

    public void hola() {
        //recorre todas las tablas creando trigger insert
        for (RPTable tablaActual = database.getFirst(); tablaActual != null; tablaActual = tablaActual.getSucc()) {
            //obtiene el nombre de la columnaPL
            String pkColumna = "";
            RPColumnSLL Columnas = tablaActual.getColums();
            for (RPColumn columnaActual = Columnas.getFirst(); columnaActual != null; columnaActual = columnaActual.getSucc()) {
                if (columnaActual.isIsPK()) {
                    pkColumna = columnaActual.getColumn_name();
                }
            }
            System.out.println(tablaActual.getName());
            System.out.println(pkColumna);
        }
    }
}
