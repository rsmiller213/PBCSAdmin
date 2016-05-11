/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luis.Castillo
 */


public class pbcsDLManager {
    public File flSrcFile;
    /**
    * Creates table model from delimited file using Open CSV. 
    *
    * @param file file object where the data is loaded from
    * @param delimiter delimiter in the file
    * @param bHeaderRow   boolean that determines whether a header row is in the file or now
    * @return            returns a DefaultTableModel
    */
    public DefaultTableModel getModelFromCsvFile(File file, String delimiter, Boolean bHeaderRow) {
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
                        if (bHeaderRow) {
                            model = new DefaultTableModel(row, 0);
                            isFirstRow = false;
                        } else {
                            model = new DefaultTableModel(getTableColumnHeaders(row.length), 0);
                            model.addRow(row);
                            isFirstRow = false;
                        }
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
    
    /**
    * Creates table model from delimited file using Open CSV. 
    *
    * @param file file object where the data is loaded from
    * @param delimiter delimiter in the file
    * @param bHeaderRow   boolean that determines whether a header row is in the file or now
    * @param linesToRead   integer that specifies the number of lines to read
    * @return            returns a DefaultTableModel
    */
    public DefaultTableModel getModelFromCsvFile(File file, String delimiter, Boolean bHeaderRow, int linesToRead) {
            DefaultTableModel model = null;
            boolean isFirstRow = true;
            try {
            CharsetDecoder UTF8_CHARSET = StandardCharsets.UTF_8.newDecoder();
            UTF8_CHARSET.onMalformedInput(CodingErrorAction.REPLACE);
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file),
                        UTF8_CHARSET), delimiter.charAt(0));
                //List<String[]> dataList = reader.readAll();
                String[] nextLine;
                int N = linesToRead;
                int counter = 0;
                while ((nextLine = reader.readNext()) != null && counter < N)  {
                    if (isFirstRow) {
                        if (bHeaderRow) {
                            model = new DefaultTableModel(nextLine, 0);
                            isFirstRow = false;
                            counter++;
                        } else {
                            model = new DefaultTableModel(getTableColumnHeaders(nextLine.length), 0);
                            model.addRow(nextLine);
                            isFirstRow = false;
                            counter++;
                        }
                    }
                    else {
                        if (model != null) {
                            model.addRow(nextLine);
                            counter++;
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return model;
        }
    
    /**
    * Creates table model from delimited file using Open CSV. 
    *
    * @param file file object where the data is loaded from
    * @param delimiter delimiter in the file
    * @param bHeaderRow   boolean that determines whether a header row is in the file or now
    * @param linesToRead   integer that specifies the number of lines to read
    * @param startLine   integer that specifies the line number to read from
    * @return            returns a DefaultTableModel
    */
    public DefaultTableModel getModelFromCsvFile(File file, String delimiter, Boolean bHeaderRow, int linesToRead, int startLine) {
            DefaultTableModel model = null;
            boolean isFirstRow = true;
            try {
            CharsetDecoder UTF8_CHARSET = StandardCharsets.UTF_8.newDecoder();
            UTF8_CHARSET.onMalformedInput(CodingErrorAction.REPLACE);
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file),
                        UTF8_CHARSET), delimiter.charAt(0));
                //List<String[]> dataList = reader.readAll();
                String[] nextLine;
                int N = linesToRead;
                int counter = 0;
                int lineNumber = 0;
                while ((nextLine = reader.readNext()) != null && counter < N)  {
                    lineNumber++;
                    if (lineNumber >= startLine) {
                            if (isFirstRow) {
                                if (bHeaderRow) {
                                    model = new DefaultTableModel(nextLine, 0);
                                    isFirstRow = false;
                                    counter++;
                                } else {
                                    model = new DefaultTableModel(getTableColumnHeaders(nextLine.length), 0);
                                    model.addRow(nextLine);
                                    isFirstRow = false;
                                    counter++;
                                }
                            }
                            else {
                                if (model != null) {
                                    model.addRow(nextLine);
                                    counter++;
                                }
                            }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return model;
        }
    
    /**
    * Creates header names for a table model. Names them 1-x
    *
    * @param size integer that specifies the number of columns in the table
    * @return            returns the table headers in an Object[]
    */
    public Object[] getTableColumnHeaders(int size) {
            Object[] header = new Object[size];
            for (int i = 0; i < header.length; i++) {
                header[i] = i + 1;
            }
            return header;
        }
    
    /**
    * Creates table model from delimited file using Open CSV. 
    *
    * @param jTable JTable used in the UI
    * @param strFind String to Find
    * @param strReplace   New Value
    * @param strPrefix   Prefix to be added
    * @param strSuffix   Suffix to be added
    * @return            returns a TableModel and also updates the JTable
    */
    public TableModel findReplaceField(JTable jTable, String strFind, String strReplace, String strPrefix, String strSuffix) {
        TableModel model = jTable.getModel();
        try{
                Object[] rows = new Object[jTable.getRowCount()];
                for (int i = 0; i < rows.length; i++) {
                    if (strFind != null || strReplace != null) {
                        rows[i] = model.getValueAt(i, jTable.getSelectedColumn());
                        if (rows[i].equals(strFind)) {
                            rows[i] = strPrefix + strReplace + strSuffix;
                            model.setValueAt(rows[i], i, jTable.getSelectedColumn());
                        } else {
                            rows[i] = strPrefix + model.getValueAt(i, jTable.getSelectedColumn()) + strSuffix;
                            model.setValueAt(rows[i], i, jTable.getSelectedColumn());
                        }
                    }
                }
                jTable.setModel(model);
            } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
            }
        return model;
    }
    
    /**
    * Creates table model from delimited file using Open CSV. 
    *
    * @param file text file to be opened
    * @param intStartRow integer with value to start reading from
    * @param intDisplayRows   integer represents number of rows to be displayed
    * @return            ArrayList<String> is returned with the lines from the text file
    */
    public ArrayList<String> openTextFile(File file, int intStartRow, int intDisplayRows){
        ArrayList<String> arrLines = new ArrayList<String>();
        BufferedReader reader = null;

        try {
                System.out.println("Opening: " + file.getName() + ".");
                reader = new java.io.BufferedReader(new java.io.FileReader(file.getAbsoluteFile()));
                String line;
                int counter = 0;
                int lineNumber = 0;
                try {
                    while ((line = reader.readLine()) != null && counter < intDisplayRows) { 
                        lineNumber++;
                        if (lineNumber >= intStartRow) {
                            arrLines.add(line);
                            counter++;
                        }
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        return arrLines;
    }
    
    
    /**
    * Adds a table column and can populate all rows with provided data 
    *
    * @param JTable JTable object used in UI
    * @param strColumnName string with column name
    * @param strRowData   string with row data
    */
    public void addTableColumn(JTable jTable, String strColumnName, String strRowData){
        try {
                DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
                tblModel.addColumn(strColumnName);
                int columns = tblModel.getColumnCount() - 1 ;
                Object[] rows = new Object[jTable.getRowCount()];
               for (int i = 0; i < rows.length; i++) {
                    rows[i] = strRowData;
                    tblModel.setValueAt(rows[i], i, columns);
                }
               tblModel.fireTableDataChanged(); 
        } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
        }
    }
    
    /**
    * Adds a table column and can populate all rows with provided data 
    *
    * @param JTable JTable object used in UI
    * @param strColumnName string with column name
    */
    public void renameTableColumn(JTable jTable, String strColumnName){
        int columnNumber = jTable.getSelectedColumn();
        jTable.getColumnModel().getColumn(columnNumber).setHeaderValue(strColumnName);
        jTable.getTableHeader().repaint();
    }
}
