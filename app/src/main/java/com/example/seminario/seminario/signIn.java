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
import org.json.JSONException;
import org.json.JSONObject;

public class signIn extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "signIn";
    private Session session;
    //Toolbar toolbar;
    EditText email,password;
    CheckBox checkBox;
    ImageButton signin;
    Button signup;

    inteResults mResultCallback = null;
    callRestApi callrestapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        //toolbar = findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        /////////
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        checkBox = findViewById(R.id.checkbox);
        findViewById(R.id.signin).setOnClickListener(this);
        findViewById(R.id.signup).setOnClickListener(this);
    }
    public void signIn(){
        /////////////////////////////////////////////////////////
        String inputEmail = email.getText().toString().trim();
        String inputPassword = password.getText().toString().trim();

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
        /////////////////////////////////////////////////////////
        initVolleyCallback();
        String url = restApi.server+"services/api/signin";
        callrestapi = new callRestApi(mResultCallback,this);
        /*callrestapi.getDataVolley("GETCALL",url);*/
        JSONObject sendObj = null;
        try {
            sendObj = new JSONObject("{'username':'"+inputEmail+"','password':'"+inputPassword+"'}");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callrestapi.postDataVolley("POSTCALL", url, sendObj);
    }

    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        session = new Session(this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                try {
                    JSONObject data = response;
                    Boolean auth = data.getBoolean("success");
                    session.setauth(auth);
                    //Boolean a = auth;
                    String token = data.getString("token");
                    session.settoken(token);
                    Toast.makeText(signIn.this, "registro de usuarios", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(signIn.this,HomeDashBoard.class));
                    //String t =session.gettoken();
                    //String tt = t;
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
            case R.id.signin:
                signIn();
                break;
            case R.id.signup:
                Toast.makeText(signIn.this, "registro de usuarios", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,signUp.class));
                break;
        }
    }
}
