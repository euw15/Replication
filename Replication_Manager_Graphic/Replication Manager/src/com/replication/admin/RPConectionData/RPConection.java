/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.RPConectionData;

/**
 *
 * @author Jason
 */
public class RPConection {

    //Contine los parametros de la coneccion
    private String driver;
    private String database;
    private String user;
    private String pass;
    private String port;
    private String ip;
    private String typeDatabase;

    public RPConection(RPConection mConection) {

        this.driver = mConection.driver;
        this.database = mConection.database;
        this.user = mConection.user;
        this.pass = mConection.pass;
        this.port = mConection.port;
        this.ip = mConection.ip;

    }

    public String getTypeDatabase() {
        return typeDatabase;
    }

    public void setTypeDatabase(String typeDatabase) {
        this.typeDatabase = typeDatabase;
    }

    public RPConection() {
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database the database to set
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

}
