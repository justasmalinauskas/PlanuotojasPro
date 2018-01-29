package com.justas.planuotojaspro.windows;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.justas.planuotojaspro.code.DatabaseActions;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;
import static com.justas.planuotojaspro.global.UserMessages.errorMessage;

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
    private JSpinner taskBase;
    private ArrayList<String> tasks = new ArrayList<>();

    public TasksWindow() {
        thisIsOneTimeRadioButton.setSelected(true);
        specificDays();


        thisIsOneTimeRadioButton.addActionListener(actionEvent -> {
            thisIsOneTimeRadioButton.setSelected(true);
            thisIsTaskHappeningRadioButton.setSelected(false);
            specificDays();
        });
        thisIsTaskHappeningRadioButton.addActionListener(actionEvent -> {
            thisIsTaskHappeningRadioButton.setSelected(true);
            thisIsOneTimeRadioButton.setSelected(false);
            specificDays();
        });
        saveChangesButton.addActionListener(actionEvent -> {
            if (taskName.getText().equals("")) {
                errorMessage(getTranslation("t_musthavetaskname"), getTranslation("t_error"));
                return;
            }
            if (thisIsTaskHappeningRadioButton.isSelected() && tasks.size() == 0) {
                errorMessage(getTranslation("t_musthavedates"), getTranslation("t_error"));
                return;
            }
            DatabaseActions db = new DatabaseActions();
            if (thisIsOneTimeRadioButton.isSelected()) {
                ArrayList<String> quick = new ArrayList<>();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.now();
                quick.add(dtf.format(localDate));
                db.insertTask(taskName.getText(), (Integer) taskBase.getValue(), quick);
            } else {
                db.insertTask(taskName.getText(), (Integer) taskBase.getValue(), tasks);
            }
        });
        insertDay.addActionListener(actionEvent -> {
            tasks.add(selectDay.getText());
            getDays();
        });
        cancelChangesButton.addActionListener(actionEvent -> {

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
        SpinnerModel sm = new SpinnerNumberModel(60, 1, 1439, 1);
        taskBase = new JSpinner(sm);
    }

    // if one time task send today
    //if task by days send week days
    // if specific days send week days as array
}
