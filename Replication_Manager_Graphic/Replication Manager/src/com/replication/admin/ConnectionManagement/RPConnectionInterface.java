/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.admin.ConnectionManagement;

import com.replication.admin.RPConectionData.RPConection;
import java.sql.ResultSet;

/**
 *
 * @author Jason
 */
public interface RPConnectionInterface {

    /**
     *
     * @param query
     * @return
     */
    public ResultSet makeQuery(String query);

    public void executeUpdate(String query);

    public void setConection(RPConection mConection);

    public RPConection getConection();

    public void makeConnection();
    
    public void execute(String query);

}
