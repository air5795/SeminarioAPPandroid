package com.example.seminario.seminario.models;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Cart {
    Map<Product,Integer> cart;
    double value = 0;

    public Cart(){
        cart = new LinkedHashMap<>();
    }

    public void addToCart(Product product){
        if(cart.containsKey(product))
            cart.put(product,cart.get(product)+1);
        else
            cart.put(product,1);
        value+=product.getValue();
    }

    public int getQuantity(Product product){
        return cart.get(product);
    }

    public Set getProducts(){
        return cart.keySet();
    }

    public void empty(){
        cart.clear();
        value=0;
    }

    public double getValue(){
        return value;
    }

    public int getSize(){
        return cart.size();
    }
}

