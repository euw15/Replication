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
    
    public RPColumn getSucc() 
    {
        return succ;
    }

    public String getColumn_name() {
        return _column_name;
    }

    public void setColumn_name(String _column_name) {
        this._column_name = _column_name;
    }

    public String getNull() {
        return _null;
    }

    public void setNull(String _null) {
        this._null = _null;
    }

    public String getKey() {
        return _key;
    }

    public void setKey(String _key) {
        this._key = _key;
    }

    public String getDefault() {
        return _default;
    }

    public void setDefault(String _default) {
        this._default = _default;
    }

    public String getExtra() {
        return _extra;
    }

    public void setExtra(String _extra) {
        this._extra = _extra;
    }

    public boolean isIsPK() {
        return _isPK;
    }

    public void setIsPK(boolean _isPK) {
        this._isPK = _isPK;
    }

    public String getType() {
        return _type;
    }

    public void setType(String _type) {
        this._type = _type;
    }
    
    
}
