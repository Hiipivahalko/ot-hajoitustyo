/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.service;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import ot.foodstorage.dao.*;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;
import ot.foodstorage.domain.ShoppingBasket;

import java.util.*;

/**
 * Käyttöliittymän toiminnallisuuksien toteuttaja luokka, josta päästää käsiksi kaikkiin soveluksen rakenteisiin
 */
public class AppService {
    
    private FoodDao foodDao;
    private LayoutDao layoutDao;
    private RecipeDao recipeDao;
    private ShoppingBasketDao shoppingBasketDao;
    private ReadyReacipesDao readyReacipesDao;
    private List<Food> layouts;
    private List<Food> allFoods;
    private List<Recipe> allRecipes;
    private List<Recipe> readyRecipe;
    private ShoppingBasket shoppingBasket;
    private Map<Food, Integer> foodsMap;

    /**
     * Toiminnallisuuksien totetuttaja
     * @param foodDao objekti josta päästään käsiksi "Food" tietokantatauluun
     * @param layoutDao objekti josta päästään käsiksi "Layout" tietokantatauluun
     * @param recipeDao objekti josta päästään käsiksi "Recipe" tietokantatauluun
     * @param shoppingBasketDao objekti josta päästään käsiksi "ShoppingBasket" tietokantatauluun
     */
    public AppService(FoodDao foodDao, LayoutDao layoutDao, RecipeDao recipeDao, ShoppingBasketDao shoppingBasketDao,
                      ReadyReacipesDao readyReacipesDao) {
        this.foodDao = foodDao;
        this.layoutDao = layoutDao;
        this.recipeDao = recipeDao;
        this.shoppingBasketDao = shoppingBasketDao;
        this.readyReacipesDao = readyReacipesDao;
        this.allFoods = foodDao.findAll();
        this.layouts = layoutDao.findAll();
        this.allRecipes = recipeDao.findAll();
        this.readyRecipe = readyReacipesDao.findAll();
        this.foodsMap = new HashMap<>();
        if (shoppingBasketDao.findAll().size() > 0) {
            this.shoppingBasket = shoppingBasketDao.findAll().get(0);
        } else {
            this.shoppingBasket = new ShoppingBasket(1, new ArrayList<>());
        }
        initializeMap(this.allFoods);
    }

    public List<Food> getLayouts() {
        return layouts;
    }

    public List<Food> getAllFoods() {
        return allFoods;
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public List<Recipe> getAllRecipes() {
        return allRecipes;
    }

    public List<Recipe> getAllReadyRecipes() {
        return readyRecipe;
    }

    private void initializeMap(List<Food> foods) {
        for (Food f : foods) {
            foodsMap.put(f, f.getAmount());
        }
    }

    /**
     * Validoi annetun objetin. Tarkastaa onko int arvot epänegatiiviset sekä onko string arvot epätyhjät
     * @param food tarkastettava objekti
     */
    private void validateFood(Food food) {
        if (food.getAmount() < 1 || food.getWeight() < 1) {
            throw new ValueException("amount were negative or zero");
        } else if (food.getName().trim().isEmpty() || food.getManufacturer().trim().isEmpty() ||
            food.getPreservation().trim().isEmpty()) {
            throw new ValueException("string was empty");
        }
    }

    /**
     * Luo uuden ruoka-olion ja tallentaa sen tietokantaan
     * @param food tallennettava ruoka
     */
    public void saveNewFood(Food food) {
        validateFood(food);
        checkIfLayoutExistAndCreate(food);
        int newValue = food.getAmount();
        boolean found = false;
        for (Food f : allFoods) {
            if (f.equals(food)) {
                found = true;
                newValue += f.getAmount();
                break;
            }
        }
        if (found) {
            foodDao.update(food, newValue);
            //foodsMap.put(food)
        } else {
            allFoods.add(food);
            foodDao.save(food);
        }
        foodsMap.put(food, foodsMap.getOrDefault(food, 0) + food.getAmount());
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
        Food newLayout = new Food(-1, newFood.getName(), newFood.getManufacturer(), newFood.getPreservation(),
                newFood.getWeight());
        boolean already = false;
        for  (Food l : layouts) {
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
                shoppingBasket.updateItem(i, f.getAmount(), f.getName());
                already = true;
                break;
            }
        }
        if (!already) {
            shoppingBasket.addItem(f);
        }
        shoppingBasketDao.saveOrUpdate(shoppingBasket);
    }

