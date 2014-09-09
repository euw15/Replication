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
public class RPCreateTableMYSQL {

    private final RPTableSLL tableList;

    public RPCreateTableMYSQL(RPTableSLL tableList) {
        this.tableList = tableList;
        createScript();
    }

    private void createScript() {

        for (RPTable table = tableList.getFirst(); table != null; table = table.getSucc()) {

            String _script = "# Crea la tabla " + table.getName() + "\n" + "CREATE TABLE IF NOT EXISTS " + table.getName() + "(\n";
            RPColumnSLL columsSLL = table.getColums();
            for (RPColumn column = columsSLL.getFirst(); column != null; column = column.getSucc()) {

                String null_check = nullCheck(column.getNull());
                String primary_check = primaryCheck(column.isIsPK(), column.getColumn_name());
                String default_check = defaultCheck(column.getDefault());
                String extra_check = extraCheck(column.getExtra());
                String key_check = keyCheck(column.getKey(), column.getColumn_name());

                _script += "    " + column.getColumn_name() + "     " + column.getType() + extra_check + "  " + null_check + default_check + primary_check;
                _script += key_check;

            }
            _script = _script.substring(0, _script.length() - 2);
            _script += "\n);\n";
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
            Default = Default.substring(1, Default.length() - 1);
            return " DEFAULT " + Default + " ";
        } else {
            return "";
        }
    }

    private String extraCheck(String extra) {

        if ("auto_increment".equals(extra)) {
            return " AUTO_INCREMENT ";
        } else {
            return "";
        }
    }

    private String keyCheck(String key, String column_name) {

        if ("UNI".equals(key)) {
            return "    UNIQUE (" + column_name + "),\n";
        }
        return "";
    }
}
