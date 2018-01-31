package com.justas.planuotojaspro.global;

public class BaselineData {
    private String taskName;
    private int duration;
    private int taskbase;
    private boolean overbase;

    public BaselineData(String taskName, int duration, int taskbase) {
        this.taskName = taskName;
        this.duration = duration;
        this.taskbase = taskbase;
        overbase = duration > taskbase;
    }

    public boolean isOverbase() {
        return overbase;
    }

    public int getTaskbase() {
        return taskbase;
    }

    public int getDuration() {
        return duration;
    }

    public String getTaskName() {
        return taskName;
    }
}
