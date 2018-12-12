package com.example.seminario.seminario;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class uploadLogo extends AppCompatActivity implements View.OnClickListener,SingleUploadBroadcastReceiver.Delegate {
    private String TAG = "uploadLogo";
    private final SingleUploadBroadcastReceiver uploadReceiver = new SingleUploadBroadcastReceiver();

    //Button savelogo,searchLogo;
    ImageView imageView;
    private static final int STORAGE_PERMISSION_CODE= 2342;
    private static final int PICK_IMAGE_RESQUEST = 22;
    private Session session;
    private Uri filePath;
    private Bitmap bitmap;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new Session(this);
        token = session.gettoken();
        requestStoragePermission();
        setContentView(R.layout.activity_upload_logo);
        imageView = findViewById(R.id.imageViewPhoto);
        findViewById(R.id.searchLogo).setOnClickListener(this);
        findViewById(R.id.savelogo).setOnClickListener(this);
    }

    private void requestStoragePermission(){
        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(uploadLogo.this, "tiene permisos", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(uploadLogo.this, "NO tiene permisos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMAGE_RESQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_RESQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            filePath = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            }catch (IOException e){
                Log.d(TAG, "error " + e);
            }
        }
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
     /*private void uploadImage(){
        String name = UUID.randomUUID().toString();
        String path = getPath(filePath);

        try{
            String uploadid = UUID.randomUUID().toString();
            new MultipartUploadRequest(this,uploadid,restApi.server+"services/api/fileUpload")
                    .addFileToUpload(path,"image")
                    .addParameter("name",name)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

        }catch (Exception e){
            Log.e(TAG, e.getMessage(), e);
        }
    }*/

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
                String thirdStep = "'fileLogo':'"+fileName+"',";//no creo con {}
                Intent intent = new Intent(this, uploadPhoto.class);
                intent.putExtra("firstStep", firstStep);
                intent.putExtra("secondStep", secondStep);
                intent.putExtra("thirdStep", thirdStep);
                startActivity(intent);
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.searchLogo:
                Toast.makeText(uploadLogo.this, "Buscar Logo", Toast.LENGTH_SHORT).show();
                showFileChooser();
                break;
            case R.id.savelogo:
                uploadImage(uploadLogo.this);
                Toast.makeText(uploadLogo.this, "Guardar Logo", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
