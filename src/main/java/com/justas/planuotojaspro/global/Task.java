package com.justas.planuotojaspro.global;

import com.justas.planuotojaspro.windows.CurrentTasksWindow;
import com.justas.planuotojaspro.windows.TasksWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class Task extends JPanel {


    private int taskid;
    private JLabel taskname;
    private JLabel tasktimes;
    private JButton changeTaskStat;
    private JButton taskSettings;
    private JPanel panel;
    private int taskbase;

    private int timeid;
    private int taskduration;
    private String taskdate;

    private boolean isRuning = false;


    public Task(int taskid, String taskname, int taskbase, int timeid, int taskduration, String taskdate) {
        this.taskid = taskid;
        this.taskname.setText(taskname);
        this.taskbase = taskbase;

        this.timeid = timeid;
        this.taskduration = taskduration;
        this.taskdate = taskdate;

        setTaskTimes();


        changeTaskStat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (!isRuning) {
                    startTask(timeid);
                }
                else {
                    stopTask();
                }
            }
        });
        taskSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                stopTask();
                TasksWindow tasksWindow = new TasksWindow();
                tasksWindow.editTask(getThis());
            }
        });
    }

    private Task getThis() {
        return this;
    }

    private void startTask(int timeid) {
        if (!isRuning) {
            TasksList.StopTask();
            TasksList.startTask(timeid);
            isRuning = true;
            changeTaskStat.setText(getTranslation("t_stop"));
        }

    }

    protected void stopTask() {
        if (isRuning) {
            isRuning = false;
            TasksList.StopTask();
            changeTaskStat.setText(getTranslation("t_start"));
        }
    }


    protected void setTaskTimes() {
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
        return timeid;
    }

    public void setTaskdateid(int timeid) {
        this.timeid = timeid;
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

    public boolean getifRunning() {
        return isRuning;
    }

    public JPanel getTaskPanel() {
        return this.panel;
    }

    public String getAll() {
        return "TaskID: " + taskid + " TaskUniqueID: " + timeid + " Task: " +  taskname.getText() + " Date: " + taskdate +
                " Time: " + getHandM(taskduration) + "/" + getHandM(taskbase);
    }

    public void addDuration() {
        this.taskduration++;
    }
}
