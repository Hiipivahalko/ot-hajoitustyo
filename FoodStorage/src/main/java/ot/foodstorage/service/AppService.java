/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.service;

import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.domain.Food;

import java.sql.SQLException;
import java.util.List;


public class AppService {
    
    private FoodDao foodDao;

    public AppService(FoodDao foodDao) {
        this.foodDao = foodDao;
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
    public void saveNewFood(String name, String manufacturer, String preservation, int weight, String dueDate) throws SQLException {
        Food newFood = new Food(name, manufacturer, preservation, weight, dueDate, -1);
        foodDao.save(newFood);
    }

    public List<Food> getAllFoods() throws SQLException {
        return foodDao.findAll();
    }
    
    
    

    
    
}
