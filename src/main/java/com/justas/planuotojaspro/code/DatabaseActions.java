package com.justas.planuotojaspro.code;

import java.sql.*;

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
                + "	id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"  // ID
                + "	name text NOT NULL,\n" // Užduoties pavadinimas
                + "	basetime INTEGER NOT NULL, \n" //Laiko limitas sekundėmis
                + "	recurring TEXT NOT NULL, \n"    // Ar pasikartojantis Y arba N
                + "	recurringdays TEXT, \n" // Formatas: MO,TU,WE,TH,FR,SA,SU
                + "	norecurringdate INTEGER \n" // Formatas yra unix timestamp pvz: 1513629878
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
	            + "id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n" // ID
	            + "starttime	INTEGER NOT NULL,\n" // Formatas yra unix timestamp pvz: 1513629878
                + "endtime	INTEGER NOT NULL,\n" // Formatas yra unix timestamp pvz: 1513629878
                + "taskid	INTEGER NOT NULL\n" // Tasks.ID
                +");";
        try (Statement stmt = c.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table TimeTasks created successfully");
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    private static void createTasksDates() {
        String sql = "CREATE TABLE IF NOT EXISTS TaskDates (\n"
                + "id	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,\n"
                + "date	INTEGER NOT NULL,\n"
                + "taskid	INTEGER NOT NULL\n"
                +");";
        try (Statement stmt = c.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table TaskDates created successfully");
        } catch (SQLException e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }

    public void insertTask(String taskName, Integer taskBase, TimeOption option) {
        String sql = "INSERT INTO Tasks(name, baseline) VALUES(?,?)";

    }
}
