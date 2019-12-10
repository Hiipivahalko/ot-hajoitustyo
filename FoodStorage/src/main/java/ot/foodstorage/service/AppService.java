/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.service;

import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.dao.LayoutDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.dao.ShoppingBasketDao;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Layout;
import ot.foodstorage.domain.Recipe;
import ot.foodstorage.domain.ShoppingBasket;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Käyttöliittymän toiminnallisuuksien toteuttaja luokka, josta päästää käsiksi kaikkiin soveluksen rakenteisiin
 */
public class AppService {
    
    private FoodDao foodDao;
    private LayoutDao layoutDao;
    private RecipeDao recipeDao;
    private ShoppingBasketDao shoppingBasketDao;
    private List<Layout> layouts;
    private List<Food> allFoods;
    private List<Recipe> recipes;
    private ShoppingBasket shoppingBasket;

    /**
     * Toiminnallisuuksien totetuttaja
     * @param foodDao objekti josta päästään käsiksi "Food" tietokantatauluun
     * @param layoutDao objekti josta päästään käsiksi "Layout" tietokantatauluun
     * @param recipeDao objekti josta päästään käsiksi "Recipe" tietokantatauluun
     * @param shoppingBasketDao objekti josta päästään käsiksi "ShoppingBasket" tietokantatauluun
     */
    public AppService(FoodDao foodDao, LayoutDao layoutDao, RecipeDao recipeDao, ShoppingBasketDao shoppingBasketDao) {
        this.foodDao = foodDao;
        this.layoutDao = layoutDao;
        this.recipeDao = recipeDao;
        this.shoppingBasketDao = shoppingBasketDao;
        this.allFoods = foodDao.findAll();
        this.layouts = layoutDao.findAll();
        this.recipes = recipeDao.findAll();
        if (shoppingBasketDao.findAll().size() > 0) {
            this.shoppingBasket = shoppingBasketDao.findAll().get(0);
        } else {
            this.shoppingBasket = new ShoppingBasket(1, new ArrayList<>());
        }
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public List<Food> getAllFoods() {
        return foodDao.findAll();
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    /**
     * Luo uuden ruoka-olion ja tallentaa sen tietokantaan
     * @param name nimi
     * @param manufacturer valmistaja
     * @param preservation säilytys
     * @param weight paino
     * @param amount tuotteen määrä
     */
    public void saveNewFood(String name, String manufacturer, String preservation, int weight, int amount) {
        Food newFood = new Food(name.toLowerCase(), manufacturer.toLowerCase(), preservation.toLowerCase(),
                weight, -1, amount);
        checkIfLayoutExistAndCreate(newFood);
        foodDao.saveOrUpdate(newFood);
    }

    /**
     * Suodattaa listan raaka-aineita, kaikista raaka-aineista
     * @param filter haluttu ominaisuus raaka-aineilla
     * @param option suodatusmuoto
     * @return lista raaka-aine olioita
     */
    public List<Food> filterFoods(String filter, int option) {
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

    /**
     * Tarkastaa löytyykö jo kyseistä raaka-aine mallia
     * @param newFood lisättävä raaka-aine
     */
    public void checkIfLayoutExistAndCreate(Food newFood) {

        Layout newLayout = new Layout(-1, newFood.getName(), newFood.getManufacturer(), newFood.getPreservation(),
                newFood.getWeight());
        boolean already = false;
        for  (Layout l : layouts) {
            if (l.getName().equals(newFood.getName()) && l.getManufacturer().equals(newFood.getManufacturer())) {
                already = true;
            }
        }

        if (!already) {
            layoutDao.saveOrUpdate(newLayout);
            layouts.add(newLayout);

        }
    }

    /**
     * Lisää tuotteen ostokoriin
     * @param f lisättyävä raaka-aine
     */
    public void addItemToShoppingBasket(Food f) {
        boolean already = false;
        for (int i = 0; i < shoppingBasket.getItems().size(); i++) {
            Food next = shoppingBasket.getItems().get(i);
            if (f.equals(next)) {
                shoppingBasket.getItems().get(i).setAmount(next.getAmount() + f.getAmount());
                already = true;
                break;
            }
        }
        if (!already) {
            shoppingBasket.addItem(f);
        }
    }


    public void deleteFood(int id) {
        foodDao.delete(id);
    }

    /**
     * Tallentaa uuden reseptin, jos sen nimistä ei löydy jo
     * @param recipe resepti
     */
    public void addNewRecipe(Recipe recipe) {
        boolean sameAlready = false;
        for (Recipe next : recipes) {
            if (next.getName().equals(recipe.getName())) {
                sameAlready = true;
            }
        }
        if (!sameAlready) {
            recipeDao.saveOrUpdate(recipe);
        }
    }
    
    
    

    
    
}
