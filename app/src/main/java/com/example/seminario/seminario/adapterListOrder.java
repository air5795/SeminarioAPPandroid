package com.example.seminario.seminario;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.Cart;
import com.example.seminario.seminario.models.Product;
import com.example.seminario.seminario.models.menurestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class adapterListOrder extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<menurestaurant> menurestaurant;
    static Cart shoppingcart;
    TextView title,textprice,text_menu1,addShopping,totalShopping;
    Button viewshoppingcart,reset,sendOrder;
    Boolean exist;


    //Product[] products;
    public adapterListOrder(Context context, List<menurestaurant> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        shoppingcart = new Cart();


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementorder,null);
        title =  view.findViewById(R.id.text_name);
        textprice =  view.findViewById(R.id.text_price);
        text_menu1 =  view.findViewById(R.id.text_menu1);
        addShopping =  view.findViewById(R.id.addShopping);
        totalShopping =  ((orderRestaurant)context).findViewById(R.id.totalShopping);//view.findViewById(R.id.totalShopping);
        viewshoppingcart =  ((orderRestaurant)context).findViewById(R.id.viewshoppingcart);
        reset =  ((orderRestaurant)context).findViewById(R.id.reset);
        sendOrder =  ((orderRestaurant)context).findViewById(R.id.sendOrder);



        ImageView img = view.findViewById(R.id.image);


        title.setText(this.menurestaurant.get(position).getName());
        textprice.setText(this.menurestaurant.get(position).getPrice().toString()+" Bs");
        text_menu1.setText("Tipo: "+this.menurestaurant.get(position).getType());
        String urlImage = this.menurestaurant.get(position).getPhoto();
        Picasso.get().load(restApi.server+"images/uploads/"+urlImage).into(img, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
            }
        });

        addShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = menurestaurant.get(position).getId();
                Integer value = menurestaurant.get(position).getPrice();
                String name = menurestaurant.get(position).getName();
                String type = menurestaurant.get(position).getType();
                String photo = menurestaurant.get(position).getPhoto();
                Product product = new Product(_id,name,value,type,photo);
                /////////////////////////////////

                /*Set<Product> products = shoppingcart.getProducts();
                Iterator iterator = products.iterator();
                exist = false;
                while (iterator.hasNext()){
                    Product prd = (Product) iterator.next();
                    String idprd = prd.getId();
                    if(_id == idprd){
                        exist = true;
                        break;
                    }
                }*/
                /////////////////////////////////
                //Product p = product;
                //Product pp = p;
                shoppingcart.addToCart(product);
                totalShopping.setText("Total = "+String.format("%.2f",shoppingcart.getValue())+" Bs");

                //Product[] p = products;
                //Product[] pp = p;
            }
        });

        sendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(context,viewOrdersShopping.class);
                //shoppingcart = shoppingcart;
                //shoppingcart = shoppingcart;
                //shoppingcart = shoppingcart;
                //context.startActivity(intent);
                Cart cart = shoppingcart;
                ArrayList<Product> itemsProduct = new ArrayList<Product>();
                Set<Product> products = cart.getProducts();
                String jsonsend = "null";
                Iterator iterator = products.iterator();
                while (iterator.hasNext()){
                    Product product = (Product) iterator.next();
                    String id = product.getId();
                    String name = product.getName();
                    double price = product.getValue();
                    String type = product.getType();
                    String photo = product.getPhoto();
                    jsonsend = jsonsend + ",{'id':'"+id+"','name':'"+name+"','price':'"+price+"','type':'"+type+"','photo':'"+photo+"'}";
                    //Product item = new Product(id,name,price,photo);
                    //itemsProduct.add(item);
                }

                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("_SYSTEM_MODULE", "ORDERSUSERS");
                //intent.putExtra("firstStepOrder", "{'orders':["+jsonsend+"]}");
                intent.putExtra("firstStepOrder", jsonsend);
                context.startActivity(intent);



            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                totalShopping.setText("Total = 0 Bs");
                shoppingcart.empty();
            }
        });

        viewshoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,viewOrdersShopping.class);
                shoppingcart = shoppingcart;
                context.startActivity(intent);
            }
        });
        return view;
    }
    @Override
    public int getCount() {
        return this.menurestaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return this.menurestaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.menurestaurant.get(position).getId());
    }


}
