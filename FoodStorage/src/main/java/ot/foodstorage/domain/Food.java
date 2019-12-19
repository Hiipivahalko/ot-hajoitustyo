/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot.foodstorage.domain;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;


public class Food {

    private String name;
    private String manufacturer;
    private String preservation;
    private int weight;
    private int id;
    private int amount;
    private TextField amountField;
    private CheckBox checkBox;


    public Food(String name, String manufacturer, String preservation, int weight,  int amount) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
        this.weight = weight;
        this.id = -1;
        this.amount = amount;
        this.amountField = new TextField();
        this.checkBox = new CheckBox();
    }

    public Food(String name, String manufacturer, String preservation, int weight) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
        this.weight = weight;
        this.id = -1;
        this.amount = -1;
        this.amountField = new TextField();
        this.checkBox = new CheckBox();
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

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public TextField getAmountField() {
        return amountField;
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

    public void setAmountField(TextField amountField) {
        this.amountField = amountField;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }


    /**
     * Tarkataa ovatko kaksi objektia samat
     * @param o verrattava objekti
     * @return true/false, riippuen ovatko samat vai ei
     */
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

    /**
     * Laskee Food objektille hajautusarvon
     * @return hajautusarvo
     */
    @Override
    public int hashCode() {
        String s = this.name + this.getManufacturer() + this.getPreservation();
        return s.hashCode();
    }

    /**
     * Palauttaa objektin merkkijono esitysmuotona
     * @return esitysmuoto
     */
    @Override
    public String toString() {
        return this.name + " " + this.manufacturer + " " + this.preservation + " " + this.weight + " " + this.amount;
    }

}
