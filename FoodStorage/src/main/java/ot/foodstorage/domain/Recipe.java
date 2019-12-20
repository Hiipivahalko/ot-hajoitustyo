package ot.foodstorage.domain;

import javafx.scene.control.TextField;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private String description;
    private String instruction;
    private List<Food> foods;
    private int cookTime;
    private int amount;
    private TextField amountField;

    public Recipe(String name, List<Food> foods, int cookTime, String description, String instruction) {
        this.id = -1;
        this.name = name;
        this.foods = foods;
        this.cookTime = cookTime;
        this.description = description;
        this.instruction = instruction;
        this.amountField = new TextField();
        this.amount = -1;
    }

    public Recipe(String name, List<Food> foods, int cookTime, String description, String instruction, int amount) {
        this.id = -1;
        this.name = name;
        this.foods = foods;
        this.cookTime = cookTime;
        this.description = description;
        this.instruction = instruction;
        this.amountField = new TextField();
        this.amount = amount;
    }

    /// GETTER's


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getInstruction() {
        return instruction;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public int getCookTime() {
        return cookTime;
    }

    public TextField getAmountField() {
        return amountField;
    }

    public int getAmount() {
        return amount;
    }

    /// SETTER's

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setAmountField(String amount) {
        this.amountField.setText(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addOneAmountMore() {
        this.amount++;
    }

    public String listToString() {
        StringBuilder sb = new StringBuilder();
        for (Food f : foods) {
            sb.append(f.getName() + ";" + f.getManufacturer() + ";" + f.getPreservation() + ";" + f.getWeight() + ";" + f.getAmount() + "\t");
        }
        return sb.toString();
    }
}
