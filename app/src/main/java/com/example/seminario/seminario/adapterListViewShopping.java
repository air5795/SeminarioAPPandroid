package com.example.seminario.seminario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seminario.seminario.models.Product;
import com.example.seminario.seminario.models.menurestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class adapterListViewShopping extends BaseAdapter {
    private static LayoutInflater inflater = null;
    Context context;
    List<Product> viewproducts;


    public adapterListViewShopping(Context context, List<Product> viewproducts){
        this.context =context;
        this.viewproducts = viewproducts;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementviewshoppingcart,null);

        TextView title =  view.findViewById(R.id.text_name);
        TextView textprice =  view.findViewById(R.id.text_price);
        //TextView text_menu1 =  view.findViewById(R.id.text_menu1);
        //RatingBar rating =  view.findViewById(R.id.proRatingBar);
        ImageView img = view.findViewById(R.id.image);
        title.setText(this.viewproducts.get(position).getName());
        textprice.setText(this.viewproducts.get(position).getValue()+" Bs");
        //text_menu1.setText("Tipo: "+this.menurestaurant.get(position).getType());
        String urlImage = this.viewproducts.get(position).getPhoto();
        Picasso.get().load(restApi.server+"images/uploads/"+urlImage).into(img, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
            }
        });
        /*img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = datarestaurant.get(position).getId();
                Intent intent = new Intent(context, viewMyRestaurant.class);
                intent.putExtra("idRestaurant",_id);
                context.startActivity(intent);
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = datarestaurant.get(position).getId();
                Intent intent = new Intent(context, viewMyRestaurant.class);
                intent.putExtra("idRestaurant",_id);
                context.startActivity(intent);
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = datarestaurant.get(position).getId();
                Intent intent = new Intent(context, viewMyRestaurant.class);
                intent.putExtra("idRestaurant",_id);
                context.startActivity(intent);
            }
        });*/

        return view;
    }
    @Override
    public int getCount() {
        return this.viewproducts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.viewproducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.viewproducts.get(position).getId());
    }
}
