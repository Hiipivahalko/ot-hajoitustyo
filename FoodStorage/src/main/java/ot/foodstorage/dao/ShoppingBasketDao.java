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

/**
 * Luokka ShoppingBasket-luokkien tietokanta käsittelyille/tapahtumille.
 */
public class ShoppingBasketDao implements Dao<ShoppingBasket> {

    private Database db;
    private String tableName;

    /**
     * ShoppingBasketDao objekti jolla voidaan totetuttaa tarvittavia kyselyitä Recipe-tauluun.
     * @param db tietokanta
     * @param tableName tietokantataulun nimi
     */
    public ShoppingBasketDao(Database db, String tableName) {
        this.db = db;
        this.tableName = tableName;
    }

    /**
     * Etsii tietyn rivin tietokannasta.
     * @param shoppingBasket etsittävä shoppingBasket
     * @return ShoppingBasket, jos ei löydy, niin null
     */
    public ShoppingBasket findOne(ShoppingBasket shoppingBasket) {
        List<ShoppingBasket> baskets = selectQuery("SELECT * FROM " + tableName + " WHERE name = 'basket';");
        if (baskets.size() > 0) {
            return baskets.get(0);
        }
        return null;
    }

    /**
     * Etsii kaikki rivit tietokannasta.
     * @return
     */
    @Override
    public List<ShoppingBasket> findAll() {
        List<ShoppingBasket> baskets = selectQuery("SELECT * FROM " + tableName + ";");
        return baskets;
    }

    /**
     * Päivityyää tai tallentaa uuden rivin tietokantaa.
     * @param basket tallennettava shoppingBasket
     */
    public void saveOrUpdate(ShoppingBasket basket) {
        ShoppingBasket sb = findOne(null);
        if (sb == null) {
            save(basket);
        } else {
            update(basket);
        }
    }

    /**
     * Tallentaa uuden rivin tietokantaan.
     * @param basket tallennettava rivi
     */
    @Override
    public void save(ShoppingBasket basket) {
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

    /**
     * Päivittää tietokannan tiettyjä rivejä.
     * @param basket
     */
    @Override
    public void update(ShoppingBasket basket) {
        try {
            Connection conn = db.getConnection();
            PreparedStatement stmt = conn.prepareStatement("UPDATE " + tableName + " SET items = ? " +
                    "WHERE name = 'basket'");
            stmt.setString(1, basket.listToString());
            stmt.execute();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Poistaa rivin tietokannasta.
     * @param shoppingBasket
     */
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

    /**
     * Toteuttaa annetun kyselyn ja palauttaa mahdolliset rivit tietokannasta.
     * @param query SQL-kysely
     * @return rivit tietokannasta
     */
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

    /**
     * Rakentaa merkkijonosta listan Food olioita.
     * @param s merkkijono Food olioina
     * @return lista Food olioita
     */
    public List<Food> handleItems(String s) {
        List<Food> shoppingItems = new ArrayList<>();
        String[] items = s.split(",");
        for (String next : items) {
            if (next.length() == 0) {
                continue;
            }
            String[] item = next.split(";");
            int weight = Integer.parseInt(item[3]);
            int amount = Integer.parseInt(item[4]);
            shoppingItems.add(new Food(item[0], item[1], item[2], weight, amount));
        }
        return shoppingItems;
    }
}
