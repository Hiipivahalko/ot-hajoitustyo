package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Recipe;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReadyRecipesDao extends RecipeDao {

    public ReadyRecipesDao(Database db, String tableName) {
        super(db, tableName);
    }

    @Override
    public List<Recipe> findAll() {
        return selectQuery("SELECT * FROM " + getTableName() + ";");
    }

    public void save(Recipe recipe) {
        try {
            Connection conn = getDb().getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + getTableName() + " (name, rawMaterials, " +
                    "cookTime, description, instruction, amount) VALUES (?,?,?,?,?,?)");
            stmt.setString(1, recipe.getName());
            stmt.setString(2,recipe.listToString());
            stmt.setInt(3, recipe.getCookTime());
            stmt.setString(4, recipe.getDescription());
            stmt.setString(5, recipe.getInstruction());
            stmt.setInt(6, recipe.getAmount());

            stmt.execute();
            stmt.close();
            conn.close();;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Recipe recipe) {
        try {
            Connection conn = getDb().getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + getTableName() + " SET amount = " +
                    recipe.getAmount() +
                    " WHERE name = '" + recipe.getName() + "';");
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Recipe recipe) {
        try {
            Connection conn = getDb().getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + getTableName() + " WHERE name = '" +
                    recipe.getName() + "' AND cookTime = " + recipe.getCookTime() + " AND amount = " + 1 + ";");
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Recipe> selectQuery(String query) {
        List<Recipe> readyRecipes = new ArrayList<>();
        try {
            Connection conn = super.getDb().getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Recipe recipe = new Recipe(rs.getString("name"), super.filterFoods(rs.getString("rawMaterials")),
                        rs.getInt("cookTime"), rs.getString("description"), rs.getString("instruction"),
                        rs.getInt("amount"));
                readyRecipes.add(recipe);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return readyRecipes;
    }


}
