package ot.foodstorage.domain;

import java.util.List;

public class Recipe {

    private String name;
    private String instruction;
    private String description;
    private int cookTime;
    private List<Food> foods;

    public Recipe(String name, String instruction, String description, List<Food> foods, int cookTime) {
        this.name = name;
        this.instruction = instruction;
        this.description = description;
        this.foods = foods;
        this.cookTime = cookTime;
    }
}
