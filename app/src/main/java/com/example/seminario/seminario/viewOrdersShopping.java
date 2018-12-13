package com.example.seminario.seminario;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.seminario.seminario.models.Cart;
import com.example.seminario.seminario.models.Product;
import com.example.seminario.seminario.models.menurestaurant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class viewOrdersShopping extends AppCompatActivity {
    ListView listMyOrderShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders_shopping);
        Cart cart = adapterListOrder.shoppingcart;
        //LinearLayout cartLayout = findViewById(R.id.cart);
        Log.e("size",""+cart.getSize());
        listMyOrderShopping = findViewById(R.id.listMyOrderShopping);
        ArrayList<Product> itemsProduct = new ArrayList<Product>();
        Set<Product> products = cart.getProducts();
        Iterator iterator = products.iterator();
        while (iterator.hasNext()){
            Product product = (Product) iterator.next();
            String id = product.getId();
            String name = product.getName();
            double price = product.getValue();
            String type = product.getType();
            String photo = product.getPhoto();
            Product item = new Product(id,name,price,type,photo);
            itemsProduct.add(item);
        }
        listMyOrderShopping.setAdapter(new adapterListViewShopping(viewOrdersShopping.this,itemsProduct));

        //menurestaurant item = new menurestaurant(id,name,price,type,photo);
        ///itemsMenu.add(item);

        //Set<Product> products = cart.getProducts();
        //Iterator iterator = products.iterator();
        //int i = 0;
        /*while (iterator.hasNext()){
            i = i + 1;
            Product product = (Product) iterator.next();
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            TextView name = new TextView(this);
            TextView quantity = new TextView(this);
            name.setText(product.getName());
            quantity.setText(Integer.toString(cart.getQuantity(product)));
            name.setTextSize(40);
            quantity.setTextSize(40);

            linearLayout.addView(name);
            linearLayout.addView(quantity);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,200, Gravity.CENTER);
            layoutParams.setMargins(20,50,20,50);
            linearLayout.setLayoutParams(layoutParams);

            name.setLayoutParams(new TableLayout.LayoutParams(0, ActionBar.LayoutParams.WRAP_CONTENT,1));
            quantity.setLayoutParams(new TableLayout.LayoutParams(0,ActionBar.LayoutParams.WRAP_CONTENT,1));

            name.setGravity(Gravity.CENTER);
            quantity.setGravity(Gravity.CENTER);

            cartLayout.addView(linearLayout);
        }*/
    }
}
