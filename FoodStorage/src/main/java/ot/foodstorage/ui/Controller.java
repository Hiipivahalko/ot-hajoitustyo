/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ot.foodstorage.service.AppService;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author osiipola
 */
public class Controller {
    
    AppService appService;
    Controller controller;
    ShoppingBasketSceneController shopController;
    AllFoodsSceneController allFoodsController;
    RecipeSceneController recipeSceneController;
    NewRecipeSceneController newRecipeSceneController;
    
    public void changeSide(ActionEvent event, String file) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource(file));
            Parent root = pageLoader.load();
            controller = pageLoader.getController();
            controller.setAppService(getAppService());
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    public void goBackToFoodList(ActionEvent event) throws SQLException {
        changeSide(event, "/fxml/AllFoodsScene.fxml");
        allFoodsController = (AllFoodsSceneController) controller;
        allFoodsController.setUpScene();
    }

    /**
     *
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void goShoppingBasket(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/ShoppingBasketScene.fxml"));
            Parent root = pageLoader.load();
            shopController = pageLoader.getController();
            shopController.setAppService(getAppService());
            shopController.setUpPage(appService.getLayouts());
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goRecipeList(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/RecipeScene.fxml"));
            Parent root = pageLoader.load();
            recipeSceneController = pageLoader.getController();
            recipeSceneController.setAppService(getAppService());
            recipeSceneController.setUpPage();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goNewRecipeScene(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/NewRecipeScene.fxml"));
            Parent root = pageLoader.load();
            newRecipeSceneController = pageLoader.getController();
            newRecipeSceneController.setAppService(getAppService());
            newRecipeSceneController.setUpPage();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


}
