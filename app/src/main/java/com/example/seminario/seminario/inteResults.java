package com.example.seminario.seminario;
import com.android.volley.VolleyError;
import org.json.JSONObject;

public interface inteResults {
    void notifySuccess(String requestType,JSONObject response);
    void notifyError(String requestType,VolleyError error);
}
