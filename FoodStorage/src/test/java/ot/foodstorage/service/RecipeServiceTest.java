package ot.foodstorage.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.dao.ReadyRecipesDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeServiceTest extends Application {

    private RecipeService service;
    private RecipeDao dao;
    private ReadyRecipesDao readyDao;
    private Database db = new Database("jdbc:sqlite:test.db");

    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;

    private List<Food> foodsWithAllBoxesChecked;
    private Recipe recipe1;

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
        db.initializeDatabase();
        readyDao = new ReadyRecipesDao(db, "readyrecipes");
        dao = new RecipeDao(db, "recipe");
        service = new RecipeService(dao, readyDao);

        food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
        food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
        food3 = new Food("food3", "manu3", "kuivakaappi", 4, 1);
        food4 = new Food("food4", "manu4", "pakastin", 50,  1);

        foodsWithAllBoxesChecked = setUpList();
        for (Food f : foodsWithAllBoxesChecked) {
            f.setAmountField("1");
            f.setCheckBox(true);
        }
        recipe1 = new Recipe("resepti", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje");
    }

    private List<Food> setUpList() {
        List<Food> foods = new ArrayList<>();
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);
        foods.add(food4);
        return foods;
    }

    /**
     * uniikin ja validin reseptin lisääminen pitäsi onnistua
     */
    @Test
    public void addNewRecipe() {

        service.addNewRecipe(recipe1, foodsWithAllBoxesChecked);

        assertEquals(1, service.getAllRecipes().size());
        assertEquals(1, dao.findAll().size());
    }

    /**
     * saman nimisen reseptin lisääminen ei luo uutta reseptiä
     */
    @Test
    public void addNewRecipe2() {
        service.addNewRecipe(recipe1, foodsWithAllBoxesChecked);
        assertEquals(1, service.getAllRecipes().size());
        boolean failed = false;
        try {
            service.addNewRecipe(recipe1, foodsWithAllBoxesChecked);
        } catch (NullPointerException e) {
            failed = true;
        }
        assertEquals(1, service.getAllRecipes().size());
        assertEquals(true, failed);
    }

    /**
     * tarkastettavan listan kaikki amountField-kentät ovat valideja ja tarkastus palauttaa valitut raaka-aineet
     */
    @Test
    public void checkBoxes() {
        List<Food> foods = setUpList();

        for (Food f : foods) {
            f.setAmountField("1");
            f.setCheckBox(true);
        }

        List<Food> ans = new ArrayList<>();
        ans = service.checkBoxes(foods);
        assertEquals(4, ans.size());

        for (int i = 0; i < foods.size(); i++) {
            assertEquals(foods.get(i), ans.get(i));
        }
    }

    /**
     * Kun yhtäkään raaka-ainetta ei ole valittu, niin funktion pitäisi palauttaa null
     */
    @Test
    public void checkBoxes2() {
        List<Food> foods = setUpList();
        List<Food> ans = new ArrayList<>();
        for (Food f : foods) {
            f.setAmountField("");
            f.setCheckBox(false);
        }
        ans = service.checkBoxes(foods);
        assertEquals(0, ans.size());
    }

    /**
     * Poistaa jo valmistetun reseptin listalta ja tietokannasta, kun niitä on vain yksi jäljellä
     */
    @Test
    public void deleteReadyRecipe() {
        Recipe ready = new Recipe("resepti", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje", 1);
        Recipe ready2 = new Recipe("resepti2", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje", 1);

        // alustetaan listat
        List<Recipe> readyRecipeList = new ArrayList<>();
        readyRecipeList.add(ready);
        readyRecipeList.add(ready2);
        service.setReadyRecipes(readyRecipeList);
        readyDao.save(ready);
        readyDao.save(ready2);

        assertEquals(2, service.getAllReadyRecipes().size());
        assertEquals(2, readyDao.findAll().size());
        service.deleteReadyRecipe(ready);
        assertEquals(1, service.getAllReadyRecipes().size());
        assertEquals(1, readyDao.findAll().size());
    }

    /**
     * Vähennetään sellaista valmista reseptiä, joita on valmistettu yli yhden
     * tällöin reseptin ei pitäisi poistua valmiiden reseptien listalta
     */
    @Test
    public void deleteReadyRecipe2() {
        Recipe ready = new Recipe("resepti", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje", 5);
        Recipe ready2 = new Recipe("resepti2", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje", 1);

        int amountBefore = ready.getAmount();
        // alustetaan listat
        List<Recipe> readyRecipeList = new ArrayList<>();
        readyRecipeList.add(ready);
        readyRecipeList.add(ready2);
        service.setReadyRecipes(readyRecipeList);
        readyDao.save(ready);
        readyDao.save(ready2);

        assertEquals(2, service.getAllReadyRecipes().size());
        assertEquals(2, readyDao.findAll().size());
        service.deleteReadyRecipe(ready);
        assertEquals(2, service.getAllReadyRecipes().size());
        assertEquals(2, readyDao.findAll().size());
        Recipe readyAfterEat = service.getAllReadyRecipes().get(0);
        assertEquals(amountBefore - 1, readyAfterEat.getAmount());
    }

}