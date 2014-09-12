/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.replication.user.GraphicInterface;

import com.replication.admin.ConnectionManagement.RPConnectionInterface;
import com.replication.admin.ConnectionManagement.RPConnectionsFactory;
import com.replication.admin.DataStructure.RPTableSLL;
import com.replication.admin.DataTransfer.RPAccessTableColumns;
import com.replication.admin.DataTransfer.RPCreateHistoricalMSQL;
import com.replication.admin.DataTransfer.RPCreateHistoricalMYSQL;
import com.replication.admin.DataTransfer.RPCreateTableMSQL;
import com.replication.admin.DataTransfer.RPCreateTableMYSQL;
import com.replication.admin.DataTransfer.RPCreateTriggersMYSQL;
import com.replication.admin.DataTransfer.RPCreateTriggersSQL;
import com.replication.admin.RPBaseData.RPBaseData;
import com.replication.admin.RPConectionData.RPBaseInformation;
import com.replication.admin.RPConectionData.RPConection;
import com.replication.admin.RPConectionData.RPCreateBase;
import com.replication.admin.TreadsReplication.RPTreadHistorical;
import com.replication.user.Error.InfError;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Melvin
 */
public class Frame extends javax.swing.JFrame {

    private final ImageIcon iconEdit;
    private final ImageIcon iconTables;
    private final ImageIcon iconStop;
    private int rowCount;

    private final List<RPTableSLL> DataBases;

