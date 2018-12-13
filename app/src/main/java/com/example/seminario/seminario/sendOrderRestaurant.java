package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class sendOrderRestaurant extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "sendorders";
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    String finalStepOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_order_restaurant);
        findViewById(R.id.sendOrder).setOnClickListener(this);
    }


    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                Toast.makeText(sendOrderRestaurant.this, "Pedido Enviado Correctamente!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(sendOrderRestaurant.this,myOrdersRestaurants.class));
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                callrestapi.parseVolleyError(error);
            }
        };
    }

    public void sendorder(){
        /////////////////////////////////////////////////////////
        if (getIntent().getExtras() != null) {
            finalStepOrder = getIntent().getStringExtra("finalStepOrder");
        }
        /////////////////////////////////////////////////////////
        initVolleyCallback();
        String url = restApi.server+"services/api/send-order";
        callrestapi = new callRestApi(mResultCallback,this);
        JSONObject sendObj = null;
        try {
            sendObj = new JSONObject(finalStepOrder);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callrestapi.postDataVolley("POSTCALL", url, sendObj);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendOrder:
                Toast.makeText(sendOrderRestaurant.this, "Enviando Pedido", Toast.LENGTH_SHORT).show();
                sendorder();
                break;
        }
    }
}
