package ot.foodstorage.service;

import org.junit.Before;
import org.junit.Test;
import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.dao.LayoutDao;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;

import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class AppServiceTest {

    private AppService appService;
    private Database db = new Database("jdbc:sqlite:test.db");
    FoodDao foodDao = new FoodDao(db, "food");
    LayoutDao layoutDao = new LayoutDao(db, "layout");

    @Before
    public void setUp() throws Exception {
        this.appService = new AppService(foodDao, layoutDao);
        db.initializeDatabase();
        assertEquals(0, foodDao.findAll().size());
        assertEquals(0, layoutDao.findAll().size());
    }

    /**
     * Testataan appservicen ruoan tallennusta
     * @throws SQLException
     */
    @Test
    public void saveNewFood() throws SQLException {
        appService.saveNewFood("testiRuoka", "valmistajaTesti","jääkaappi", 1, "22.09.2020", 1);
        assertEquals(1, foodDao.findAll().size());
    }

    /**
     * Testataan ruoan tallennusta monella ruoalla
     * @throws SQLException
     */
    @Test
    public void saveNewFood2() throws SQLException {
        appService.saveNewFood("testiRuoka", "valmistajaTesti","jääkaappi", 1, "22.09.2020", 1);
        appService.saveNewFood("testiRuoka2", "valmistajaTesti2","jääkaappi", 3, "22.10.2020", 1);
        appService.saveNewFood("testiRuoka3", "valmistajaTesti","jääkaappi", 1, "22.08.2020", 1);
        List<Food> foods = foodDao.findAll();
        assertEquals(3, foods.size());
        assertEquals("testiruoka", foods.get(0).getName());
    }
}