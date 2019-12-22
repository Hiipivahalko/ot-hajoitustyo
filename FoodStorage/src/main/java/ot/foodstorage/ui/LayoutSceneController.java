package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import ot.foodstorage.domain.Food;
import ot.foodstorage.service.AppService;

/**
 * AllFoodsScene.fxml tiedoston controllerluokka.
 */
public class LayoutSceneController extends Controller {

    private ObservableList<Food> layoutsList;
    private NewFoodSceneController newFoodController;
    @FXML private TableView<Food> layoutView;
    @FXML private Label label;

    /**
     * Alustaa sivun.
     * @param appService appService
     */
    public void setUpController(AppService appService) {
        layoutsList = FXCollections.observableList(appService.getFoodService().getLayouts());
        layoutView.setItems(layoutsList);
        label.setVisible(false);
    }

    /**
     * Siirtyy sivulle jossa voit luoda uuden raaka-aineen.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void addNewRawMaterial(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewFoodScene.fxml"));
            Parent root =  loader.load();
            newFoodController = loader.getController();
            newFoodController.setAppService(getAppService());
            newFoodController.setLayoutController(this);
            newFoodController.setUpPage();
            ((Node) event.getSource()).getScene().setRoot(root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lisää raaka-aine mallia vastaavan raaka-aineen varastoon.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void addExistRawMaterial(ActionEvent event) {
        try {
            Food selected = layoutView.getSelectionModel().getSelectedItem();
            Food food = new Food(selected.getName(), selected.getManufacturer(), selected.getPreservation(),
                    selected.getWeight(), Integer.parseInt(selected.getAmountField().getText()));
            appService.getFoodService().saveNewFood(food);
            label.setText(food.getName() + " lisätty onnistuneesti varastoon");
            label.setVisible(true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            label.setText("Jokin meni pieleen, valitse ensin tuote ja kuinka paljon tuotetta haluat. Paina sen jälkeen lisää nappia");
            label.setVisible(true);
        }
    }
}
