/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.dao;

import java.util.List;

/**
 * Rajapinta domain-luokkien tietokantatoiminnallisuuksia hoitaville luokille.
 */
public interface Dao<T> {

    /**
     * Etsii tietokantataulusta kaikki rivit.
     * @return listan riveistä
     */
    List<T> findAll();

    /**
     * Tallentaa annentun objektin.
     * @param object tallennettava objekti
     */
    void save(T object);

    /**
     * Päivittää taulun arvoja.
     * @param object päivitettävä objekti
     */
    void update(T object);

    /**
     * Poistaa tietokannasta tietyn rivin.
     * @param object poistettava objekti
     */
    void delete(T object);

    /**
     * Totetuttaa annetun kyselyn tietokantaan.
     * @param query toteutettva kysely
     * @return listan rivijä tietokannasta
     */
    List<T> selectQuery(String query);
}
