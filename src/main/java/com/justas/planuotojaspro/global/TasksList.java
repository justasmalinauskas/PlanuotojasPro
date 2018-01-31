package com.justas.planuotojaspro.global;

import com.justas.planuotojaspro.code.DatabaseActions;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.justas.planuotojaspro.global.GlobalMethods.getTranslation;

public class TasksList extends JPanel {
    private static List<Task> tasks;
    private static int activeTask = -1;
    private static Thread thread;
    private static JPanel panel;
    private static String searchname;
    private static String searchdate;

    public static void add(Task task) {tasks.add(task);}

    public static List<Task> searchByDate(String date) {
        List<Task> t = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTaskdate().equals(date)) {
                t.add(task);
            }
        }
        return t;
    }

    public static List<Task> searchByName(String name) {
        ArrayList<Task> t = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTaskname().equals(name)) {
                t.add(task);
            }
        }
        return t;
    }

    private static int getTaskByUniqueTaskID(int timeid) {
        int usedtask = 0;
        for (Task task : tasks) {
            if (task.getTaskdateid() == timeid) {
                return tasks.indexOf(task);
            }
        }
        return -1;
    }

    public static void startTask(int timeid) {

        activeTask = getTaskByUniqueTaskID(timeid);
         thread = new Thread(() -> {
             ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
             exec.scheduleAtFixedRate(() -> {
                 System.out.println("Thread Updated");
                 tasks.get(activeTask).addDuration();
                 tasks.get(activeTask).setTaskTimes();
             }, 0, 60, TimeUnit.SECONDS);
         });
        thread.start();
    }
    
    public static void setTasks(ArrayList<Task> t) {
        tasks = t;
    }

    public static List<Task> getTasks(ArrayList<Task> t) {
        return tasks;
    }

    public static List<Task> searchByNameAndDate(String name, String date) {
        List<Task> t = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getTaskdate().equals(date) && task.getTaskname().contains(name)) {
                t.add(task);
            }
        }
        return t;
    }

    public static void StopTask() {

        if (thread != null) {
            thread.interrupt();
            DatabaseActions.updateTask(tasks.get(activeTask).getTaskdateid(), tasks.get(activeTask).getTaskduration());
            if (tasks.get(activeTask).getifRunning())
                tasks.get(activeTask).stopTask();
            System.out.println("Thread Stopped");
            activeTask = -1;
            thread = null;
        }
    }

    public static Task getRunningTask() {
        for (Task task : tasks) {
            if (task.getifRunning()) {
                return task;
            }
        }
        return null;
    }

    public static int getRunningTaskID() {
        for (Task task : tasks) {
            if (task.getifRunning()) {
                return tasks.indexOf(task);
            }
        }
        return -1;
    }

    public static Task getTask(int runtid) {
        for (Task task : tasks) {
            if (tasks.indexOf(task) == runtid) {
                return task;
            }
        }
        return null;
    }


    public static void getAllTasks() {
        List<Task> taskslist = DatabaseActions.getAllTasks();
        List<Task> taskc = tasks;
        if (isActive()) {
            taskslist.remove(activeTask);
            taskc.remove(activeTask);
        }
        if (taskslist != taskc) {
            StopTask();
            tasks = taskslist;
        }
    }

    public static boolean isActive()
    {
        return activeTask != -1;
    }
}
