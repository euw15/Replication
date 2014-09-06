/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionManagement;

import RPConectionData.RPConection;

/**
 *
 * @author
 */
public class RPConnectionsFactory {

    public static RPConnectionInterface createConnection(String DataBaseType) {
        
        switch (DataBaseType) {
            case "MySQL":
                return new RPMysqlConcreteConection();
                
            case "SQLMS":
                return new RPMSSQLConcreteConection();

        }

        return null;
    }

}
