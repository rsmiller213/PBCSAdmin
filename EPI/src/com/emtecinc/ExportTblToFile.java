/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis.Castillo
 */
public class ExportTblToFile {

    ArrayList<Integer> skipRows = new ArrayList<Integer>();
    ArrayList<Integer> acceptRows = new ArrayList<Integer>();
    HashMap hmAcceptRejectItems = new HashMap();
    JTable dataTable = new JTable();

    public ExportTblToFile(JTable jTable, HashMap hmAcceptRejectItems) throws Exception {
        this.hmAcceptRejectItems = hmAcceptRejectItems;
        this.dataTable = jTable;
        getMainTableData();
    }

    private Vector getMainTableData() {

        Vector mainTableData = new Vector();
        try {
//            String[][] rowData = new String[dataTable.getRowCount()][dataTable.getColumnCount()];
//            for (int i = 0; i < dataTable.getRowCount(); i++) {
//                for (int j = 0; j < dataTable.getColumnCount(); j++) {
//                    rowData[i][j] = dataTable.getValueAt(i, j).toString();
//                    mainTableData.add(i, rowData[i][j]);
//                }
//            }
            mainTableData = ((DefaultTableModel) dataTable.getModel()).getDataVector();
        } catch (Throwable x) {
            JOptionPane.showMessageDialog(null, "Error getting data!. Error: " + x.getMessage());
        }
        return mainTableData;
    }

    public void setAcceptReject() {
        if (!hmAcceptRejectItems.isEmpty()) {
            //System.out.println(Arrays.toString(hmAcceptRejectItems.entrySet().toArray()));
            ArrayList<String> columns = new ArrayList<String>();
            for (int j = 0; j < dataTable.getColumnCount(); j++) {
                //System.out.println("Index: " + j + " Column: " + jTable.getColumnModel().getColumn(j).getHeaderValue().toString());
                columns.add(dataTable.getColumnModel().getColumn(j).getHeaderValue().toString());
            }
            for (int i = 0; i < columns.size(); i++) {
                Vector allData = new Vector();
                if (hmAcceptRejectItems.containsKey(columns.get(i))) {
                    //System.out.println(i + " : " + columns.get(i));
                    allData = (Vector) hmAcceptRejectItems.get(columns.get(i));
                    //System.out.println(Arrays.toString(allData.toArray()));
                    if (!allData.isEmpty()) {
                        for (Iterator it = allData.iterator(); it.hasNext();) {
                            Vector row = (Vector) it.next();
                            flagAcceptReject(columns.get(i), row);
                        }
                    }
                }
            }
        }
    }

