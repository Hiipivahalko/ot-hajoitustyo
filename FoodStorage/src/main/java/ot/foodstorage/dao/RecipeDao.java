package ot.foodstorage.dao;

import ot.foodstorage.domain.Recipe;

import java.sql.SQLException;
import java.util.List;

public class RecipeDao implements Dao<Recipe, Integer> {



    @Override
    public Recipe findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Recipe> findAll() throws SQLException {
        return null;
    }

    @Override
    public void saveOrUpdate(Recipe object) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Recipe> filterFromAll(String filter) throws SQLException {
        return null;
    }
}
