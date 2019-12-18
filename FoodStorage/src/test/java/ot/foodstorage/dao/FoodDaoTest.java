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
    private Food food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
    private Food food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
    private Food food3 = new Food("milk3", "arla", "kuivakaappi", 1, 3);
    private Food food4 = new Food("milk4", "valio", "jääkaappi", 1, 1);
    private Food food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);

    @BeforeClass
    public static void setClass() {
        try {
            launch();
        } catch (Exception e) {

        }
    }

    @Before
    public void setUp() throws Exception {

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

    /*@Test
    public void findOne() {
    }*/

    /**
     * Testataan että tietokanta palauttaa kaikki ruokarivit sitä pyydettäessä
     * @throws SQLException
     */
    @Test
    public void findAll() throws SQLException {
        Food food2 = new Food("juusto", "valio", "jääkaappi", 1, 2, 1);
        Food food3 = new Food("leipä", "fazer", "kuivakaappi", 2, 1, 3);

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

    @Test
    public void delete() {
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


    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }
}