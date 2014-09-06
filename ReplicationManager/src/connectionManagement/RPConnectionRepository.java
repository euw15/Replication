/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package connectionManagement;

import RPConectionData.RPConection;
import java.sql.ResultSet;

/**
 *
 * @author Jason
 */
public interface RPConnectionRepository {
   /**
    * 
    * @return 
    */
    public ResultSet makeQuery();

}
