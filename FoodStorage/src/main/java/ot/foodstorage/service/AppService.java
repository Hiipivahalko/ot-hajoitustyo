/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.service;

import ot.foodstorage.dao.*;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.*;

/**
 * Käyttöliittymän toiminnallisuuksien toteuttaja luokka, josta päästää käsiksi kaikkiin soveluksen rakenteisiin
 */
public class AppService {

    private FoodService foodService;
    private RecipeService recipeService;
    private ShoppingBasketService shoppingBasketService;

    /**
     * Toiminnallisuuksien totetuttaja
     * @param foodDao objekti josta päästään käsiksi "Food" tietokantatauluun
     * @param layoutDao objekti josta päästään käsiksi "Layout" tietokantatauluun
     * @param recipeDao objekti josta päästään käsiksi "Recipe" tietokantatauluun
     * @param shoppingBasketDao objekti josta päästään käsiksi "ShoppingBasket" tietokantatauluun
     */
    public AppService(FoodDao foodDao, LayoutDao layoutDao, RecipeDao recipeDao, ShoppingBasketDao shoppingBasketDao,
                      ReadyReacipesDao readyReacipesDao) {
        this.foodService = new FoodService(foodDao, layoutDao);
        this.recipeService = new RecipeService(recipeDao, readyReacipesDao);
        this.shoppingBasketService = new ShoppingBasketService(shoppingBasketDao);
    }

    public FoodService getFoodService() {
        return foodService;
    }

    public RecipeService getRecipeService() {
        return recipeService;
    }

    public ShoppingBasketService getShoppingBasketService() {
        return shoppingBasketService;
    }

    /**
     * Tarkastaa mitä kaikkia reseptejä pystyttäisiin valmistamaan saatavilla olevista raaka-aineista
     * @return listan reseptejä
     */
    public List<Recipe> checkPossibleRecipes() {
        List<Recipe> possibleRecipes = new ArrayList<>();
        for (Recipe r : getRecipeService().getAllRecipes()) {
            boolean allFound = true;
            for (Food f : r.getFoods()) {
                int amountOfFood = foodService.getFoodsMap().getOrDefault(f, 0);
                if (amountOfFood < f.getAmount()) {
                    allFound = false;
                    break;
                }
            }
            if (allFound) {
                possibleRecipes.add(r);
            }
        }
        return possibleRecipes;
    }

}
