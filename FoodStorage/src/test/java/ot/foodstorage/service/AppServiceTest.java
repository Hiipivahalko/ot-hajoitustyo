package ot.foodstorage.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.dao.*;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppServiceTest extends Application {

    private AppService appService;
    private Database db = new Database("jdbc:sqlite:test.db");
    private FoodDao foodDao = new FoodDao(db, "food");
    private LayoutDao layoutDao = new LayoutDao(db, "layout");
    private RecipeDao recipeDao = new RecipeDao(db, "recipe");
    private ShoppingBasketDao shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");
    private ReadyReacipesDao readyReacipesDao = new ReadyReacipesDao(db, "readyRecipes");

    private Food food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
    private Food food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
    private Food food3 = new Food("food3", "manu3", "kuivakaappi", 4, 1);
    private Food food4 = new Food("food4", "manu4", "pakastin", 50,  1);

    @BeforeClass
    public static void setClass() {
        try {
            launch();
        } catch (Exception e) {

        }
    }

    /**
     * Tallentaa varastoon testien paikalliset raaka-aine oliot
     */
    private void saveFoodPackageToService() {
        appService.saveNewFood(food1);
        appService.saveNewFood(food2);
        appService.saveNewFood(food3);
        appService.saveNewFood(food4);
    }

    @Before
    public void setUp() {
        db.initializeDatabase();
        this.appService = new AppService(foodDao, layoutDao, recipeDao, shoppingBasketDao, readyReacipesDao);
        assertEquals(0, foodDao.findAll().size());
        assertEquals(0, layoutDao.findAll().size());
        assertEquals(0, recipeDao.findAll().size());
        assertEquals(0, shoppingBasketDao.findAll().size());
        assertEquals(0, readyReacipesDao.findAll().size());
    }

    /**
     * Testataan appservicen ruoan tallennusta
     * @throws SQLException
     */
    @Test
    public void saveNewFood() {
        Food test = new Food("testiRuoka", "valmistajaTesti", "jääkaappi", 1, 1);
        appService.saveNewFood(test);
        assertEquals(1, foodDao.findAll().size());
    }

    /**
     * Testataan ruoan tallennusta monella ruoalla
     * @throws SQLException
     */
    @Test
    public void saveNewFood2() {
        Food test1 = new Food("testiruoka", "valmistajaTesti", "jääkaappi", 1, 1);
        Food test2 = new Food("testiruoka2", "valmistajaTesti2", "jääkaappi", 1, 1);
        Food test3 = new Food("testiruoka3", "valmistajaTesti", "jääkaappi", 1, 1);
        appService.saveNewFood(test1);
        appService.saveNewFood(test2);
        appService.saveNewFood(test3);
        List<Food> foods = foodDao.findAll();
        assertEquals(3, foods.size());
        assertEquals("testiruoka", foods.get(0).getName());
    }

    /**
     * Testataan filtterin toimintaa, kun halutaan jääkaapin asiat
     */
    @Test
    public void filterFoodsPreservationFridge() {
        List<Food> allFoods = new ArrayList<>();
        saveFoodPackageToService();
        allFoods = appService.filterFoods("jääkaappi", 1);

        assertTrue(allFoods.size() == 2);
        boolean presevation = true;
        for (Food f : allFoods) {
            if (!f.getPreservation().equals("jääkaappi")) presevation = false;
        }

        assertEquals(true, presevation);
    }

    /**
     * Testataan filtterin toimintaa, kun halutaan pakastimen asiat
     */
    @Test
    public void filterFoodsPreservationFreezer() {
        List<Food> allFoods = new ArrayList<>();
        saveFoodPackageToService();
        allFoods = appService.filterFoods("pakastin", 1);

        assertTrue(allFoods.size() == 1);
        boolean presevation = true;
        for (Food f : allFoods) {
            if (!f.getPreservation().equals("pakastin")) presevation = false;
        }

        assertEquals(true, presevation);
    }

    /**
     * Testataan filtterin toimintaa, kun halutaan kuivakaapin asiat
     */
    @Test
    public void filterFoodsPreservationOthers() {
        List<Food> allTestFoods = new ArrayList<>();
        saveFoodPackageToService();
        allTestFoods = appService.filterFoods("kuivakaappi", 1);
        assertTrue(allTestFoods.size() == 1);
        boolean presevation = true;
        for (Food f : allTestFoods) {
            if (!f.getPreservation().equals("kuivakaappi")) presevation = false;
        }

        assertEquals(true, presevation);
    }

    /**
     * Testataan filtterin toimintaa kun haetaan nimen tai valmistaja perusteella.
     * Testeissä haetaan 'food' merkkijonolla raaka-aineista
     */
    @Test
    public void filterFoodsWithString() {
        List<Food> filterFoods = new ArrayList<>();
        saveFoodPackageToService();
        filterFoods = appService.filterFoods("food", 0);

        assertTrue(filterFoods.size() == 4);
        for (Food f : filterFoods) {
            assertTrue(f.getName().contains("food") || f.getManufacturer().contains("food"));
        }
    }

    /**
     * Testataan filtterin toimintaa kun haetaan nimen tai valmistaja perusteella.
     * Testeissä haetaan 'manu' merkkijonolla
     */
    @Test
    public void filterFoodsWithString2() {
        List<Food> filterFoods = new ArrayList<>();
        saveFoodPackageToService();
        filterFoods = appService.filterFoods("manu", 0);

        assertTrue(filterFoods.size() == 4);
        for (Food f : filterFoods) {
            assertTrue(f.getName().contains("manu") || f.getManufacturer().contains("manu"));
        }
    }

    /**
     * Testataan filtterin toimintaa kun haetaan nimen tai valmistaja perusteella.
     * Testeissä haetaan 'food1' merkkijonolla
     */
    @Test
    public void filterFoodsWithString1() {
        saveFoodPackageToService();
        List<Food> filterFoods = appService.filterFoods("food1", 0);

        assertTrue(filterFoods.size() == 1);
        for (Food f : filterFoods) {
            assertTrue(f.getName().contains("food1") || f.getManufacturer().contains("food1"));
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }
}