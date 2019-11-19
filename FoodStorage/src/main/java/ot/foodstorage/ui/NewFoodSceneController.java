/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class NewFoodSceneController extends Controller {
    
    @FXML private TextField nameField;
    @FXML private TextField manufacturerField;
    @FXML private TextField weightField;
    @FXML private TextField amount;
    @FXML private DatePicker dueDateField;
    @FXML private ChoiceBox<String> preservationChoice;

    private AllFoodsSceneController mainController;
    private FoodLayoutSceneController layoutController;
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Tapahtumankäsittelijä "Takaisin" napille, jolla päästään takaisin ruokalistaan
     * @param event
     */
    @Override
    @FXML
    public void goBackToFoodList(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/AllFoodsScene.fxml"));
            Parent root = pageLoader.load();
            mainController = pageLoader.getController();
            mainController.setAppService(getAppService());
            mainController.setAllFoods();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void goBackToLayoutList(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/FoodLayoutScene.fxml"));
            Parent root = pageLoader.load();
            layoutController = pageLoader.getController();
            layoutController.setAppService(getAppService());
            ((Node) event.getSource()).getScene().setRoot(root);
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
        String date = dueDateField.getValue().toString();
        String preservation = preservationChoice.getValue();

        if (getAppService() != null) {
            getAppService().saveNewFood(nameField.getText(),
                    manufacturerField.getText(),
                    preservation,
                    weigth,
                    date,
                    Integer.parseInt(amount.getText()));
        } else System.out.println("it was null");
        //goBackToFoodList(event);
        stage.close();
    }
    
}
