package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class signUp extends AppCompatActivity implements View.OnClickListener {

    private String TAG = "signUp";
    //Toolbar toolbar;
    EditText email,password,name,fatherLastName,motherLastName,ci;
    //CheckBox checkBox;
    ImageButton signup;
    Button signin;

    inteResults mResultCallback = null;
    callRestApi callrestapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //toolbar = findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        fatherLastName = findViewById(R.id.fatherLastName);
        motherLastName = findViewById(R.id.motherLastName);
        ci = findViewById(R.id.ci);
        //checkBox = findViewById(R.id.checkbox);
        findViewById(R.id.signin).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);
    }

    public void signUp(){
        ////////////////////////////////////////////////////////////////////////////////
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();
        String inputName = name.getText().toString().trim();
        String inputFatherLastName = fatherLastName.getText().toString().trim();
        String inputMotherLastName = motherLastName.getText().toString().trim();
        String inputCi = ci.getText().toString().trim();

        if(inputName.isEmpty()){
            name.setError("El nombre es requerido");
            name.requestFocus();
            return;
        }

        if(inputFatherLastName.isEmpty()){
            fatherLastName.setError("El Apellido Paterno es requerido");
            fatherLastName.requestFocus();
            return;
        }

        if(inputMotherLastName.isEmpty()){
            motherLastName.setError("El Apellido Materno es requerido");
            motherLastName.requestFocus();
            return;
        }

        if(inputCi.isEmpty()){
            ci.setError("El C.I. es requerido");
            ci.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(inputEmail).matches()){
            email.setError("Por favor ingrese un email valido");
            email.requestFocus();
            return;
        }
        if(inputPassword.isEmpty()){
            password.setError("El password es obligatorio");
            password.requestFocus();
            return;
        }

        if(inputPassword.length()<6){
            password.setError("El password debe ser mayor a 6 caracteres");
            password.requestFocus();
            return;
        }
        ////////////////////////////////////////////////////////////////////////////////
        initVolleyCallback();
        String url = restApi.server+"services/api/signup";
        callrestapi = new callRestApi(mResultCallback,this);
        JSONObject sendObj = null;
        try {
            sendObj = new JSONObject("{'name':'"+inputName+"','fatherlastname':'"+inputFatherLastName+"','motherlastname':'"+inputMotherLastName+"','ci':'"+inputCi+"','email':'"+inputEmail+"','password':'"+inputPassword+"'}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callrestapi.postDataVolley("POSTCALL", url, sendObj);
    }

    public void initVolleyCallback(){
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                Toast.makeText(signUp.this, "Volviendo Inicio de Sesion", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(signUp.this,signIn.class));
            }

            @Override
            public void notifyError(String requestType,VolleyError error) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + "That didn't work!");
                Log.d(TAG, "Volley JSON post" + error.toString());
                parseVolleyError(error);
            }
        };
    }

    public void parseVolleyError(VolleyError error) {
        try {
            String responseBody = new String(error.networkResponse.data, "utf-8");
            JSONObject data = new JSONObject(responseBody);
            //JSONArray errors = data.getJSONArray("message");
            //JSONObject jsonMessage = errors.getJSONObject(0);
            String message = data.getString("message");
            Toast.makeText(signUp.this, message, Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            Log.d(TAG, "error " + e);
        } catch (UnsupportedEncodingException e) {
            Log.d(TAG, "error " + e);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                signUp();
                break;
            case R.id.signin:
                Toast.makeText(signUp.this, "Volviendo Inicio de Sesion", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,signIn.class));
                break;
        }
    }
}
