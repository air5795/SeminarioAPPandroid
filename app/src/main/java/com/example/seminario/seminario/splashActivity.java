package com.example.seminario.seminario;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class splashActivity extends AppCompatActivity {
    private String TAG = "signIn";
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                session = new Session(splashActivity.this);
                if(session.getauth()){
                    Log.d(TAG, "auth" );
                    Toast.makeText(splashActivity.this, "Inicio de Sesion", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(splashActivity.this,HomeDashBoard.class));
                }else{
                    Log.d(TAG, "no auth" );
                    Toast.makeText(splashActivity.this, "Inicio de Sesion", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(splashActivity.this,signIn.class));
                }
            }
        },2500);
    }
}
