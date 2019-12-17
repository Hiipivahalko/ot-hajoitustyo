package ot.foodstorage.service;

import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.dao.LayoutDao;
import ot.foodstorage.domain.Food;

import java.util.List;

public class FoodService {

    private FoodDao foodDao;
    private LayoutDao layoutDao;
    private List<Food> allFoods;
    private List<Food> layouts;

    public FoodService(FoodDao foodDao, LayoutDao layoutDao) {
        this.foodDao = foodDao;
        this.layoutDao = layoutDao;
        this.allFoods = foodDao.findAll();
        this.layouts = layoutDao.findAll();
    }
}
