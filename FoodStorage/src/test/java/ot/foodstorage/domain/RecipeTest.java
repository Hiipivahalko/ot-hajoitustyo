package ot.foodstorage.domain;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RecipeTest extends Application {

    private Recipe recipe;

    private Food food1 = new Food("milk", "valio", "jääkaappi", 1, 1);
    private Food food2 = new Food("milk2", "arla", "jääkaappi", 1, 1);
    private Food food3 = new Food("leipä", "arla", "kuivakaappi", 1, 3);
    private Food food4 = new Food("juusto", "valio", "jääkaappi", 1, 1);
    private Food food5 = new Food("jäätelö", "valio", "pakastin", 1, 2);

    @BeforeClass
    public static void setUpClass() {
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
        this.recipe = new Recipe("keitto", new ArrayList<>(), 20, "hyvä keitto", "keitä 20min");
        recipe.setFoods(createRecipeList());
    }

    private List<Food> createRecipeList() {
        List<Food> r = new ArrayList<>();
        r.add(food1);
        r.add(food4);
        return r;
    }

    /**
     * tarkastetaan että food objekteista muodestetaan oikeanlainen merkkijono
     */
    @Test
    public void listToString() {
        String s = "milk;valio;jääkaappi;1;1\tjuusto;valio;jääkaappi;1;1\t";
        assertEquals(recipe.listToString(), s);
    }
}