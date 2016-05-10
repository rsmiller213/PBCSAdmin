/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import javax.swing.JOptionPane;
import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 *
 * @author Randall.Miller
 */
public class PBCSAdmin extends javax.swing.JFrame {
    public File flSourceFile;

    /**
     * Creates new form PBCSAdmin
     */
    public PBCSAdmin() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btgDelimiter = new javax.swing.ButtonGroup();
        MainTabbedPane = new javax.swing.JTabbedPane();
        tabDLMgr = new javax.swing.JPanel();
        pnlDSMgmt = new javax.swing.JPanel();
        lblDelim = new javax.swing.JLabel();
        rbComma = new javax.swing.JRadioButton();
        rbSpace = new javax.swing.JRadioButton();
        rbTab = new javax.swing.JRadioButton();
        rbCustom = new javax.swing.JRadioButton();
        txtCustDelim = new javax.swing.JTextField();
        lblDSMgmt = new javax.swing.JLabel();
        btnLoadSQL = new javax.swing.JButton();
        lblDisplayRows = new javax.swing.JLabel();
        txtDisplayRows = new javax.swing.JTextField();
        lblStartRow = new javax.swing.JLabel();
        txtStartRow = new javax.swing.JTextField();
        lblDisplayRows2 = new javax.swing.JLabel();
        txtHeaderRows = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        pnlColProps = new javax.swing.JPanel();
        lblColumn = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPrefix = new javax.swing.JTextField();
        lblSuffix = new javax.swing.JLabel();
        txtSuffix = new javax.swing.JTextField();
        lblFind = new javax.swing.JLabel();
        txtFind = new javax.swing.JTextField();
        lblReplace = new javax.swing.JLabel();
        txtReplace = new javax.swing.JTextField();
        btnUpdateField = new javax.swing.JButton();
        btnAddColumn = new javax.swing.JButton();
        tabFSMgr = new javax.swing.JPanel();

