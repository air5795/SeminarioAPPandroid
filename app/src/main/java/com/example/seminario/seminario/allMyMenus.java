package com.example.seminario.seminario;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.example.seminario.seminario.models.menurestaurant;
import com.example.seminario.seminario.models.restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class allMyMenus extends AppCompatActivity {

    private String TAG = "allMyMenus";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    ListView listMyMenu;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_my_menus);

        listMyMenu = findViewById(R.id.listMyMenu);
        ///initVolleyCallback();
        ///String url = restApi.server+"services/api/show-menu";
        ///callrestapi = new callRestApi(mResultCallback,this);
        ///callrestapi.getDataVolley("GETCALL",url);
        if (getIntent().getExtras() != null) {
            initVolleyCallback();
            _id = getIntent().getStringExtra("idRestaurant");
            String url = restApi.server+"services/api/show-menu";
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
    //hola
    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    JSONObject data = response;
                    ArrayList<menurestaurant> itemsMenu = new ArrayList<menurestaurant>();
                    JSONArray menu = (JSONArray)data.get("data");
                    for (int  i=0; i<menu.length();i++){
                        JSONObject p = (JSONObject)menu.get(i);
                        String id = p.getString("_id");
                        String name = p.getString("name");
                        Integer price = p.getInt("price");
                        String type = p.getString("type");
                        String photo = p.getString("photo");
                        menurestaurant item = new menurestaurant(id,name,price,type,photo);
                        itemsMenu.add(item);
                    }

                    listMyMenu.setAdapter(new adapterListMenu(allMyMenus.this,itemsMenu));

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
