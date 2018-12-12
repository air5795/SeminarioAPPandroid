package com.example.seminario.seminario.models;

public class menurestaurant {
    private String id;
    private String name;
    private Integer price;
    private String type;
    private String photo;

    public menurestaurant(String id, String name, Integer price, String type, String photo) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String tipo) {
        this.type = tipo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}