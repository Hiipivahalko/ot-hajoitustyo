package ot.foodstorage.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ot.foodstorage.database.Database;
import ot.foodstorage.domain.Layout;

import java.util.List;

import static org.junit.Assert.*;

public class LayoutDaoTest {

    private LayoutDao layoutDao;
    private Database db;

    private Layout layout1 = new Layout(1, "milk", "valio", "jääkaappi", 1);
    private Layout layout2 = new Layout(1, "milk", "arla", "jääkaappi", 1);
    private Layout layout3 = new Layout(1, "milk", "nönnönnöö", "jääkaappi", 2);
    private Layout layout4 = new Layout(1, "juusto", "valio", "jääkaappi", 1);
    private Layout layout5 = new Layout(1, "juusto", "arla", "jääkaappi", 1);

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
        layoutDao.saveOrUpdate(layout1);
        layoutDao.saveOrUpdate(layout2);
        layoutDao.saveOrUpdate(layout3);
        layoutDao.saveOrUpdate(layout4);
        layoutDao.saveOrUpdate(layout5);
    }

    @Test
    public void findOne() {
    }

    @Test
    public void findAll() {
        layoutDao.saveOrUpdate(layout1);
        List<Layout> layouts = layoutDao.findAll();
        assertEquals(1, layouts.size());
        assertEquals("milk", layouts.get(0).getName());
        assertEquals("valio", layouts.get(0).getManufacturer());
    }

    @Test
    public void findAll2() {
        saveLayouts();
        List<Layout> layouts = layoutDao.findAll();
        assertEquals(5, layouts.size());

        int milk = 0;
        int cheese = 0;

        for (Layout l :layouts) {
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
}