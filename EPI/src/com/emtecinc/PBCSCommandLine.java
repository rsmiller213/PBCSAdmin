/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import static com.emtecinc.PBCSAdmin.pbcsUserName;
import com.emtecinc.pbcsDLManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis.Castillo
 */
public class PBCSCommandLine extends PBCSAdmin {

    //pbcsDLManager dlManager = new pbcsDLManager();
    String profileLocation;
    String pbcsJobName;
    String dataFile;
    String delim;
    String outFile;
    JTable exportTable = new JTable();
    Boolean bAppend = false;

//    public PBCSCommandLine(String profileLocation, String dataFile, String delim, String pbcsJobName, String outFile, Boolean bAppend) {
    public PBCSCommandLine(String profileLocation, String dataFile, String delim, String pbcsJobName, String outFile) {
        this.profileLocation = profileLocation;
        this.pbcsJobName = pbcsJobName;
        this.dataFile = dataFile;
        this.delim = delim;
        this.outFile = outFile;
        //this.bAppend = bAppend;
        ArrayList<String> tas = new ArrayList<String>();
        
    }

    public void transformAndLoad() {
    //public void transformAndLoad(int lineCount, int numberToLoad, int startLine) {
        File flDataFile = new File(dataFile);
        File flProfile = new File(profileLocation);
        File flOutFile = new File(outFile);
        DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, false);
        //DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, false, numberToLoad, startLine);
        exportTable.setModel(mainModel);
        ArrayList<String> pbcsInfo = null;
        for (int i = 0; i < exportTable.getColumnCount(); i++) {
            arrColNames.add(i, "");
            arrDataColumn.add(i, "");
            arrIgnoreColumn.add(i, "");
        }
        try {
            hm.putAll(dlManager.openProfileFromCL(flProfile, exportTable));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        try {
            if (flOutFile.exists()) {
                flOutFile.delete();
                flOutFile.createNewFile();
            }
            this.export = new ExportTblToFile(exportTable, dlManager.hmAcceptRejectItems);
            if (bAppend){
                export.exportFileFromTable(exportTable, flOutFile, arrDataColumn, arrIgnoreColumn, true, true);
                
            } else {
                export.exportFileFromTable(exportTable, flOutFile, arrDataColumn, arrIgnoreColumn, true, false);
            }

        } catch (Exception ex) {
            Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
//        try {
//            pbcsInfo = new PBCSGetPropsFile().getPropValues();
//        } catch (IOException ex) {
//            Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
//            System.exit(1);
//        }
//        pbcsUrl = pbcsInfo.get(0);
//        pbcsDomain = pbcsInfo.get(1);
//        pbcsUserName = pbcsInfo.get(2);
//        pbcsPassword = pbcsInfo.get(3);
//        
//        try {
//            PBCSActions pbcsclient = new PBCSActions(pbcsUserName, pbcsDomain, pbcsPassword, pbcsUrl);
//            pbcsclient.uploadFile(flOutFile);
//        } catch (Exception ex2) {
//            System.out.println("Error: " + ex2.getMessage());
//            System.exit(1);
//        }
    }
}
