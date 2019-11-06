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
import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.service.AppService;
import ot.foodstorage.ui.MainSceneController;

/**
 *
 * @author osiipola
 */
public class Main extends Application{

    private Database db;
    private Scene mainScene;
    private Stage mainStage;
    private MainSceneController conroller;
    private AppService appService;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws IOException {
        this.db = new Database("jdbc:sqlite:foodStorage.db");
        FoodDao foodDao = new FoodDao(db, "Food");
        this.appService = new AppService(foodDao);
        FXMLLoader mainPageLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
        Parent root = mainPageLoader.load();
        conroller = mainPageLoader.getController();
        
        this.mainScene = new Scene(root);
    }

    @Override
    public void start(Stage stage) throws Exception {
       
       
       //Scene scene = new Scene(root);
       stage.setTitle("RuokaVarasto");
       stage.setScene(mainScene);
       this.conroller.setApplication(this);
       stage.show();
    }

    
}
