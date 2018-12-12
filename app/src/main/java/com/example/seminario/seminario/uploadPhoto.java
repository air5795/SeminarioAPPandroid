package com.example.seminario.seminario;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class uploadPhoto extends AppCompatActivity implements View.OnClickListener,SingleUploadBroadcastReceiver.Delegate {
    private String TAG = "uploadPhoto";
    private Uri filePath;
    private Session session;
    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();
    inteResults mResultCallback = null;
    callRestApi callrestapi;
    ImageView imageView;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_photo);
        session = new Session(this);
        token = session.gettoken();
        imageView = findViewById(R.id.imageViewPhoto);
        findViewById(R.id.openCamera).setOnClickListener(this);
        findViewById(R.id.savephoto).setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        filePath = getImageUri(this,bitmap);
        //String pathPhoto = getPath(uphoto);
        //String pp = pathPhoto;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private String getPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":")+1);
        cursor.close();

        cursor=getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,MediaStore.Images.Media._ID+"=?",new String[]{document_id},null
        );
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    @Override
    protected void onResume() {
        super.onResume();
        uploadReceiver.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        uploadReceiver.unregister(this);
    }

    public void uploadImage(final Context context) {
        try {
            String uploadid = UUID.randomUUID().toString();
            String path = getPath(filePath);
            uploadReceiver.setDelegate(this);
            uploadReceiver.setUploadID(uploadid);

            new MultipartUploadRequest(this,uploadid,restApi.server+"services/api/fileUpload")
                    .addFileToUpload(path,"image")
                    .addHeader("token",token)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

        } catch (Exception exc) {
            Log.e(TAG, exc.getMessage(), exc);
        }
    }

    @Override
    public void onProgress(int progress) {
        //your implementation
    }

    @Override
    public void onProgress(long uploadedBytes, long totalBytes) {
        //your implementation
    }

    @Override
    public void onError(Exception exception) {
        //your implementation
    }

    @Override
    public void onCompleted(int serverResponseCode, byte[] serverResponseBody) {
        try {
            JSONObject response = new JSONObject(new String(serverResponseBody, "UTF-8"));
            String fileName = response.getString("fileName");

            if (getIntent().getExtras() != null) {
                String firstStep = getIntent().getStringExtra("firstStep");
                String secondStep = getIntent().getStringExtra("secondStep");
                String thirdStep = getIntent().getStringExtra("thirdStep");
                String fourthStep = "'filePhoto':'"+fileName+"'}";
                String finalParameters = firstStep+""+secondStep+""+thirdStep+""+fourthStep;
                saveRestaurant(finalParameters);
                /*Intent intent = new Intent(this, uploadPhoto.class);
                intent.putExtra("firstStep", firstStep);
                intent.putExtra("secondStep", secondStep);
                intent.putExtra("thirdStep", thirdStep);
                startActivity(intent);*/
            }
        }catch (JSONException e){

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCancelled() {
        //your implementation
    }

    public void saveRestaurant(String parameters){
        initVolleyCallback();
        String url = restApi.server+"services/api/save-restaurant";
        callrestapi = new callRestApi(mResultCallback,this);
        JSONObject sendObj = null;
        try {
            sendObj = new JSONObject(parameters);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callrestapi.postDataVolley("POSTCALL", url, sendObj);
    }

    public void initVolleyCallback(){
        callrestapi = new callRestApi(mResultCallback,this);
        mResultCallback = new inteResults() {
            @Override
            public void notifySuccess(String requestType,JSONObject response) {
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + response);
                Toast.makeText(uploadPhoto.this, "Volviendo a principal", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(uploadPhoto.this,HomeDashBoard.class));

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
            case R.id.openCamera:
                Toast.makeText(uploadPhoto.this, "Abrir camara", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
                break;
            case R.id.savephoto:
                uploadImage(uploadPhoto.this);
                Toast.makeText(uploadPhoto.this, "Guardar Logo", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
