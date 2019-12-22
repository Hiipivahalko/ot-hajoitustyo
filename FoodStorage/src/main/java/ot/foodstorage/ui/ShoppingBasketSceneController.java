package ot.foodstorage.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;
import ot.foodstorage.service.FoodService;
import ot.foodstorage.service.RecipeService;
import ot.foodstorage.service.ShoppingBasketService;

import java.util.List;

/**
 * ShoppingBasketScene.fxml tiedoston controllerluokka.
 */
public class ShoppingBasketSceneController extends Controller{

    private ObservableList<Food> layouts;
    private ObservableList<Food> basketItems;
    private ObservableList<Recipe> recipes;
    private ShoppingBasketService basketService;
    private RecipeService recipeService;
    private FoodService foodService;
    @FXML private TableView<Food> layoutView;
    @FXML private TableView<Food> basketView;
    @FXML private TableView<Recipe> recepiView;
    @FXML private Label errorLabel;


    /**
     * Alustaa ostoskorin.
     */
    private void setUpBasket() {
        this.basketItems = FXCollections.observableList(basketService.getShoppingBasket().getItems());
        this.basketView.refresh();
        this.basketView.setItems(basketItems);
    }

    /**
     * alustaa sivun.
     * @param layouts alustettavat raaka-aine mallit
     */
    public void setUpPage(List<Food> layouts) {
        this.basketService = appService.getShoppingBasketService();
        this.recipeService = appService.getRecipeService();
        this.foodService = appService.getFoodService();
        this.layouts = FXCollections.observableList(layouts);
        this.layoutView.setItems(this.layouts);
        recepiView.setItems(FXCollections.observableList(recipeService.getAllRecipes()));
        setUpBasket();
        errorLabel.setVisible(false);
    }

    /**
     * Lisää reseptin tuotteet ostoskoriin.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void AddRecipeToBasket(ActionEvent event) {
        try {
            Recipe temp = recepiView.getSelectionModel().getSelectedItem();
            Recipe r = new Recipe(temp.getName(), temp.getFoods(), temp.getCookTime(), temp.getDescription(),
                    temp.getInstruction(), Integer.parseInt(temp.getAmountField().getText()));
            basketService.addRecipeToBasket(r);
            setUpBasket();
            errorLabel.setVisible(false);
        } catch (Exception e) {
            errorLabel.setVisible(true);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lisää raaka-aineen ostoskoriin.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void addLayoutToBasket(ActionEvent event) {
        try {
            Food temp = layoutView.getSelectionModel().getSelectedItem();
            int a = Integer.parseInt(temp.getAmountField().getText());
            Food f = new Food(temp.getName(), temp.getManufacturer(), temp.getPreservation(), temp.getWeight(), a);
            appService.getShoppingBasketService().addItemToShoppingBasket(f);
            setUpBasket();
            errorLabel.setVisible(false);
        } catch (Exception e) {
            errorLabel.setVisible(true);
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Lisää ostoskorin tuotteet varastoon ja tyhjentää varaston.
     * @param event tapahtumankäsittelija
     */
    @FXML
    public void addItemsToStorage(ActionEvent event) {
        if (appService.addBasketItemsToStorageAndClearItemList()) {
            setUpBasket();
        }
    }
}
