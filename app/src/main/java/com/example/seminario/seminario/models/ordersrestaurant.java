package com.example.seminario.seminario.models;

public class ordersrestaurant {
    String id;
    String nameRestaurant;
    String addressRestaurant;
    String latitude;
    String longitude;
    String phone;
    String productsOrder;
    String state;
    String totalOrder;
    String photo;

    public ordersrestaurant(String id, String nameRestaurant, String addressRestaurant, String latitude, String longitude, String phone, String productsOrder, String state, String totalOrder, String photo) {
        this.id = id;
        this.nameRestaurant = nameRestaurant;
        this.addressRestaurant = addressRestaurant;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phone = phone;
        this.productsOrder = productsOrder;
        this.state = state;
        this.totalOrder = totalOrder;
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameRestaurant() {
        return nameRestaurant;
    }

    public void setNameRestaurant(String nameRestaurant) {
        this.nameRestaurant = nameRestaurant;
    }

    public String getAddressRestaurant() {
        return addressRestaurant;
    }

    public void setAddressRestaurant(String addressRestaurant) {
        this.addressRestaurant = addressRestaurant;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(String productsOrder) {
        this.productsOrder = productsOrder;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(String totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