    /**
     * Creates new form Frame
     */
    public Frame() {
        initComponents();
        tableConfig();

        iconEdit = new javax.swing.ImageIcon(getClass().
                getResource("/com/replication/user/images/sync.png"));
        iconTables = new javax.swing.ImageIcon(getClass().
                getResource("/com/replication/user/images/sql.png"));
        iconStop = new javax.swing.ImageIcon(getClass().
                getResource("/com/replication/user/images/stop.png"));
        rowCount = 0;

        DataBases = new LinkedList<>();

        FilllTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Replication Manager");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Calibri Light", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 102, 102));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Replication Manager");

        table.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        table.setFont(new java.awt.Font("Calibri Light", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Motor Origen\n", "IP Origen\n", "Nombre BD\n", "Usuario Origen\n", "Contraseña\nOrigen\n", "\n-\n", "Motor Destino\n", "IP Destino\n", "Nombre BD\n", "Usuario Destino\n", "Contraseña\nDestino\n", "Sincronizar\n", "Detener\n","idConnection\n"
            }
        ){
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, true, true,true,true,true,true,true,false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        }

    );
    table.setCellSelectionEnabled(true);
    table.setFocusable(false);
    table.setGridColor(new java.awt.Color(204, 204, 204));
    table.setRowHeight(20);
    table.getTableHeader().setReorderingAllowed(false);
    jScrollPane1.setViewportView(table);

    jButton1.setText("Agregar");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jButton2.setText("jButton2");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1442, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jButton1)
                    .addGap(18, 18, 18)
                    .addComponent(jButton2)
                    .addGap(0, 0, Short.MAX_VALUE))
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addGap(10, 10, 10)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButton1)
                .addComponent(jButton2))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        String[] datos = {"", "localhost", "", "sa", "1234", "Ver Tablas", "", "localhost", "", "", "", ""};
        model.addRow(datos);

        TableColumn exp2 = table.getColumnModel().getColumn(0);//Agrega un combobox a la celda 
        exp2.setCellEditor(new ComboBoxEditor());

        TableColumn exp3 = table.getColumnModel().getColumn(6);//Agrega un combobox a la celda 
        exp3.setCellEditor(new ComboBoxEditor());

        table.setValueAt(iconEdit, rowCount, 11);
        table.setValueAt(iconTables, rowCount, 5);
        table.setValueAt(iconStop, rowCount, 12);

        rowCount++;

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        RPTreadHistorical historical = new RPTreadHistorical();
        historical.setPausar(true);
        historical.start();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables

    private void tableConfig() {

        jScrollPane1.setBorder(BorderFactory.createEmptyBorder());

        DefaultTableCellRenderer centerRender = new DefaultTableCellRenderer();
        centerRender.setHorizontalAlignment(JLabel.CENTER);

        table.setRowHeight(35);

        table.getColumnModel().getColumn(13).setMinWidth(0);
        table.getColumnModel().getColumn(13).setPreferredWidth(0);
        table.getColumnModel().getColumn(13).setMaxWidth(0);

        table.getColumnModel().getColumn(0).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(1).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(2).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(5).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(6).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(7).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(8).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(9).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(10).setCellRenderer(centerRender);
        table.getColumnModel().getColumn(11).setCellRenderer(centerRender);

        table.getTableHeader().setDefaultRenderer(centerRender);
        table.getTableHeader().setResizingAllowed(true);
        table.getTableHeader().setFont(new Font("Calibri Light", Font.PLAIN, 30));

        MultiLineHeaderRenderer renderer = new MultiLineHeaderRenderer();
        Enumeration e = table.getColumnModel().getColumns();

        while (e.hasMoreElements()) {
            ((TableColumn) e.nextElement()).setHeaderRenderer(renderer);
        }
        Action accion;
        accion = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int fila = table.getSelectedRow();

                String Motor_Origen = table.getValueAt(fila, 0).toString();
                String IP_Origen = table.getValueAt(fila, 1).toString();
                String Nombre_BD = table.getValueAt(fila, 2).toString();
                String Usuario = table.getValueAt(fila, 3).toString();
                String Contraseña = table.getValueAt(fila, 4).toString();

                RPConection conectionMySql = new RPConection();
                RPConnectionInterface RPconnect;

                SeleccionarTablas selector = new SeleccionarTablas(null, true);

                switch (Motor_Origen) {
                    case "MySQL":

                        conectionMySql.setDatabase(Nombre_BD);
                        conectionMySql.setDriver("com.mysql.jdbc.Driver");
                        conectionMySql.setUser(Usuario);
                        conectionMySql.setPass(Contraseña);
                        conectionMySql.setIp(IP_Origen);
                        conectionMySql.setPort("3306");

                        RPconnect = RPConnectionsFactory.createConnection("MySQL");
                        RPconnect.setConection(conectionMySql);

                        if (RPconnect.validConnection()) {
                            RPBaseInformation baseInformation = new RPBaseInformation(RPconnect);

                            ArrayList<String> tablesMySQL = baseInformation.getTablesMySQL();

                            if (!tablesMySQL.isEmpty()) {

                                ArrayList<String> data = selector.showDialog(tablesMySQL);
                                RPTableSLL tablesList = new RPTableSLL(IP_Origen + "::" + Nombre_BD);//Se crea una lista de tablas
                                data.stream().forEach((data1) -> {
                                    tablesList.insert(data1);// se insertan las tablas                             
                                });

                                RPAccessTableColumns access = new RPAccessTableColumns(tablesList, Motor_Origen, RPconnect);
                                access.getTableColums();
                                DataBases.add(tablesList);//se agrega a la lista de bases de datos

                                tablesList.printTables();
                            }
                        }
                        break;

                    case "SQLMS":

                        conectionMySql.setDatabase(Nombre_BD);
                        conectionMySql.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        conectionMySql.setUser(Usuario);
                        conectionMySql.setPass(Contraseña);
                        conectionMySql.setIp(IP_Origen);
                        conectionMySql.setPort("1433");

                        RPconnect = RPConnectionsFactory.createConnection("SQLMS");
                        RPconnect.setConection(conectionMySql);

                        if (RPconnect.validConnection()) {
                            RPBaseInformation baseInformation1 = new RPBaseInformation(RPconnect);

                            ArrayList<String> tablesMS_SQL = baseInformation1.getTablesMSSQL();

                            if (!tablesMS_SQL.isEmpty()) {

                                ArrayList<String> data = selector.showDialog(tablesMS_SQL);

                                RPTableSLL tablesList = new RPTableSLL(IP_Origen + "::" + Nombre_BD);//Se crea una lista de tablas

                                data.stream().forEach((data1) -> {
                                    tablesList.insert(data1);// se insertan las tablas                             
                                });

                                RPAccessTableColumns access = new RPAccessTableColumns(tablesList, Motor_Origen, RPconnect);
                                access.getTableColums();
                                DataBases.add(tablesList);//se agrega a la lista de bases de datos
                            }
                        }
                        break;

                    default:
                        System.out.println("No se ha creado codigo para ello");
                }

            }
        };

        ButtonColumn buttonViewTables = new ButtonColumn(table, accion, 5);
        buttonViewTables.setMnemonic(KeyEvent.VK_DELETE);

        ButtonColumn buttonSync = new ButtonColumn(table, syncDataBases, 11);
        buttonSync.setMnemonic(KeyEvent.VK_DELETE);

        ButtonColumn buttonAction = new ButtonColumn(table, actionReplication, 12);
        buttonAction.setMnemonic(KeyEvent.VK_DELETE);

    }
    public Action syncDataBases = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int fila = table.getSelectedRow();

            String Motor_Destino = table.getValueAt(fila, 6).toString();
            String IP_Destino = table.getValueAt(fila, 7).toString();
            String Nombre_BD_Destino = table.getValueAt(fila, 8).toString();
            String Usuario_Destino = table.getValueAt(fila, 9).toString();
            String Contraseña_Destino = table.getValueAt(fila, 10).toString();

            String Motor_Origen = table.getValueAt(fila, 0).toString();
            String IP_Origen = table.getValueAt(fila, 1).toString();
            String Nombre_BD = table.getValueAt(fila, 2).toString();
            String Usuario = table.getValueAt(fila, 3).toString();
            String Contraseña = table.getValueAt(fila, 4).toString();

            RPConnectionInterface RPconnect;

            switch (Motor_Destino) {

                case "MySQL":

                    RPConection conectionMySql = new RPConection();
                    conectionMySql.setDatabase("dbo");
                    conectionMySql.setDriver("com.mysql.jdbc.Driver");
                    conectionMySql.setUser(Usuario_Destino);
                    conectionMySql.setPass(Contraseña_Destino);
                    conectionMySql.setIp(IP_Destino);
                    conectionMySql.setPort("3306");

                    RPconnect = RPConnectionsFactory.createConnection("MySQL");
                    RPconnect.setConection(conectionMySql);

                    if (RPconnect.validConnection()) {

                        RPCreateTableMYSQL rp_createMYSQL = new RPCreateTableMYSQL(DataBases.get(0), Nombre_BD_Destino);

                        RPCreateBase creatorMYSQL = new RPCreateBase(RPconnect);
                        creatorMYSQL.replicTables(DataBases.get(0), "MYSQL", Nombre_BD_Destino);

                        //Se vuelve a conectar la base de datos
                        conectionMySql.setDatabase(Nombre_BD_Destino);

                        //se agrega la tabla de historial
                        RPCreateHistoricalMYSQL historialMYSQL = new RPCreateHistoricalMYSQL(RPconnect);
                        historialMYSQL.createHistorical();

                        //se agregan los trigers a cada tabla
                        RPCreateTriggersMYSQL trigger = new RPCreateTriggersMYSQL(RPconnect, DataBases.get(0));
                        trigger.CreateInsertTrigger();

                        // se agrega la nueva conexion a la base de datos de Replica
                        RPBaseData baseData = new RPBaseData();
                        baseData.RPSaveConnecton(Motor_Origen, IP_Origen, Nombre_BD, Usuario, Contraseña, Motor_Destino, IP_Destino, Nombre_BD_Destino, Usuario_Destino, Contraseña_Destino);
                        InfError.showMessage(null, "EXITO MYSQL");
                    }
                    break;

                case "SQLMS":

                    RPConection conectionMSQL = new RPConection();

                    conectionMSQL.setDatabase("master");
                    conectionMSQL.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conectionMSQL.setUser(Usuario_Destino);
                    conectionMSQL.setPass(Contraseña_Destino);
                    conectionMSQL.setIp(IP_Destino);
                    conectionMSQL.setPort("1433");

                    RPconnect = RPConnectionsFactory.createConnection("SQLMS");
                    RPconnect.setConection(conectionMSQL);

                    if (RPconnect.validConnection()) {

                        RPCreateTableMSQL rp_createMSQL = new RPCreateTableMSQL(DataBases.get(0), Nombre_BD_Destino);
                        rp_createMSQL.createScript();

                        // crea la base de datos
                        String crearBase = "CREATE DATABASE " + Nombre_BD_Destino + ";";
                        RPconnect.execute(crearBase);

                        //Se vuelve a conectar la base de datos
                        conectionMSQL.setDatabase(Nombre_BD_Destino);
                        RPCreateBase creatorMSQL = new RPCreateBase(RPconnect);
                        creatorMSQL.replicTables(DataBases.get(0), "SQL SERVER", Nombre_BD_Destino);

                        //se agrega la tabla de historial
                        RPCreateHistoricalMSQL historialMSQL = new RPCreateHistoricalMSQL(RPconnect);
                        historialMSQL.createHistorical();

                        //se agregan los trigers a cada tabla        
                        RPCreateTriggersSQL.CreateDeleteTrigger(DataBases.get(0), RPconnect);
                        RPCreateTriggersSQL.CreateInsertTrigger(DataBases.get(0), RPconnect);
                        RPCreateTriggersSQL.CreateUpdateTrigger(DataBases.get(0), RPconnect);

                        // se agrega la nueva conexion a la base de datos de Replica
                        RPBaseData baseData = new RPBaseData();
                        baseData.RPSaveConnecton(Motor_Origen, IP_Origen, Nombre_BD, Usuario, Contraseña, Motor_Destino, IP_Destino, Nombre_BD_Destino, Usuario_Destino, Contraseña_Destino);

                        InfError.showMessage(null, "EXITO SQL SERVER");
                    }
                    break;

                default:
                    System.out.println("No se ha creado codigo para ello");
            }

            switch (Motor_Origen) {

                case "MySQL":

                    RPConection conectionMySql = new RPConection();
                    conectionMySql.setDatabase(Nombre_BD);
                    conectionMySql.setDriver("com.mysql.jdbc.Driver");
                    conectionMySql.setUser(Usuario);
                    conectionMySql.setPass(Contraseña);
                    conectionMySql.setIp(IP_Origen);
                    conectionMySql.setPort("3306");

                    RPconnect = RPConnectionsFactory.createConnection("MySQL");
                    RPconnect.setConection(conectionMySql);

                    if (RPconnect.validConnection()) {

                        //se agrega la tabla de historial
                        RPCreateHistoricalMYSQL historialMYSQL = new RPCreateHistoricalMYSQL(RPconnect);
                        historialMYSQL.createHistorical();

                        //se agregan los trigers a cada tabla
                        RPCreateTriggersMYSQL trigger = new RPCreateTriggersMYSQL(RPconnect, DataBases.get(0));
                        trigger.CreateInsertTrigger();
                    }
                    break;

                case "SQLMS":

                    RPConection conectionMSQL = new RPConection();

                    conectionMSQL.setDatabase(Nombre_BD);
                    conectionMSQL.setDriver("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    conectionMSQL.setUser(Usuario);
                    conectionMSQL.setPass(Contraseña);
                    conectionMSQL.setIp(IP_Origen);
                    conectionMSQL.setPort("1433");

                    RPconnect = RPConnectionsFactory.createConnection("SQLMS");
                    RPconnect.setConection(conectionMSQL);

                    if (RPconnect.validConnection()) {

                        //se agrega la tabla de historial
                        RPCreateHistoricalMSQL historialMSQL = new RPCreateHistoricalMSQL(RPconnect);
                        historialMSQL.createHistorical();

                        //se agregan los trigers a cada tabla        
                        RPCreateTriggersSQL.CreateDeleteTrigger(DataBases.get(0), RPconnect);
                        RPCreateTriggersSQL.CreateInsertTrigger(DataBases.get(0), RPconnect);
                        RPCreateTriggersSQL.CreateUpdateTrigger(DataBases.get(0), RPconnect);

                    }
                    break;

                default:
                    System.out.println("No se ha creado codigo para ello");
            }

        }

    };

    public Action actionReplication = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("NO ACTION");
        }

    };

    private void FilllTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        RPBaseData baseData = new RPBaseData();
        ArrayList<String[]> connection = baseData.getConnection();
        connection.stream().forEach((connection1) -> {
            model.addRow(connection1);
            rowCount++;
        });
    }

}
