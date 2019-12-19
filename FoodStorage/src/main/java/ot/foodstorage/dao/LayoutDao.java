package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka Layout-luokkien tietokanta käsittelyille/tapahtumille
 */
public class LayoutDao implements Dao<Food> {

    private Database db;
    private String tableName;

    /**
     * LayoutDao objekti jolla voidaan totetuttaa tarvittavia kyselyitä Layout-tauluun
     * @param db tietokanta
     * @param tableName tietokantataulun nimi
     */
    public LayoutDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * Hakee tietokantataulusta Layout kaikki rivit.
     * Rivit on järjestetty aakkosittain nimen mukaan.
     * @return Layout taulun kaikki rivit
     */
    @Override
    public List<Food> findAll() {
        return selectQuery("SELECT * FROM " + tableName + " ORDER BY name;");
    }

    /**
     * Tallentaa uuden rivin Layout tietokantatauluun
     * @param object tallennettava layout
     */
    @Override
    public void save(Food object) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName +
                    " (name, manufacturer, preservation, weight) VALUES (?,?,?,?)");
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getManufacturer());
            stmt.setString(3, object.getPreservation());
            stmt.setInt(4, object.getWeight());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void update(Food object) {

    }


    /**
     * Poistaa tietyn rivin tietokannasta
     * @param food poistettavan rivi
     */
    @Override
    public void delete(Food food) {

    }

    @Override
    public List<Food> selectQuery(String query) {
        List<Food> foods = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Food food = new Food(rs.getString("name"), rs.getString("manufacturer"),
                        rs.getString("preservation"), rs.getInt("weight"));
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
