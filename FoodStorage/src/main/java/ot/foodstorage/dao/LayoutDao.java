package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka Layout-luokkien tietokanta käsittelyille/tapahtumille
 */
public class LayoutDao implements Dao<Layout, Integer> {

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
     * Etsii tietyn rivin tietokannasta
     * @param key tietokanta rivin ID
     * @return rivistä muodostettu Layout objekti
     */
    @Override
    public Layout findOne(Integer key) throws SQLException {
        return null;
    }

    /**
     * Hakee tietokantataulusta Layout kaikki rivit.
     * Rivit on järjestetty aakkosittain nimen mukaan.
     * @return Layout taulun kaikki rivit
     */
    @Override
    public List<Layout> findAll() {
        List<Layout> layouts = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " ORDER BY name;");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Layout layout = new Layout(rs.getInt("id"), rs.getString("name"),
                        rs.getString("manufacturer"), rs.getString("preservation"),
                        rs.getInt("weight"));
                layouts.add(layout);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return layouts;
    }

    /**
     * Tallentaa uuden rivin Layout tietokantatauluun tai päivittää määrän jos on jo olemassa
     * @param object tallennettava layout
     */
    @Override
    public void saveOrUpdate(Layout object) {
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

    /**
     * Poistaa tietyn rivin tietokannasta
     * @param key poistettavan rivin ID
     */
    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Layout> filterFromAll(String filter) throws SQLException {
        return null;
    }
}
