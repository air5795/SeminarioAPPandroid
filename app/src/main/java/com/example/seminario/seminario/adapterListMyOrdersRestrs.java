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
import com.example.seminario.seminario.models.Cart;
import com.example.seminario.seminario.models.orderspeoples;
import com.example.seminario.seminario.models.ordersrestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class adapterListMyOrdersRestrs extends BaseAdapter {
    private String TAG = "adapterlistmyorderrestrs";
    private static LayoutInflater inflater = null;
    Context context;
    List<orderspeoples> menurestaurant;
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    static Cart shoppingcart;
    TextView title,text_location,text_menu1,totalmoney,mapuser,updatesendorder,updateorderdelivered;
    Button viewshoppingcart,reset,sendOrder;
    Boolean exist;


    //Product[] products;
    public adapterListMyOrdersRestrs(Context context, List<orderspeoples> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        shoppingcart = new Cart();


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementdetailordersresta,null);
        title =  view.findViewById(R.id.text_name);
        //text_location =  view.findViewById(R.id.text_location);
        text_menu1 =  view.findViewById(R.id.text_menu1);
        totalmoney =  view.findViewById(R.id.totalmoney);
        mapuser = view.findViewById(R.id.mapuser);
        updatesendorder = view.findViewById(R.id.updateOrderSend);
        updateorderdelivered = view.findViewById(R.id.updateOrderDelivered);
        ImageView img = view.findViewById(R.id.image);
        title.setText(this.menurestaurant.get(position).getName());
        //text_location.setText(this.menurestaurant.get(position).getLatitude());
        text_menu1.setText("ESTADO: "+this.menurestaurant.get(position).getState());
        totalmoney.setText("TOTAL DE PAGO: "+this.menurestaurant.get(position).getTotalorder() +" Bs");
        //String urlImage = this.menurestaurant.get(position).getPhoto();

        /*Picasso.get().load(restApi.server+"images/uploads/"+urlImage).into(img, new Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError(Exception e) {
            }
        });*/

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = menurestaurant.get(position).getId();
                Intent intent = new Intent(context, detailOrdersRestaurant.class);
                intent.putExtra("idOrder",_id);
                context.startActivity(intent);
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String _id = menurestaurant.get(position).getId();
                Intent intent = new Intent(context, detailOrdersRestaurant.class);
                intent.putExtra("idOrder",_id);
                context.startActivity(intent);
            }
        });

        mapuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitude = menurestaurant.get(position).getLatitude();
                String longitude = menurestaurant.get(position).getLongitude();
                Intent intentmap = new Intent(context, MapsActivity.class);
                intentmap.putExtra("_SHOW_LATLONG","true");
                intentmap.putExtra("latitude",latitude);
                intentmap.putExtra("longitude",longitude);
                context.startActivity(intentmap);
            }
        });

        updatesendorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyCallback();
                String _id = menurestaurant.get(position).getId();
                String url = restApi.server+"services/api/update-send-order";
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

        updateorderdelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyCallback();
                String _id = menurestaurant.get(position).getId();
                String url = restApi.server+"services/api/update-delivered-order";
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

                Intent intent = new Intent(context, allOrdersForRestaurants.class);
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
