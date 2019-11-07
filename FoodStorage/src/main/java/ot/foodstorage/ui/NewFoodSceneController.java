/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewFoodSceneController extends Controller {
    
    @FXML private TextField nameField;
    @FXML private TextField manufacturerField;
    @FXML private TextField preservationField;
    @FXML private TextField weightField;
    @FXML private DatePicker dueDateField;

    private MainSceneController mainController;




    /**
     * Tapahtumankäsittelijä "Takaisin" napille, jolla päästään takaisin ruokalistaan
     * @param event
     */
    @FXML
    public void goBackToFoodList(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/MainScene.fxml"));
            Parent root = pageLoader.load();
            mainController = pageLoader.getController();
            mainController.setAppService(getAppService());
            mainController.setFoods();
            Scene dashboard = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(dashboard);
            window.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Funktio "Lisää ruoka" napille, joka lisää uuden ruoan varastoon
     * @param event
     * @throws SQLException
     */
    @FXML
    public void addNewFood(ActionEvent event) throws SQLException {
        int weigth = Integer.parseInt(weightField.getText());
        String date = dueDateField.getValue().toString();//"29.08.2001";
        if (getAppService() != null) {
            super.getAppService().saveNewFood(nameField.getText(),
                    manufacturerField.getText(),
                    preservationField.getText(),
                    weigth,
                    date);
        } else System.out.println("it was null");
        goBackToFoodList(event);
    }
    
}
