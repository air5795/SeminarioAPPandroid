package com.example.seminario.seminario.models;

public class orderspeoples {
    String id;
    String name;
    String latitude;
    String longitude;
    String state;
    String productsOrder;
    String totalorder;

    public orderspeoples(String id, String name, String latitude, String longitude, String state, String productsOrder, String totalorder) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.productsOrder = productsOrder;
        this.totalorder = totalorder;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductsOrder() {
        return productsOrder;
    }

    public void setProductsOrder(String productsOrder) {
        this.productsOrder = productsOrder;
    }

    public String getTotalorder() {
        return totalorder;
    }

    public void setTotalorder(String totalorder) {
        this.totalorder = totalorder;
    }
}
