/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emtecinc;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Randall.Miller
 */
public class pbcsFSManager {
    
    
    public DefaultTableModel setTableModelFromStrArray(ArrayList<Object[]> arrRows, ArrayList<String> arrHeader) {
        DefaultTableModel model = null;
        //System.out.println("Before: " + arrHeader);
        model = new DefaultTableModel(getTableHeaders(arrHeader),0);        
        //model.addRow(new Object[]{"RowName","RowPath","RowModded","RowSize"});
        for (int i = 0; i < arrRows.size(); i++) {
            model.addRow(arrRows.get(i));
        }
        return model;
    }
    
    public Object[] getTableHeaders(ArrayList<String> headers){
        Object[] colObj = new Object[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
                colObj[i] = headers.get(i);
                //System.out.println("colObj(" + i + "): " + colObj[i]);
            }
        return colObj;
    }
}
