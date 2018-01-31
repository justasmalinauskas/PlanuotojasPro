package com.justas.planuotojaspro.windows;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.justas.planuotojaspro.code.DatabaseActions;
import com.justas.planuotojaspro.global.Task;
import com.justas.planuotojaspro.global.UserMessages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

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
    private Task editableTask;
    private JDialog dialog;

    public TasksWindow() {
        thisIsTaskHappeningRadioButton.setSelected(true);



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
            DatabaseActions db = new DatabaseActions();
            if (taskName.getText().equals("")) {
                new UserMessages().errorMessage(getTranslation("t_musthavetaskname"), getTranslation("t_error"));
                return;
            }
            if (thisIsTaskHappeningRadioButton.isSelected() && tasks.size() == 0 && editableTask.getTaskname().length() == 0) {
                new UserMessages().errorMessage(getTranslation("t_musthavedates"), getTranslation("t_error"));
                return;
            }
            if (editableTask != null) {
                boolean msg = new UserMessages().confirmMessage(getTranslation("t_comfirmchanges"), getTranslation("t_comfirmchanges"));
                if (msg) {

                    db.updateTasks(editableTask.getTaskid(), taskName.getText(), (Integer) taskBase.getValue(), tasks);
                    new UserMessages().infoMessage(getTranslation("t_changesdone"), getTranslation("t_done"));
                    dialog.dispose();
                }
            }
            else {

                if (thisIsOneTimeRadioButton.isSelected()) {
                    ArrayList<String> quick = new ArrayList<>();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate localDate = LocalDate.now();
                    quick.add(dtf.format(localDate));
                    db.insertTask(taskName.getText(), (Integer) taskBase.getValue(), quick);
                } else {
                    db.insertTask(taskName.getText(), (Integer) taskBase.getValue(), tasks);
                }
                new UserMessages().infoMessage(getTranslation("t_changesdone"), getTranslation("t_done"));
                setDefault();

            }

        });
        insertDay.addActionListener(actionEvent -> {
            tasks.add(selectDay.getText());
            getDays();
        });
        cancelChangesButton.addActionListener(actionEvent -> {
            setDefault();
        });
        selectedDays.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    tasks.remove(index);
                    getDays();
                } else if (evt.getClickCount() == 3) {

                    // Triple-click detected
                    int index = list.locationToIndex(evt.getPoint());
                }
            }
        });
    }

    private void setDefault() {
        taskBase.setValue(60);
        taskName.setText("");
        tasks = new ArrayList<>();
        selectedDays.setModel(new DefaultListModel<>());
    }

    private void dispose() {
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

    public void editTask(Task task) {
        editableTask = task;


        taskName.setText(editableTask.getTaskname());
        taskBase.setValue(editableTask.getTaskbase());
        final DefaultListModel<String> model = new DefaultListModel<>();
        tasks.addAll(DatabaseActions.getDaysByTask(editableTask.getTaskid()));
        getDays();
        dialog = new JDialog();
        dialog.add(this.panel);
        dialog.setPreferredSize(new Dimension(600,500));
        dialog.pack();
        dialog.setVisible(true);

    }
}
