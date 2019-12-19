package ot.foodstorage.domain;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ShoppingBasketTest extends Application {

    private ShoppingBasket basket;

    private Food food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
    private Food food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
    private Food food3 = new Food("leipä", "arla", "kuivakaappi", 1, 3);
    private Food food4 = new Food("juusto", "valio", "jääkaappi", 1, 1);
    private Food food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);

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
        this.basket = new ShoppingBasket(-1, new ArrayList<>());
        assertEquals(0, basket.getItems().size());
        assertTrue(basket.listToString().isEmpty());
    }

    private void addItemsToBasket() {
        basket.addItem(food1);
        basket.addItem(food2);
        basket.addItem(food3);
        basket.addItem(food4);
        basket.addItem(food5);
    }

    /**
     * Lisätään tyhjään ostoslistaan raaka-aine, jolloin ostoslistalla on yksi tavara
     */
    @Test
    public void addItem() {
        basket.addItem(food1);
        assertEquals(1, basket.getItems().size());
        assertEquals("milk", basket.getItems().get(0).getName());
        String s = "milk;valio;jääkaappi;1;1,";
        assertEquals(s, basket.listToString());
    }

    /**
     * Lisätään epätyhjään ostoslistaan uniikki raaka-aine, jolloin listalla on 6 tavaraa
     */
    @Test
    public void addItem2() {
        addItemsToBasket();
        String listToStringBefore = basket.listToString();
        basket.addItem(new Food("uusi", "uusivalmistaja", "pakastin", 4, 3));
        assertEquals(6, basket.getItems().size());
        assertEquals(listToStringBefore + "uusi;uusivalmistaja;pakastin;4;3,", basket.listToString());
    }

    /**
     * Testataan kun ostoslistaan lisätään tuote joka on jo listalla
     */
    @Test
    public void updateItem() {
        addItemsToBasket();
        Food f = food1;
        int fAmount = f.getAmount();
        int sizeBefore = basket.getItems().size();
        int amountBefore = basket.getItems().get(0).getAmount();
        basket.updateItem(0, f.getAmount() , f.getName());
        Food index0 = basket.getItems().get(0);
        assertEquals(sizeBefore, basket.getItems().size());
        assertEquals("milk", index0.getName());
        assertEquals(amountBefore + fAmount, index0.getAmount());
        assertEquals("milk;valio;jääkaappi;1;2,milk2;arla;jääkaappi;1;1,leipä;arla;kuivakaappi;1;3,juusto;valio;jääkaappi;1;1,jäätelö;valio;pakastin;1;2,",
                basket.listToString());

    }

    /**
     * Yritetään päivittää ostolistalla olevaa raaka-ainetta negatiivisella arvolla.
     * Tämä pitäisi epäonnistua ja lista pysyä muuttumattomana
     */
    @Test
    public void updateItem2() {
        addItemsToBasket();
        Food neg = new Food("milk", "valio" , "jääkaappi", 1, -7);
        String listStringBefore = basket.listToString();
        int sizeBefore = basket.getItems().size();
        int amountBefore = basket.getItems().get(0).getAmount();
        int negAmount = neg.getAmount();
        boolean failed = false;
        try {
            basket.updateItem(0, negAmount, "milk");
        } catch (IllegalArgumentException e) {
            Food index0 = basket.getItems().get(0);
            assertEquals(sizeBefore, basket.getItems().size());
            assertEquals("milk", index0.getName());
            assertEquals(amountBefore, index0.getAmount());
            assertEquals(listStringBefore, basket.listToString());
            failed = true;
        }
        assertEquals(failed, true);
    }


}