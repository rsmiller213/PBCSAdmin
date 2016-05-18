/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

//import static com.emtecinc.PBCSAdmin.arrDataColumn;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Luis.Castillo
 */


public class pbcsDLManager {
    public File flSrcFile;
    ArrayList<String[]> eventRows = new ArrayList<String[]>();
    HashMap hmMoves = new HashMap();
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
        int index = jTable.convertColumnIndexToModel(jTable.getSelectedColumn());
        TableModel model = jTable.getModel();
        try{
                Object[] rows = new Object[jTable.getRowCount()];
                for (int i = 0; i < rows.length; i++) {
                    if (strFind != null || strReplace != null) {
                        rows[i] = model.getValueAt(i, index);
                        if (rows[i].equals(strFind)) {
                            rows[i] = strPrefix + strReplace + strSuffix;
                            model.setValueAt(rows[i], i, index);
                        } else {
                            rows[i] = strPrefix + model.getValueAt(i, index) + strSuffix;
                            model.setValueAt(rows[i], i, index);
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
            updateColumnModelHeaders(jTable);
            DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
                tblModel.addColumn(strColumnName);
                //int columns = tblModel.getColumnCount() - 1 ;
                //TableColumn t = new TableColumn();
                //t.setHeaderValue((Object) strColumnName);
                //jTable.getColumnModel().addColumn(t);
                //System.out.println("Table Model " + jTable.getModel().getColumnCount() + " Column Model " + jTable.getColumnModel().getColumnCount());
                //jTable.repaint();
                //int columns = jTable.getColumnModel().getColumnIndex((Object) strColumnName);
                int columns = tblModel.findColumn(strColumnName);
                Object[] rows = new Object[jTable.getRowCount()];
                //updateColumnValues(jTable, columns, strRowData);
               for (int i = 0; i < rows.length; i++) {
                    tblModel.setValueAt(strRowData, i, columns);
                    //jTable.setValueAt(strRowData, i, columns);
                }
               tblModel.fireTableDataChanged();
        } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
        }
    }
    
   /**
    * Split a column by either a delim or # of chars
    *
    * @param tTable JTable object used in UI
    * @param iColIdx int with Column Index
    * @param bCharSplit boolean, True for Char Split, False for Delim Split
    * @param strSplitBy   string with either the # of chars or delimiter
    */
    public void splitColumn(JTable tTable, boolean bCharSplit, String strSplitBy ){
        try{
            // Locals
            int iCurCol = tTable.convertColumnIndexToModel(tTable.getSelectedColumn());
            String strCur;
            String strNew;
            String strColHeader = tTable.getColumnModel().getColumn(tTable.getSelectedColumn()).getHeaderValue().toString();
            int iNewCol;
            
            // Create New Column
            DefaultTableModel tblModel = (DefaultTableModel) tTable.getModel();
            addTableColumn(tTable,strColHeader + "_Split","abc");
            iNewCol = tblModel.getColumnCount() - 1;
            
            // Get Model to Loop Rows
            TableModel model = tTable.getModel();
            Object[] rows = new Object[tTable.getRowCount()];
            
            //Loop Rows
            for (int i = 0; i < rows.length; i++) {
                strCur = model.getValueAt(i, iCurCol).toString();
                
                if (bCharSplit){
                    // Convert Str to Int
                    int iSplitBy = Integer.valueOf(strSplitBy);
                    // Split into Two Strings
                    strNew = strCur.substring(iSplitBy);
                    strCur = strCur.substring(0, iSplitBy);
                } else {
                    strNew = strCur.substring(strCur.indexOf(strSplitBy) + 1);
                    strCur = strCur.substring(0, strCur.indexOf(strSplitBy));
                }
                
                // Update Rows
                model.setValueAt(strCur, i, iCurCol);
                model.setValueAt(strNew, i, iNewCol);
                }
                tTable.setModel(model);
            } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
            }
    }  
    
