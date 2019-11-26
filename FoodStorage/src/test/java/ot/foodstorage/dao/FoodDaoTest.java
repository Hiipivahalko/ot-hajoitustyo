package ot.foodstorage.dao;

import org.junit.Before;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class FoodDaoTest {

    private Database database;
    private FoodDao foodDao;
    private Food food = new Food("milk", "valio", "jääkaappi", 1, "20.08.2040", 1, 1);

    @Before
    public void setUp() throws Exception {
        database = new Database("jdbc:sqlite:test.db");
        foodDao = new FoodDao(database, "Food");
        database.initializeDatabase();
        assertEquals(0, foodDao.findAll().size());
    }

    @Test
    public void findOne() {
    }

    /**
     * Testataan että tietokanta palauttaa kaikki ruokarivit sitä pyydettäessä
     * @throws SQLException
     */
    @Test
    public void findAll() throws SQLException {

        Food food2 = new Food("juusto", "valio", "jääkaappi", 1, "20.09.2040", 2, 1);
        Food food3 = new Food("leipä", "fazer", "kuivakaappi", 2, "20.10.2040", 1, 3);

        foodDao.saveOrUpdate(food);
        foodDao.saveOrUpdate(food2);
        foodDao.saveOrUpdate(food3);

        List<Food> all = foodDao.findAll();

        assertEquals(3, all.size());

        for(Food food : all) {
            assertTrue(food.getName().equals("juusto") || food.getName().equals("milk") || food.getName().equals("leipä"));
        }
    }

    /**
     * Testaan että tietokantaan Food objektin tallennus onnistuu
     */
    @Test
    public void saveOrUpdate() throws SQLException {
        foodDao.saveOrUpdate(food);
        List<Food> all = foodDao.findAll();
        assertEquals(1, all.size());
        assertEquals("milk", all.get(0).getName());
        assertNotEquals("myllynparas", all.get(0).getManufacturer());
    }

    @Test
    public void delete() {
    }

    @Test
    public void filterFromAll() {
    }

    @Test
    public void preservationFilter() {
    }
}