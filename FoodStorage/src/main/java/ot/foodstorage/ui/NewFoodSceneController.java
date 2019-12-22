/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ot.foodstorage.domain.Food;

/**
 * NewFoodScene.fxml FXML-tiedoston controllerluokka.
 */
public class NewFoodSceneController extends Controller {
    
    @FXML private TextField nameField;
    @FXML private TextField manufacturerField;
    @FXML private TextField weightField;
    @FXML private TextField amount;
    @FXML private ChoiceBox<String> preservationChoice;
    @FXML private Label errorLabel;

    public void setLayoutController(LayoutSceneController controller) {
        this.layoutController = controller;
    }

    /**
     * Alustaa sivun.
     */
    public void setUpPage() {
        errorLabel.setVisible(false);
    }

    /**
     * Funktio "Lisää ruoka" napille, joka lisää uuden ruoan varastoon.
     * @param event tapahtumankäsitelijä
     */
    @FXML
    public void addNewFood(ActionEvent event) {

        try {
            Food food = getFoodObject();
            appService.getFoodService().saveNewFood(food);
            super.goLayoutList(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
    }

    private Food getFoodObject() {
        Food food = new Food(nameField.getText().toLowerCase(), manufacturerField.getText().toLowerCase(),
                preservationChoice.getValue().toLowerCase(), Integer.parseInt(weightField.getText()),
                Integer.parseInt(amount.getText()));
        return food;
    }

    /**
     * Lisätään uusi mallin raaka-ainetta.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void addNewLayout(ActionEvent event) {
        try {
            Food food = getFoodObject();
            appService.getFoodService().checkIfLayoutExistAndCreate(food);
            super.goLayoutList(event);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
    }
    
}
