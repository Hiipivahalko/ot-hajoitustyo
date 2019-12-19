package ot.foodstorage.dao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.ShoppingBasket;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ShoppingBasketDaoTest extends Application {

    private Database db;
    private ShoppingBasketDao basketDao;
    private ShoppingBasket basket = new ShoppingBasket(-1, new ArrayList<>());

    private Food food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
    private Food food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
    private Food food3 = new Food("leipä", "arla", "kuivakaappi", 1, 3);
    private Food food4 = new Food("juusto", "valio", "jääkaappi", 1, 1);
    private Food food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);

    @BeforeClass
    public static void setUpClass() {
        try {
            launch();
        } catch (Exception e) {

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }

    @Before
    public void setUp() throws Exception {
        this.db = new Database("jdbc:sqlite:test.db");
        db.initializeDatabase();
        this.basketDao = new ShoppingBasketDao(db, "shoppingbasket");
        assertEquals(0, basketDao.findAll().size());
        basket.addItem(food1);
        basket.addItem(food2);
        basket.addItem(food3);
        basket.addItem(food4);
        basket.addItem(food5);
    }

    /**
     * Testataan ostoslistan tallennusta tietokantaa ja että sinne tallentui oikea objekti
     */
    @Test
    public void save() {
        basketDao.save(basket);
        List<ShoppingBasket> baskets = basketDao.findAll();
        ShoppingBasket test = baskets.get(0);
        assertEquals(1, baskets.size());
        assertEquals("basket", test.getName());
        assertEquals(5, test.getItems().size());

        for (Food f : test.getItems()) {
            String name = f.getName();
            assertEquals(true, (name.equals("milk") || name.equals("milk2") || name.equals("leipä") ||
                    name.equals("juusto") || name.equals("jäätelö")));
        }
    }

    @Test
    public void update() {
        basketDao.save(basket);
        int beforeSize = basketDao.findAll().get(0).getItems().size();
        assertEquals(1, basketDao.findAll().size());

        basket.addItem(new Food("uusi", "valmistaja", "kuivakaappi", 50, 1));
        basketDao.update(basket);
        assertEquals(beforeSize + 1, basketDao.findAll().get(0).getItems().size());
    }

    @Test
    public void delete() {
        basketDao.save(basket);
        assertEquals(1, basketDao.findAll().size());
        basketDao.delete(basket);
        assertEquals(0, basketDao.findAll().size());
    }

    /**
     * testataan että raaka-aineiden muodostus merkkijonosta onnistuu
     */
    @Test
    public void handleItems() {
        String itemsString = "food1;manu1;pakastin;1;1,food2;manu2;kuivakaappi;400;60,food3;manu3;jääkaappi;50;4,";
        Food tes1 = new Food("food1", "manu1", "pakastin", 1, 1);
        Food tes2 = new Food("food2", "manu2", "kuivakaappi", 400, 60);
        Food tes3 = new Food("food3", "manu3", "jääkaappi", 50, 4);

        List<Food> items = basketDao.handleItems(itemsString);
        assertEquals(3, items.size());
        for (Food f : items) {
            assertEquals(true, (f.equals(tes1) || f.equals(tes2) || f.equals(tes3)));
        }
    }

    /**
     * Testataan että jos merkkijono on tyhjä, niin siitä ei muodostu yhtään raaka-ainetta
     */
    @Test
    public void handleItems2() {
        String itemsString = ",";
        List<Food> itesm = basketDao.handleItems(itemsString);
        assertEquals(0, itesm.size());
    }


}