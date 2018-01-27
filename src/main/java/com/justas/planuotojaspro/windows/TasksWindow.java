package com.justas.planuotojaspro.windows;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TasksWindow {
    private JPanel panel;
    private JTextField taskName;
    private JRadioButton thisIsOneTimeRadioButton;
    private JRadioButton thisIsTaskHappeningRadioButton;
    private JList<String> selectedDays;
    private JPanel calendarField;
    private JButton cancelChangesButton;
    private JButton saveChangesButton;
    private com.github.lgooddatepicker.components.DatePicker selectDay;
    private JButton insertDay;
    private ArrayList<String> tasks = new ArrayList<>();

    public TasksWindow() {
        thisIsOneTimeRadioButton.setSelected(true);
        specificDays();
        thisIsOneTimeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisIsOneTimeRadioButton.setSelected(true);
                thisIsTaskHappeningRadioButton.setSelected(false);
                specificDays();
            }
        });
        thisIsTaskHappeningRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisIsTaskHappeningRadioButton.setSelected(true);
                thisIsOneTimeRadioButton.setSelected(false);
                specificDays();
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
        insertDay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tasks.add(selectDay.getText());
                getDays();
            }
        });
        cancelChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                
            }
        });
    }

    private void specificDays() {
        if (!thisIsTaskHappeningRadioButton.isSelected()) {
            selectDay.setEnabled(false);
            selectedDays.setEnabled(false);
            insertDay.setEnabled(false);
        } else {
            selectDay.setEnabled(true);
            selectedDays.setEnabled(true);
            insertDay.setEnabled(true);
        }
    }

    private void getDays() {
        final DefaultListModel<String> model = new DefaultListModel<>();
        List<String> listWithoutDuplicates = tasks.stream()
                .distinct()
                .collect(Collectors.toList());
        tasks = new ArrayList<>();
        tasks.addAll(listWithoutDuplicates);
        for (String task : tasks) {
            model.addElement(task);
        }

        selectedDays.setModel(model);
    }


    public JPanel returnPanel() {
        return this.panel;
    }

    private void createUIComponents() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        settings.setAllowKeyboardEditing(false);
        settings.setFormatForDatesCommonEra("yyyy-MM-dd");
        selectDay = new DatePicker(settings);
        selectDay.setDateToToday();
    }

    // if one time task send today
    //if task by days send week days
    // if specific days send week days as array
}
