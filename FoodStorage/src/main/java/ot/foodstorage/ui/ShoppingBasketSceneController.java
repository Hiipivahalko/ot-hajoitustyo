package ot.foodstorage.ui;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;
import ot.foodstorage.domain.Recipe;


public class ShoppingBasketSceneController extends Controller{

    private ObservableList<Layout> layouts;
    private ObservableList<Food> basketItemsItems;
    private ObservableList<Recipe> recipes;
    @FXML private TableView layoutView;
    @FXML private TableView basketView;
    @FXML private TableView recepiView;


    @FXML
    public void addLayoutToBasket(ActionEvent event) {

    }

}
