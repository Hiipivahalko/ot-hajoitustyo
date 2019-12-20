package ot.foodstorage.service;

import ot.foodstorage.dao.ShoppingBasketDao;
import ot.foodstorage.domain.Food;
import ot.foodstorage.domain.Recipe;
import ot.foodstorage.domain.ShoppingBasket;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasketService {

    private ShoppingBasket shoppingBasket;
    private ShoppingBasketDao shoppingBasketDao;

    public ShoppingBasketService(ShoppingBasketDao shoppingBasketDao) {
        this.shoppingBasketDao = shoppingBasketDao;

        if (shoppingBasketDao.findAll().size() > 0) {
            this.shoppingBasket = shoppingBasketDao.findAll().get(0);
        } else {
            this.shoppingBasket = new ShoppingBasket(1, new ArrayList<>());
        }
    }

    public ShoppingBasket getShoppingBasket() {
        return shoppingBasket;
    }

    public ShoppingBasketDao getShoppingBasketDao() {
        return shoppingBasketDao;
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
     */
    public void addRecipeToBasket(Recipe recipe) {
        if (recipe.getAmount() < 1) {
            throw new IllegalArgumentException("lisättävä määrä reseptin tuotteita oli alle yhden");
        }
        for (Food f : recipe.getFoods()) {
            Food next = new Food(f.getName(), f.getManufacturer(), f.getPreservation(), f.getWeight(), f.getAmount());
            next.setAmount(next.getAmount() * recipe.getAmount());
            addItemToShoppingBasket(next);
        }
    }


}
