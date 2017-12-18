package com.justas.planuotojaspro.windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TasksWindow {
    private JPanel panel;
    private JTextField taskName;
    private JRadioButton thisIsOneTimeRadioButton;
    private JCheckBox mondayCheckBox;
    private JCheckBox wednesdayCheckBox;
    private JCheckBox thursdayCheckBox;
    private JCheckBox fridayCheckBox;
    private JCheckBox saturdayCheckBox;
    private JCheckBox sundayCheckBox;
    private JCheckBox tuesdayCheckBox;
    private JRadioButton thisIsReccuringTaskRadioButton;
    private JRadioButton thisIsTaskHappeningRadioButton;
    private JList selectedDays;
    private JPanel calendarField;
    private JButton cancelChangesButton;
    private JButton saveChangesButton;
    private JTextField textField1;
    private JButton button1;

    public TasksWindow() {
        thisIsOneTimeRadioButton.setSelected(true);
        thisIsOneTimeRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisIsOneTimeRadioButton.setSelected(true);
                thisIsReccuringTaskRadioButton.setSelected(false);
                thisIsTaskHappeningRadioButton.setSelected(false);
                reccuringDays();
            }
        });
        thisIsReccuringTaskRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisIsReccuringTaskRadioButton.setSelected(true);
                thisIsOneTimeRadioButton.setSelected(false);
                thisIsTaskHappeningRadioButton.setSelected(false);
                reccuringDays();
            }
        });
        thisIsTaskHappeningRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                thisIsTaskHappeningRadioButton.setSelected(true);
                thisIsReccuringTaskRadioButton.setSelected(false);
                thisIsOneTimeRadioButton.setSelected(false);
                reccuringDays();
            }
        });
        saveChangesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }

    private void reccuringDays() {
        if (!thisIsReccuringTaskRadioButton.isSelected()) {
            mondayCheckBox.setEnabled(false);
            tuesdayCheckBox.setEnabled(false);
            wednesdayCheckBox.setEnabled(false);
            thursdayCheckBox.setEnabled(false);
            fridayCheckBox.setEnabled(false);
            saturdayCheckBox.setEnabled(false);
            sundayCheckBox.setEnabled(false);
        } else {
            mondayCheckBox.setEnabled(true);
            tuesdayCheckBox.setEnabled(true);
            wednesdayCheckBox.setEnabled(true);
            thursdayCheckBox.setEnabled(true);
            fridayCheckBox.setEnabled(true);
            saturdayCheckBox.setEnabled(true);
            sundayCheckBox.setEnabled(true);
        }
    }

    public JPanel returnPanel() {
        return this.panel;
    }

    // if one time task send today
    //if task by days send week days
    // if specific days send week days as array
}