    /**
     * Lisätään kaikki reseptin ainekset ostoskoriin
     * @param recipe lisättävän reseptin ainekset
     * @param amount kuinka monta kertaa reseptin tuotteet halutaan
     */
    public void addRecipeToBasket(Recipe recipe, int amount) {
        for (int i = 0; i < amount; i++) {
            for (Food next : recipe.getFoods()) {
                addItemToShoppingBasket(next);
            }
        }
    }

    /**
     * Vähentää yhden Food objektin varastosta, jos Food objetin määrä tippuu nollaan, poistetaan tuote kokonaan
     * tietokannasta
     * @param food vähennettävä objekti
     * @throws Exception
     */
    public void deleteFood(Food food) throws Exception {
        Iterator<Food> it = allFoods.iterator();
        while (it.hasNext()) {
            Food f = it.next();
            if (f.equals(food)) {
                if (f.getAmount() > 1) {
                    foodDao.update(f, f.getAmount() - 1);
                    f.setAmount(f.getAmount() - 1);
                    foodsMap.put(f, f.getAmount());
                } else if (f.getAmount() == 1) {
                    it.remove();
                    foodDao.delete(f);
                    foodsMap.remove(f);
                } else {
                    throw new Exception("Yritetään poistaa tuoteta mitä ei pitäisi olla varastossa");
                }
            }
        }
    }

    public void deleteRecipe(Recipe recipe) {
        Iterator<Recipe> it = readyRecipe.iterator();
        while (it.hasNext()) {
            Recipe r = it.next();
            if (r.getName().equals(recipe.getName())) {
                if (r.getAmount() > 1) {
                    r.setAmount(r.getAmount() - 1);
                    readyReacipesDao.update(r);
                } else if (r.getAmount() == 1) {
                    it.remove();
                    readyReacipesDao.delete(r);
                }
            }
        }
    }

    /**
     * Tarkastaa mitä kaikkia reseptejä pystyttäisiin valmistamaan saatavilla olevista raaka-aineista
     * @return listan reseptejä
     */
    public List<Recipe> checkPossibleRecipes() {
        List<Recipe> possibleRecipes = new ArrayList<>();
        for (Recipe r : allRecipes) {
            boolean allFound = true;
            for (Food f : r.getFoods()) {
                int amountOfFood = foodsMap.getOrDefault(f, 0);
                //System.out.println("a:" + amountOfFood + " f:" + f.getAmount());
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

    /**
     * valmistaa reseptin käytettävissä olevista raaka-aineista
     * @param recipe valmistettava resepti
     */
    public void cookRecipe(Recipe recipe) {
        recipe.setAmount(1);
        for (Food f : recipe.getFoods()) {
            try {
                deleteFood(f);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        boolean allReady = false;
        for (Recipe r : readyRecipe) {
            if (r.getName().equals(recipe.getName())) {
                r.addOneAmountMore();
                recipe = r;
                allReady = true;
                break;
            }
        }
        if (!allReady) {
            readyRecipe.add(recipe);
            readyReacipesDao.save(recipe);
        } else {
            readyReacipesDao.update(recipe);
        }
    }

    /**
     * Tallentaa uuden reseptin, jos sen nimistä ei löydy jo
     * @param recipe resepti
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
            return false;
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

    public boolean addBasketItemsToStorageAndClearItemList() {
        if (shoppingBasket.getItems().size() > 0) {
            for (Food next : shoppingBasket.getItems()) {
                saveNewFood(next);
            }
            shoppingBasket.setItems(new ArrayList<>());
            shoppingBasketDao.delete(null);
            return true;
        }
        return false;
    }



}
