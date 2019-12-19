package ot.foodstorage.service;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import ot.foodstorage.dao.FoodDao;
import ot.foodstorage.dao.LayoutDao;
import ot.foodstorage.domain.Food;

import java.util.*;

public class FoodService {

    private FoodDao foodDao;
    private LayoutDao layoutDao;
    private List<Food> allFoods;
    private List<Food> layouts;
    private Map<Food, Integer> foodsMap;

    public FoodService(FoodDao foodDao, LayoutDao layoutDao) {
        this.foodDao = foodDao;
        this.layoutDao = layoutDao;
        this.allFoods = foodDao.findAll();
        this.layouts = layoutDao.findAll();
        this.foodsMap = new HashMap<>();
        initializeMap(this.allFoods);
    }

    public List<Food> getLayouts() {
        return layouts;
    }

    public List<Food> getAllFoods() {
        return allFoods;
    }

    public Map<Food, Integer> getFoodsMap() {
        return foodsMap;
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
     * Tarkastaa löytyykö jo kyseistä raaka-aine mallia
     * @param newFood lisättävä raaka-aine
     */
    public void checkIfLayoutExistAndCreate(Food newFood) {
        Food newLayout = new Food(newFood.getName(), newFood.getManufacturer(), newFood.getPreservation(),
                newFood.getWeight());
        boolean already = false;
        for  (Food l : layouts) {
            if (l.getName().equals(newFood.getName()) && l.getManufacturer().equals(newFood.getManufacturer())) {
                already = true;
                break;
            }
        }
        if (!already) {
            layoutDao.save(newLayout);
            layouts.add(newLayout);
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
                food.setAmount(food.getAmount() + f.getAmount());
                //newValue += f.getAmount();
                break;
            }
        }
        if (found) {
            foodDao.update(food);
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
                    f.setAmount(f.getAmount() - 1);
                    foodDao.update(f);
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


}
