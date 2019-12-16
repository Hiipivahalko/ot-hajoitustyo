package ot.foodstorage.domain;

import java.util.ArrayList;
import java.util.List;

public class ShoppingBasket {

    private List<Food> items;
    private int id;
    private String name;
    private StringBuilder itemsAtString;

    public ShoppingBasket(int id, List<Food> items) {
        this.id = id;
        this.items = items;
        this.name = "basket";
        this.itemsAtString = new StringBuilder();
    }

    public List<Food> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String listToString() {
        return itemsAtString.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<Food> items) {
        this.items = items;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addItem(Food food) {
        items.add(food);
        if (items.size() > 1) {
            itemsAtString.append(",");
        }
        itemsAtString.append(food.getName() + ";" + food.getManufacturer() + ";" + food.getPreservation() + ";" +
                food.getWeight() + ";" + food.getAmount());
    }

    public void updateItem(int index, int addedAmount, String name) {
        int newAmount = items.get(index).getAmount() + addedAmount;
        StringBuilder sb = new StringBuilder();
        items.get(index).setAmount(newAmount);
        String[] parts = itemsAtString.toString().split(",");
        for (String part : parts) {
            String[] food = part.split(";");
            if (food[0].equals(name)) {
                food[3] = String.valueOf(newAmount);
                part = food[0] + ";" + food[1] + ";" + food[2] + ";" + food[3] + ";" + food[4];
            }
            if (sb.length() == 0) {
                sb.append(part);
            } else {
                sb.append("," + part);
            }
        }

    }

}
