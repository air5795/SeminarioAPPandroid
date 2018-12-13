package com.example.seminario.seminario.models;

public class Product {
    String name;
    String id;
    double value;
    String type;
    String photo;

    public Product(String id,String name, double value,String type,String photo) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.type = type;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.name = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.photo = type;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String name) {
        this.photo = photo;
    }
}
