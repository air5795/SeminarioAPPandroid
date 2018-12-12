package com.example.seminario.seminario;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class createRestaurant extends AppCompatActivity implements View.OnClickListener{
    EditText name,nameOwner,lastNameOwner,nit,street,phone;
    Button firstsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_restaurant);

        name = findViewById(R.id.name);
        nameOwner = findViewById(R.id.nameOwner);
        lastNameOwner = findViewById(R.id.lastNameOwner);
        nit = findViewById(R.id.nit);
        street = findViewById(R.id.street);
        phone = findViewById(R.id.phone);

        findViewById(R.id.firststep).setOnClickListener(this);
    }

    public void firstStep(){
        /////////////////////////////////////////////////////////
        String inputName = name.getText().toString().trim();
        String inputNameOwner = nameOwner.getText().toString().trim();
        String inputLastNameOwner = lastNameOwner.getText().toString().trim();
        String inputNit = nit.getText().toString().trim();
        String inputStreet = street.getText().toString().trim();
        String inputPhone = phone.getText().toString().trim();

        if(inputName.isEmpty()){
            name.setError("El nombre del restaurante es requerido");
            name.requestFocus();
            return;
        }

        if(inputNameOwner.isEmpty()){
            nameOwner.setError("El nombre del propietario es requerido");
            nameOwner.requestFocus();
            return;
        }
        if(inputLastNameOwner.isEmpty()){
            lastNameOwner.setError("Los apellidos del propietario son requeridos");
            lastNameOwner.requestFocus();
            return;
        }
        if(inputNit.isEmpty()){
            nit.setError("El NIT es requerido");
            nit.requestFocus();
            return;
        }
        if(inputStreet.isEmpty()){
            street.setError("La Direcion es requerido");
            street.requestFocus();
            return;
        }
        if(inputPhone.isEmpty()){
            phone.setError("el numero de telefono es requerido");
            phone.requestFocus();
            return;
        }
        /////////////////////////////////////////////////////////
        //initVolleyCallback();
        //String url = restApi.server+"services/api/signin";
        //callrestapi = new callRestApi(mResultCallback,this);
        /*callrestapi.getDataVolley("GETCALL",url);*/
        //JSONObject sendObj = null;
        //try {
            //sendObj = new JSONObject("{'username':'"+inputName+"','password':'"+inputNameOwner+"'}");
            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("firstStep", "{'name':'"+inputName+"','inputNameOwner':'"+inputNameOwner+"','inputLastNameOwner':'"+inputLastNameOwner+"','inputNit':'"+inputNit+"','inputStreet':'"+inputStreet+"','inputPhone':'"+inputPhone+"',");// no estoy cerrando {}
            startActivity(intent);
        //} catch (JSONException e) {
            //e.printStackTrace();
        //}
        //callrestapi.postDataVolley("POSTCALL", url, sendObj);
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
