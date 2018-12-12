package com.example.seminario.seminario;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class createMenu extends AppCompatActivity implements View.OnClickListener{
    EditText name,price,type;
    String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_menu);

        name = findViewById(R.id.name);
        price = findViewById(R.id.price);
        type = findViewById(R.id.type);

        findViewById(R.id.firststep).setOnClickListener(this);

        if (getIntent().getExtras() != null) {
            _id = getIntent().getStringExtra("idRestaurant");
        }
    }

    public void firstStep(){
        /////////////////////////////////////////////////////////
        String inputName = name.getText().toString().trim();
        String inputPrice = price.getText().toString().trim();
        String inputType = type.getText().toString().trim();

        if(inputName.isEmpty()){
            name.setError("El nombre del Plato es requerido");
            name.requestFocus();
            return;
        }

        if(inputPrice.isEmpty()){
            price.setError("El precio es requerido");
            price.requestFocus();
            return;
        }
        if(inputType.isEmpty()){
            type.setError("El Tipo requeridos");
            type.requestFocus();
            return;
        }

        /////////////////////////////////////////////////////////

        Intent intent = new Intent(this, uploadPhotoFood.class);
        intent.putExtra("firstStep", "{'_id':'"+_id+"','name':'"+inputName+"','price':'"+inputPrice+"','type':'"+inputType+"',");// no estoy cerrando {}
        intent.putExtra("idRestaurant",_id);
        startActivity(intent);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.firststep:
                firstStep();
                break;
        }
    }
}
