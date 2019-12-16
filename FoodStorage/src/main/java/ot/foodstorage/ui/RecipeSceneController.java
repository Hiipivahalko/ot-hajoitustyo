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
    @FXML private Label recipeNameLabel;
    @FXML private TextArea instructionArea;
    @FXML private TextArea descriptionArea;
    @FXML private TextField cookTimeField;
    @FXML private TableView<Food> recipeFoodsView;

    private ObservableList<Recipe> recipeList;

    //private CheckComboBox<Food> checkComboBox;

    public void setUpPage() {
        recipeList = FXCollections.observableList(appService.getAllRecipes());
        recipeView.setItems(recipeList);
    }

    @FXML
    public void showRecipe(ActionEvent event) {
        Recipe r = recipeView.getSelectionModel().getSelectedItem();
        recipeNameLabel.setText(r.getName());
        instructionArea.setText(r.getInstruction());
        descriptionArea.setText(r.getDescription());
        String ct = String.valueOf(r.getCookTime());
        cookTimeField.setText(ct);
        System.out.println(r.getFoods().size());
        System.out.println(r.getFoods());
        recipeFoodsView.setItems(FXCollections.observableList(r.getFoods()));
    }



}
