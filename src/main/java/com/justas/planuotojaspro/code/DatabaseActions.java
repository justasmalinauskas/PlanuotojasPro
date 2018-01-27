package com.justas.planuotojaspro.code;

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
        String sql = "INSERT INTO Tasks(taskname, taskbase VALUES(?,?)";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setString(1, taskName);
            pstmt.setInt(2, taskBase);
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

        for (int i = 0; i < dates.size(); i++) {

            sql = "INSERT INTO TimeTasks(timetaskid, taskduration, taskdate VALUES(?,?,?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, taskName);
                pstmt.setInt(2, taskBase);
            } catch (SQLException e) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        }

    }
}
