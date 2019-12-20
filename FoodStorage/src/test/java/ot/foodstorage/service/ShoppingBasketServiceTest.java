package ot.foodstorage.service;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.dao.ReadyRecipesDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.dao.ShoppingBasketDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;
import ot.foodstorage.domain.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShoppingBasketServiceTest extends Application {

    private RecipeService recipeService;
    private ShoppingBasketService service;
    private RecipeDao recipeDao;
    private ReadyRecipesDao readyDao;
    private ShoppingBasketDao shoppingBasketDao;
    private Database db = new Database("jdbc:sqlite:test.db");

    private Food food1;
    private Food food2;
    private Food food3;
    private Food food4;

    private List<Food> foodsWithAllBoxesChecked;
    private Recipe recipe1;

    @BeforeClass
    public static void setClass() {
        try {
            launch();
        } catch (Exception e) {

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }

    @Before
    public void setUp() throws Exception {
        db.initializeDatabase();
        readyDao = new ReadyRecipesDao(db, "readyrecipes");
        recipeDao = new RecipeDao(db, "recipe");
        shoppingBasketDao = new ShoppingBasketDao(db, "shoppingbasket");
        recipeService = new RecipeService(recipeDao, readyDao);
        service = new ShoppingBasketService(shoppingBasketDao);
        food1 = new Food("food1", "manu1", "jääkaappi", 1, 1);
        food2 = new Food("food2", "manu2", "jääkaappi", 300, 4);
        food3 = new Food("food3", "manu3", "kuivakaappi", 4, 7);
        food4 = new Food("food4", "manu4", "pakastin", 50,  100);

        foodsWithAllBoxesChecked = setUpList();
        for (Food f : foodsWithAllBoxesChecked) {
            f.setAmountField(String.valueOf(f.getAmount()));
            f.setCheckBox(true);
        }

        recipe1 = new Recipe("resepti", foodsWithAllBoxesChecked, 20, "kuvaus", "ohje");

        assertEquals(0, shoppingBasketDao.findAll().size());
        assertEquals(0, service.getShoppingBasket().getItems().size());
    }

    private List<Food> setUpList() {
        List<Food> foods = new ArrayList<>();
        foods.add(food1);
        foods.add(food2);
        foods.add(food3);
        foods.add(food4);
        return foods;
    }

    /**
     * tarkastetaan että kun tallennetaan ostoslistaan raaka-aine, niin myös tietokantaan ja ostoslistan listaan
     * tulee oikea tuote
     */
    @Test
    public void addItemToShoppingBasket() {
        service.addItemToShoppingBasket(food1);

        assertEquals(1, shoppingBasketDao.findAll().size());
        ShoppingBasket basket = shoppingBasketDao.findOne(null);
        assertEquals(1, basket.getItems().size());
        assertEquals(1, service.getShoppingBasket().getItems().size());
        Food basketItemFromDao = basket.getItems().get(0);
        Food basketItem = service.getShoppingBasket().getItems().get(0);
        assertEquals(basketItem, food1);
        assertEquals(basketItemFromDao, food1);
        assertEquals(food1.getAmount(), basketItem.getAmount());
        assertEquals(food1.getAmount(), basketItemFromDao.getAmount());
    }

    /**
     * Tarkastetaan että jos sama tuote lisätään monta kertaa ostoslistaan, niin sen määrä ostoslistalla
     * kasvaa. Sama tuote ei pitäisi löytyä duplikaattinena listalta.
     */
    @Test
    public void addItemToShoppingBasket2() {
        int amount = food1.getAmount();

        service.addItemToShoppingBasket(food1);
        service.addItemToShoppingBasket(food1);

        assertEquals(1, service.getShoppingBasket().getItems().size());
        assertEquals(1, shoppingBasketDao.findOne(null).getItems().size());

        assertEquals(amount * 2, service.getShoppingBasket().getItems().get(0).getAmount());
        assertEquals(amount * 2, shoppingBasketDao.findOne(null).getItems().get(0).getAmount());
    }

    /**
     * yritetään laitetaan listalle monta uniikkia toutetta.
     * Listalta pitäisi löytä saman verran tuotteita
     */
    @Test
    public void addItemToShoppingBasket3() {
        service.addItemToShoppingBasket(food1);
        service.addItemToShoppingBasket(food2);
        service.addItemToShoppingBasket(food3);

        assertEquals(1, shoppingBasketDao.findAll().size());
        assertEquals(3, shoppingBasketDao.findOne(null).getItems().size());
        assertEquals(3, service.getShoppingBasket().getItems().size());

        for (Food f : service.getShoppingBasket().getItems()) {
            assertEquals(true, (f.equals(food1) || f.equals(food2) || f.equals(food3)));
        }
    }

    /**
     * yritetään lisätän reseptin tuotteet ostoslistalle.
     * Tällöin ostoslistaan tulee lisää raaka-ainetta
     * (raaka-aineen määrä reseptissä * reseptin haluttu määrä)
     */
    @Test
    public void addRecipeToBasket() {
        recipe1.setAmount(3);
        int amoun1 = food1.getAmount() * 3;
        int amoun2 = food2.getAmount() * 3;
        int amoun3 = food3.getAmount() * 3;
        int amoun4 = food4.getAmount() * 3;
        assertEquals(service.getShoppingBasket().getItems().size(), 0);
        service.addRecipeToBasket(recipe1);
        assertEquals(4, service.getShoppingBasket().getItems().size());
        assertEquals(4, shoppingBasketDao.findOne(null).getItems().size());
        for (Food f : service.getShoppingBasket().getItems()) {
            if (f.equals(food1)) {
                food1.getAmount();
                assertEquals(amoun1, f.getAmount());
            } else if (f.equals(food2)) {
                assertEquals(amoun2, f.getAmount());
            } else if (f.equals(food3)) {
                assertEquals(amoun3, f.getAmount());
            } else {
                assertEquals(amoun4, f.getAmount());
            }
        }
        for (Food f : shoppingBasketDao.findOne(null).getItems()) {
            if (f.equals(food1)) {
                food1.getAmount();
                assertEquals(amoun1, f.getAmount());
            } else if (f.equals(food2)) {
                assertEquals(amoun2, f.getAmount());
            } else if (f.equals(food3)) {
                assertEquals(amoun3, f.getAmount());
            } else {
                assertEquals(amoun4, f.getAmount());
            }
        }
    }

    /**
     * Yritetään lisätä ostoslistaan resepti, jossa on jo muutama tuote valmiiksi
     */
    @Test
    public void addRecipeToBasket2() {
        service.addItemToShoppingBasket(food1);
        service.addItemToShoppingBasket(food2);
        recipe1.setAmount(4);
        int amoun1 = (food1.getAmount() * 4) + food1.getAmount();
        int amoun2 = (food2.getAmount() * 4) + food2.getAmount();
        int amoun3 = food3.getAmount() * 4;
        int amoun4 = food4.getAmount() * 4;
        service.addRecipeToBasket(recipe1);
        assertEquals(4, service.getShoppingBasket().getItems().size());
        assertEquals(4, shoppingBasketDao.findOne(null).getItems().size());
        for (Food f : service.getShoppingBasket().getItems()) {
            if (f.equals(food1)) {
                food1.getAmount();
                assertEquals(amoun1, f.getAmount());
            } else if (f.equals(food2)) {
                assertEquals(amoun2, f.getAmount());
            } else if (f.equals(food3)) {
                assertEquals(amoun3, f.getAmount());
            } else {
                assertEquals(amoun4, f.getAmount());
            }
        }
    }

    /**
     * Yritetään lisätä reseptiä jonka haluttu lisäys määrä on negatiivinen.
     * Tälöin reseptiä ei pitäisi lisätä ollenkaan ja ostoslitan tuotemäärien pysyä samoina
     */
    @Test
    public void addRecipeToBasket3() {
        recipe1.setAmount(-3);
        service.addItemToShoppingBasket(food1);
        service.addItemToShoppingBasket(food2);
        int amoun1 = food1.getAmount();
        int amoun2 = food2.getAmount();
        assertEquals(2, service.getShoppingBasket().getItems().size());
        assertEquals(2, shoppingBasketDao.findOne(null).getItems().size());
        try {
            service.addRecipeToBasket(recipe1);
        } catch (Exception e) {

        }
        assertEquals(2, service.getShoppingBasket().getItems().size());
        assertEquals(2, shoppingBasketDao.findOne(null).getItems().size());
        boolean failed = false;
        for (Food f : service.getShoppingBasket().getItems()) {
            if (f.equals(food1)) {
                food1.getAmount();
                assertEquals(amoun1, f.getAmount());
            } else if (f.equals(food2)) {
                assertEquals(amoun2, f.getAmount());
            } else {
                failed = true;
            }
        }
        assertEquals(false, failed);
    }

    /**
     * Testataan ostoslistan alustusta, kun tietokannasta lyötyy "vanha" ostoslista
     */
    @Test
    public void initializeServiceWithNotNullShoppingBsket() {
        service.addItemToShoppingBasket(food1);
        service.addItemToShoppingBasket(food2);

        assertEquals(service.getShoppingBasket().getItems().size(), 2);
        ShoppingBasketService newSerivce = new ShoppingBasketService(shoppingBasketDao);

        assertEquals(2, newSerivce.getShoppingBasket().getItems().size());

        for (Food f : newSerivce.getShoppingBasket().getItems()) {
            assertEquals(true, f.equals(food1) || f.equals(food2));
        }
    }
}