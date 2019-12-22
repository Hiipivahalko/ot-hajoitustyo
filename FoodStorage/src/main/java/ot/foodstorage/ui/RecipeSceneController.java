package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

/**
 * RecipeScene.fxml tiedoston controllerluokka.
 */
public class RecipeSceneController extends Controller {

    @FXML private TableView<Recipe> recipeView;
    @FXML private Label recipeNameLabel;
    @FXML private TextArea instructionArea;
    @FXML private TextArea descriptionArea;
    @FXML private TextField cookTimeField;
    @FXML private TableView<Food> recipeFoodsView;
    @FXML private Label errorLabel;
    private ObservableList<Recipe> recipeList;

    /**
     * Alustaa sivun.
     */
    public void setUpPage() {
        recipeList = FXCollections.observableList(appService.getRecipeService().getAllRecipes());
        recipeView.setItems(recipeList);
        errorLabel.setVisible(false);
    }

    /**
     * Näyttää reseptin tiedot sivulla.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void showRecipe(ActionEvent event) {
        try {
            Recipe r = recipeView.getSelectionModel().getSelectedItem();
            recipeNameLabel.setText(r.getName());
            instructionArea.setText(r.getInstruction());
            descriptionArea.setText(r.getDescription());
            String ct = String.valueOf(r.getCookTime());
            cookTimeField.setText(ct);
            recipeFoodsView.setItems(FXCollections.observableList(r.getFoods()));
            errorLabel.setVisible(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            errorLabel.setVisible(true);
        }
    }

}
