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
import javafx.stage.Stage;
import ot.foodstorage.domain.Layout;
import ot.foodstorage.service.AppService;

import java.io.IOException;


public class LayoutSceneController extends Controller {

    private ObservableList<Layout> layoutsList;
    @FXML private TableView<Layout> layoutView;
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

    @FXML
    public void addExistRawMaterial(ActionEvent event) throws IOException {
        Layout selected = layoutView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
        Parent root =  loader.load();
        newFoodController = loader.getController();
        newFoodController.setAppService(getAppService());
        newFoodController.setNameField(selected.getName());
        newFoodController.setManufacturerField(selected.getManufacturer());
        newFoodController.setPreservationChoice(selected.getPreservation());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        newFoodController.setStage(stage);
        stage.showAndWait();
    }

    public void setUpController(AppService appService) {
        //System.out.println(appService.getLayouts().size());
        layoutsList = FXCollections.observableList(appService.getLayouts());
        //if (layoutsList == null) System.out.println("se oli null");
        layoutView.setItems(layoutsList);
    }




}