package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import ot.foodstorage.domain.Food;
import ot.foodstorage.service.AppService;
import java.io.IOException;

/**
 * AllFoodsScene.fxml tiedoston controllerluokka
 */
public class LayoutSceneController extends Controller {

    private ObservableList<Food> layoutsList;
    private NewFoodSceneController newFoodController;
    @FXML private TableView<Food> layoutView;

    @FXML
    public void addNewRawMaterial(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
            Parent root =  loader.load();
            newFoodController = loader.getController();
            newFoodController.setAppService(getAppService());
            newFoodController.setLayoutController(this);
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void addExistRawMaterial(ActionEvent event) {
        try {
            Food selected = layoutView.getSelectionModel().getSelectedItem();
            Food food = new Food(selected.getName(), selected.getManufacturer(), selected.getPreservation(),
                    selected.getWeight(), Integer.parseInt(selected.getAmountField().getText()));
            appService.getFoodService().saveNewFood(food);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void setUpController(AppService appService) {
        layoutsList = FXCollections.observableList(appService.getFoodService().getLayouts());
        layoutView.setItems(layoutsList);
    }

}
