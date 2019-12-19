package ot.foodstorage.domain;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class FoodTest extends Application {

    private Food food;

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
        this.food = new Food("maito", "valio", "jääkaappi", 1000, 1);
    }

    /**
     * tarkastaa ovatko kaksi samaa food objektia oikeasti samat, niinkuin  ovat
     */
    @Test
    public void testEquals() {
        Food other = new Food("maito", "valio", "jääkaappi", 1000, 300);
        boolean same = food.equals(other);

        assertEquals(true, same);
    }

    /**
     * tarkastaa ovatko kaksi uniikkia food objektia samoja.
     * Niiden ei pitäisi olla
     */
    @Test
    public void testEquals2() {
        Food notSame = new Food("juusto", "valio", "jääkaappi", 500, 1);
        boolean same = food.equals(notSame);

        assertEquals(false, same);
    }

    /**
     * Testaa ovatko food olio ja int olio samoja.
     * Niiden ei pitäisi olla
     */
    @Test
    public void testEquals3() {
        int number = 55;
        assertEquals(false, food.equals(number));
    }

}