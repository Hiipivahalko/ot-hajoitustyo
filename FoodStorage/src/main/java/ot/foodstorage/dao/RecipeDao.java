package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Luokka Recipe-luokkien tietokanta käsittelyille/tapahtumille
 */
public class RecipeDao implements Dao<Recipe> {

    private Database db;
    private String tableName;

    /**
     * RecipeDao objekti jolla voidaan totetuttaa tarvittavia kyselyitä Recipe-tauluun
     * @param db tietokanta
     * @param tableName tietokantataulun nimi
     */
    public RecipeDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    public Database getDb() {
        return db;
    }

    public String getTableName() {
        return tableName;
    }

    /**
     * Hakee tietokantataulusta Recipe kaikki rivit.
     * Rivit on järjestetty aakkosittain nimen mukaan.
     * @return Recipe taulun kaikki rivit
     */
    @Override
    public List<Recipe> findAll() {
        return selectQuery("SELECT * FROM " + tableName + ";");
    }

    /**
     * Tallentaa uuden rivin Recipe tietokantatauluun
     * @param recipe tallennettava ruoka
     */
    @Override
    public void save(Recipe recipe)  {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (name, rawMaterials, " +
                    "cookTime, description, instruction) VALUES (?,?,?,?,?)");
            stmt.setString(1, recipe.getName());
            stmt.setString(2,recipe.listToString());
            stmt.setInt(3, recipe.getCookTime());
            stmt.setString(4, recipe.getDescription());
            stmt.setString(5, recipe.getInstruction());

            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Recipe recipe) {

    }

    /**
     * Poistaa tietyn rivin tietokannasta
     * @param recipe poistettavan rivin ID
     */
    @Override
    public void delete(Recipe recipe) {

    }

    /**
     * Apufunktio totetuttamaan haluttu kysely tietokantaan
     * @param query tietokantaan tehtävä kysely
     * @return lista Recipe olioita
     */
    @Override
    public List<Recipe> selectQuery(String query) {
        List<Recipe> recipes = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe(rs.getString("name"), filterFoods(rs.getString("rawMaterials")),
                        rs.getInt("cookTime"), rs.getString("description"), rs.getString("instruction"));
                recipes.add(recipe);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recipes;
    }

    /**
     * Käsittelee hajoittaa foods-merkkijonon tietokantarivissä osiin.
     * Muodastaa merkkijonosta Food objekteja. Food objektit on eroteltu "tab" symbolilla.
     * Food objektin omat ominaisuudet on eroteltu ";" symbolilla
     * @param items Food objektit yhtenä merkkijonona
     * @return lista Food objekteja
     */
    public List<Food> filterFoods(String items) {
        List<Food> foods = new ArrayList<>();
        String[] parts = items.split("\t");
        for (String part : parts) {
            String[] item = part.split(";");
            int weight = Integer.parseInt(item[3]);
            int amount = Integer.parseInt(item[4]);
            foods.add(new Food(item[0], item[1], item[2], weight, amount));
        }
        return foods;
    }
}
