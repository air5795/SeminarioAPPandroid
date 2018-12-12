package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class viewMyRestaurant extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "viewMyRestaurant";
    TextView nameRestaurant,nitRestaurant,ownerRestaurant,streetRestaurant,phoneRestaurant;
    ImageView logoImage,imagePhoto;
    String _id;
    inteResults mResultCallback = null;
    callRestApi callrestapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_restaurant);

        nameRestaurant = findViewById(R.id.name);
        nitRestaurant = findViewById(R.id.nit);
        ownerRestaurant = findViewById(R.id.nameOwner);
        streetRestaurant = findViewById(R.id.street);
        phoneRestaurant = findViewById(R.id.phone);
        logoImage = findViewById(R.id.banar1);
        imagePhoto = findViewById(R.id.image);

        findViewById(R.id.createMenu).setOnClickListener(this);
        findViewById(R.id.viewmenu).setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            _id = getIntent().getStringExtra("idRestaurant");
            initVolleyCallback();
            String url = restApi.server+"services/api/view-restaurant";
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

    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    JSONObject data = response.getJSONObject("data");
                    String name = data.getString("name");
                    String nit = data.getString("nit");
                    String firstNameOwner = data.getString("firstNameOwner");
                    String lastNameOwner = data.getString("lastNameOwner");
                    String street = data.getString("street");
                    String phone = data.getString("phone");
                    String logo = data.getString("logo");
                    String photo = data.getString("photo");

                    nameRestaurant.setText(name);
                    nitRestaurant.setText("NIT: "+nit);
                    ownerRestaurant.setText("Propietario. :"+ firstNameOwner +" "+ lastNameOwner);
                    streetRestaurant.setText("Direccion: "+street);
                    phoneRestaurant.setText("Telefono: "+phone);

                    Picasso.get().load(restApi.server+"images/uploads/"+logo).into(logoImage, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });

                    Picasso.get().load(restApi.server+"images/uploads/"+photo).into(imagePhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createMenu:
                Intent intent = new Intent(this, createMenu.class);
                intent.putExtra("idRestaurant",_id);
                startActivity(intent);
                break;
            case R.id.viewmenu:
                Intent intentmenu = new Intent(this, allMyMenus.class);
                intentmenu.putExtra("idRestaurant",_id);
                startActivity(intentmenu);
                break;
        }
    }
}
