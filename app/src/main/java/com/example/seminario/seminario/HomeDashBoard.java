package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class HomeDashBoard extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "HomeDashBoard";
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dash_board);

        session = new Session(this);
        if(!session.getauth()){
            Toast.makeText(this, "Volviendo Inicio de Sesion", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,signIn.class));
        }

        findViewById(R.id.createRestaurant).setOnClickListener(this);
        findViewById(R.id.myRestaurants).setOnClickListener(this);
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.allrestaurants).setOnClickListener(this);

        findViewById(R.id.uploadPhoto).setOnClickListener(this);

    }

    public void logout(){
        session = new Session(this);
        session.setauth(false);
        session.settoken("");
        Toast.makeText(HomeDashBoard.this, "Cerrando Session", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,signIn.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createRestaurant:
                Toast.makeText(HomeDashBoard.this, "Formulario crear Restaurante", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,createRestaurant.class));
                break;
            case R.id.myRestaurants:
                Toast.makeText(HomeDashBoard.this, "Mis Restaurantes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,allMyRestaurants.class));
                break;
            case R.id.allrestaurants:
                Toast.makeText(HomeDashBoard.this, "Todos los restaurantes", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,allRestaurants.class));
                break;
            case R.id.logout:
                Toast.makeText(HomeDashBoard.this, "Cerrando Sesion", Toast.LENGTH_SHORT).show();
                logout();
                break;
            case R.id.uploadPhoto:
                Toast.makeText(HomeDashBoard.this, "subir foto", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,viewMyRestaurant.class));
                break;
        }
    }
}
