package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;
import ot.foodstorage.service.AppService;

import java.io.IOException;


public class LayoutSceneController extends Controller {

    private ObservableList<Layout> layoutsList;
    @FXML private TableView<Layout> layoutView;
    @FXML private TextField amountField;
    private NewFoodSceneController newFoodController;

    @FXML
    public void addNewRawMaterial(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
        Parent root =  loader.load();
        newFoodController = loader.getController();
        newFoodController.setAppService(getAppService());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        newFoodController.setStage(stage);
        stage.showAndWait();
    }

    @FXML
    public void addExistRawMaterial(ActionEvent event) throws IOException {
        if (layoutView.getSelectionModel().getSelectedItem() != null) {
            Layout selected = layoutView.getSelectionModel().getSelectedItem();
            appService.saveNewFood(selected.getName(), selected.getManufacturer(), selected.getPreservation(),
                    selected.getWeight(), Integer.parseInt(amountField.getText()));
        }

    }

    public void setUpController(AppService appService) {
        layoutsList = FXCollections.observableList(appService.getLayouts());
        layoutView.setItems(layoutsList);
    }




}
