/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FoodDao  implements Dao<Food, Integer>{

    private Database db;
    private String tableName;

    public FoodDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    @Override
    public Food findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Hakee tietokantataulusta Food kaikki rivit.
     * Rivit on järjestetty aakkosittain nimen mukaan.
     * @return - Food taulun kaikki rivit
     * @throws SQLException
     */
    @Override
    public List<Food> findAll() throws SQLException {
        List<Food> foods = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " ORDER BY name;");

        ResultSet rs = stmt.executeQuery();

        while(rs.next()) {
            Food food = new Food(rs.getString("name"),
                    rs.getString("manufacturer"),
                    rs.getString("preservation"),
                    rs.getInt("weight"),
                    rs.getString("dueDate"),
                    rs.getInt("id"));
            foods.add(food);
        }
        return foods;
    }

    /**
     * Tallentaa uuden rivin ruoan tietokantatauluun Food
     * @param food
     * @throws SQLException
     */
    @Override
    public void save(Food food) throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName +
                "(name, manufacturer, preservation, weight, dueDate) VALUES (?,?,?,?,?)");
        stmt.setString(1, food.getName());
        stmt.setString(2, food.getManufacturer());
        stmt.setString(3, food.getPreservation());
        stmt.setInt(4, food.getWeight());
        stmt.setString(5,food.getDueDate());
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }
    
    @Override
    public Food update(Food object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Food> filterFromAll(String filter) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
