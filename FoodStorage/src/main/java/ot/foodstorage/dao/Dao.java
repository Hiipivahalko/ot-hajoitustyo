/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.dao;

import java.sql.SQLException;
import java.util.List;

/**
 *  Rajapinta domain-luokkien tietokantatoiminnallisuuksia hoitaville luokille
 */
public interface Dao<T> {
    List<T> findAll();
    void save(T object);
    void update(T object);
    void delete(T object);
    List<T> selectQuery(String query);
}
