package com.example.seminario.seminario;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class callRestApi {
    private String TAG = "callRestApi";
    private Session session;
    inteResults mResultCallback = null;
    Context mContext;
    String token;
    callRestApi(inteResults resultCallback, Context context){
        session = new Session(context);
        token = session.gettoken();
        mResultCallback = resultCallback;
        mContext = context;
    }


    public void postDataVolley(final String requestType, String url,JSONObject sendObj){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(url,sendObj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(mResultCallback != null)
                            mResultCallback.notifySuccess(requestType,response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(mResultCallback != null)
                            mResultCallback.notifyError(requestType,error);
                    }
                })
            {
                @Override
                public Map getHeaders() throws AuthFailureError {

                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("token",token );
                    return headers;
                }
            };

            queue.add(jsonObj);

        }catch(Exception e){

        }
    }

    public void getDataVolley(final String requestType, String url){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url,null,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            })
            {
                @Override
                public Map getHeaders() throws AuthFailureError {

                    HashMap headers = new HashMap();
                    headers.put("Content-Type", "application/json");
                    headers.put("token",token );
                    return headers;
                }
            };

            queue.add(jsonObj);

        }catch(Exception e){

        }
    }

    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            //JSONArray errors = data.getJSONArray("message");
            //JSONObject jsonMessage = errors.getJSONObject(0);
            String message = data.getString("message");
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Log.d(TAG, "error " + e);
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "error " + e);
        }
    }
}
