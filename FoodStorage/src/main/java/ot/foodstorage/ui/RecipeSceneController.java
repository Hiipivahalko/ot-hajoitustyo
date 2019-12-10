package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeSceneController extends Controller {

    @FXML private TableView<Recipe> recipeView;

    private ObservableList<Recipe> recipeList;

    //private CheckComboBox<Food> checkComboBox;

    public void setUpPage() {
        recipeList = FXCollections.observableList(appService.getAllRecipes());
        recipeView.setItems(recipeList);
    }



}
