package com.justas.planuotojaspro.global;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Task {


    private int taskid;
    private JLabel taskname;
    private JLabel tasktimes;
    private JButton changeTaskStat;
    private JButton taskSettings;
    private JPanel panel;
    private int taskbase;

    private int taskdateid;
    private int taskduration;
    private String taskdate;


    public Task(int taskid, String taskname, int taskbase, int taskdateid, int taskduration, String taskdate) {
        this.taskid = taskid;
        this.taskname.setText(taskname);
        this.taskbase = taskbase;

        this.taskdateid = taskdateid;
        this.taskduration = taskduration;
        this.taskdate = taskdate;

        setTaskTimes();


        changeTaskStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });
    }


    private void setTaskTimes() {
        tasktimes.setText(getHandM(taskduration) + "/" + getHandM(taskbase));
    }


    private String getHandM(int time) {
        int hours = time / 60;
        int minutes = time % 60;
        return ((String.format("%02d", hours)) + ":" + (String.format("%02d", minutes)));
    }

    public String getTaskdate() {
        return taskdate;
    }

    public void setTaskdate(String taskdate) {
        this.taskdate = taskdate;
    }

    public int getTaskduration() {
        return taskduration;
    }

    public void setTaskduration(int taskduration) {
        this.taskduration = taskduration;
    }

    public int getTaskdateid() {
        return taskdateid;
    }

    public void setTaskdateid(int taskdateid) {
        this.taskdateid = taskdateid;
    }

    public int getTaskbase() {
        return taskbase;
    }

    public void setTaskbase(int taskbase) {
        this.taskbase = taskbase;
    }

    public String getTaskname() {
        return taskname.getText();
    }

    public void setTaskname(String taskname) {
        this.taskname.setText(taskname);
    }

    public int getTaskid() {
        return taskid;
    }

    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }

    public JPanel getTaskPanel() {
        return this.panel;
    }
}
