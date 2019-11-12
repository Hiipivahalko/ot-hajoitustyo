package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javax.swing.text.html.ListView;

public class FoodLayoutSceneController extends Controller {

    @FXML private ListView<FoodLayout> layoutList;

    @FXML
    public void addNewRawMaterial(ActionEvent event) {
        super.changeSide(event, "/fxml/NewFoodScene.fxml");
    }




}
