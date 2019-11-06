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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }

    private String createFoodTable() {
        return "CREATE TABLE IF NOT EXIST" +
                " Food(" +
                " id INTEGER NOT NULL PRIMARY KEY AUTOINCREAMENT," +
                " name TEXT NOT NULL" +
                " manufacturer TEXT NOT NULL" +
                " preservation TEXT NOT NULL" +
                " weight INTEGER NOT NULL" +
                " dueDate TEXT NOT NULL" +
                ");";
    }


}
