package ot.foodstorage.domain;

public class Layout {

    private int id;
    private String name;
    private String manufacturer;
    private String preservation;

    public Layout(int id, String name, String manufacturer, String preservation) {
        this.id = id;
        this.name = name;
        this.manufacturer = manufacturer;
        this.preservation = preservation;
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
}
