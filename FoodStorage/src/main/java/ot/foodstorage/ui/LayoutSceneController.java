package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ot.foodstorage.domain.Food;
import ot.foodstorage.service.AppService;

import java.io.IOException;


public class LayoutSceneController extends Controller {

    private ObservableList<Food> layoutsList;
    @FXML private TableView<Food> layoutView;
    @FXML private TextField amountField;
    private NewFoodSceneController newFoodController;

    public TableView<Food> getLayoutView() {
        return layoutView;
    }

    @FXML
    public void addNewRawMaterial(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
        Parent root =  loader.load();
        newFoodController = loader.getController();
        newFoodController.setAppService(getAppService());
        newFoodController.setLayoutController(this);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        newFoodController.setStage(stage);
        stage.showAndWait();
    }

    @FXML
    public void addExistRawMaterial(ActionEvent event) {
        try {
            Food selected = layoutView.getSelectionModel().getSelectedItem();
            Food food = new Food(selected.getName(), selected.getManufacturer(), selected.getPreservation(),
                    selected.getWeight(), Integer.parseInt(selected.getAmountField().getText()));
            appService.saveNewFood(food);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public void setUpController(AppService appService) {
        layoutsList = FXCollections.observableList(appService.getLayouts());
        layoutView.setItems(layoutsList);
    }




}
