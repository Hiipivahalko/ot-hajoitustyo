/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

/**
 * FXML Controller class
 *
 * @author osiipola
 */
public class AllFoodsSceneController extends Controller implements Initializable {
    
    private ObservableList<Food> foodList;
    @FXML private TextField filterField;
    @FXML private TableView<Food> tableview;
    @FXML private TableView<Recipe> readyRecipeView;
    @FXML private TableView<Recipe> possibleRecipeView;

    private LayoutSceneController layoutController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tableview.setItems(FXCollections.observableArrayList(getAppService().getAllFoods()));
    }

    /**
     * Vaihtaa sivun jossa voi lisätä raaka-aineen
     * @param event
     * @throws IOException
     */
    @FXML
    public void addNewFood(ActionEvent event) {
        super.changeSide(event, "/fxml/LayoutScene.fxml");
        layoutController = (LayoutSceneController) controller;
        layoutController.setUpController(appService);
    }

    /**
     * Asettaa ruokalistaukseen kaikki ruoat mitä tietokannasta löytyy
     * @throws SQLException
     */
    @FXML
    public void setAllFoods() {
        setTableview(getAppService().getAllFoods());
    }

    /**
     * Asettaa ruokalistaa ruoat joiden nimet tai valmistaja nimet sisältyvät annettuun hakuehtoon
     * @throws SQLException
     */
    @FXML
    public void filterByNameOrManufacturer() {
        setTableview(getAppService().filterFoods(filterField.getText(), 0));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään vain jääkaapissa
     * @throws SQLException
     */
    @FXML
    public void filterFridgeFoods() {
        setTableview(getAppService().filterFoods("jääkaappi", 1));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään kuivakaapeissa
     * @throws SQLException
     */
    @FXML
    public void filterDryFoods() {
        setTableview(getAppService().filterFoods("kuivakaappi", 1));
    }

    /**
     * Asettaa ruokalistaukseen ruoat, joita säilytetään pakastimessa
     * @throws SQLException
     */
    @FXML
    public void filterFreezerFoods() {
        setTableview(getAppService().filterFoods("pakastin", 1));
    }

    @FXML
    public void deleteFood() {
        appService.deleteFood(tableview.getSelectionModel().getSelectedItem().getId());
        setAllFoods();
    }

    private void setTableview(List<Food> foods) {
        foodList = FXCollections.observableArrayList(foods);
        tableview.setItems(foodList);
    }

    public void setUpScene() {
        setAllFoods();
    }




}
