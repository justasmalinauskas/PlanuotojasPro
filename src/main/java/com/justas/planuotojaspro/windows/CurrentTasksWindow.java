package com.justas.planuotojaspro.windows;

import javax.swing.*;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.justas.planuotojaspro.code.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class CurrentTasksWindow {

    private JPanel panel;
    private JRoundedTextField searchbar;
    private JLabel datesjobs;
    private JLabel datepopup;

    public CurrentTasksWindow() {
        datesjobs.setText(getTranslation("t_todaytasks"));
        datepopup.setText(getTranslation("t_changedate"));

        //searchbar.setPreferredSize(new Dimension(200,63));

        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        DatePicker datePicker1 = new DatePicker(dateSettings);
        datepopup.add(datePicker1);

        datepopup.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                datePicker1.togglePopup();
            }
        });

        datePicker1.addDateChangeListener(
                dateChangeEvent -> {
                    if (datePicker1.getDate().equals(LocalDate.now())) {
                        datesjobs.setText(getTranslation("t_todaytasks"));
                        //šiandienos užduotys
                    } else {
                        datesjobs.setText(datePicker1.getDate().toString() + " " + getTranslation("t_tasks").toLowerCase());
                        //pasirinktos dienos užduotys
                    }

                }
        );
    }

    public JPanel returnPanel() {
        return this.panel;
    }

    private void createUIComponents() {
        searchbar = new JRoundedTextField(20, getTranslation("t_searchpanel"));
        // TODO: place custom component creation code here
    }
}
