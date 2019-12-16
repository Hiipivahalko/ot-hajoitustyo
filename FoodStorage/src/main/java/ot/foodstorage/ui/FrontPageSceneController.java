package ot.foodstorage.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FrontPageSceneController extends Controller {

    private AllFoodsSceneController mainController;
    private ShoppingBasketSceneController shopController;

    @FXML private TableView jokuTable;
    @FXML private TableColumn name;
    @FXML private TableColumn amount;

    private void test() {
        name.cellFactoryProperty().setValue("joo");
        amount.cellFactoryProperty().setValue(new TextField());
        jokuTable.getColumns().addAll(name, amount);
    }


    /**
     * Tapahtumankäsittelijä "Takaisin" napille, jolla päästään takaisin ruokalistaan
     * @param event tapahtumankäsittelija
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
