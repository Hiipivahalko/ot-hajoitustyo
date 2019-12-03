package ot.foodstorage.service;

import org.junit.Before;
import org.junit.Test;
import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.dao.LayoutDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.dao.ShoppingBasketDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppServiceTest {

    private AppService appService;
    private Database db = new Database("jdbc:sqlite:test.db");
    FoodDao foodDao = new FoodDao(db, "food");
    LayoutDao layoutDao = new LayoutDao(db, "layout");
    RecipeDao recipeDao = new RecipeDao(db, "recipe");
    ShoppingBasketDao shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");

    private Food food1 = new Food("food1", "manu1", "jääkaappi", 1, -1, 1);
    private Food food2 = new Food("food2", "manu2", "jääkaappi", 300, -1, 4);
    private Food food3 = new Food("food3", "manu3", "kuivakaappi", 4, -1, 1);
    private Food food4 = new Food("food4", "manu4", "pakastin", 50, -1, 1);

    /**
     * funktio raaka-aineiden tallentamiseksi testeissä
     * @param food - tallennettava ruoka
     */
    private void saveFoodInTest(Food food) {
        appService.saveNewFood(food.getName(), food.getManufacturer(), food.getPreservation(), food.getWeight(), food.getAmount());
    }

    /**
     * Tallentaa varastoon testien paikalliset raaka-aine oliot
     */
    private void saveFoodPackageToService() {
        saveFoodInTest(food1);
        saveFoodInTest(food2);
        saveFoodInTest(food3);
        saveFoodInTest(food4);
    }

    @Before
    public void setUp() throws Exception {
        this.appService = new AppService(foodDao, layoutDao, recipeDao, shoppingBasketDao);
        db.initializeDatabase();
        assertEquals(0, foodDao.findAll().size());
        assertEquals(0, layoutDao.findAll().size());
    }

    /**
     * Testataan appservicen ruoan tallennusta
     * @throws SQLException
     */
    @Test
    public void saveNewFood() throws SQLException {
        appService.saveNewFood("testiRuoka", "valmistajaTesti",
                "jääkaappi", 1, 1);
        assertEquals(1, foodDao.findAll().size());
    }

    /**
     * Testataan ruoan tallennusta monella ruoalla
     * @throws SQLException
     */
    @Test
    public void saveNewFood2() throws SQLException {
        appService.saveNewFood("testiRuoka", "valmistajaTesti",
                "jääkaappi", 1, 1);
        appService.saveNewFood("testiRuoka2", "valmistajaTesti2",
                "jääkaappi", 3, 1);
        appService.saveNewFood("testiRuoka3", "valmistajaTesti",
                "jääkaappi", 1, 1);
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
        List<Food> allFoods = new ArrayList<>();
        saveFoodPackageToService();
        allFoods = appService.filterFoods("kuivakaappi", 1);

        assertTrue(allFoods.size() == 1);
        boolean presevation = true;
        for (Food f : allFoods) {
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
        List<Food> filterFoods = new ArrayList<>();
        saveFoodPackageToService();
        filterFoods = appService.filterFoods("food1", 0);

        assertTrue(filterFoods.size() == 1);
        for (Food f : filterFoods) {
            assertTrue(f.getName().contains("food1") || f.getManufacturer().contains("food1"));
        }
    }


}