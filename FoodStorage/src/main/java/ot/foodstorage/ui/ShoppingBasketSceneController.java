package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;
import ot.foodstorage.domain.Recipe;

import java.util.Collections;
import java.util.List;


public class ShoppingBasketSceneController extends Controller{

    private ObservableList<Layout> layouts;
    private ObservableList<Food> basketItems;
    private ObservableList<Recipe> recipes;
    @FXML private TableView<Layout> layoutView;
    @FXML private TableView<Food> basketView;
    @FXML private TableView recepiView;
    @FXML private TextField amountField;


    @FXML
    public void addLayoutToBasket(ActionEvent event) {
        if (layoutView.getSelectionModel().getSelectedItem() != null) {
            Layout temp = layoutView.getSelectionModel().getSelectedItem();
            int a = Integer.parseInt(amountField.getText());
            Food f = new Food(temp.getName(), temp.getManufacturer(), temp.getPreservation(), temp.getWeight(), -1, a);
            appService.addItemToShoppingBasket(f);
            setUpBasket();
        }
    }

    private void setUpBasket() {
        for (Food f : appService.getShoppingBasket().getItems()) System.out.println(f.getName());
        this.basketItems = FXCollections.observableList(appService.getShoppingBasket().getItems());
        this.basketView.setItems(basketItems);
    }

    public void setUpPage(List<Layout> layouts) {
        this.layouts = FXCollections.observableList(layouts);
        this.layoutView.setItems(this.layouts);
        setUpBasket();
    }



}
