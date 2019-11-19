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

/**
 *
 * @author osiipola
 */
public class Controller {
    
    private AppService appService;
    private Controller controller;
    
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
    public void goBackToFoodList(ActionEvent event) {
        changeSide(event, "/fxml/AllFoodsScene.fxml");
    }
}
