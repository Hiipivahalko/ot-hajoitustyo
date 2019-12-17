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

public class ShoppingBasketDao implements Dao<ShoppingBasket> {

    private Database db;
    private String tableName;

    public ShoppingBasketDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    @Override
    public ShoppingBasket findOne(ShoppingBasket shoppingBasket) {
        List<ShoppingBasket> baskets = selectQuery("SELECT * FROM " + tableName + " WHERE name = 'basket';");
        if (baskets.size() > 0) {
            return baskets.get(0);
        }
        return null;
    }

    @Override
    public List<ShoppingBasket> findAll() {
        List<ShoppingBasket> baskets = selectQuery("SELECT * FROM " + tableName + ";");
        return baskets;
    }

    @Override
    public void saveOrUpdate(ShoppingBasket basket) {
        ShoppingBasket sb = findOne(null);
        if (sb == null) {
            save(basket);
        } else {
            update(basket);
        }
    }

    private void save(ShoppingBasket basket) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO " + tableName + " (name, items)" +
                    " VALUES (?, ?);");
            stmt.setString(1, basket.getName());
            stmt.setString(2, basket.listToString());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void update(ShoppingBasket basket) {
        System.out.println("here");
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + tableName + " SET items = ? " +
                    "WHERE name = 'basket'");
            stmt.setString(1,basket.listToString());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ShoppingBasket shoppingBasket) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM " + tableName + " WHERE name = 'basket';");
            stmt.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<ShoppingBasket> filterFromAll(String filter) throws SQLException {
        return null;
    }

    @Override
    public List<ShoppingBasket> selectQuery(String query) {
        List<ShoppingBasket> baskets = new ArrayList<>();
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ShoppingBasket basket = new ShoppingBasket(rs.getInt("id"),
                        handleItems(rs.getString("items")));
                basket.setListToString(rs.getString("items"));
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
        List<Food> shoppingItems = new ArrayList<>();
        String[] items = s.split(",");
        for (String next : items) {
            if (next.length() == 0) {
                continue;
            }
            String[] item = next.split(";");
            int weight = Integer.parseInt(item[3]);
            int amount = Integer.parseInt(item[4]);
            shoppingItems.add(new Food(item[0], item[1], item[2], weight, -1, amount));
        }
        return shoppingItems;
    }
}
