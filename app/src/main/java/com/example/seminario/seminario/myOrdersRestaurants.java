package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.menurestaurant;
import com.example.seminario.seminario.models.ordersrestaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class myOrdersRestaurants extends AppCompatActivity {
    private String TAG = "allMyOrders";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    ListView myordersuser;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders_restaurants);
        myordersuser = findViewById(R.id.myordersuser);
        initVolleyCallback();
        String url = restApi.server+"services/api/view-order-username";
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
                    ArrayList<ordersrestaurant> itemsMenu = new ArrayList<ordersrestaurant>();
                    JSONArray menu = (JSONArray)data.get("data");
                    for (int  i=0; i<menu.length();i++){
                        JSONObject p = (JSONObject)menu.get(i);
                        String id = p.getString("_id");
                        String nameRestaurant = p.getString("nameRestaurant");
                        String addressRestaurant = p.getString("addressRestaurant");
                        String latitude = p.getString("latitude");
                        String longitude = p.getString("logitude");
                        String phone = p.getString("phone");
                        String state = p.getString("state");
                        String productsOrder = p.getString("state");
                        String totalOrder = p.getString("totalOrder");
                        String photo = p.getString("logoRestaurant");
                        ordersrestaurant item = new ordersrestaurant(id,nameRestaurant,addressRestaurant,latitude,longitude,phone,productsOrder,state,totalOrder,photo);
                        itemsMenu.add(item);
                    }

                    myordersuser.setAdapter(new adapterListMyOrdersUsers(myOrdersRestaurants.this,itemsMenu));

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
