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
import ot.foodstorage.domain.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AppServiceTest extends Application {

    private AppService appService;
    private FoodService foodService;
    private RecipeService recipeService;
    private ShoppingBasketService shoppingBasketService;
    private FoodDao foodDao;
    private LayoutDao layoutDao;
    private RecipeDao recipeDao;
    private ReadyRecipesDao readyRecipesDao;
    private ShoppingBasketDao shoppingBasketDao;
    private Database db = new Database("jdbc:sqlite:test.db");
    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;
    private List<Food> foodsWithAllBoxesChecked;
    private List<Food> foodsWithAllBoxesChecked2;
    private Recipe recipe1;
    private Recipe recipe2;


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
        foodDao = new FoodDao(db, "food");
        layoutDao = new LayoutDao(db, "layout");
        recipeDao = new RecipeDao(db, "recipe");
        readyRecipesDao = new ReadyRecipesDao(db, "readyrecipes");
        shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");

        appService = new AppService(foodDao, layoutDao, recipeDao, readyRecipesDao, shoppingBasketDao);
        foodService = appService.getFoodService();
        recipeService = appService.getRecipeService();
        shoppingBasketService = appService.getShoppingBasketService();

        food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
        food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
        food3 = new Food("food3", "manu3", "kuivakaappi", 4, 556);
        food4 = new Food("food4", "manu4", "pakastin", 50,  70);

        recipe1 = new Recipe("resepti", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje");
        recipe2 = new Recipe("resepti2", foodsWithAllBoxesChecked2, 220, "kuvaus2", "ohje2");
        setUpList();

        assertEquals(0, foodService.getAllFoods().size());
        assertEquals(0, recipeService.getAllRecipes().size());
        assertEquals(0, recipeService.getAllReadyRecipes().size());
    }

    private void setUpList() {
        foodsWithAllBoxesChecked = new ArrayList<>();
        foodsWithAllBoxesChecked.add(food1);
        foodsWithAllBoxesChecked.add(food2);
        foodsWithAllBoxesChecked.add(food3);
        foodsWithAllBoxesChecked.add(food4);

        for (Food f : foodsWithAllBoxesChecked) {
            f.setAmountField(String.valueOf(f.getAmount()));
            f.setCheckBox(true);
        }
        foodsWithAllBoxesChecked2 = new ArrayList<>();
        foodsWithAllBoxesChecked2.add(food1);
        foodsWithAllBoxesChecked2.add(food2);

    }

    /**
     * Tarkastetaan löydämme yhden reseptin, jota voi valmistaa raaka-aineista, kun reseptejä on vain yksi ja raaka-aineita
     * tarpeeksi siihen
     */
    @Test
    public void checkPossibleRecipes() {
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food2);
        foodService.saveNewFood(food3);
        foodService.saveNewFood(food4);

        recipeService.addNewRecipe(recipe1, foodsWithAllBoxesChecked);
        List<Recipe> recipes = appService.checkPossibleRecipes();
        assertEquals(1, recipes.size());
        assertEquals("resepti", recipes.get(0).getName());
    }

    /**
     * mahdollisia reseptejä mitä voisi valmistaa, ei pitäisi löytä, sillä yksi raaka-aine puuttuu varastosta
     * reseptiltä
     */
    @Test
    public void checkPossibleRecipes2() {
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food3);
        foodService.saveNewFood(food4);

        recipeService.addNewRecipe(recipe1, foodsWithAllBoxesChecked);
        List<Recipe> recipes = appService.checkPossibleRecipes();
        assertEquals(0, recipes.size());
    }

    /**
     * Jos kahdelle reseptille on raaka-aineet, niin kasksi reseptiä pitäisi löytyä
     */
    @Test
    public void checkPossibleRecipes3() {
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food2);
        foodService.saveNewFood(food3);
        foodService.saveNewFood(food4);

        recipeService.addNewRecipe(recipe1, foodsWithAllBoxesChecked);
        recipeService.addNewRecipe(recipe2, foodsWithAllBoxesChecked2);
        List<Recipe> recipes = appService.checkPossibleRecipes();
        assertEquals(2, recipes.size());
        assertEquals("resepti", recipes.get(0).getName());
        assertEquals("resepti2", recipes.get(1).getName());
    }

    /**
     * Reseptiä mitä ei löydy valmiiden reseptien joukosta valmistetaan.
     * Tällöin valmiisiin resepteihin pitäisi lisätä tämä resepti.
     * Samalla varastosta pitäisi vähentyä raaka-aineita sen verran mitä reseptiin käytetään
     */
    @Test
    public void cookRecipe() {
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food2);
        foodService.saveNewFood(food3);
        foodService.saveNewFood(food4);
        assertEquals(foodService.getAllFoods().size(), 4);
        recipeService.addNewRecipe(recipe2, foodsWithAllBoxesChecked2);

        assertEquals(1, appService.checkPossibleRecipes().size());
        assertEquals("resepti2", appService.checkPossibleRecipes().get(0).getName());

        appService.cookRecipe(recipe2);

        assertEquals(1, recipeService.getAllReadyRecipes().size());
        assertEquals(1, recipeService.getAllReadyRecipes().get(0).getAmount());
        assertEquals(2, foodService.getAllFoods().size());
    }

    /**
     * Resepti mitä löytyy valmiiden reseptien joukosta, lisätään yksi lisää.
     * Tällöin valmiiden reseptien määrä ei kasva vaan kyseisen reseptin määrä arvo kasvaa
     */
    @Test
    public void cookRecipe2() {
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food1);
        foodService.saveNewFood(food2);
        foodService.saveNewFood(food2);
        foodService.saveNewFood(food3);
        foodService.saveNewFood(food4);

        recipeService.addNewRecipe(recipe2, foodsWithAllBoxesChecked2);

        appService.cookRecipe(recipe2);
        appService.cookRecipe(recipe2);

        assertEquals(1, recipeService.getAllReadyRecipes().size());
        assertEquals(2, recipeService.getAllReadyRecipes().get(0).getAmount());
        assertEquals(2, foodService.getAllFoods().size());
    }

    /**
     * Tyhjennetään ostoskori, tällöin ostoskorin pitäsi olla tyhjä ja tuotteiden siirtyä varastoon.
     * Tässä testissä varasto on tyhjä, joten varastossa täytyy olla saman verran tuotteita kuin ostoskorissa
     * ennen tyhjennystä
     */
    @Test
    public void addBasketItemsToStorageAndClearItemList() {
        shoppingBasketService.getShoppingBasket().addItem(food1);
        shoppingBasketService.getShoppingBasket().addItem(food1);
        shoppingBasketService.getShoppingBasket().addItem(food2);
        shoppingBasketService.getShoppingBasket().addItem(food3);
        shoppingBasketService.getShoppingBasket().addItem(food4);

        int amount = 0;
        for (Food f : shoppingBasketService.getShoppingBasket().getItems()) {
            amount += f.getAmount();
        }
        appService.addBasketItemsToStorageAndClearItemList();
        assertEquals(0, shoppingBasketService.getShoppingBasket().getItems().size());
        assertEquals(4, foodService.getAllFoods().size());
        int amount2 = 0;
        for (Food f : foodService.getAllFoods()) {
            amount2 += f.getAmount();
        }
        assertEquals(amount, amount2);
    }
}