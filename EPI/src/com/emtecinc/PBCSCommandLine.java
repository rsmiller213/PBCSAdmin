/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;


import com.emtecinc.pbcsDLManager;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public PBCSCommandLine(String profileLocation, String dataFile, String delim, String pbcsJobName, String outFile) {
        this.profileLocation = profileLocation;
        this.pbcsJobName = pbcsJobName;
        this.dataFile = dataFile;
        this.delim = delim;
        this.outFile = outFile;
    }
    
    public void transformAndLoad(){
        File flDataFile = new File(dataFile);
        File flProfile = new File(profileLocation);
        File flOutFile = new File(outFile);
        DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, false);
        exportTable.setModel(mainModel);
        for (int i = 0 ; i < exportTable.getColumnCount(); i++){
               arrColNames.add(i,"");
               arrDataColumn.add(i,"");
               arrIgnoreColumn.add(i, "");
           }
        try {
            hm.putAll(dlManager.openProfileFromCL(flProfile, exportTable));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (flOutFile.exists()) {
                flOutFile.delete();
                flOutFile.createNewFile();
            }
                this.export = new ExportTblToFile(exportTable, dlManager.hmAcceptRejectItems);
                export.exportFileFromTable(exportTable, flOutFile, arrDataColumn, arrIgnoreColumn, true);
            } catch (Exception ex) {
                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
