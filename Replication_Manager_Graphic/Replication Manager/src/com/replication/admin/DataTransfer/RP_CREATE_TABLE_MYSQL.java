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
public class RP_CREATE_TABLE_MYSQL {

    private final RPTableSLL tableList;

    public RP_CREATE_TABLE_MYSQL(RPTableSLL tableList) {
        this.tableList = tableList;

        createScript();
    }

    private void createScript() {

        for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {

            String _script = "-- Crea la tabla " + table.getName() + "\n" + "CREATE TABLE " + table.getName() + "(\n";

            RPColumnSLL columsSLL = table.getColums();
            for (RPColumn column = columsSLL.getFirst(); column != null; column = column.getSucc()) {

                String type_check = typeCheck(column.getType());
                String null_check = nullCheck(column.getNull());
                String primary_check = primaryCheck(column.isIsPK(), column.getColumn_name());
                String default_check = defaultCheck(column.getDefault());
                String extra_check = extraCheck(column.getExtra());
                String key_check = keyCheck(column.getKey());

                _script += "    " + column.getColumn_name() + "     " + type_check + extra_check + "  " + null_check + key_check + default_check + primary_check;

            }
            _script = _script.substring(0, _script.length() - 2);
            _script += "\n);";
            table.setScript_MYSQL(_script);

        }

    }

    private String typeCheck(String type) {

        if (type.startsWith("int")) {
            return "int";
        }
        return type;
    }

    private String nullCheck(String aNull) {
        if ("NO".equals(aNull)) {
            return "NOT NULL";
        } else {
            return " ";
        }
    }

    private String primaryCheck(boolean isPK, String column_name) {
        if (isPK) {
            return ",\n" + "    PRIMARY KEY (" + column_name + "),\n";
        }
        return ",\n";
    }

    private String defaultCheck(String Default) {
        if (Default != null) {
            return " DEFAULT (1)";
        } else {
            return "";
        }
    }

    private String extraCheck(String extra) {

        if ("auto_increment".equals(extra)) {
            return " IDENTITY(1,1) ";
        } else {
            return "";
        }
    }

    private String keyCheck(String key) {

        if ("UNI".equals(key)) {
            return " UNIQUE ";
        }
        return "";
    }

}
