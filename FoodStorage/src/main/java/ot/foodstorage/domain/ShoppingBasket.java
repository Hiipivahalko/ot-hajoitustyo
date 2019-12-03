package ot.foodstorage.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private List<Food> items;
    private int id;

    public ShoppingBasket(int id, List<Food> items) {
        this.id = id;
        this.items = items;
    }

    public List<Food> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public void setItems(List<Food> items) {
        this.items = items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(Food food) {
        boolean alredy = false;
        for (Food f : items) {
            //if ()
        }
    }
}