        rbComma.doClick();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        MainTabbedPane.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);

        tabDLMgr.setPreferredSize(new java.awt.Dimension(1450, 512));

        pnlDSMgmt.setBackground(new java.awt.Color(153, 204, 255));

        lblDelim.setForeground(new java.awt.Color(0, 0, 0));
        lblDelim.setText("Delimiter");

        btgDelimiter.add(rbComma);
        rbComma.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        rbComma.setText("Comma");

        btgDelimiter.add(rbSpace);
        rbSpace.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        rbSpace.setText("Space");

        btgDelimiter.add(rbTab);
        rbTab.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        rbTab.setText("Tab");

        btgDelimiter.add(rbCustom);
        rbCustom.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        rbCustom.setText("Custom");

        lblDSMgmt.setFont(new java.awt.Font("Dialog", 1, 10)); // NOI18N
        lblDSMgmt.setForeground(new java.awt.Color(51, 102, 255));
        lblDSMgmt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDSMgmt.setText("Data Source Management");

        btnLoadSQL.setText("Load SQL");
        btnLoadSQL.setEnabled(false);

        lblDisplayRows.setForeground(new java.awt.Color(0, 0, 0));
        lblDisplayRows.setText("Rows to Display");

        txtDisplayRows.setText("500");

        lblStartRow.setForeground(new java.awt.Color(0, 0, 0));
        lblStartRow.setText("Start Row");
        lblStartRow.setToolTipText("");

        txtStartRow.setText("1");

        lblDisplayRows2.setForeground(new java.awt.Color(0, 0, 0));
        lblDisplayRows2.setText("Header Rows");

        txtHeaderRows.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHeaderRowsActionPerformed(evt);
            }
        });

        btnBrowse.setText("Load File");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDSMgmtLayout = new javax.swing.GroupLayout(pnlDSMgmt);
        pnlDSMgmt.setLayout(pnlDSMgmtLayout);
        pnlDSMgmtLayout.setHorizontalGroup(
            pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                        .addComponent(rbComma, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbTab))
                    .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnBrowse)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLoadSQL))
                    .addComponent(lblDelim, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                        .addComponent(rbSpace)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbCustom)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCustDelim, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lblDSMgmt))
                    .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                        .addComponent(lblDisplayRows)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDisplayRows, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDSMgmtLayout.createSequentialGroup()
                        .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDisplayRows2)
                            .addComponent(lblStartRow))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtStartRow)
                            .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                                .addComponent(txtHeaderRows, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDSMgmtLayout.setVerticalGroup(
            pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDSMgmtLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDSMgmt)
                .addGap(11, 11, 11)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadSQL)
                    .addComponent(btnBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDelim, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(rbTab, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbComma, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbSpace, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbCustom, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCustDelim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDisplayRows)
                    .addComponent(txtDisplayRows, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartRow)
                    .addComponent(txtStartRow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDSMgmtLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDisplayRows2)
                    .addComponent(txtHeaderRows, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblDelim.getAccessibleContext().setAccessibleName("lblDelim");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        btnRefresh.setText("Refresh Table");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        pnlColProps.setBackground(new java.awt.Color(204, 204, 204));
        pnlColProps.setForeground(new java.awt.Color(204, 204, 204));

        lblColumn.setForeground(new java.awt.Color(0, 0, 0));
        lblColumn.setText("Select Column to Edit");

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Prefix");

        txtPrefix.setMaximumSize(new java.awt.Dimension(60, 24));
        txtPrefix.setMinimumSize(new java.awt.Dimension(60, 24));

        lblSuffix.setForeground(new java.awt.Color(0, 0, 0));
        lblSuffix.setText("Suffix");

        txtSuffix.setMaximumSize(new java.awt.Dimension(60, 24));
        txtSuffix.setMinimumSize(new java.awt.Dimension(60, 24));

        lblFind.setForeground(new java.awt.Color(0, 0, 0));
        lblFind.setText("Find");

        txtFind.setMaximumSize(new java.awt.Dimension(60, 24));
        txtFind.setMinimumSize(new java.awt.Dimension(60, 24));
        txtFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFindActionPerformed(evt);
            }
        });

        lblReplace.setForeground(new java.awt.Color(0, 0, 0));
        lblReplace.setText("Replace");

        txtReplace.setMaximumSize(new java.awt.Dimension(60, 24));
        txtReplace.setMinimumSize(new java.awt.Dimension(60, 24));

        btnUpdateField.setText("Update Field");
        btnUpdateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlColPropsLayout = new javax.swing.GroupLayout(pnlColProps);
        pnlColProps.setLayout(pnlColPropsLayout);
        pnlColPropsLayout.setHorizontalGroup(
            pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColPropsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlColPropsLayout.createSequentialGroup()
                        .addComponent(lblSuffix)
                        .addGap(25, 25, 25)
                        .addComponent(txtSuffix, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlColPropsLayout.createSequentialGroup()
                        .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFind)
                            .addComponent(lblReplace))
                        .addGap(12, 12, 12)
                        .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFind, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(txtReplace, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)))
                    .addGroup(pnlColPropsLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(24, 24, 24)
                        .addComponent(txtPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(pnlColPropsLayout.createSequentialGroup()
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblColumn)
                    .addComponent(btnUpdateField))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlColPropsLayout.setVerticalGroup(
            pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlColPropsLayout.createSequentialGroup()
                .addComponent(lblColumn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSuffix)
                    .addComponent(txtSuffix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFind)
                    .addComponent(txtFind, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlColPropsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblReplace)
                    .addComponent(txtReplace, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(btnUpdateField))
        );

        btnAddColumn.setText("Add Simple Column");
        btnAddColumn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddColumnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabDLMgrLayout = new javax.swing.GroupLayout(tabDLMgr);
        tabDLMgr.setLayout(tabDLMgrLayout);
        tabDLMgrLayout.setHorizontalGroup(
            tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDLMgrLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlDSMgmt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlColProps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(tabDLMgrLayout.createSequentialGroup()
                        .addGroup(tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(tabDLMgrLayout.createSequentialGroup()
                                .addComponent(btnRefresh)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAddColumn))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        tabDLMgrLayout.setVerticalGroup(
            tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabDLMgrLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(tabDLMgrLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(tabDLMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRefresh)
                            .addComponent(btnAddColumn))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(tabDLMgrLayout.createSequentialGroup()
                        .addComponent(pnlDSMgmt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlColProps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        MainTabbedPane.addTab("Data Load Manager", tabDLMgr);

        javax.swing.GroupLayout tabFSMgrLayout = new javax.swing.GroupLayout(tabFSMgr);
        tabFSMgr.setLayout(tabFSMgrLayout);
        tabFSMgrLayout.setHorizontalGroup(
            tabFSMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1450, Short.MAX_VALUE)
        );
        tabFSMgrLayout.setVerticalGroup(
            tabFSMgrLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 540, Short.MAX_VALUE)
        );

        MainTabbedPane.addTab("File System Manager", tabFSMgr);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(42, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MainTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtHeaderRowsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHeaderRowsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHeaderRowsActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getSelectedColumn();
        jTable1.setRowSelectionInterval(0, jTable1.getRowCount()-1);
        lblColumn.setText(jTable1.getColumnName(jTable1.getSelectedColumn()) + " Properties");
    }//GEN-LAST:event_jTable1MouseClicked

    
    
    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        try{
            if (txtCustDelim.getText() == null){
                JOptionPane.showMessageDialog(this.getParent(), "Error: Please enter a delimiter character into the field");
            } else {
                DefaultTableModel model = getModelFromCsvFile(this.flSourceFile, this.txtCustDelim.getText());
                jTable1.setModel(model);

            }
        } catch (Throwable x) {
            
            JOptionPane.showMessageDialog(this.getParent(), "Error: " + x.getMessage());
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFindActionPerformed

    private void btnUpdateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateFieldActionPerformed
        // TODO add your handling code here:
        try{
            TableModel model = jTable1.getModel();
            Object[] rows = new Object[jTable1.getRowCount()];
            for (int i = 0; i < rows.length; i++) {
                if (txtFind.getText() != null || txtReplace.getText() != null) {
                    rows[i] = model.getValueAt(i, jTable1.getSelectedColumn());
                    if (rows[i].equals(txtFind.getText())) {
                        rows[i] = txtPrefix.getText() + txtReplace.getText() + txtSuffix.getText();
                        model.setValueAt(rows[i], i, jTable1.getSelectedColumn());
                    } else {
                        rows[i] = txtPrefix.getText() + model.getValueAt(i, jTable1.getSelectedColumn()) + txtSuffix.getText();
                        model.setValueAt(rows[i], i, jTable1.getSelectedColumn());
                    }
                }
            }
            jTable1.setModel(model);
        } catch (Throwable x) {
            JOptionPane.showMessageDialog(this.getParent(), "Error: Please ensure you select a field. Error: " + x.getMessage());
        }
    }//GEN-LAST:event_btnUpdateFieldActionPerformed

    private void btnAddColumnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddColumnActionPerformed
        // TODO add your handling code here:
        TableModel model = jTable1.getModel();
        TableColumn simpleColumn = new TableColumn();
        String columnData = new String(JOptionPane.showInputDialog(this.getParent(), "Enter Column Data"));
        simpleColumn.setHeaderValue(columnData);
        //simpleColumn.equals(JOptionPane.showInputDialog(this.getParent(), "Enter Column Data"));
        jTable1.addColumn(simpleColumn);
        final JOptionPane optionPane = new JOptionPane();
    }//GEN-LAST:event_btnAddColumnActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int returnVal = fc.showOpenDialog(this.getParent());

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION){
                File txtSourceFile = fc.getSelectedFile();
                flSourceFile = fc.getSelectedFile();
                System.out.println("Opening: " + txtSourceFile.getName() + ".");
                //System.out.println("Good");
                List<String> srcTextFile = Files.readAllLines(txtSourceFile.getAbsoluteFile().toPath(), StandardCharsets.UTF_8);
                for (String t: srcTextFile) {
                    jTextArea1.append(t + "\n");
                }
            } else {
                //System.out.println("Bad");
                System.out.println("Open command cancelled by user.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }//GEN-LAST:event_btnBrowseActionPerformed

    public DefaultTableModel getModelFromCsvFile(File file, String delimiter) {
            DefaultTableModel model = null;
            boolean isFirstRow = true;
            try {
            CharsetDecoder UTF8_CHARSET = StandardCharsets.UTF_8.newDecoder();
            UTF8_CHARSET.onMalformedInput(CodingErrorAction.REPLACE);
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file),
                        UTF8_CHARSET), delimiter.charAt(0));
                List<String[]> dataList = reader.readAll();
                for (String[] row: dataList) {
                    if (isFirstRow) {
                        //model = new DefaultTableModel(getTableColumnHeaders(row.length), 0);
                        model = new DefaultTableModel(row, 0);
                        /*                         for (String columnName: row){
                            System.out.println(columnName);
                        } */
                        //model.addRow(row);
                        isFirstRow = false;
                    }
                    else {
                        if (model != null) {
                            model.addRow(row);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return model;
        }
    public Object[] getTableColumnHeaderName(String[] cols){
        return null;
    }
    
    
    public Object[] getTableColumnHeaders(int size) {
            Object[] header = new Object[size];
            for (int i = 0; i < header.length; i++) {
                header[i] = i + 1;
            }
            return header;
        }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PBCSAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PBCSAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PBCSAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PBCSAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PBCSAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane MainTabbedPane;
    private javax.swing.ButtonGroup btgDelimiter;
    private javax.swing.JButton btnAddColumn;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnLoadSQL;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdateField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblColumn;
    private javax.swing.JLabel lblDSMgmt;
    private javax.swing.JLabel lblDelim;
    private javax.swing.JLabel lblDisplayRows;
    private javax.swing.JLabel lblDisplayRows2;
    private javax.swing.JLabel lblFind;
    private javax.swing.JLabel lblReplace;
    private javax.swing.JLabel lblStartRow;
    private javax.swing.JLabel lblSuffix;
    private javax.swing.JPanel pnlColProps;
    private javax.swing.JPanel pnlDSMgmt;
    private javax.swing.JRadioButton rbComma;
    private javax.swing.JRadioButton rbCustom;
    private javax.swing.JRadioButton rbSpace;
    private javax.swing.JRadioButton rbTab;
    private javax.swing.JPanel tabDLMgr;
    private javax.swing.JPanel tabFSMgr;
    private javax.swing.JTextField txtCustDelim;
    private javax.swing.JTextField txtDisplayRows;
    private javax.swing.JTextField txtFind;
    private javax.swing.JTextField txtHeaderRows;
    private javax.swing.JTextField txtPrefix;
    private javax.swing.JTextField txtReplace;
    private javax.swing.JTextField txtStartRow;
    private javax.swing.JTextField txtSuffix;
    // End of variables declaration//GEN-END:variables
}
