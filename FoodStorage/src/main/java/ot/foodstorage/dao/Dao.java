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
    T findOne(T object) throws SQLException;
    List<T> findAll() throws SQLException;
    void saveOrUpdate(T object) throws SQLException;
    void delete(T object) throws SQLException;
    List<T> filterFromAll(String filter) throws SQLException;
    List<T> selectQuery(String query);
}
