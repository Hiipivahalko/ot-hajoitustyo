package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.ShoppingBasket;

import java.sql.SQLException;
import java.util.List;

public class ShoppingBasketDao implements Dao<ShoppingBasket, Integer> {

    private Database db;
    private String tableName;

    public ShoppingBasketDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    @Override
    public ShoppingBasket findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<ShoppingBasket> findAll() throws SQLException {
        return null;
    }

    @Override
    public void saveOrUpdate(ShoppingBasket object) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<ShoppingBasket> filterFromAll(String filter) throws SQLException {
        return null;
    }
}
