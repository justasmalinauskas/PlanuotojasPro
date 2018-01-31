package com.justas.planuotojaspro.windows;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.justas.planuotojaspro.code.*;
import com.justas.planuotojaspro.global.Task;
import com.justas.planuotojaspro.global.TasksList;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;
import static com.justas.planuotojaspro.global.TasksList.StopTask;

public class CurrentTasksWindow extends JFrame {

    private JPanel panel;
    private JRoundedTextField searchbar;
    private JLabel datesjobs;
    private DatePicker datePicker;
    private JScrollPane scrollPane;
    private JPanel tasksList;
    private JPanel topPanel;
    private JButton refresh;

    public CurrentTasksWindow() {
        datesjobs.setText(getTranslation("t_todaytasks"));

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
        refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                refreshTasks();
            }
        });
    }

    private void refreshTasks() {
        SwingUtilities.invokeLater(() -> {
            StopTask();
            System.out.println("Getting " + datePicker.getDate().toString() + " tasks.");
            TasksList.getAllTasks();
            System.out.println("Used query: " + searchbar.getText() + " With date: " + datePicker.getDate().toString());
            tasksList.removeAll();
            int runtid = TasksList.getRunningTaskID();
            if (runtid != -1) {
                tasksList.add(TasksList.getTask(runtid));
                System.out.println("Running: " + TasksList.getTask(runtid).getAll());
            }
            System.out.println(tasksList.getComponentCount());
            for (Task task : TasksList.searchByNameAndDate(searchbar.getText(), datePicker.getDate().toString())) {
                if (TasksList.searchByNameAndDate(searchbar.getText(), datePicker.getDate().toString()).indexOf(task) != runtid) {
                    tasksList.add(task.getTaskPanel(), BorderLayout.NORTH);
                    System.out.println(task.getAll());
                }
            }
            tasksList.revalidate();
            tasksList.repaint();
            tasksList.revalidate();
            tasksList.repaint();
            tasksList.setVisible(true);
            System.out.println(tasksList.getComponentCount());
        });
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
        tasksList = new JPanel(new GridLayout(0, 1));
    }

    public void update() {
        refreshTasks();
    }
}
