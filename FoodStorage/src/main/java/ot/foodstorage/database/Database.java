/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) {
        this.databaseAddress = databaseAddress;

        try {
            Connection conn = DriverManager.getConnection(this.databaseAddress);
            Statement stmt = conn.createStatement();
            createTables(stmt);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    public void initializeDatabase() {
        try {
            Connection conn = DriverManager.getConnection(databaseAddress);
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE Food");
            stmt.execute("DROP TABLE Layout");
            createTables(stmt);

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getStackTrace();
        }
    }

    private void createTables(Statement stmt) throws SQLException {
        stmt.execute(createFoodTable());
        stmt.execute(createLayoutTable());
    }

    private String createFoodTable() {
        return "CREATE TABLE IF NOT EXISTS" +
                " Food(" +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " manufacturer TEXT NOT NULL," +
                " preservation TEXT NOT NULL," +
                " weight INTEGER NOT NULL," +
                " dueDate TEXT NOT NULL," +
                " amount INTEGER NOT NULL" +
                ");";
    }

    private String createLayoutTable() {
        return "CREATE TABLE IF NOT EXISTS" +
                " Layout(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " manufacturer TEXT NOT NULL," +
                " preservation TEXT NOT NULL" +
                ");";
    }


}
