package ot.foodstorage.domain;

import javafx.scene.control.TextField;

public class Layout {

    private int id;
    private String name;
    private String manufacturer;
    private String preservation;
    private int weight;
    private TextField amountField;

    public Layout(int id, String name, String manufacturer, String preservation, int weight) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
        this.weight = weight;
        this.amountField = new TextField();
    }

    //// Getterit

    public int getId() {
        return id;
    }

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

    public TextField getAmountField() {
        return amountField;
    }

    /// Setterit

    public void setId(int id) {
        this.id = id;
    }

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

    public void setAmountField(TextField amountField) {
        this.amountField = amountField;
    }
}
