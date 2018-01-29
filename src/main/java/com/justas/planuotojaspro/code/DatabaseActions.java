package com.justas.planuotojaspro.code;

import com.justas.planuotojaspro.global.Task;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseActions {
    private static Connection c = null;
    public static void createDB() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:database.db");
            createTables();
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Opened database successfully");
    }

    private static void createTables() {
        createTimeTasks();
        createTasks();
    }

    private static void createTasks() {
        String sql = "CREATE TABLE IF NOT EXISTS Tasks (\n"
                + "	taskid INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"  // ID
                + "	taskname TEXT NOT NULL,\n" // Užduoties pavadinimas
                + "	taskbase INTEGER NOT NULL \n" //Laiko limitas minutėmis
                + ");";
        try (Statement stmt = c.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table Tasks created successfully");
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    private static void createTimeTasks() {
        String sql = "CREATE TABLE IF NOT EXISTS TimeTasks (\n"
	            + "timeid	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" // ID
	            + "timetaskid	INTEGER NOT NULL,\n" // taskid iš Tasks lentelės
                + "taskduration	INTEGER NOT NULL,\n" // realus įvykdytas laikas, minutėmis
                + "taskdate	TEXT NOT NULL\n" // Data, kuriai priskirta užduotis
                +");";
        try (Statement stmt = c.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table TimeTasks created successfully");
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void insertTask(String taskName, Integer taskBase, ArrayList<String> dates) {
        String sql = "INSERT INTO Tasks(taskname, taskbase) VALUES(?,?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, taskName);
            pstmt.setInt(2, taskBase);
            pstmt.execute();
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        //
        String sqlmaxid = "SELECT * FROM Tasks ORDER BY taskid DESC LIMIT 1";
        int maxid = 0;
        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(sqlmaxid)) {
            while (rs.next()) {
                maxid = rs.getInt("taskid");
                System.out.println(rs.getInt("taskid"));
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        //
        for (String date : dates) {
            sql = "INSERT INTO TimeTasks(timetaskid, taskduration, taskdate) VALUES(?,?,?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setInt(1, maxid);
                pstmt.setInt(2, 0);
                pstmt.setString(3, date);
                pstmt.execute();
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }


    public ArrayList<Task> getTasksBetweenDates(String dateFrom, String dateTo) {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.taskid, t.taskname, t.taskbase, td.timeid, td.taskduration, td.taskdate FROM Tasks t " +
                "INNER JOIN TimeTasks td ON t.taskid=td.timetaskid " +
                "WHERE td.taskdate BETWEEN ? AND ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, dateFrom);
            pstmt.setString(2, dateTo);
            ResultSet rs = pstmt.executeQuery();
            tasks = getTasks(rs);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tasks;
    }


    public ArrayList<Task> getTasksAtDate(String date) {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.taskid, t.taskname, t.taskbase, td.timeid, td.taskduration, td.taskdate FROM Tasks t " +
                "INNER JOIN TimeTasks td ON t.taskid=td.timetaskid " +
                "WHERE td.taskdate = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();
            tasks = getTasks(rs);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tasks;
    }


    private ArrayList<Task> getTasks(ResultSet rs) {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            while (rs.next()) {
                int taskid = rs.getInt("taskid");
                String taskname = rs.getString("taskname");
                int taskbase = rs.getInt("taskbase");

                int timeid = rs.getInt("timeid");
                int taskduration = rs.getInt("taskduration");
                String taskdate = rs.getString("taskdate");

                tasks.add(new Task(taskid, taskname, taskbase, timeid, taskduration, taskdate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
