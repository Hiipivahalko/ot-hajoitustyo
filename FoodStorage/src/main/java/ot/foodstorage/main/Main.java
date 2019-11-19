/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.main;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.service.AppService;
import ot.foodstorage.ui.FrontPageSceneController;
import ot.foodstorage.ui.AllFoodsSceneController;

/**
 *
 * @author osiipola
 */
public class Main extends Application{

    private Database db;
    private Scene mainScene;
    private Stage mainStage;
    private AllFoodsSceneController mainConroller;
    private FrontPageSceneController controller;
    private AppService appService;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws IOException, SQLException {
        this.db = new Database("jdbc:sqlite:foodStorage.db");
        FoodDao foodDao = new FoodDao(db, "Food");
        this.appService = new AppService(foodDao);
        
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/fxml/FrontPageScene.fxml"));
        Parent root = mainPageLoader.load();
        controller = mainPageLoader.getController();
        controller.setAppService(appService);
        this.mainScene = new Scene(root, 1000, 1000);
    }

    @Override
    public void start(Stage stage) throws Exception {
       stage.setTitle("RuokaVarasto");
       stage.setScene(mainScene);
       stage.show();
    }

    
}
