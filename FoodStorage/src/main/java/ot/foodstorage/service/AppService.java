/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.service;

import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.domain.Food;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class AppService {
    
    private FoodDao foodDao;
    private List<Food> allFoods;

    public AppService(FoodDao foodDao) throws SQLException {
        this.foodDao = foodDao;
        this.allFoods = foodDao.findAll();
    }

    /**
     * Luo uuden ruoka-olion ja tallentaa sen tietokantaan
     * @param name - nimi
     * @param manufacturer - valmistaja
     * @param preservation - säilytys
     * @param weight - paino
     * @param dueDate - eräpäivä
     * @throws SQLException
     */
    public void saveNewFood(String name, String manufacturer, String preservation, int weight, String dueDate, int amount) throws SQLException {
        Food newFood = new Food(name.toLowerCase(), manufacturer.toLowerCase(), preservation.toLowerCase(),
                weight, dueDate, -1, amount);
        foodDao.saveOrUpdate(newFood);
    }

    public List<Food> getAllFoods() throws SQLException {
        return foodDao.findAll();
    }

    public List<Food> filterFoods(String filter, int option) throws SQLException {
        List<Food> foods = new ArrayList<>();
        switch (option) {
            case 0:
                foods = foodDao.filterFromAll(filter);
                break;
            case 1:
                foods = foodDao.preservationFilter(filter);
                break;
        }
        return foods;
    }

    public void deleteFood(int id) throws SQLException {
        foodDao.delete(id);
    }
    
    
    

    
    
}
