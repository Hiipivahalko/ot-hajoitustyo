package ot.foodstorage.dao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeDaoTest extends Application {

    private RecipeDao recipeDao;
    private Database db;

    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;
    private Food food5;
    private List<Food> recipeFoods;
    private Recipe recipe1;

    @BeforeClass
    public static void setClass() {
        try {
            launch();
        } catch ( Exception e) {

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
        this.recipeDao = new RecipeDao(db, "recipe");
        food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
        food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
        food3 = new Food("milk3", "arla", "kuivakaappi", 1, 3);
        food4 = new Food("milk4", "valio", "jääkaappi", 1, 1);
        food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);
        recipeFoods = new ArrayList<>();
        recipeFoods.add(food1);
        recipeFoods.add(food2);
        recipeFoods.add(food3);
        recipeFoods.add(food4);
        recipeFoods.add(food5);
        recipe1 = new Recipe("testName", recipeFoods, 20, "kuvaus", "ohje");

        assertEquals(0, recipeDao.findAll().size());
    }


    /**
     * testataan että recipe-tietokantatauluun tallentuu rivi
     */
    @Test
    public void save() {
        recipeDao.save(recipe1);
        assertEquals(1, recipeDao.findAll().size());
        Recipe fromDb = recipeDao.findAll().get(0);

        assertTrue(fromDb.getName().equals(recipe1.getName()));
        assertTrue(fromDb.getDescription().equals(recipe1.getDescription()));
        assertTrue(fromDb.getInstruction().equals(recipe1.getInstruction()));
        assertEquals(recipe1.getFoods().size(), fromDb.getFoods().size());
        assertEquals(recipe1.getCookTime(), fromDb.getCookTime());
    }

    /**
     * testataan monen rivin tallennusta recipe-tietokantatauluun
     */
    @Test
    public void save2() {
        recipeDao.save(recipe1);
        recipeDao.save(recipe1);
        recipeDao.save(recipe1);

        assertEquals(3, recipeDao.findAll().size());

        for (int i = 0; i < 3; i++) {
            Recipe fromDb = recipeDao.findAll().get(i);

            assertTrue(fromDb.getName().equals(recipe1.getName()));
            assertTrue(fromDb.getDescription().equals(recipe1.getDescription()));
            assertTrue(fromDb.getInstruction().equals(recipe1.getInstruction()));
            assertEquals(recipe1.getFoods().size(), fromDb.getFoods().size());
            assertEquals(recipe1.getCookTime(), fromDb.getCookTime());
        }

        recipeDao.save(recipe1);

        assertEquals(4, recipeDao.findAll().size());
    }

    /**
     * testaus reseptin raaka-aineiden muodostus merkkijonosta, joka on tietokantarivin kolumni
     */
    @Test
    public void filterFoods() {
        String items = "milk;valio;jääkaappi;1;1";
        List<Food> filterItems = recipeDao.filterFoods(items);

        assertEquals(1, filterItems.size());
        assertEquals(food1, filterItems.get(0));
    }

    /**
     * Sama testi kuin filterFoods, mutta isommalla otannalla
     */
    @Test
    public void filterFoods2() {
        String s = "milk;valio;jääkaappi;1;1\tmilk2;arla;jääkaappi;1;1\tmilk3;arla;kuivakaappi;1;3;\tmilk4;valio;jääkaappi;1;1\tjäätelö;valio;pakastin;1;1";
        List<Food> items = recipeDao.filterFoods(s);

        assertEquals(5, items.size());

        for (int i = 0; i < items.size(); i++) {
            assertEquals(recipeFoods.get(i), items.get(i));
        }
    }


}