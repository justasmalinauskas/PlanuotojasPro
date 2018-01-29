package com.justas.planuotojaspro.windows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.justas.planuotojaspro.code.*;
import com.justas.planuotojaspro.global.Task;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class CurrentTasksWindow {

    private JPanel panel;
    private JRoundedTextField searchbar;
    private JLabel datesjobs;
    private DatePicker datePicker;
    private JScrollPane tasksLists;
    private JPanel tasksList;
    private ArrayList<Task> tasks;

    public CurrentTasksWindow() {
        datesjobs.setText(getTranslation("t_todaytasks"));


        //searchbar.setPreferredSize(new Dimension(200,63));

        refreshTasks();

        datePicker.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                datePicker.togglePopup();
            }
        });

        datePicker.addDateChangeListener(
                dateChangeEvent -> {
                    if (datePicker.getDate().equals(LocalDate.now())) {
                        datesjobs.setText(getTranslation("t_todaytasks"));
                        //šiandienos užduotys
                    } else {
                        datesjobs.setText(datePicker.getDate().toString() + " " + getTranslation("t_tasks").toLowerCase());
                        //pasirinktos dienos užduotys
                    }
                    refreshTasks();
                }
        );
        searchbar.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                refreshTasks();
            }

            public void removeUpdate(DocumentEvent e) {
                refreshTasks();
            }

            public void insertUpdate(DocumentEvent e) {
                refreshTasks();
            }
        });
    }

    private void refreshTasks() {

        DatabaseActions db = new DatabaseActions();
        System.out.println("Getting " + datePicker.getDate().toString() + " tasks.");
        tasks = db.getTasksAtDate(datePicker.getDate().toString());
        System.out.println("Found: " + tasks.size() + " tasks.");
        tasksLists.removeAll();
        System.out.println(tasksLists.getComponentCount());
        tasks.forEach(task -> {
            System.out.println("Task:" + task.getTaskid() + " " + task.getTaskdateid() + " " +
                    task.getTaskname() + " " + task.getTaskduration() + " " +
                    task.getTaskbase());
            if (task.getTaskname().contains(searchbar.getText())) {
                tasksLists.add(task.getTaskPanel());
            }
        });
        SwingUtilities.invokeLater(() -> {
            tasksLists.revalidate();
            tasksLists.repaint();
            tasksLists.setVisible(true);
        });
        System.out.println(tasksLists.getComponentCount());

    }

    public JPanel returnPanel() {
        return this.panel;
    }

    private void createUIComponents() {
        searchbar = new JRoundedTextField(20, getTranslation("t_searchpanel"));
        DatePickerSettings dateSettings = new DatePickerSettings();
        dateSettings.setVisibleDateTextField(false);
        dateSettings.setGapBeforeButtonPixels(0);
        dateSettings.setFirstDayOfWeek(DayOfWeek.MONDAY);
        dateSettings.setAllowEmptyDates(false);
        datePicker = new DatePicker(dateSettings);
        JButton datePickerButton = datePicker.getComponentToggleCalendarButton();
        datePickerButton.setFocusPainted(false);
        datePickerButton.setMargin(new Insets(0, 0, 0, 0));
        datePickerButton.setContentAreaFilled(false);
        datePickerButton.setBorderPainted(false);
        datePickerButton.setOpaque(false);
        datePickerButton.setText(getTranslation("t_changedate"));
    }
}
