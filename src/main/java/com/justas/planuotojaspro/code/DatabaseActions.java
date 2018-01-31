package com.justas.planuotojaspro.code;

import com.justas.planuotojaspro.global.BaselineData;
import com.justas.planuotojaspro.global.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
            addTaskFromDate(maxid, date);
        }
    }


    public static ArrayList<Task> getTasksBetweenDates(String dateFrom, String dateTo) {
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


    public static ArrayList<Task> getTasksAtDate(String date) {
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


    private static ArrayList<Task> getTasks(ResultSet rs) {
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

    public static void updateTask(int taskid, int taskduration) {
        String sql = "UPDATE TimeTasks SET taskduration = ? WHERE timeid = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, taskduration);
            pstmt.setInt(2, taskid);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public static ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String sql = "SELECT t.taskid, t.taskname, t.taskbase, td.timeid, td.taskduration, td.taskdate FROM Tasks t " +
                "INNER JOIN TimeTasks td ON t.taskid=td.timetaskid";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            tasks = getTasks(rs);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return tasks;
    }

    public static ArrayList<String> getDaysByTask(int taskid) {
        ArrayList<String> days = new ArrayList<>();
        String sql = "SELECT taskdate FROM  TimeTasks WHERE timetaskid = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, taskid);
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    String taskdate = rs.getString("taskdate");
                    days.add(taskdate);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return days;
    }

    public void updateTasks(int id, String text, Integer baseline, ArrayList<String> tasks) {
        String sql = "UPDATE Tasks SET taskname = ?, taskbase = ? WHERE taskid = ?";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, text);
            pstmt.setInt(2, baseline);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        for (String task: tasks) {
            if(!dateExists(id, task)) {
                addTaskFromDate(id, task);
            } else {
                if (existsNotInList(id, tasks)) {
                    removeTaskFromDate(id, task);
                }
            }
        }
        if(!ifAnyDateExists(id)) {
            deleteTask(id);
        }
    }

    private boolean existsNotInList(int id, ArrayList<String> tasks) {
        boolean exists = false;
        for (String task: tasks) {
            String sql = "SELECT * FROM TimeTasks WHERE timetaskid = ? AND taskdate != ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.setString(2, task);
                ResultSet rs = pstmt.executeQuery();
                try {
                    if (rs.next()){
                        exists = true;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
            }

        }
        return exists;
    }

    private void deleteTask(int id) {
        String sqld = "DELETE FROM Tasks WHERE taskid = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sqld)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void addTaskFromDate(int id, String date) {
        String sql = "INSERT INTO TimeTasks(timetaskid, taskduration, taskdate) VALUES(?,?,?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, 0);
            pstmt.setString(3, date);
            pstmt.execute();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private void removeTaskFromDate(int id, String date) {
        String sql = "DELETE FROM TimeTasks WHERE timetaskid = ? AND taskdate = ?";

        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, date);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private boolean ifAnyDateExists(int taskid) {
        String sql = "SELECT * FROM TimeTasks WHERE timetaskid = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, taskid);
            ResultSet rs = pstmt.executeQuery();
            try {
                if (rs.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    private boolean dateExists(int taskid, String date) {
        String sql = "SELECT COUNT(*) FROM TimeTasks WHERE timetaskid = ? AND taskdate = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, taskid);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            try {
                if (rs.next()){
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }
    public static List<BaselineData> getBaselines(String from, String to) {
        List<BaselineData> data = new ArrayList<>();
        String sql = "SELECT t.taskname, SUM(td.taskduration) AS duration, SUM(t.taskbase) AS baseline FROM Tasks t " +
                "INNER JOIN TimeTasks td ON t.taskid=td.timetaskid WHERE td.taskdate " +
                "BETWEEN ? AND ? GROUP BY t.taskid";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, from);
            pstmt.setString(2, to);
            ResultSet rs = pstmt.executeQuery();
            try {
                while (rs.next()) {
                    data.add(new BaselineData(rs.getString("taskname"), rs.getInt("duration"), rs.getInt("baseline")));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        for (BaselineData base : data) {
            System.out.println(base.getTaskName() + " " + base.getDuration() + "/" + base.getTaskbase() + " " + base.isOverbase());
        }
        return data;
    }
}
