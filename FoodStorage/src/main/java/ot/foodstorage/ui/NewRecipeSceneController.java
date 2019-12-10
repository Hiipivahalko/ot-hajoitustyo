package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    @FXML private TableView selectView;

    private ObservableList<Food> foodsList;

    @FXML
    public void saveNewRecipe(ActionEvent event) {
        List<Food> recipeFoods = new ArrayList<>();
        //recipeFoods.add(foodsComboBox.getValue());

        Recipe r = new Recipe(-1, newNameField.getText().toLowerCase(), recipeFoods, Integer.parseInt(newCookTimeField.getText())
                , newDescriptonField.getText(), newInstructionField.getText());
        appService.addNewRecipe(r);
    }

    public void setUpPage() {
        foodsList = FXCollections.observableList(appService.getAllFoods());
        foodTable.setItems(foodsList);

        foodTable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

    }


}
