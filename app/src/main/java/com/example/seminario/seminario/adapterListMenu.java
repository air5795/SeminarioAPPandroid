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

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.menurestaurant;
import com.example.seminario.seminario.models.restaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class adapterListMenu extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<menurestaurant> menurestaurant;
    private String TAG = "menurestaurants";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    String idback;
    public adapterListMenu(Context context, List<menurestaurant> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementmenu,null);

        TextView title =  view.findViewById(R.id.text_name);
        TextView textprice =  view.findViewById(R.id.text_price);
        TextView text_menu1 =  view.findViewById(R.id.text_menu1);
        TextView menu_delete =  view.findViewById(R.id.menu_delete);

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
        menu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyCallback();
                String _id = menurestaurant.get(position).getId();
                idback = _id;
                String url = restApi.server+"services/api/delete-menu-restaurant";
                callrestapi = new callRestApi(mResultCallback,context);
                JSONObject sendObj = null;
                try {
                    sendObj = new JSONObject("{'_id':'"+_id+"'}");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callrestapi.postDataVolley("POSTCALL", url, sendObj);
            }
        });

       /*title.setOnClickListener(new View.OnClickListener() {
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
                context.startActivity(intent);;
            }
        });*/

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

    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,context);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);

                Intent intent = new Intent(context, allMyMenus.class);
                intent.putExtra("idRestaurant",viewMyRestaurant._id);
                context.startActivity(intent);
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                callrestapi.parseVolleyError(error);
            }
        };
    }
}
