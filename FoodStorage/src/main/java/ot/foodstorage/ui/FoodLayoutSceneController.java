package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ot.foodstorage.domain.Layout;

import java.io.IOException;


public class FoodLayoutSceneController extends Controller {

    @FXML private ListView<Layout> layoutList;
    private NewFoodSceneController newFoodController;

    @FXML
    public void addNewRawMaterial(ActionEvent event) throws IOException {
        //super.changeSide(event, "/fxml/NewFoodScene.fxml");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
        Parent root =  loader.load();
        newFoodController = loader.getController();
        newFoodController.setAppService(getAppService());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        newFoodController.setStage(stage);
        stage.showAndWait();
    }




}
