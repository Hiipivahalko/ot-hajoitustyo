package ot.foodstorage.dao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class FoodDaoTest extends Application{

    private Database database;
    private FoodDao foodDao;
    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;
    private Food food5;

    @BeforeClass
    public static void setClass() {
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
        food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
        food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
        food3 = new Food("milk3", "arla", "kuivakaappi", 1, 3);
        food4 = new Food("milk4", "valio", "jääkaappi", 1, 1);
        food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);
        database = new Database("jdbc:sqlite:test.db");
        foodDao = new FoodDao(database, "Food");
        database.initializeDatabase();
        assertEquals(0, foodDao.findAll().size());
    }

    /**
     * apufuntio testeille, jossa tallennetaan pieni määrä raaka-aine olioita kantaan
     */
    private void saveFoods() {
        foodDao.save(food1);
        foodDao.save(food2);
        foodDao.save(food3);
        foodDao.save(food4);
        foodDao.save(food5);
    }

    /**
     * Testataan että tietokanta palauttaa kaikki ruokarivit sitä pyydettäessä
     * @throws SQLException
     */
    @Test
    public void findAll() throws SQLException {
        Food food2 = new Food("juusto", "valio", "jääkaappi", 1, 1);
        Food food3 = new Food("leipä", "fazer", "kuivakaappi", 2, 3);

        foodDao.save(food1);
        foodDao.save(food2);
        foodDao.save(food3);

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
    public void save()  {
        foodDao.save(food1);
        List<Food> all = foodDao.findAll();
        assertEquals(1, all.size());
        assertEquals("milk", all.get(0).getName());
        assertNotEquals("myllynparas", all.get(0).getManufacturer());
    }

    /**
     * Testaan että delete-funktio poistaa tietokannasta oiekan rivin
     */
    @Test
    public void delete() {
        saveFoods();
        foodDao.delete(food1);
        List<Food> items = foodDao.findAll();
        assertEquals(4, items.size());

        for (Food f : items) {
            assertEquals(false, f.equals(food1));
        }
    }

    @Test
    public void delete2() {
        saveFoods();
        assertEquals(5, foodDao.findAll().size());

        Food del = new Food("poisto", "manu", "jääkaappi", 1, 1);
        foodDao.delete(del);
        assertEquals(5, foodDao.findAll().size());
    }

    /**
     * testataan raaka-aineiden filtteröintiä,
     * kun haetaan raaka-aineita merkkijonolla 'milk'
     */
    @Test
    public void filterFromAll() {
        saveFoods();
        List<Food> foods = foodDao.filterFromAll("milk");

        assertTrue(foods.size() == 4);
        for (Food f : foods) {
            assertTrue(f.getName().contains("milk"));
        }
    }

    /**
     * testataan raaka-aineiden filtteröintiä,
     * kun haetaan raaka-aineita merkkijonolla 'arla'
     */
    @Test
    public void filterFromAll2() {
        saveFoods();
        List<Food> foods = foodDao.filterFromAll("arla");

        assertTrue(foods.size() == 2);
        for (Food f : foods) {
            assertTrue(f.getManufacturer().contains("arla"));
        }
    }

    /**
     * Tarkastetaan että saadaan raaka-aineet, joiden säilytys on jääkaapissa
     */
    @Test
    public void preservationFilter() {
        saveFoods();
        List<Food> foods = foodDao.preservationFilter("jääkaappi");

        assertTrue(foods.size() == 3);
        for (Food f :foods) assertTrue(f.getPreservation().equals("jääkaappi"));
    }

    /**
     * Tarkastetaan että saadaan raaka-aineet, joiden säilytys on kuivakaapissa
     */
    @Test
    public void preservationFilter2() {
        saveFoods();
        List<Food> foods = foodDao.preservationFilter("kuivakaappi");

        assertTrue(foods.size() == 1);
        for (Food f :foods) assertTrue(f.getPreservation().equals("kuivakaappi"));
    }

    /**
     * Tarkastetaan että saadaan raaka-aineet, joiden säilytys on pakastimessa
     */
    @Test
    public void preservationFilter3() {
        saveFoods();
        List<Food> foods = foodDao.preservationFilter("pakastin");

        assertTrue(foods.size() == 1);
        for (Food f :foods) assertTrue(f.getPreservation().equals("pakastin"));
    }

    /**
     * Tarkastetaan että findByNameAndManufacture funktio palauttaa, vain yhden tietin olion
     */
    @Test
    public void findByNameAndManufacture() {
        saveFoods();
        List<Food> foods = foodDao.findByNameAndManufacture("milk", "valio");
        assertTrue(foods.size() == 1);
        assertEquals("milk", foods.get(0).getName());
        assertEquals("valio", foods.get(0).getManufacturer());
    }

    /**
     * Tarkastetaan että findByNameAndManufacture funktio ei löydä yhtään oliota
     */
    @Test
    public void findByNameAndManufacture2() {
        saveFoods();
        List<Food> foods = foodDao.findByNameAndManufacture("karkki", "haribo");
        assertTrue(foods.size() == 0);
    }

    /**
     * raaka-aineen päivtys tietokantaan onnistuu, kun tietokannassa yksi rivi
     */
    @Test
    public void update() {
        foodDao.save(food1);
        assertEquals(1, foodDao.findAll().size());
        Food savedOne = foodDao.findAll().get(0);
        food1.setAmount(food1.getAmount() + 4);

        foodDao.update(food1);
        List<Food> afterUpdateList = foodDao.findAll();

        assertEquals(1, afterUpdateList.size());
        assertEquals(true, food1.equals(afterUpdateList.get(0)));
        assertEquals(savedOne.getAmount() + 4, afterUpdateList.get(0).getAmount());
    }

    /**
     * raaka-aineen päivitys toimii kun tietokannassa monta riviä
     */
    @Test
    public void update2() {
        saveFoods();
        assertEquals(5, foodDao.findAll().size());
        Food savedOne = foodDao.findAll().get(1);
        food1.setAmount(food1.getAmount() + 7);

        foodDao.update(food1);
        Food afterUpdate = foodDao.findAll().get(1);

        assertEquals(5, foodDao.findAll().size());
        assertEquals(true, food1.equals(afterUpdate));
        assertEquals(savedOne.getAmount() + 7, afterUpdate.getAmount());
    }

}