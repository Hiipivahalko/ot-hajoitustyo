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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ot.foodstorage.domain.Food;
import ot.foodstorage.main.Main;
import ot.foodstorage.service.AppService;

/**
 * FXML Controller class
 *
 * @author osiipola
 */
public class MainSceneController extends Controller {
    
    private ObservableList<Food> allFoods;
    
    private Main application;
    //private AppService appService;
    
    @FXML private TableView<Food> tableview;
    
    
    @FXML
    public void addNewFood(ActionEvent event) throws IOException {
        super.changeSide(event, "/fxml/NewFoodScene.fxml");
    }

    public void setFoods() throws SQLException {
        allFoods = FXCollections.observableArrayList(getAppService().getAllFoods());
        tableview.setItems(allFoods);
    }
    
}
