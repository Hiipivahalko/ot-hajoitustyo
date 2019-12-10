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

/**
 * Luokka Food-luokkien tietokanta käsittelyille/tapahtumille
 */
public class FoodDao  implements Dao<Food, Integer> {

    private Database db;
    private String tableName;

    /**
     * FoodDao objekti jolla voidaan totetuttaa tarvittavia kyselyitä Food-tauluun
     * @param db tietokanta
     * @param tableName tietokantataulun nimi
     */
    public FoodDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * Etsii tietyn rivin tietokannasta
     * @param key tietokanta rivin ID
     * @return rivistä muodostettu raaka-aine objekti
     */
    @Override
    public Food findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Etsii tietyn rivin tietokannasta
     * @param food etsittävä rivi
     * @return rivistä muodostettu raaka-aine objekti
     */
    public Food findOne(Food food) {
        Food f = null;
        List<Food> foods = selectQuery("SELECT * FROM " + tableName +
                " WHERE name = '" + food.getName() + "' AND manufacturer = '" + food.getManufacturer() + "' " +
                "AND preservation = '" + food.getPreservation() + "' AND weight = '" + food.getWeight() + "';");

        if (foods.size() == 1) {
            return foods.get(0);
        }
        return f;
    }

    /**
     * Hakee tietokantataulusta Food kaikki rivit.
     * Rivit on järjestetty aakkosittain nimen mukaan.
     * @return Food taulun kaikki rivit
     */
    @Override
    public List<Food> findAll() {
        List<Food> foods = selectQuery("SELECT * FROM " + tableName + " ORDER BY name;");

        return foods;
    }

    /**
     * Tallentaa uuden rivin tietokantatauluun Food tai päivittää määrän jos on jo olemassa
     * @param food tallennettava ruoka
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

    /**
     * Tallentaa annetun raaka-aineen tietokantaan
     * @param food tallennettava raaka-aine
     */
    public void save(Food food) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName +
                    "(name, manufacturer, preservation, weight, amount) VALUES (?,?,?,?,?)");
            stmt.setString(1, food.getName());
            stmt.setString(2, food.getManufacturer());
            stmt.setString(3, food.getPreservation());
            stmt.setInt(4, food.getWeight());
            stmt.setInt(5, food.getAmount());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Päivittää tietyn raaka-aineen amount arvoa tietokannassa
     * @param food päivitettävä raaka-aine
     * @param newValue päivitettävä arvo (uusi arvo = vanha + newValue)
     */
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

    /**
     * Poistaa tietyn rivin tietokannasta
     * @param key poistettavan rivin ID
     */
    @Override
    public void delete(Integer key) {
        try {
            Connection conn = db.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM " + tableName + " WHERE id = " + key);
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Haravoi vain tietyn säännän/nimen omaavat tietokantarivit
     * @param filter sääntö millä haravoidaan
     * @return osajoukko kaikista tietokannan riveistä
     */
    @Override
    public List<Food> filterFromAll(String filter)  {
        return selectQuery("SELECT * FROM " + tableName +
                " WHERE name LIKE '%" + filter + "%' OR manufacturer LIKE '%" + filter + "%' ORDER BY name;");
    }

    /**
     * Etsitään tietokannasta raaka-aineita tietyn nimen ja valmistajan perusteella
     * @param name nimi
     * @param manufacture valmistaja
     * @return lista raaka-aineita
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
     * @param query tietokantaan tehtävä kysely
     * @return lista Food olioita
     */
    private List<Food> selectQuery(String query) {
        List<Food> foods = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Food food = new Food(rs.getString("name"), rs.getString("manufacturer"),
                        rs.getString("preservation"), rs.getInt("weight"),
                        rs.getInt("id"), rs.getInt("amount"));
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
