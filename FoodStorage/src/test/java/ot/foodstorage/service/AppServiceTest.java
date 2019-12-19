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
        readyRecipesDao = new ReadyRecipesDao(db, "readyrecipe");
        shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");

        appService = new AppService(foodDao, layoutDao, recipeDao, readyRecipesDao, shoppingBasketDao);
        foodService = appService.getFoodService();
        recipeService = appService.getRecipeService();
        shoppingBasketService = appService.getShoppingBasketService();

        food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
        food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
        food3 = new Food("food3", "manu3", "kuivakaappi", 4, 1);
        food4 = new Food("food4", "manu4", "pakastin", 50,  1);
    }


}