    private void flagAcceptReject(String columnHeader, Vector row) {
        Vector mainTblData = getMainTableData();
        int colIndex = dataTable.getColumnModel().getColumnIndex(columnHeader);
        switch (row.get(0).toString()) {
            case pbcsConstants.AR_ACCEPT:
                //Check if it's a string
                //if (row.get(1).equals(pbcsConstants.AR_STRING)) {
                //now check the condition
                if (row.get(3).equals(pbcsConstants.AR_EQUALS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (dataTable.getValueAt(i, colIndex).equals(row.get(2))) {
                                //skipRows.add(i);
                                acceptRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().equalsIgnoreCase(row.get(2).toString())) {
                            //skipRows.add(i);
                            acceptRows.add(i);
                        } //} else if (dataTable.getValueAt(i, colIndex).equals(row.get(2))) {
                        //skipRows.add(i);
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_NOT_EQUALS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (!dataTable.getValueAt(i, colIndex).equals(row.get(2))) {
                                acceptRows.add(i);
                            }
                        } else if (!dataTable.getValueAt(i, colIndex).toString().equalsIgnoreCase(row.get(2).toString())) {
                            acceptRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_CONTAINS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (dataTable.getValueAt(i, colIndex).toString().regionMatches(0, row.get(2).toString(), 0, row.get(2).toString().length())) {
                                //skipRows.add(i);
                                acceptRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().contains(row.get(2).toString())) {
                            //skipRows.add(i);
                            acceptRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_NOT_CONTAINS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (!dataTable.getValueAt(i, colIndex).toString().regionMatches(0, row.get(2).toString(), 0, row.get(2).toString().length())) {
                                //skipRows.add(i);
                                acceptRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().contentEquals(row.get(2).toString())) {
                            //skipRows.add(i);
                            acceptRows.add(i);
                        }

                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_GREATER_THAN)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) > Integer.parseInt(row.get(2).toString())) {
                            acceptRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_GREATER_THAN_EQUAL)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) >= Integer.parseInt(row.get(2).toString())) {
                            acceptRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_LESS_THAN)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) < Integer.parseInt(row.get(2).toString())) {
                            acceptRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_LESS_THAN_EQUAL)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) <= Integer.parseInt(row.get(2).toString())) {
                            acceptRows.add(i);
                        }
                    }
                }
                //}
                break;
            case pbcsConstants.AR_REJECT:
                if (row.get(3).equals(pbcsConstants.AR_EQUALS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (dataTable.getValueAt(i, colIndex).equals(row.get(2))) {
                                skipRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().equalsIgnoreCase(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_NOT_EQUALS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (!dataTable.getValueAt(i, colIndex).equals(row.get(2))) {
                                skipRows.add(i);
                            }
                        } else if (!dataTable.getValueAt(i, colIndex).toString().equalsIgnoreCase(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_CONTAINS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (dataTable.getValueAt(i, colIndex).toString().regionMatches(0, row.get(2).toString(), 0, row.get(2).toString().length())) {
                                skipRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().contains(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_NOT_CONTAINS)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (row.get(4).equals("true")) {
                            if (!dataTable.getValueAt(i, colIndex).toString().regionMatches(0, row.get(2).toString(), 0, row.get(2).toString().length())) {
                                skipRows.add(i);
                            }
                        } else if (dataTable.getValueAt(i, colIndex).toString().contentEquals(row.get(2).toString())) {
                            skipRows.add(i);
                        }

                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_GREATER_THAN)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) > Integer.parseInt(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_GREATER_THAN_EQUAL)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) >= Integer.parseInt(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_LESS_THAN)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) < Integer.parseInt(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                if (row.get(3).equals(pbcsConstants.AR_LESS_THAN_EQUAL)) {
                    for (int i = 0; i < dataTable.getRowCount(); i++) {
                        //Vector dataRow = (Vector) mainTblData.get(i);
                        if (Integer.parseInt(dataTable.getValueAt(i, colIndex).toString()) <= Integer.parseInt(row.get(2).toString())) {
                            skipRows.add(i);
                        }
                    }
                }
                break;
        }
        //System.out.println(Arrays.toString(skipRows.toArray()));
    }

    public void exportFileFromTable(JTable jTable, File file, ArrayList<String> arrDataColumn, ArrayList<String> arrIgnoreField, boolean bCommandLine, boolean bAppend) throws IOException {
        setAcceptReject();
        //Start update of index in ArrayList. When new columns are added the index for data and ignore gets out of wack so this takes care of that
        if (!arrDataColumn.isEmpty()) {
            for (int i = 0; i < arrDataColumn.size(); i++) {
                if (!arrDataColumn.get(i).equals("")) {
                    int indexInArray = i;
                    int indexInTable;
                    try {
                        indexInTable = jTable.getColumnModel().getColumnIndex(arrDataColumn.get(i));
                    } catch (IllegalArgumentException e) {
                        continue;
                    }
                    if (indexInArray != indexInTable) {
                        String colHeader = arrDataColumn.get(i);
                        arrDataColumn.set(i, "");
                        arrDataColumn.set(indexInTable, colHeader);
                    }
                }
            }
        }
        if (!arrIgnoreField.isEmpty()) {
            for (int i = 0; i < arrIgnoreField.size(); i++) {
                //if (!arrIgnoreField.get(i).isEmpty()) {
                if (!arrIgnoreField.get(i).equals("")) {
                    int indexInArray = i;
                    //System.out.println("Col Error: " + arrIgnoreField.get(i));
                    int indexInTable = jTable.getColumnModel().getColumnIndex(arrIgnoreField.get(i));
                    if (indexInArray != indexInTable) {
                        String colHeader = arrIgnoreField.get(i);
                        arrIgnoreField.set(i, "");
                        arrIgnoreField.set(indexInTable, colHeader);
                    }
                }
            }
        }
        if (!file.exists()) {
            file.createNewFile();
            //writeFileFromTable(jTable, file);
        } else if (file.exists() && bCommandLine && !bAppend) {
            file.delete();
            file.createNewFile();
        } else if (file.exists() && bCommandLine && bAppend) {

        } else {
            int option = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Select File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (option == JOptionPane.OK_OPTION) {
                file.delete();
                file.createNewFile();
                //writeFileFromTable(jTable, file);
            }
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < jTable.getColumnCount(); i++) {
            //bw.write(jTable.getColumnName(i));
            //if (arrDataColumn[i] == 1){
            //System.out.println(Arrays.toString(arrDataColumn.toArray()));
            if (arrDataColumn.size() > i) {
                //if (!arrDataColumn.get(i).equals("")) {
                if (arrDataColumn.get(i).equals(jTable.getColumnModel().getColumn(i).getHeaderValue().toString())) {
                    //System.out.println("Data Col Error: " + arrDataColumn.get(i));
                    //bw.write("\"" + jTable.getColumnModel().getColumn(jTable.getColumnModel().getColumnIndex(arrDataColumn.get(i))).getHeaderValue().toString() + "\"");
                    bw.write("\"" + jTable.getColumnModel().getColumn(i).getHeaderValue().toString() + "\"");
                    bw.write("\t");
                }
            }
        }

        for (int i = 0; i < jTable.getRowCount(); i++) {
            //System.out.println(skipRows.indexOf((Object) i));
            //if (acceptRows.indexOf((Object) i) != -1 || skipRows.indexOf((Object) i) == -1 ) {
            if (!skipRows.contains(i)) {
                if (acceptRows.contains(i)) {
                    bw.newLine();
                    for (int j = 0; j < jTable.getColumnCount(); j++) {
                        //if (arrDataColumn[j] == 0) {
                        if (arrDataColumn.size() > j) {
                            //if (!arrDataColumn.get(j).equals("")) {
                            if (arrDataColumn.get(j).equals(jTable.getColumnModel().getColumn(j).getHeaderValue().toString())) {
                                //if (j <= arrDataColumn.size()) {
                                //bw.write((String) (jTable.getValueAt(i, jTable.getColumnModel().getColumnIndex(arrDataColumn.get(j)))));
                                bw.write((String) (jTable.getValueAt(i, j)));
                                bw.write("\t");
                                //} else if (!arrIgnoreField.get(j).equals("")) {
                            } else if (arrIgnoreField.get(j).equals(jTable.getColumnModel().getColumn(j).getHeaderValue().toString())) {
                                continue;
                            } else {
                                bw.write((String) ("\"" + jTable.getValueAt(i, j) + "\""));
                                bw.write("\t");
                            }
                        }
                    }
                }

            } else {
                continue;
            }
        }
        bw.close();
    }
//    public void exportFileFromTable(JTable jTable, File file, ArrayList<String> arrDataColumn, ArrayList<String> arrIgnoreField, boolean bCommandLine) throws IOException {
//        setAcceptReject();
//        Vector rows = new Vector();
//        rows = ((DefaultTableModel) jTable.getModel()).getDataVector();
//        if (!file.exists()) {
//            file.createNewFile();
//            //writeFileFromTable(jTable, file);
//        } else if (file.exists() && bCommandLine) {
//            file.delete();
//            file.createNewFile();
//        } else {
//            int option = JOptionPane.showConfirmDialog(null, "File already exists. Overwrite?", "Select File", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
//            if (option == JOptionPane.OK_OPTION) {
//                file.delete();
//                file.createNewFile();
//                //writeFileFromTable(jTable, file);
//            }
//        }
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw, 8192);
////        for (int i = 0; i < jTable.getColumnCount(); i++) {
////            //bw.write(jTable.getColumnName(i));
////            //if (arrDataColumn[i] == 1){
////            if (!arrDataColumn.get(i).equals("")) {
////                bw.write("\"" + jTable.getColumnModel().getColumn(i).getHeaderValue().toString() + "\"");
////                bw.write("\t");
////            }
////        }
//
//        //for (int i = 0; i < rows.size(); i++) {
//        int i = 0;
//        for (Object record: rows){
//            bw.write((char[]) record);
//        }
////        while (rows.iterator().hasNext()) {
////            if (i == rows.size()) {
////                break;
////            }
////            //System.out.println(skipRows.indexOf((Object) i));
////            if (skipRows.indexOf((Object) i) == -1) {
////                bw.newLine();
////                for (int j = 0; j < jTable.getColumnCount(); j++) {
////                    //if (arrDataColumn[j] == 0) {
////                    if (!arrDataColumn.get(j).equals("")) {
////                        //if (j <= arrDataColumn.size()) {
////                        bw.write((String) ((Vector) rows.elementAt(i)).elementAt(j));
////                        //bw.write((String)(jTable.getValueAt(i,j)));
////                        bw.write("\t");
////                    } else if (!arrIgnoreField.get(j).equals("")) {
////                        continue;
////                    } else {
////                        //bw.write((String) ("\"" + jTable.getValueAt(i, j) + "\""));
////                        bw.write((String) ("\"" + ((Vector) rows.elementAt(i)).elementAt(j) + "\""));
////                        bw.write("\t");
////                    }
////                }
////            } else {
////                continue;
////            }
////            i++;
////        }
//        bw.close();
//    }
}
