package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class allMyRestaurants extends AppCompatActivity {
    private String TAG = "allMyRestaurant";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    ListView listRestaurants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_my_restaurants);
        listRestaurants = findViewById(R.id.listRestaurants);
        initVolleyCallback();
        String url = restApi.server+"services/api/all-my-restaurant";
        callrestapi = new callRestApi(mResultCallback,this);
        callrestapi.getDataVolley("GETCALL",url);

    }

    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    JSONObject data = response;
                    JSONObject d = data;
                    ArrayList<restaurant> itemsRestaurants = new ArrayList<restaurant>();
                    JSONArray restaurants = (JSONArray)data.get("data");
                    JSONArray r = restaurants;
                    for (int  i=0; i<restaurants.length();i++){
                        JSONObject p = (JSONObject)restaurants.get(i);
                        String id = p.getString("_id");
                        String name = p.getString("name");
                        String street = p.getString("street");
                        String phone = p.getString("phone");
                        String logo = p.getString("logo");
                        String photo = p.getString("photo");
                        restaurant item = new restaurant(id,name,street,phone,logo,photo);
                        itemsRestaurants.add(item);
                    }


                    listRestaurants.setAdapter(new adapterList(allMyRestaurants.this,itemsRestaurants));
                } catch (JSONException e) {
                    Log.d(TAG, "error " + e);
                }
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
