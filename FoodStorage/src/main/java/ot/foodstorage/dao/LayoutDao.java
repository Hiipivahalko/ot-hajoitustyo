package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;

import java.sql.*;
import java.util.ArrayList;
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
        List<Layout> layouts = new ArrayList<>();

        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " ORDER BY name;");

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Layout layout = new Layout(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("manufacturer"),
                    rs.getString("preservation"));
            layouts.add(layout);
        }

        rs.close();
        stmt.close();
        conn.close();

        return layouts;
    }

    @Override
    public void saveOrUpdate(Layout object) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName +
                    " (name, manufacturer, preservation) VALUES (?,?,?)");
            stmt.setString(1, object.getName());
            stmt.setString(2, object.getManufacturer());
            stmt.setString(3, object.getPreservation());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Integer key) throws SQLException {

    }

    @Override
    public List<Layout> filterFromAll(String filter) throws SQLException {
        return null;
    }
}
