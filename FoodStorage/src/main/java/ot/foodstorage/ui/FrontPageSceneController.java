package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FrontPageSceneController extends Controller {

    private AllFoodsSceneController mainController;


    /**
     * Tapahtumankäsittelijä "Takaisin" napille, jolla päästään takaisin ruokalistaan
     * @param event
     */
    @Override
    @FXML
    public void goBackToFoodList(ActionEvent event) {
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource("/fxml/AllFoodsScene.fxml"));
            Parent root = pageLoader.load();
            mainController = pageLoader.getController();
            mainController.setAppService(getAppService());
            mainController.setAllFoods();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
