package com.justas.planuotojaspro.global;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class SaveToCSV {
    public void exportTable(JTable table, String file) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);

        for(int i=0; i < model.getColumnCount(); i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write("\n");
        for(int i=0; i< model.getRowCount(); i++) {
            for(int j=0; j < model.getColumnCount(); j++) {
                out.write(model.getValueAt(i,j).toString()+"\t");
            }
            out.write("\n");
        }
        out.close();
        new UserMessages().infoMessage(getTranslation("t_filesavedto") + " " + file, getTranslation("t_filesaved"));
        System.out.println("write out to: " + file);
    }
}
