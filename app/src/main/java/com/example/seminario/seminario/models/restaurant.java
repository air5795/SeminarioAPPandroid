package com.example.seminario.seminario.models;

public class restaurant {

    private String id;
    private String name;
    private String street;
    private String phone;
    private String logo;
    private String photo;

    public restaurant(String id, String name, String street, String phone, String logo, String photo) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.phone = phone;
        this.logo = logo;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
