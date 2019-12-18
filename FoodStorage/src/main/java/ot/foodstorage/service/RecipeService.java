package ot.foodstorage.service;

import ot.foodstorage.dao.ReadyReacipesDao;
import ot.foodstorage.dao.RecipeDao;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecipeService {

    private RecipeDao recipeDao;
    private ReadyReacipesDao readyRecipesDao;
    private List<Recipe> allRecipes;
    private List<Recipe> readyRecipes;

    public RecipeService(RecipeDao recipeDao, ReadyReacipesDao readyRecipesDao) {
        this.recipeDao = recipeDao;
        this.readyRecipesDao = readyRecipesDao;
        this.allRecipes = recipeDao.findAll();
        this.readyRecipes = readyRecipesDao.findAll();
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public List<Recipe> getAllReadyRecipes() {
        return readyRecipes;
    }

    /**
     * Tallentaa uuden reseptin, jos sen nimistä ei löydy jo
     * @param recipe resepti
     */
    public boolean addNewRecipe(Recipe recipe, List<Food> foods) throws Exception {
        boolean sameAlready = false;
        for (Recipe next : allRecipes) {
            if (next.getName().equals(recipe.getName())) {
                sameAlready = true;
            }
        }
        foods = checkBoxes(foods);
        if (foods == null || sameAlready || foods.size() == 0) {
            throw new Exception("Raaka-aineita ei ollut tai saman niminen resepti löytyy jo varastosta");
        }
        recipe.setFoods(foods);
        recipeDao.saveOrUpdate(recipe);
        allRecipes.add(recipe);
        return true;
    }

    /**
     * Tarkistaa mitkä annetun Food-listan Food objetien checkBoxit on valittuna
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
            if (next.getAmountField().getText() != null || next.getAmountField().getText().length() > 0) {
                try {
                    int amount = Integer.parseInt(next.getAmountField().getText());
                    temp.setAmount(amount);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
            selected.add(temp);
        }
        return selected;
    }

    /**
     * valmistaa reseptin käytettävissä olevista raaka-aineista
     * @param recipe valmistettava resepti
     */
    public void cookRecipe(Recipe recipe, FoodService foodService) {
        recipe.setAmount(1);
        for (Food f : recipe.getFoods()) {
            try {
                foodService.deleteFood(f);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
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



    public void deleteRecipe(Recipe recipe) {
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
}