    public void splitColumnByProfile(JTable tTable, boolean bCharSplit, String strSplitBy, int oldColumn ){
        try{
            // Locals
            int iCurCol = tTable.convertColumnIndexToModel(oldColumn);
            String strCur;
            String strNew;
            String strColHeader = tTable.getColumnModel().getColumn(oldColumn).getHeaderValue().toString();
            int iNewCol;
            
            // Create New Column
            DefaultTableModel tblModel = (DefaultTableModel) tTable.getModel();
            addTableColumn(tTable,strColHeader + "_Split","abc");
            iNewCol = tblModel.getColumnCount() - 1;
            
            // Get Model to Loop Rows
            TableModel model = tTable.getModel();
            Object[] rows = new Object[tTable.getRowCount()];
            
            //Loop Rows
            for (int i = 0; i < rows.length; i++) {
                strCur = model.getValueAt(i, iCurCol).toString();
                
                if (bCharSplit){
                    // Convert Str to Int
                    int iSplitBy = Integer.valueOf(strSplitBy);
                    // Split into Two Strings
                    strNew = strCur.substring(iSplitBy);
                    strCur = strCur.substring(0, iSplitBy);
                } else {
                    strNew = strCur.substring(strCur.indexOf(strSplitBy) + 1);
                    strCur = strCur.substring(0, strCur.indexOf(strSplitBy));
                }
                
                // Update Rows
                model.setValueAt(strCur, i, iCurCol);
                model.setValueAt(strNew, i, iNewCol);
                }
                tTable.setModel(model);
            } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
            }
    }
    
    /**
    * Adds a table column and can populate all rows with provided data 
    *
    * @param JTable JTable object used in UI
    * @param strColumnName string with column name
    * @param columnNumber int for the column being updated
    */
    public void renameTableColumn(JTable jTable, String strColumnName, int columnNumber){
        //int columnNumber = jTable.getSelectedColumn();
        jTable.getColumnModel().getColumn(columnNumber).setHeaderValue(strColumnName);
        jTable.getTableHeader().repaint();
        updateColumnModelHeaders(jTable);
    }
    
    //public void exportFileFromTable(JTable jTable, File file, int[] arrDataColumn) throws IOException{
    public void exportFileFromTable(JTable jTable, File file, ArrayList<String> arrDataColumn) throws IOException{
        if (!file.exists()){
            file.createNewFile();
            //writeFileFromTable(jTable, file);
        } else {
           int option = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Select File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
           if (option == JOptionPane.OK_OPTION) {
               file.delete();
               file.createNewFile();
               //writeFileFromTable(jTable, file);
           }
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for(int i = 0 ; i < jTable.getColumnCount() ; i++) {
            //bw.write(jTable.getColumnName(i));
            //if (arrDataColumn[i] == 1){
            if (!arrDataColumn.get(i).equals("")){
                bw.write("\"" + jTable.getColumnModel().getColumn(i).getHeaderValue().toString()+ "\"");
                bw.write("\t");
            }
        }

        for (int i = 0 ; i < jTable.getRowCount(); i++) {
            bw.newLine();
            for(int j = 0 ; j < jTable.getColumnCount();j++) {
                //if (arrDataColumn[j] == 0) {
                if (!arrDataColumn.get(j).equals("")) {
                //if (j <= arrDataColumn.size()) {
                    bw.write((String)("\"" + jTable.getValueAt(i,j) + "\""));
                    bw.write("\t");
                } else {
                bw.write((String)(jTable.getValueAt(i,j)));
                bw.write("\t");
                }
            }
        }
        bw.close();
    }
    public ArrayList<String> getTableColumnNames(JTable jTable){
        ArrayList<String> columns = new ArrayList<String>();
        for (int i = 0 ; i < jTable.getColumnCount(); i++){
            //columns.add(jTable.getModel().getColumnName(i));
            columns.add(jTable.getColumnModel().getColumn(i).getHeaderValue().toString());
        }
        return columns;
    }
    
    //public DefaultTableModel duplicateColumn (JTable jTable, String header, String leftColumn, String rightColumn, String delimiter, Boolean deleteColumns){
    public void duplicateColumn (JTable jTable, String header, String leftColumn, String rightColumn, String delimiter, Boolean deleteColumns){
        updateColumnModelHeaders(jTable);
        DefaultTableModel model = (DefaultTableModel)jTable.getModel();
        int leftColIndex = jTable.convertColumnIndexToModel(model.findColumn(leftColumn));
        int rightColIndex = jTable.convertColumnIndexToModel(model.findColumn(rightColumn));
        model.addColumn(header);
        TableColumn t = new TableColumn();
        t.setHeaderValue(header);
        //jTable.getColumnModel().addColumn(t);
        //jTable.addColumn(t);
        for (int i = 0 ; i < jTable.getRowCount(); i++){
            String leftValue = model.getValueAt(i, model.findColumn(leftColumn)).toString();
            String rightValue = model.getValueAt(i, model.findColumn(rightColumn)).toString();
            //String leftValue = (String) jTable.getValueAt(i, jTable.getColumnModel().getColumnIndex((Object) leftColumn));
            //String rightValue =  (String) jTable.getValueAt(i, jTable.getColumnModel().getColumnIndex((Object) rightColumn));
            model.setValueAt(leftValue + delimiter + rightValue, i, model.findColumn(header));
            //jTable.setValueAt(leftValue + delimiter + rightValue, i, model.findColumn(header));
            //jTable.setValueAt(leftValue + delimiter + rightValue, i, jTable.getColumnModel().getColumnIndex((Object) header));
            //jTable.getModel().setValueAt(leftValue + delimiter + rightValue, i, jTable.getColumnModel().getColumnIndex((Object) header));
            jTable.getTableHeader().repaint();
            //System.out.println("setValueAt: " + leftValue + delimiter + rightValue + " " + i + " " + jTable.getColumnModel().getColumnIndex((Object) header));
        }
        if (deleteColumns) {
            jTable.getColumnModel().removeColumn(jTable.getColumn(leftColumn));
            jTable.getColumnModel().removeColumn(jTable.getColumn(rightColumn));
            updateColumnModelData(jTable);
        }
        //model.fireTableDataChanged();
    }
    
    public void duplicateColumnFromProfile (JTable jTable, String header, int leftColumn, int rightColumn, String delimiter, Boolean deleteColumns){
        //dlManager.addTableColumn(jTable1, colHeader.getText(), textValue.getText());
        //updateColumnModelHeaders(jTable);
        updateColumnModelData(jTable);
        DefaultTableModel model = (DefaultTableModel)jTable.getModel();
        //model.addColumn(header);
        TableColumn t = new TableColumn();
        //t.setHeaderValue(header);
        model.addColumn(header);
        //jTable.getColumnModel().addColumn(t);
        for (int i = 0 ; i < jTable.getRowCount(); i++){
            //System.out.println(jTable.getColumnModel().getColumn(jTable.convertColumnIndexToModel(leftColumn)).getHeaderValue());
            //System.out.println(jTable.getColumnModel().getColumn(leftColumn).getHeaderValue());
            String leftValue = jTable.getValueAt(i, jTable.convertColumnIndexToView(leftColumn)).toString();
            String rightValue = jTable.getValueAt(i, rightColumn).toString();
            model.setValueAt(leftValue + delimiter + rightValue, i, model.getColumnCount() - 1);
            //String leftValue = (String) jTable.getValueAt(i, leftColumn);
            //String rightValue =  (String) jTable.getValueAt(i, rightColumn);
            //jTable.setValueAt(leftValue + delimiter + rightValue, i, jTable.getColumnModel().getColumnIndex((Object) header));
        }
        if (deleteColumns) {
            jTable.removeColumn(jTable.getColumn(leftColumn));
            jTable.removeColumn(jTable.getColumn(rightColumn));
            updateColumnModelData(jTable);
        }
        //model.fireTableDataChanged();
    }
    
    public void updateEventLog(String operation, String movedFrom, String movedTo, String characters) {
        eventRows.add(new String[]{operation, movedFrom, movedTo, characters});
    }

//    public void saveFile(){
//        final JFileChooser fc = new JFileChooser();
//        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        //fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
//        int returnVal = fc.showOpenDialog(null);
//        
//        if (returnVal == JFileChooser.APPROVE_OPTION){
//            File file = fc.getSelectedFile();
//            if (file.exists()) {
//                int option = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?");
//                if (option == JOptionPane.OK_OPTION) {
//                    file.delete();
//                }
//            }
//            try {
//                file.createNewFile();
//                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
//                for (Object event: eventRows){
//                    if (event instanceof String[]){
//                        String[] arr = (String[]) event;
//                        bw.write(Arrays.toString(arr));
//                        bw.newLine();
//                    }
//                }
//                bw.close();
//            } catch (IOException ex) {
//                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    public void saveFile(){
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int returnVal = fc.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            if (file.exists()) {
                int option = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?");
                if (option == JOptionPane.OK_OPTION) {
                    file.delete();
                }
            }
            try {
                file.createNewFile();
                ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
                outputStream.writeObject(eventRows);
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
//    public void openProfile(){
//        final JFileChooser fc = new JFileChooser();
//        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
//        //fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
//        int returnVal = fc.showOpenDialog(null);
//        
//        if (returnVal == JFileChooser.APPROVE_OPTION){
//            File file = fc.getSelectedFile();
//            try {
//                BufferedReader br = new BufferedReader(new FileReader(file));
//                while (!br.readLine().isEmpty())
//                    //System.out.println(br.readLine().replace("[", "").replace("[", "").split(","));
//                    System.out.println(br.readLine());
//                br.close();
//            } catch (IOException ex) {
//                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    //public void openProfile(JTable jTable) throws ClassNotFoundException{
    public HashMap openProfile(JTable jTable) throws ClassNotFoundException{
        HashMap hm =  new HashMap();
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        PBCSAdmin pbcsAdmin = new PBCSAdmin();
        boolean bMoves = false;
        //fc.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
        int returnVal = fc.showOpenDialog(null);
        
        if (returnVal == JFileChooser.APPROVE_OPTION){
            File file = fc.getSelectedFile();
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                Object importProfile = (Object) inputStream.readObject();
                if (importProfile instanceof ArrayList){
                    eventRows = (ArrayList<String[]>) importProfile;
                    for (Object events: eventRows){
                        if (events instanceof String[]){
                            String[] arr = (String[]) events;
                            //System.out.println(Arrays.toString(arr));
                            //System.out.println(arr[0] + " " + arr[1] + " " + arr[1] + " " + arr[3]);
                            //pbcsAdmin.setImportProfile(arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[1]), arr[3]);
                            //hm = setImportProfile(jTable, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[1]), arr[3]);
                            if (arr[0].equals(pbcsConstants.EVT_CREATE_JOIN)){
                                hm.putAll(setImportProfile(jTable, arr[0], Integer.parseInt(arr[1].split(" ")[0]), Integer.parseInt(arr[1].split(" ")[1]), arr[3])); 
                                //System.out.println(Arrays.toString(arr));
                            } else if (arr[0].equals(pbcsConstants.EVT_MOVE)){
                                bMoves = true;
                                hmMoves.put(arr[3], arr[2]);
                                setMovesFromProfile(jTable);
                            } else {
                                hm.putAll(setImportProfile(jTable, arr[0], Integer.parseInt(arr[1]), Integer.parseInt(arr[1]), arr[3])); 
                                //System.out.println(Arrays.toString(arr));
                            }
                        }
                    }
                    //setMovesFromProfile(jTable);
                }
                
//                for (Object event: importItems){
//                    if (event instanceof String[]){
//                        String[] arr = (String[]) event;
//                        System.out.println(Arrays.toString(arr));
//                    }
//                }
            } catch (IOException ex) {
                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Array: " + Arrays.toString(hm.entrySet().toArray()));
        return hm;
    }
    
    public void setMovesFromProfile(JTable jTable){
        //String header = hmMoves.entrySet().toArray()[0].toString();
        //int from = ((DefaultTableModel)jTable.getModel()).findColumn(hmMoves.get(header));
        for (int i = 0; i < hmMoves.size(); i++) {
            String header = hmMoves.entrySet().toArray()[i].toString().split("=")[0];
            //int from = ((DefaultTableModel)jTable.getModel()).findColumn(hmMoves.get(header).toString());
            //int from = jTable.convertColumnIndexToView(model.findColumn(header));
            int from = jTable.getColumnModel().getColumnIndex((Object) header);
            int to = Integer.parseInt(hmMoves.entrySet().toArray()[i].toString().split("=")[1]);
            //System.out.println("Header: " + header + " From: " + from + " To: " + to);
            //jTable.moveColumn(to, from);
            //jTable.moveColumn(from, to);
            jTable.getColumnModel().moveColumn(from, to);
            updateColumnModelData(jTable);
            ((DefaultTableModel)jTable.getModel()).fireTableStructureChanged();
        }
    }
    
    //public void setImportProfile (JTable jTable, String action, int fromColumn, int toColumn, String value){
    public HashMap setImportProfile (JTable jTable, String action, int fromColumn, int toColumn, String value){
        //ArrayList<String> arrDataColumn = new ArrayList<>();
        //System.out.println(action);
        HashMap hm =  new HashMap();
        //jTable.moveColumn(4,0);
        if (action.equals(pbcsConstants.EVT_RENAME)) {
            renameTableColumn(jTable, value, toColumn);
        } else if (action.equals(pbcsConstants.EVT_DATA)){
            PBCSAdmin.arrDataColumn.add(toColumn, jTable.getColumnModel().getColumn(toColumn).getHeaderValue().toString());
        } else if (action.equals(pbcsConstants.EVT_PREFIX)){
            hm.put(jTable.getColumnModel().getColumn(toColumn).getHeaderValue().toString() + "|Prefix", value);
        } else if (action.equals(pbcsConstants.EVT_SUFFIX)){
            hm.put(jTable.getColumnModel().getColumn(toColumn).getHeaderValue().toString()+ "|Suffix", value);
        } else if (action.equals(pbcsConstants.EVT_FIND)){
            hm.put(jTable.getColumnModel().getColumn(toColumn).getHeaderValue().toString()+ "|Find", value);
        } else if (action.equals(pbcsConstants.EVT_REPLACE)){
            hm.put(jTable.getColumnModel().getColumn(toColumn).getHeaderValue().toString()+ "|Replace", value);
        } else if (action.equals(pbcsConstants.EVT_MOVE)){
           
        } else if (action.equals(pbcsConstants.EVT_ADD)){
            addTableColumn(jTable, value, "");
            //jTable.getTableHeader().repaint();
        } else if (action.equals(pbcsConstants.EVT_COLUMN_VALUES)){
            updateColumnValues(jTable, toColumn, value);
        } else if (action.equals(pbcsConstants.EVT_SPLIT_CHARS)){
            splitColumnByProfile(jTable, true, value, fromColumn);
        } else if (action.equals(pbcsConstants.EVT_SPLIT_DELIM)){
            splitColumnByProfile(jTable, false, value, fromColumn);
        } else if (action.equals(pbcsConstants.EVT_CREATE_JOIN)){
            //System.out.println(Integer.toString(toColumn) + " " + fromColumn + " " + value);
            duplicateColumnFromProfile(jTable, Integer.toString(toColumn), fromColumn, toColumn, value, false);
        } else if (action.equals(pbcsConstants.EVT_DELETE_COLUMN)){
            jTable.removeColumn(jTable.getColumnModel().getColumn(fromColumn));
            jTable.removeColumn(jTable.getColumnModel().getColumn(toColumn));
        }
        return hm;
    }
    
    public void updateColumnModelHeaders(JTable jTable){
        int selectedColumn = jTable.getSelectedColumn();
        Vector columns = new Vector();
        for (int i = 0 ; i < jTable.getColumnCount(); i++){
            //columns.add(jTable.getModel().getColumnName(i));
            columns.add(jTable.getColumnModel().getColumn(i).getHeaderValue().toString());
        }
        ((DefaultTableModel) jTable.getModel()).setColumnIdentifiers(columns);
        if (selectedColumn >= 0){
        jTable.setColumnSelectionInterval(selectedColumn, selectedColumn);
        }
    }
    
    public void updateColumnModelData(JTable jTable){
        int selectedColumn = jTable.getSelectedColumn();
        Vector columns = new Vector();
        //Vector rows = ((DefaultTableModel) jTable.getModel()).getDataVector();
        Vector rows = new Vector();
        String[][] rowData = new String[jTable.getRowCount()][jTable.getColumnCount()];
        //System.out.println(Arrays.toString(rows.toArray()));
        for (int i = 0 ; i < jTable.getColumnCount(); i++){
            columns.add(jTable.getColumnModel().getColumn(i).getHeaderValue().toString());
        }
        for (int i = 0 ; i < jTable.getRowCount(); i++){
            for (int j = 0 ; j < jTable.getColumnCount(); j++){
                rowData[i][j] = jTable.getValueAt(i, j).toString();
                rows.add(i, rowData[i][j]);
            }
        }
        //System.out.println(Arrays.toString(rowData[0]));
//        ((DefaultTableModel) jTable.getModel()).setDataVector(rows, columns);
        ((DefaultTableModel) jTable.getModel()).setDataVector((Object[][])rowData, columns.toArray());
    }
    
    public void updateColumnValues(JTable jTable, int columnIndex, String strRowData){
        try {
                //DefaultTableModel tblModel = (DefaultTableModel) jTable.getModel();
                TableModel tblModel = jTable.getModel();
                //tblModel.addColumn(strColumnName);
                Object[] rows = new Object[jTable.getRowCount()];
               for (int i = 0; i < rows.length; i++) {
                    rows[i] = strRowData;
                    tblModel.setValueAt(rows[i], i, columnIndex);
                }
               //tblModel.fireTableDataChanged(); 
        } catch (Throwable x) {
                JOptionPane.showMessageDialog(null, "Error: Please ensure you select a field. Error: " + x.getMessage());
        }
    }
    
    public void getTableColumnMoves(JTable jTable){
        jTable.getColumnModel().addColumnModelListener(new TableColumnModelListener(){
            @Override
            public void columnAdded(TableColumnModelEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void columnMoved(TableColumnModelEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if (e.getFromIndex() != e.getToIndex()){
                    //System.out.println(pbcsConstants.EVT_MOVE + " " +e.getFromIndex()+", "+e.getToIndex());
                    updateEventLog(pbcsConstants.EVT_MOVE, Integer.toString(e.getFromIndex()), Integer.toString(e.getToIndex()), jTable.getColumnModel().getColumn(e.getToIndex()).getHeaderValue().toString());
                    //arrMoves.add(new String[]{Integer.toString(e.getFromIndex()), Integer.toString(e.getToIndex()), jTable.getColumnModel().getColumn(e.getToIndex()).getHeaderValue().toString()});
                }
            }

            @Override
            public void columnMarginChanged(ChangeEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
    }
}
