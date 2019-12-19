package ot.foodstorage.dao;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;

import java.util.List;

import static org.junit.Assert.*;

public class LayoutDaoTest  extends Application {

    private LayoutDao layoutDao;
    private Database db;

    private Food layout1 = new Food("milk", "valio", "jääkaappi", 1);
    private Food layout2 = new Food("milk", "arla", "jääkaappi", 1);
    private Food layout3 = new Food("milk", "nönnönnöö", "jääkaappi", 2);
    private Food layout4 = new Food("juusto", "valio", "jääkaappi", 1);
    private Food layout5 = new Food("juusto", "arla", "jääkaappi", 1);

    @BeforeClass
    public static void setClass() {
        try {
            launch();
        } catch (Exception e) {

        }
    }

    @Before
    public void setUp() throws Exception {
        db = new Database("jdbc:sqlite:test.db");
        db.initializeDatabase();
        layoutDao = new LayoutDao(db, "layout");
        assertEquals(0, layoutDao.findAll().size());
    }

    @After
    public void tearDown() throws Exception {
    }

    private void saveLayouts() {
        layoutDao.save(layout1);
        layoutDao.save(layout2);
        layoutDao.save(layout3);
        layoutDao.save(layout4);
        layoutDao.save(layout5);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        layoutDao.save(layout1);
        List<Food> layouts = layoutDao.findAll();
        assertEquals(1, layouts.size());
        assertEquals("milk", layouts.get(0).getName());
        assertEquals("valio", layouts.get(0).getManufacturer());
    }

    @Test
    public void findAll2() {
        saveLayouts();
        List<Food> layouts = layoutDao.findAll();
        assertEquals(5, layouts.size());

        int milk = 0;
        int cheese = 0;

        for (Food l :layouts) {
            if (l.getName().equals("milk")) milk++;
            else if (l.getName().equals("juusto")) cheese++;
        }

        assertEquals(3, milk);
        assertEquals(2, cheese);
    }


    @Test
    public void delete() {
    }

    @Test
    public void filterFromAll() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Platform.exit();
    }
}