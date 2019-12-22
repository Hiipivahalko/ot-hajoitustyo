/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ot.foodstorage.dao.*;
import ot.foodstorage.database.Database;
import ot.foodstorage.service.AppService;
import ot.foodstorage.ui.Controller;

/**
 * Sovelluksen main-luokka joka alustaa ja käynnistää ohjelman.
 */
public class Main extends Application {

    private Database db;
    private Scene mainScene;
    private Controller controller;
    private AppService appService;

    /**
     * Sovelluksen käynnistys.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Sovelluksen alustus.
     * @throws IOException IO-virheilmoitukset
     */
    @Override
    public void init() throws IOException {
        this.db = new Database("jdbc:sqlite:foodStorage.db");
        FoodDao foodDao = new FoodDao(db, "food");
        LayoutDao layoutDao = new LayoutDao(db, "layout");
        RecipeDao recipeDao = new RecipeDao(db, "recipe");
        ReadyRecipesDao readyRecipesDao = new ReadyRecipesDao(db, "readyRecipes");
        ShoppingBasketDao shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");

        this.appService = new AppService(foodDao, layoutDao, recipeDao, readyRecipesDao, shoppingBasketDao);
        
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/fxml/FrontPageScene.fxml"));
        Parent root = mainPageLoader.load();
        controller = mainPageLoader.getController();
        controller.setAppService(appService);
        this.mainScene = new Scene(root, 1000, 1000);
    }

    /**
     * Käynnistää käyttöliittymän.
     * @param stage UI-objekti
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("RuokaVarasto");
        stage.setScene(mainScene);
        stage.show();
    }
}
