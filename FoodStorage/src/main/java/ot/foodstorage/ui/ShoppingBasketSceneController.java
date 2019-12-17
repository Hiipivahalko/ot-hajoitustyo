package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;
import java.util.List;


public class ShoppingBasketSceneController extends Controller{

    private ObservableList<Food> layouts;
    private ObservableList<Food> basketItems;
    private ObservableList<Recipe> recipes;
    @FXML private TableView<Food> layoutView;
    @FXML private TableView<Food> basketView;
    @FXML private TableView<Recipe> recepiView;
    @FXML private TextField amountField;
    @FXML private Label errorLabel;

    @FXML
    public void addLayoutToBasket(ActionEvent event) {
        try {
            Food temp = layoutView.getSelectionModel().getSelectedItem();
            int a = Integer.parseInt(temp.getAmountField().getText());
            Food f = new Food(temp.getName(), temp.getManufacturer(), temp.getPreservation(), temp.getWeight(), -1, a);
            appService.addItemToShoppingBasket(f);
            setUpBasket();
        } catch (Exception e) {
            errorLabel.setVisible(true);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void setUpBasket() {
        this.basketItems = FXCollections.observableList(appService.getShoppingBasket().getItems());
        this.basketView.refresh();
        this.basketView.setItems(basketItems);
    }

    public void setUpPage(List<Food> layouts) {
        this.layouts = FXCollections.observableList(layouts);
        this.layoutView.setItems(this.layouts);
        recepiView.setItems(FXCollections.observableList(appService.getAllRecipes()));
        setUpBasket();
        errorLabel.setVisible(false);
    }

    @FXML
    public void AddRecipeToBasket(ActionEvent event) {
        try {
            Recipe r = recepiView.getSelectionModel().getSelectedItem();
            int amount = Integer.parseInt(r.getAmountField().getText());
            appService.addRecipeToBasket(r, amount);
            setUpBasket();
        } catch (Exception e) {
            errorLabel.setVisible(true);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void addItemsToStorage(ActionEvent event) {
        if (appService.addBasketItemsToStorageAndClearItemList()) {
            setUpBasket();
        }
    }

}
