package com.example.seminario.seminario;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seminario.seminario.models.menurestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterListDetailOrders extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<com.example.seminario.seminario.models.menurestaurant> menurestaurant;


    public adapterListDetailOrders(Context context, List<menurestaurant> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementdetailsorder,null);

        TextView title =  view.findViewById(R.id.text_name);
        TextView textprice =  view.findViewById(R.id.text_price);
        TextView text_menu1 =  view.findViewById(R.id.text_menu1);
        //RatingBar rating =  view.findViewById(R.id.proRatingBar);

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
