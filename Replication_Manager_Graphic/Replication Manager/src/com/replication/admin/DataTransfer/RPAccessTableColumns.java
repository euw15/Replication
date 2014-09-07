package com.replication.admin.DataTransfer;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.DataStructure.RPTabla;
import com.replication.admin.DataStructure.RPTableSLL;
import com.replication.admin.RPConectionData.RPBaseInformation;

/**
 *
 * @author Melvin
 */
public class RPAccessTableColumns {

    private final RPTableSLL tableList;
    private final String Motor_BD;
    private final RPConnectionInterface connect;

    public RPAccessTableColumns(RPTableSLL tableList, String Motor_DB, RPConnectionInterface connect) {
        this.Motor_BD = Motor_DB;
        this.tableList = tableList;
        this.connect = connect;
    }

    public void getTableColums() {
        
        RPBaseInformation baseInformation = new RPBaseInformation(connect);

        for (RPTabla curr = tableList.getFirst(); curr != null; curr = curr.getSucc()) {
            curr.insertColumn(baseInformation.getColumnsMYSQL(curr.getName()));
        }

        
    }

}
