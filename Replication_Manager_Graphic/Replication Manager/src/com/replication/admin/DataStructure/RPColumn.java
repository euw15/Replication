/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.DataStructure;

/**
 *
 * @author Melvin
 */
public class RPColumn {

    protected RPColumn succ;

    protected String _column_name;
    protected String _type;
    protected String _null;
    protected String _key;
    protected String _default;
    protected String _extra;
    protected boolean _isPK;

    public RPColumn(String columnName, String tipo, String _null, String _key, String _default, String extra) {

        this._column_name = columnName;
        this._type = tipo;
        this._null = _null;
        this._key = _key;
        this._default = _default;
        this._extra = extra;
     
        if (_key.equals("PRI")) {
            this._isPK = true;
        }

  
    }
}
