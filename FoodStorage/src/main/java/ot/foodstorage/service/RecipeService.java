package ot.foodstorage.service;

import ot.foodstorage.dao.ReadyRecipesDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Sovelluksen sovelluslogiikka luokka, hoitaa Recipe ja ReadyRecipes objekteihin tapahtuvat toiminnot
 * sekä johtaa ne myös tietokantaan.
 */
public class RecipeService {

    private RecipeDao recipeDao;
    private ReadyRecipesDao readyRecipesDao;
    private List<Recipe> allRecipes;
    private List<Recipe> readyRecipes;

    /**
     * Service objekti joka hoitaa Recipe ja ReadyRecipe luokkien toimintoja.
     * @param recipeDao Dao-rajapinta Foodtaulun toimintoihin
     * @param readyRecipesDao Dao-rajapinta LAyouttaulun toimintoihin
     */
    public RecipeService(RecipeDao recipeDao, ReadyRecipesDao readyRecipesDao) {
        this.recipeDao = recipeDao;
        this.readyRecipesDao = readyRecipesDao;
        this.allRecipes = recipeDao.findAll();
        this.readyRecipes = readyRecipesDao.findAll();
    }

    /// GETTERIT ja SETTERIT

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public List<Recipe> getAllReadyRecipes() {
        return readyRecipes;
    }

    public RecipeDao getRecipeDao() {
        return recipeDao;
    }

    public ReadyRecipesDao getReadyRecipesDao() {
        return readyRecipesDao;
    }

    public void setReadyRecipes(List<Recipe> readyRecipes) {
        this.readyRecipes = readyRecipes;
    }

    ////

    /**
     * Tallentaa uuden reseptin, jos sen nimistä ei löydy jo.
     * @param recipe resepti
     * @param foods lisättävän reseptin tarvittavat raaka-aineet
     * @return palauttaa true jos lisäys onnistuu
     */
    public boolean addNewRecipe(Recipe recipe, List<Food> foods) {
        boolean sameAlready = false;
        for (Recipe next : allRecipes) {
            if (next.getName().equals(recipe.getName())) {
                sameAlready = true;
            }
        }
        foods = checkBoxes(foods);
        if (foods == null || sameAlready || foods.size() == 0) {
            throw new NullPointerException("Raaka-aineita ei ollut tai saman niminen resepti löytyy jo varastosta");
        }
        recipe.setFoods(foods);
        recipeDao.save(recipe);
        allRecipes.add(recipe);
        return true;
    }

    /**
     * Tarkistaa mitkä annetun Food-listan Food objetien checkBoxit on valittuna.
     * @param foods Food lista
     * @return valitut Food objektit
     */
    public List<Food> checkBoxes(List<Food> foods) {
        List<Food> selected = new ArrayList<>();

        for (Food next : foods) {
            Food temp = new Food(next.getName(), next.getManufacturer(), next.getPreservation(), next.getWeight(),
                    next.getAmount());
            if (!next.getCheckBox().isSelected()) {
                continue;
            }
            int amount = Integer.parseInt(next.getAmountField().getText());
            temp.setAmount(amount);
            selected.add(temp);
        }
        return selected;
    }

    /**
     * Poistaa valmiiksi valmistetun reseptin.
     * @param recipe poistettava resepti
     */
    public void deleteReadyRecipe(Recipe recipe) {
        Iterator<Recipe> it = readyRecipes.iterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            if (r.getName().equals(recipe.getName())) {
                if (r.getAmount() > 1) {
                    r.setAmount(r.getAmount() - 1);
                    readyRecipesDao.update(r);
                } else if (r.getAmount() == 1) {
                    it.remove();
                    readyRecipesDao.delete(r);
                }
            }
        }
    }

    /**
     * Lisää uuden valmiin reseptin tai päivittää jo olemassa olevan valmiin reseptin määrää.
     * @param recipe lisättävä resepti
     */
    public void addNewReadyRecipe(Recipe recipe) {
        boolean allReady = false;
        for (Recipe r : readyRecipes) {
            if (r.getName().equals(recipe.getName())) {
                r.addOneAmountMore();
                recipe = r;
                allReady = true;
                break;
            }
        }
        if (!allReady) {
            readyRecipes.add(recipe);
            readyRecipesDao.save(recipe);
        } else {
            readyRecipesDao.update(recipe);
        }
    }
}
