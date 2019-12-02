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

public class FoodDao  implements Dao<Food, Integer> {

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

    public Food findOne(Food food) {
        Food f = null;//new Food("", "", "", -1, "00.00.0000",-1, -1);
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName +
                    " WHERE name = '" + food.getName() + "' AND manufacturer = '" + food.getManufacturer() + "' " +
                    "AND preservation = '" + food.getPreservation() + "' AND weight = '" + food.getWeight() + "';");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                f = new Food(rs.getString("name"),
                        rs.getString("manufacturer"),
                        rs.getString("preservation"),
                        rs.getInt("weight"),
                        rs.getString("dueDate"),
                        rs.getInt("id"),
                        rs.getInt("amount"));
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return f;
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

        while (rs.next()) {
            Food food = new Food(rs.getString("name"),
                    rs.getString("manufacturer"),
                    rs.getString("preservation"),
                    rs.getInt("weight"),
                    rs.getString("dueDate"),
                    rs.getInt("id"),
                    rs.getInt("amount"));
            foods.add(food);
        }

        rs.close();
        stmt.close();
        conn.close();

        return foods;
    }

    /**
     * Tallentaa uuden rivin ruoan tietokantatauluun Food tai päivittää määrän jos on jo olemassa
     * @param food - tallennettava ruoka
     */
    @Override
    public void saveOrUpdate(Food food) {
        Food old = findOne(food);
        if (findOne(food) == null) {
            save(food);
        } else  {
            update(food, food.getAmount() + old.getAmount());
        }

    }

    public void save(Food food) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt;
            stmt = conn.prepareStatement("INSERT INTO " + tableName +
                    "(name, manufacturer, preservation, weight, dueDate, amount) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, food.getName());
            stmt.setString(2, food.getManufacturer());
            stmt.setString(3, food.getPreservation());
            stmt.setInt(4, food.getWeight());
            stmt.setString(5, food.getDueDate());
            stmt.setInt(6, food.getAmount());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(Food food, int newValue) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + tableName + " SET amount = " + newValue +
                    " WHERE name = '" + food.getName() + "' AND manufacturer = '" + food.getManufacturer() + "' " +
                    "AND preservation = '" + food.getPreservation() + "' AND weight = '" + food.getWeight() + "';");

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM " + tableName + " WHERE id = " + key);
    }

    @Override
    public List<Food> filterFromAll(String filter)  {
        return selectQuery("SELECT * FROM " + tableName +
                " WHERE name LIKE '%" + filter + "%' OR manufacturer LIKE '%" + filter + "%' ORDER BY name;");
    }

    /**
     * Etsitään tietokannasta raaka-aineita tietyn nimen ja valmistajan perusteella
     * @param name - nimi
     * @param manufacture - valmistaja
     * @return - lista raaka-aineita
     */
    public List<Food> findByNameAndManufacture(String name, String manufacture) {
        return selectQuery("SELECT * FROM " + tableName +
                " WHERE name = '" + name + "' AND manufacturer = '" + manufacture + "';");
    }

    /**
     * Hakee tietokannasta rivit joilla on tietty sama säilytysmuoto
     * @param filter - säilytysmuoto
     * @return - raaka-aineet
     */
    public List<Food> preservationFilter(String filter)  {
        return selectQuery("SELECT * FROM " + tableName +
                " WHERE preservation = '" + filter + "' ORDER BY name;");
    }

    /**
     * Apufunktio totetuttamaan haluttu kysely tietokantaan
     * @param query - tietokantaan tehtävä kysely
     * @return - lista raaka-aine olioita
     */
    private List<Food> selectQuery(String query) {
        List<Food> foods = new ArrayList<>();

        Connection conn; //= db.getConnection();
        PreparedStatement stmt;// = conn.prepareStatement(query);
        ResultSet rs;

        try {
            conn = db.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Food food = new Food(rs.getString("name"),
                        rs.getString("manufacturer"),
                        rs.getString("preservation"),
                        rs.getInt("weight"),
                        rs.getString("dueDate"),
                        rs.getInt("id"),
                        rs.getInt("amount"));
                foods.add(food);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return foods;
    }
}
