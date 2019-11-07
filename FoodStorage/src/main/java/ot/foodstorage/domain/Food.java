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
    private String dueDate;
    private int weight;
    private int id;

    public Food(String name, String manufacturer, String preservation, int weight, String dueDate, int id) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
        this.weight = weight;
        this.dueDate = dueDate;
        this.id = id;
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

    public String getDueDate() {
        return dueDate;
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

}
