package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Layout;

import java.sql.SQLException;
import java.util.List;

public class LayoutDao implements Dao<Layout, Integer> {

    private Database db;
    private String tableName;

    public LayoutDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    @Override
    public Layout findOne(Integer key) throws SQLException {
        return null;
    }

    @Override
    public List<Layout> findAll() throws SQLException {
        return null;
    }

    @Override
    public void saveOrUpdate(Layout object) throws SQLException {

    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Layout> filterFromAll(String filter) throws SQLException {
        return null;
    }
}
