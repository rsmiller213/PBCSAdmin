
package com.emtecinc;

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
 * @author Luis.Castillo
 */
public class pbcsFileEditor extends javax.swing.JPanel {
    public File flSourceFile;
    /** Creates new form pbcsFileEditor */
    public pbcsFileEditor() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnBrowse = new javax.swing.JButton();
        txtDelimiter = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPrefix = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        lblColumn = new javax.swing.JLabel();
        btnUpdateField = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        txtDelimiter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDelimiterActionPerformed(evt);
            }
        });

        jLabel1.setText("Delimiter");

        jLabel2.setText("Prefix");

        btnRefresh.setText("Refresh Table");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

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
        jTable1.setCellSelectionEnabled(true);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        lblColumn.setText("Select Column to edit");

        btnUpdateField.setText("Update Field");
        btnUpdateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1081, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(163, 163, 163)
                                .addComponent(btnRefresh))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBrowse)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel1)
                                            .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtDelimiter, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                            .addComponent(txtPrefix)))
                                    .addComponent(lblColumn)
                                    .addComponent(btnUpdateField))
                                .addGap(68, 68, 68)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBrowse)
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(41, 41, 41)
                        .addComponent(lblColumn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPrefix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnUpdateField)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );
    }//GEN-END:initComponents

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

    private void txtDelimiterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDelimiterActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDelimiterActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        try{
            if (txtDelimiter.getText() == null){
                JOptionPane.showMessageDialog(this.getParent(), "Error: Please enter a delimiter character into the field");
            } else {
                DefaultTableModel model = getModelFromCsvFile(this.flSourceFile, this.txtDelimiter.getText());
                jTable1.setModel(model);

            }
        } catch (Throwable x) {
            JOptionPane.showMessageDialog(this.getParent(), "Error: " + x.getMessage());
        }
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        jTable1.setCellSelectionEnabled(true);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setColumnSelectionAllowed(true);
        jTable1.getSelectedColumn();
        jTable1.setRowSelectionInterval(0, jTable1.getRowCount()-1);
        lblColumn.setText(jTable1.getColumnName(jTable1.getSelectedColumn()));
    }//GEN-LAST:event_jTable1MouseClicked

    private void btnUpdateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateFieldActionPerformed
        // TODO add your handling code here:
        TableModel model = jTable1.getModel();
        Object[] rows = new Object[jTable1.getRowCount()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = txtPrefix.getText() + model.getValueAt(i, jTable1.getSelectedColumn());
            model.setValueAt(rows[i], i, jTable1.getSelectedColumn());
        }
        jTable1.setModel(model);
    }//GEN-LAST:event_btnUpdateFieldActionPerformed

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblColumn;
    private javax.swing.JTextField txtDelimiter;
    private javax.swing.JTextField txtPrefix;
    // End of variables declaration//GEN-END:variables

}
