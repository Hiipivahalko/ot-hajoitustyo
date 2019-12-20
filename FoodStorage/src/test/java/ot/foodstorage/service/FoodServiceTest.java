package ot.foodstorage.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.dao.*;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class FoodServiceTest extends Application {

    private FoodService service;
    private Database db = new Database("jdbc:sqlite:test.db");
    private FoodDao foodDao = new FoodDao(db, "food");
    private LayoutDao layoutDao = new LayoutDao(db, "layout");

    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;

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
    public void setUp() {
        db.initializeDatabase();
        this.service = new FoodService(foodDao, layoutDao);
        assertEquals(0, foodDao.findAll().size());
        assertEquals(0, layoutDao.findAll().size());

        food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
        food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
        food3 = new Food("food3", "manu3", "kuivakaappi", 4, 1);
        food4 = new Food("food4", "manu4", "pakastin", 50,  1);
        saveFoodPackageToService();
        assertEquals(4, foodDao.findAll().size());
        assertEquals(4, service.getAllFoods().size());
    }

    /**
     * Tallentaa varastoon testien paikalliset raaka-aine oliot
     */
    private void saveFoodPackageToService() {
        service.saveNewFood(food1);
        service.saveNewFood(food2);
        service.saveNewFood(food3);
        service.saveNewFood(food4);
    }

    /**
     * Testataan appservicen ruoan tallennusta
     */
    @Test
    public void saveNewFood() {
        Food test = new Food("testiRuoka", "valmistajaTesti", "jääkaappi", 1, 1);
        service.saveNewFood(test);
        assertEquals(5, foodDao.findAll().size());
        assertEquals(5, service.getAllFoods().size());
    }

    /**
     * Testataan ruoan tallennusta monella ruoalla
     */
    @Test
    public void saveNewFood2() {
        Food test1 = new Food("testiruoka", "valmistajaTesti", "jääkaappi", 1, 1);
        int test1Amount = test1.getAmount();
        Food test2 = new Food("testiruoka2", "valmistajaTesti2", "jääkaappi", 1, 1);
        Food test3 = new Food("testiruoka3", "valmistajaTesti", "jääkaappi", 1, 1);
        service.saveNewFood(test1);
        service.saveNewFood(test2);
        service.saveNewFood(test3);
        List<Food> foods = foodDao.findAll();
        assertEquals(7, foods.size());
        assertEquals(7, service.getAllFoods().size());
        assertEquals("testiruoka", foods.get(4).getName());
        int mapValue = service.getFoodsMap().get(test1);
        assertEquals(test1Amount, mapValue);
    }

    /**
     * Yritetään tallentaa raaka-ainetta jolla on epävalidi nimi.
     * Raaka-aineen ei pitäisi tallentua.
     */
    @Test
    public void saveNewFood3() {
        Food test = new Food(" ", "valmistaja", "jääkaappi", 1,1);
        boolean failed = false;
        try {
            service.saveNewFood(test);
        } catch (Exception e) {
            failed = true;
        }
        assertEquals(4, foodDao.findAll().size());
        assertEquals(4, service.getAllFoods().size());
        assertEquals(true, failed);
    }

    /**
     * Yritetään tallentaa raaka-ainetta jonka amount arvo on negatiivinen.
     * Tämän raaka-aineen ei pitäisi tallnetua
     */
    @Test
    public void saveNewFood4() {
        Food test = new Food("nimi", "valmistaja", "jääkaappi", 1,-5);
        boolean failed = false;
        try {
            service.saveNewFood(test);
        } catch (Exception e) {
            failed = true;
        }
        assertEquals(4, foodDao.findAll().size());
        assertEquals(4, service.getAllFoods().size());
        assertEquals(true, failed);
    }

    /**
     * Yritetään tallentaa raaka-ainetta joka löytyy varastosta,
     * tällöin tarkastamme että raaka-aineen päivitys toimii.
     * raaka-aine listaan ei tule uutta objektia
     */
    @Test
    public void saveAndUpdate() {
        Food up = new Food(food1.getName(), food1.getManufacturer(), food1.getPreservation(), food1.getWeight(), food1.getAmount());
        int amountBefore = up.getAmount();
        up.setAmount(5);
        int addedAmount = up.getAmount();

        service.saveNewFood(up);
        assertEquals(4, service.getAllFoods().size());
        boolean found = false;
        for (Food f : service.getAllFoods()) {
            if (f.equals(food1)) {
                found = true;
                assertEquals(addedAmount + amountBefore, f.getAmount());
            }
        }
        assertEquals(true, found);
        int keyValue = service.getFoodsMap().get(up);
        assertEquals(addedAmount + amountBefore, keyValue);
    }

    /**
     * Testataan filtterin toimintaa, kun halutaan jääkaapin asiat
     */
    @Test
    public void filterFoodsPreservationFridge() {
        List<Food> allFoods = service.filterFoods("jääkaappi", 1);

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
        List<Food> allFoods = service.filterFoods("pakastin", 1);

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
        List<Food> allTestFoods = service.filterFoods("kuivakaappi", 1);
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
        List<Food> filterFoods = service.filterFoods("food", 0);

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
        List<Food> filterFoods = service.filterFoods("manu", 0);

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
        List<Food> filterFoods = service.filterFoods("food1", 0);

        assertTrue(filterFoods.size() == 1);
        for (Food f : filterFoods) {
            assertTrue(f.getName().contains("food1") || f.getManufacturer().contains("food1"));
        }
    }

    /**
     * Jos initializeMap funktiolle antaa tyhjän listan, niin hajautustalukin on tyhjä
     */
    @Test
    public void initializeMap() {
        List<Food> emptyList = new ArrayList<>();

        service.setFoodsMap(new HashMap<>());
        assertEquals(0, service.getFoodsMap().size());
        service.initializeMap(emptyList);
        assertEquals(0, service.getFoodsMap().size());
    }

    /**
     * Tarkastetaan että foodsMap hajautustaulu alustetaan oikein,
     * kun initializeMap funktiolle annetaan epätyhjä lista
     */
    @Test
    public void initializeMap2() {
        List<Food> items = new ArrayList<>();
        items.add(food1);
        items.add(food2);
        items.add(food3);
        items.add(food4);

        service.setFoodsMap(new HashMap<>());
        assertEquals(0, service.getFoodsMap().size());

        service.initializeMap(items);

        for (int i = 0; i < items.size(); i++) {
            int value = service.getFoodsMap().get(items.get(i));
            assertEquals(items.get(i).getAmount(), value);
        }
    }

    /**
     * tallennetaan raaka-ainetta ja tarkastetaan että raaka-aineelle luodaa raaka-aine malli,
     * kun sitä ei vielä ole olemassa
     */
    @Test
    public void checkIfLayoutExistAndCreate() {
        Food uniq = new Food("uniikki", "valmistaja", "pakastin", 1, 1);
        assertEquals(4, service.getLayouts().size());
        service.checkIfLayoutExistAndCreate(uniq);
        assertEquals(5, service.getLayouts().size());

        boolean found = false;
        for (Food layout : service.getLayouts()) {
            if (layout.equals(uniq)) {
                found = true;
            }
        }
        assertEquals(true, found);
    }

    @Test
    public void checkIfLayoutExistAndCreate2() {
        Food notUniq = new Food(food1.getName(), food1.getManufacturer(), food1.getPreservation(), food1.getWeight(), food1.getAmount());
        assertEquals(4, service.getLayouts().size());
        assertEquals(4, layoutDao.findAll().size());
        service.checkIfLayoutExistAndCreate(notUniq);
        assertEquals(4, service.getLayouts().size());
        assertEquals(4, layoutDao.findAll().size());
    }

    /**
     * Vähennetään raaka-aineen määrää varastossa, jota on jäljellä yksi kpl,
     * tällöin sen pitäisi poistua tietokannasta ja listalta kokonaan
     * @throws Exception
     */
    @Test
    public void deleteFood() throws Exception {
        service.deleteFood(food1, 1);
        assertEquals(3, service.getAllFoods().size());
        assertEquals(3, foodDao.findAll().size());
        assertEquals(4, service.getLayouts().size());

        boolean notFound1 = true;
        boolean notFound2 = true;
        for (Food food : service.getAllFoods()) {
            if (food.equals(food1)) {
                notFound1 = false;
            }
        }
        for (Food food : foodDao.findAll()) {
            if (food.equals(food1)) {
                notFound2 = false;
            }
        }
        assertEquals(true, (notFound1 && notFound2));
    }

    /**
     * Vähennetään raaka-aineen määrää yhdellä varastossa, jonka kappalemäärä on yli yhden.
     * Tällöin vain päivitämme uuden kappalearvon ja raaka-aine ei poistu tietokannasta eikä listalta
     */
    @Test
    public void deleteFood2() throws Exception {
        int amountAfterDelete = food2.getAmount() - 1;
        service.deleteFood(food2, 1);
        assertEquals(4, service.getAllFoods().size());
        assertEquals(4, foodDao.findAll().size());
        assertEquals(4, service.getLayouts().size());

        boolean found1 = false;
        boolean found2 = false;
        for (Food food : service.getAllFoods()) {
            if (food.equals(food2)) {
                found1 = true;
                assertEquals(amountAfterDelete, food.getAmount());
            }
        }

        for (Food food : foodDao.findAll()) {
            if (food.equals(food2)) {
                assertEquals(amountAfterDelete, food.getAmount());
                found2 = true;
            }
        }
        assertEquals(true, (found1 && found2));
    }

}