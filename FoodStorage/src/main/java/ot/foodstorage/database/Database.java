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

    private void createTables(Statement stmt) throws SQLException {
        stmt.execute(createFoodTable());
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

    private String createFoodTemplateTable() {
        return "CREATE TABLE IF NOT EXISTS" +
                " FoodTemplate(" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " name TEXT NOT NULL," +
                " manufacturer TEXT NOT NULL," +
                " preservation TEXT NOT NULL" +
                ");";
    }


}
