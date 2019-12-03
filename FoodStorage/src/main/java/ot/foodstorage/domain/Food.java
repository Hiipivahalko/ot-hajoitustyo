/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.domain;

public class Food {

    private String name;
    private String manufacturer;
    private String preservation;
    private int weight;
    private int id;
    private int amount;

    public Food(String name, String manufacturer, String preservation, int weight, int id, int amount) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
        this.weight = weight;
        this.id = id;
        this.amount = amount;
    }

    ////// GETTERIT
    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPreservation() {
        return preservation;
    }

    public int getWeight() {
        return weight;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    ///// SETTERIT
    public void setName(String name) {
        this.name = name;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setPreservation(String preservation) {
        this.preservation = preservation;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {

        if (this.getClass() != o.getClass()) {
            return false;
        }
        Food f = (Food) o;
        if (!this.getName().equals(f.getName()) ||
                !this.manufacturer.equals(f.getManufacturer()) ||
                !this.preservation.equals(f.getPreservation()) ||
                this.weight != f.getWeight()) {
            return false;
        }

        return true;
    }

}
