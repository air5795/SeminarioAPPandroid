package com.example.seminario.seminario;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seminario.seminario.models.Cart;
import com.example.seminario.seminario.models.Product;
import com.example.seminario.seminario.models.menurestaurant;
import com.example.seminario.seminario.models.ordersrestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class adapterListMyOrdersUsers extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<ordersrestaurant> menurestaurant;
    static Cart shoppingcart;
    TextView title,text_location,text_menu1,totalmoney;
    Button viewshoppingcart,reset,sendOrder;
    Boolean exist;


    //Product[] products;
    public adapterListMyOrdersUsers(Context context, List<ordersrestaurant> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        shoppingcart = new Cart();


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementmyorders,null);
        title =  view.findViewById(R.id.text_name);
        text_location =  view.findViewById(R.id.text_location);
        text_menu1 =  view.findViewById(R.id.text_menu1);
        totalmoney =  view.findViewById(R.id.totalmoney);
        ImageView img = view.findViewById(R.id.image);
        title.setText(this.menurestaurant.get(position).getNameRestaurant());
        text_location.setText(this.menurestaurant.get(position).getAddressRestaurant());
        totalmoney.setText("ESTADO: "+this.menurestaurant.get(position).getState());
        totalmoney.setText("TOTAL DE PAGO: "+this.menurestaurant.get(position).getTotalOrder() +" Bs");
        String urlImage = this.menurestaurant.get(position).getPhoto();

        Picasso.get().load(restApi.server+"images/uploads/"+urlImage).into(img, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = menurestaurant.get(position).getId();
                Intent intent = new Intent(context, detailOrdersRestaurant.class);
                intent.putExtra("idOrder",_id);
                context.startActivity(intent);
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = menurestaurant.get(position).getId();
                Intent intent = new Intent(context, detailOrdersRestaurant.class);
                intent.putExtra("idOrder",_id);
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
