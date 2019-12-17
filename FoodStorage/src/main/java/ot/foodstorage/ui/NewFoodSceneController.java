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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ot.foodstorage.domain.Food;


public class NewFoodSceneController extends Controller {
    
    @FXML private TextField nameField;
    @FXML private TextField manufacturerField;
    @FXML private TextField weightField;
    @FXML private TextField amount;
    @FXML private DatePicker dueDateField;
    @FXML private ChoiceBox<String> preservationChoice;

    private AllFoodsSceneController mainController;
    private LayoutSceneController layoutController;
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setNameField(String nameField) {
        this.nameField.setText(nameField);
    }

    public void setManufacturerField(String manufacturerField) {
        this.manufacturerField.setText(manufacturerField);
    }

    public void setPreservationChoice(String preservationChoice) {
        this.preservationChoice.setValue(preservationChoice);
    }

    public void setLayoutController(LayoutSceneController controller) {
        this.layoutController = controller;
    }

    //public void

    /**
     * Tapahtumankäsittelijä "Takaisin" napille, jolla päästään takaisin ruokalistaan
     * @param event tapahtumankäsittelijä
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
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/LayoutScene.fxml"));
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
     * @param event tapahtumankäsitelijä
     */
    @FXML
    public void addNewFood(ActionEvent event) {
        int weigth = Integer.parseInt(weightField.getText());
        String preservation = preservationChoice.getValue();
        Food food = new Food(nameField.getText().toLowerCase(), manufacturerField.getText().toLowerCase(),
                preservation.toLowerCase(), weigth, Integer.parseInt(amount.getText()));

        if (appService != null) {
            appService.saveNewFood(food);
        } else System.out.println("it was null");
        this.layoutController.getLayoutView().refresh();
        stage.close();
    }

    @FXML
    public void closeWindow(ActionEvent event) {
        stage.close();
    }
    
}
