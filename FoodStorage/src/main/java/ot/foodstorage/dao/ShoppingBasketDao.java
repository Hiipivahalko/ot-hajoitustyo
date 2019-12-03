package ot.foodstorage.dao;

import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.ShoppingBasket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<ShoppingBasket> findAll() {
        List<ShoppingBasket> baskets = selectQuery("SELECT * FROM " + tableName + ";");
        return baskets;
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

    private List<ShoppingBasket> selectQuery(String query) {
        List<ShoppingBasket> baskets = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShoppingBasket basket = new ShoppingBasket(rs.getInt("id"),
                        handleItems(rs.getString("items")));
                baskets.add(basket);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return baskets;
    }

    private List<Food> handleItems(String s) {
        //StringBuilder sb = new StringBuilder();
        List<Food> shoppingItems = new ArrayList<>();
        String[] items = s.split(",");
        for (String next : items) {
            String[] item = next.split(";");
            int weight = Integer.parseInt(item[3]);
            int id = Integer.parseInt(item[4]);
            int amount = Integer.parseInt(item[5]);
            shoppingItems.add(new Food(item[0], item[1], item[2], weight, id, amount));
        }
        return shoppingItems;
    }
}
