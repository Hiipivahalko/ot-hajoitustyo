/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

/**
 * AllFoodsScene.fxml tiedoston controllerluokka.
 */
public class AllFoodsSceneController extends Controller {
    
    private ObservableList<Food> foodList;
    @FXML private TextField filterField;
    @FXML private TableView<Food> tableview;
    @FXML private TableView<Recipe> readyRecipeView;
    @FXML private TableView<Recipe> possibleRecipeView;
    @FXML private Label errorLabel;

    /**
     * Alustaa taulun, joka sisältää kaikki raaka-aineet.
     * @param foods asetettava lista
     */
    private void setTableview(List<Food> foods) {
        foodList = FXCollections.observableArrayList(foods);
        tableview.setItems(foodList);
        tableview.refresh();
    }

    /**
     * Päivittää valmiit reseptit-taulun.
     */
    private void setReadyRecipeView() {
        readyRecipeView.setItems(FXCollections.observableList(appService.getRecipeService().getAllReadyRecipes()));
        readyRecipeView.refresh();
    }

    /**
     * Alustaa varastosivun kokonaan (AllFoodsScene).
     */
    public void setUpScene() {
        setAllFoods();
        possibleRecipeView.setItems(FXCollections.observableList(appService.checkPossibleRecipes()));
        possibleRecipeView.refresh();
        setReadyRecipeView();
        errorLabel.setVisible(false);
    }

    /**
     * Asettaa ruokalistaukseen kaikki ruoat mitä tietokannasta löytyy.
     */
    public void setAllFoods() {
        setTableview(appService.getFoodService().getAllFoods());
    }

    /**
     * Asettaa ruokalistaa ruoat joiden nimet tai valmistaja nimet sisältyvät annettuun hakuehtoon.
     */
    @FXML
    public void filterByNameOrManufacturer() {
        setTableview((appService.getFoodService().filterFoods(filterField.getText(), 0)));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään vain jääkaapissa.
     */
    @FXML
    public void filterFridgeFoods() {
        setTableview(appService.getFoodService().filterFoods("jääkaappi", 1));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään kuivakaapeissa.
     */
    @FXML
    public void filterDryFoods() {
        setTableview(appService.getFoodService().filterFoods("kuivakaappi", 1));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään pakastimessa.
     */
    @FXML
    public void filterFreezerFoods() {
        setTableview(appService.getFoodService().filterFoods("pakastin", 1));
    }

    /**
     * Poistaa raaka-aineen raaka-aine listalta (tableView).
     */
    @FXML
    public void deleteFood() {
        try {
            appService.getFoodService().deleteFood(tableview.getSelectionModel().getSelectedItem(), 1);
            setAllFoods();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        setAllFoods();
        tableview.refresh();
    }

    /**
     * Poistaa valmiiksi tehdyn reseptin listalta (readyRecipeView).
     */
    @FXML
    public void deleteRecipe() {
        try {
            appService.getRecipeService().deleteReadyRecipe(readyRecipeView.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        setReadyRecipeView();
    }

    /**
     * Valmistaa reseptin mukaisen annoksen varastoon, saatavilla olevista raaka-aineista.
     * @param event
     */
    @FXML
    public void cook(ActionEvent event) {
        try {
            Recipe r = possibleRecipeView.getSelectionModel().getSelectedItem();
            appService.cookRecipe(r);
            errorLabel.setVisible(false);
            setUpScene();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
    }
}
