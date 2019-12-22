package ot.foodstorage.domain;

import java.util.List;

/**
 * Ostoskoriluokka.
 */
public class ShoppingBasket {

    private List<Food> items;
    private int id;
    private String name;
    private StringBuilder itemsAtString;

    /**
     * Alustaa ostoskorin.
     * @param id id
     * @param items ostoskorin tuotteet
     */
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

    /**
     * Palauttaa listan merkkijonona.
     * @return lista merkkijonona
     */
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

    public void setListToString(String items) {
        itemsAtString = new StringBuilder(items);
    }

    /**
     * Lisää tuotteen ostoskoriin.
     * @param food lisättävä raaka-aine
     */
    public void addItem(Food food) {
        items.add(food);
        itemsAtString.append(food.getName() + ";" + food.getManufacturer() + ";" + food.getPreservation() + ";" +
                food.getWeight() + ";" + food.getAmount() + ",");
    }

    /**
     * Päivittää ostoskorissa olevaa tuotetta.
     * @param index indeksi listalla
     * @param addedAmount lisättävä määrä
     * @param name tuotteen nimi
     */
    public void updateItem(int index, int addedAmount, String name) {
        if (addedAmount < 1) {
            throw new IllegalArgumentException("addedAmount oli negatiivinen tai nolla");
        }
        int newAmount = items.get(index).getAmount() + addedAmount;
        StringBuilder sb = new StringBuilder();
        items.get(index).setAmount(newAmount);
        String[] parts = itemsAtString.toString().split(",");
        for (String part : parts) {
            String[] food = part.split(";");
            if (food[0].equals(name)) {
                food[4] = String.valueOf(newAmount);
                part = food[0] + ";" + food[1] + ";" + food[2] + ";" + food[3] + ";" + food[4];
            }
            sb.append(part + ",");
        }
        itemsAtString = sb;
    }

}
