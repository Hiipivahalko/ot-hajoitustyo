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

public class NewRecipeSceneController extends Controller {

    @FXML private TextField newNameField;
    @FXML private TextArea newInstructionField;
    @FXML private TextArea newDescriptonField;
    @FXML private TextField newCookTimeField;
    @FXML private TableView<Food> foodTable;
    @FXML private Label errorLabel;
    private ObservableList<Food> foodsList;

    @FXML
    public void saveNewRecipe(ActionEvent event) {
        List<Food> recipeFoods = new ArrayList<>();
        //recipeFoods.add(foodsComboBox.getValue());
        int cookTime = -1;
        String name, description, instruction;
        try {
            cookTime = Integer.parseInt(newCookTimeField.getText());
            name = newNameField.getText();
            description = newDescriptonField.getText();
            instruction = newInstructionField.getText();
            Recipe r = new Recipe(name, recipeFoods, cookTime
                    , description, instruction);
            appService.getRecipeService().addNewRecipe(r, foodTable.getItems());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            errorLabel.setVisible(true);
            return;
        }
        goRecipeList(event);
    }

    public void setUpPage() {
        foodsList = FXCollections.observableList(appService.getFoodService().getLayouts());
        foodTable.setItems(foodsList);
        errorLabel.setVisible(false);

    }


}
