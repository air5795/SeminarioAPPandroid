package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.orderspeoples;
import com.example.seminario.seminario.models.ordersrestaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class allOrdersForRestaurants extends AppCompatActivity {
    private String TAG = "allMyOrders";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    ListView listorderforrestaurant;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_orders_for_restaurants);
        listorderforrestaurant = findViewById(R.id.listorderforrestaurant);
        if (getIntent().getExtras() != null) {
            initVolleyCallback();
            _id = getIntent().getStringExtra("idRestaurant");
            String url = restApi.server+"services/api/view-order-restaurant";
            callrestapi = new callRestApi(mResultCallback,this);
            JSONObject sendObj = null;
            try {
                sendObj = new JSONObject("{'_id':'"+_id+"'}");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            callrestapi.postDataVolley("POSTCALL", url, sendObj);
        }
    }

    @Override
    public void onBackPressed() {
        _id = getIntent().getStringExtra("idRestaurant");
        Intent intentorder = new Intent(this, viewMyRestaurant.class);
        intentorder.putExtra("idRestaurant",_id);
        startActivity(intentorder);
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
                    ArrayList<orderspeoples> itemsMenu = new ArrayList<orderspeoples>();
                    JSONArray menu = (JSONArray)data.get("data");
                    for (int  i=0; i<menu.length();i++){
                        JSONObject p = (JSONObject)menu.get(i);
                        String id = p.getString("_id");
                        String nameUser = p.getString("nameUser");
                        String latitude = p.getString("latitude");
                        String logitude = p.getString("logitude");
                        String state = p.getString("state");
                        String productsOrder = p.getString("productsOrder");
                        String totalOrder = p.getString("totalOrder");
                        orderspeoples item = new orderspeoples(id,nameUser,latitude,logitude,state,productsOrder,totalOrder);
                        itemsMenu.add(item);
                    }

                    listorderforrestaurant.setAdapter(new adapterListMyOrdersRestrs(allOrdersForRestaurants.this,itemsMenu));

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
