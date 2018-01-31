package com.justas.planuotojaspro.windows;

import com.github.lgooddatepicker.components.DatePicker;
import com.justas.planuotojaspro.code.DatabaseActions;
import com.justas.planuotojaspro.global.BaselineData;
import com.justas.planuotojaspro.global.SaveToCSV;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class StatisticsWindow {
    private JPanel panel;
    private JTable dataset;
    private DatePicker dateFrom;
    private DatePicker dateTo;
    private JButton saveAs;
    private DefaultTableModel model;

    public StatisticsWindow() {


        dateTo.setDateToToday();
        dateFrom.setDate(LocalDate.now().minusDays(30));
        updateTable();
        dateFrom.addDateChangeListener(
                dateChangeEvent -> {
                    updateTable();
                }
        );
        dateTo.addDateChangeListener(
                dateChangeEvent -> {
                    updateTable();
                }
        );
        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileDialog saveDialog = new FileDialog(new Frame(), "Pasirinkite failą į kurį norite išsaugoti ", FileDialog.SAVE);
                saveDialog.setFile(".csv");
                saveDialog.setVisible(true);
                try {
                    SaveToCSV save = new SaveToCSV();
                    save.exportTable(dataset, saveDialog.getDirectory() + saveDialog.getFile());
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void updateTable() {
        model = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        dataset.setModel(model);
        model.addColumn(getTranslation("t_taskname"));
        model.addColumn(getTranslation("t_taskduration"));
        model.addColumn(getTranslation("t_taskbaseline"));
        model.addColumn(getTranslation("t_overbaseline"));
        for (int i = 0; i < model.getRowCount(); i++)
        {
            model.removeRow(i);
        }
        List<BaselineData> list = DatabaseActions.getBaselines(dateFrom.getDate().toString(), dateTo.getDate().toString());
        for (BaselineData data : list) {
            model.addRow(new Object[] {data.getTaskName(), data.getDuration(), data.getTaskbase(), data.isOverbase()});
        }

    }

    public JPanel returnPanel() {
        return this.panel;
    }
}
