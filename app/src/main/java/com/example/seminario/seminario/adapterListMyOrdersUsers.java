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
import com.example.seminario.seminario.models.Product;
import com.example.seminario.seminario.models.menurestaurant;
import com.example.seminario.seminario.models.orderspeoples;
import com.example.seminario.seminario.models.ordersrestaurant;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class adapterListMyOrdersUsers extends BaseAdapter {

    private String TAG = "adapterlistmyorderrestrs";
    private static LayoutInflater inflater = null;
    Context context;
    List<ordersrestaurant> menurestaurant;

    inteResults mResultCallback = null;
    callRestApi callrestapi;

    static Cart shoppingcart;
    TextView title,text_location,text_menu1,totalmoney, ordercancel;
    Button viewshoppingcart,reset,sendOrder;
    Boolean exist;


    //Product[] products;
    public adapterListMyOrdersUsers(Context context, List<ordersrestaurant> datamenurestaurant){
        this.context =context;
        this.menurestaurant = datamenurestaurant;
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        shoppingcart = new Cart();


    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.listelementmyorders,null);
        title =  view.findViewById(R.id.text_name);
        text_location =  view.findViewById(R.id.text_location);
        text_menu1 =  view.findViewById(R.id.text_menu1);
        totalmoney =  view.findViewById(R.id.totalmoney);
        ordercancel =  view.findViewById(R.id.updateOrderCancel);
        ImageView img = view.findViewById(R.id.image);
        title.setText(this.menurestaurant.get(position).getNameRestaurant());
        text_location.setText(this.menurestaurant.get(position).getAddressRestaurant());
        text_menu1.setText("ESTADO: "+this.menurestaurant.get(position).getState());
        totalmoney.setText("TOTAL DE PAGO: "+this.menurestaurant.get(position).getTotalOrder() +" Bs");
        String urlImage = this.menurestaurant.get(position).getPhoto();

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

        ordercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initVolleyCallback();
                String _id = menurestaurant.get(position).getId();
                String url = restApi.server+"services/api/update-cancel-order";
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

                Intent intent = new Intent(context, myOrdersRestaurants.class);
                //intent.putExtra("idRestaurant",viewMyRestaurant._id);
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
