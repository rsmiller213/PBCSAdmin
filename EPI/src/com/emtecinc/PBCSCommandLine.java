/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import static com.emtecinc.PBCSAdmin.pbcsUserName;
import com.emtecinc.pbcsDLManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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

    public PBCSCommandLine(String profileLocation, String dataFile, String delim, String pbcsJobName, String outFile, Boolean bAppend) {
//    public PBCSCommandLine(String profileLocation, String dataFile, String delim, String pbcsJobName, String outFile) {
        this.profileLocation = profileLocation;
        this.pbcsJobName = pbcsJobName;
        this.dataFile = dataFile;
        this.delim = delim;
        this.outFile = outFile;
        this.bAppend = bAppend;
        ArrayList<String> tas = new ArrayList<String>();

    }

    private boolean getHeaderRow(File file) throws IOException, ClassNotFoundException {
        boolean bHeaderRow = false;
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        Object importProfile = (Object) inputStream.readObject();
        if (importProfile instanceof ArrayList) {
            ArrayList<String[]> eventRowsProfile = (ArrayList<String[]>) importProfile;
            for (Iterator it = eventRowsProfile.iterator(); it.hasNext();) {
                String[] currLine = (String[]) it.next();
                //System.out.println(Arrays.toString(currLine));
                if (currLine[0].equals(pbcsConstants.EVT_HEADER)) {
                    bHeaderRow = true;
                    //System.out.println("Found Header!");
                } else {
                    bHeaderRow = false;
                }
            }
            inputStream.close();
        }
        return bHeaderRow;
    }

    private static int countLines(File aFile) throws IOException {
        LineNumberReader reader = null;
        try {
            reader = new LineNumberReader(new FileReader(aFile));
            while ((reader.readLine()) != null);
            return reader.getLineNumber();
        } catch (Exception ex) {
            return -1;
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    //public void transformAndLoad() {
    public void transformAndLoad(int lineCount, int numberToLoad, int startLine, boolean bPBCSLoad, boolean bOutHeader) {
        File flDataFile = new File(dataFile);
        File flProfile = new File(profileLocation);
        File flOutFile = new File(outFile);
        int linesInFile = 0;
        boolean bHeaderRow = false;
        try {
            linesInFile = countLines(flDataFile) + 1;
            bHeaderRow = getHeaderRow(flProfile);
        } catch (IOException ex) {
            Logger.getLogger(PBCSCommandLine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PBCSCommandLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        //    DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, bHeaderRow);
        //DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, false);
        DefaultTableModel mainModel = dlManager.getModelFromCsvFile(flDataFile, delim, bHeaderRow, numberToLoad, startLine);
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
//            if (flOutFile.exists()) {
//                flOutFile.delete();
//                flOutFile.createNewFile();
//            }
            this.export = new ExportTblToFile(exportTable, dlManager.hmAcceptRejectItems);
            if (bAppend) {
                export.exportFileFromTable(exportTable, flOutFile, arrDataColumn, arrIgnoreColumn, true, true, bOutHeader);

            } else {
                export.exportFileFromTable(exportTable, flOutFile, arrDataColumn, arrIgnoreColumn, true, false, bOutHeader);
            }

        } catch (Exception ex) {
            Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
        if (bPBCSLoad) {
            try {
                pbcsInfo = new PBCSGetPropsFile().getPropValues(pbcsConstants.DECRYPT);
            } catch (IOException ex) {
                Logger.getLogger(PBCSAdmin.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
            pbcsUrl = pbcsInfo.get(0);
            pbcsDomain = pbcsInfo.get(1);
            pbcsUserName = pbcsInfo.get(2);
            pbcsPassword = pbcsInfo.get(3);

            try {
                PBCSActions pbcsclient = new PBCSActions(pbcsUserName, pbcsDomain, pbcsPassword, pbcsUrl);
                ArrayList<String> pbcsFiles = pbcsclient.listFilesReturn();
                for (String fileName : pbcsFiles) {
                    if (fileName.equals(flOutFile.getName())) {
                        System.out.println("File " + fileName + " already exists. Deleting file first...");
                        pbcsclient.deleteFile(fileName);
                    }
                }
                pbcsclient.uploadFile(flOutFile);
                pbcsclient.ImportData(flOutFile.getName(), pbcsJobName);
            } catch (Exception ex2) {
                System.out.println("Error: " + ex2.getMessage());
                System.exit(1);
            }
        }
    }
}
