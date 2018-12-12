package com.example.seminario.seminario;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.seminario.seminario.models.restaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import java.util.List;

public class adapterList extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<restaurant> datarestaurant;


    public adapterList(Context context, List<restaurant> datarestaurant){
        this.context =context;
        this.datarestaurant = datarestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.list_element,null);

        TextView title =  view.findViewById(R.id.text_name);
        TextView description =  view.findViewById(R.id.text_location);
        //TextView delivery =  view.findViewById(R.id.proDelivery);
        //RatingBar rating =  view.findViewById(R.id.proRatingBar);

        ImageView img = view.findViewById(R.id.image);


        title.setText(this.datarestaurant.get(position).getName());
        description.setText(this.datarestaurant.get(position).getStreet());
        //delivery.setText(this.datarestaurant.get(position).getPhone());
        //rating.setProgress(3);
        String urlImage = this.datarestaurant.get(position).getLogo();
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
        });

        return view;
    }
    @Override
    public int getCount() {
        return this.datarestaurant.size();
    }

    @Override
    public Object getItem(int position) {
        return this.datarestaurant.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(this.datarestaurant.get(position).getId());
    }

}
