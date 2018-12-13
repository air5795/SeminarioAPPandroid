package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeDashBoard extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "HomeDashBoard";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    private Session session;
    String username;
    TextView userprofle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dash_board);

        session = new Session(this);
        if(!session.getauth()){
            Toast.makeText(this, "Volviendo Inicio de Sesion", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,signIn.class));
        }
        userprofle = findViewById(R.id.user);
        findViewById(R.id.createRestaurant).setOnClickListener(this);
        findViewById(R.id.myRestaurants).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.allrestaurants).setOnClickListener(this);
        findViewById(R.id.userOrders).setOnClickListener(this);
        findViewById(R.id.shoppingcart).setOnClickListener(this);

        initVolleyCallback();
        String url = restApi.server+"services/api/all-restaurant";
        callrestapi = new callRestApi(mResultCallback,this);
        callrestapi.getDataVolley("GETCALL",url);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(getIntent());
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
                    JSONArray user = (JSONArray)data.get("data");
                    for (int  i=0; i<user.length();i++) {
                        JSONObject u = (JSONObject) user.get(i);
                        username = u.getString("name")+" "+u.getString("firstNameOwner")+" "+u.getString("lastNameOwner");
                    }
                    //String username = user.getString("name")
                    userprofle.setText(username);

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

    public void logout(){
        session = new Session(this);
        session.setauth(false);
        session.settoken("");
        Toast.makeText(HomeDashBoard.this, "Cerrando Session", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,signIn.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createRestaurant:
                Toast.makeText(HomeDashBoard.this, "Formulario crear Restaurante", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,createRestaurant.class));
                break;
            case R.id.myRestaurants:
                Toast.makeText(HomeDashBoard.this, "Mis Restaurantes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,allMyRestaurants.class));
                break;
            case R.id.allrestaurants:
                Toast.makeText(HomeDashBoard.this, "Todos los restaurantes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,allRestaurants.class));
                break;
            case R.id.userOrders:
                Toast.makeText(HomeDashBoard.this, "Mis ordenes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,myOrdersRestaurants.class));
                break;
            case R.id.logout:
                Toast.makeText(HomeDashBoard.this, "Cerrando Sesion", Toast.LENGTH_SHORT).show();
                logout();
                break;
            case R.id.shoppingcart:
                Toast.makeText(HomeDashBoard.this, "order oficial", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,orderRestaurant.class));
                break;
        }
    }
}