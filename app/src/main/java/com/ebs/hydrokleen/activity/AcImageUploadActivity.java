package com.ebs.hydrokleen.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.databinding.ActivityAcImageUploadBinding;
import com.ebs.hydrokleen.models.AcImage;
import com.ebs.hydrokleen.models.DeviceItem;
import com.ebs.hydrokleen.models.Serviceitem;
import com.ebs.hydrokleen.models.Team;
import com.ebs.hydrokleen.networkutils.RetrofitClient;
import com.ebs.hydrokleen.networkutils.StatusUpdateCallBack;
import com.ebs.hydrokleen.networkutils.StatusUpdateNetworkCall;
import com.ebs.hydrokleen.utils.GPSTracker;
import com.ebs.hydrokleen.utils.GlobalVariables;
import com.ebs.hydrokleen.utils.LoadingDialog;
import com.ebs.hydrokleen.utils.LocationForLatLng;
import com.ebs.hydrokleen.utils.NetworkCheck;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AcImageUploadActivity extends AppCompatActivity implements View.OnClickListener{

    public ActivityAcImageUploadBinding binding;
    private static final int IMG_REQUEST = 777;
    private Bitmap bitmap;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;
    private String teamid = " ";
    private Serviceitem currentServiceItem;
    public DeviceItem deviceItem = new DeviceItem();
    public String lat = "", lng = "", pictureImagePath = "";
    private LoadingDialog loadingDialog;
    private String finalImage="";
    private Location location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ac_image_upload);

        loadingDialog = new LoadingDialog(AcImageUploadActivity.this);
        getIntentData();

        GlobalVariables globalVariables = new GlobalVariables();
        teamid = globalVariables.getTeamID(AcImageUploadActivity.this);

        binding.selectImageBtn.setOnClickListener(this);
        binding.imageUploadBtn.setOnClickListener(this);
        binding.openCameraApp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.select_image_btn:
                selectImage();
                break;

            case R.id.open_camera_app:
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    File imageFile = null;

                    try {
                        imageFile = getImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(imageFile!=null){
                        Uri imageUri = FileProvider.getUriForFile(AcImageUploadActivity.this,
                                "com.ebs.hydrokleen.fileprovider", imageFile);

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }

                }
                break;

            case R.id.image_upload_btn:
                uploadImage();
                break;
        }

    }

    private void uploadImage(){

        final String serviceId = currentServiceItem.getServiceid();
        final String serviceItemId = currentServiceItem.getServiceitemid();
        final String clientaccode = currentServiceItem.getClientaccode();

        if(getLatLng()){
            Log.d("Image String", finalImage);
            Log.d("deviceid", lat);

            if(NetworkCheck.isNetworkAvailable(getApplicationContext())){

                binding.imageUploadBtn.setVisibility(View.INVISIBLE);
                loadingDialog.startLoadingDialog();

                new Thread(new Runnable() {
                    public void run() {
                        // a potentially time consuming task
                        StatusUpdateNetworkCall.uploadImage(lat, lng,serviceId, serviceItemId, clientaccode,finalImage,AcImageUploadActivity.this,  new StatusUpdateCallBack() {
                            @Override
                            public void OnResult(boolean code) {
                                if (code){
                                    loadingDialog.dismissDialog();
                                    Toast.makeText(getApplicationContext(), "Image uploaded Successfully", Toast.LENGTH_SHORT).show();
                                    binding.imageViewAc.setImageResource(R.drawable.ic_add_a_photo);
                                }
                                else {
                                    loadingDialog.dismissDialog();
                                    binding.imageUploadBtn.setVisibility(View.VISIBLE);
                                    Toast.makeText(AcImageUploadActivity.this, "Uploading Failed. Please try again!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                }).start();


            }else{
                Toast.makeText(AcImageUploadActivity.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                binding.imageViewAc.setImageBitmap(bitmap);
                binding.imageViewAc.setVisibility(View.VISIBLE);
                binding.imageUploadBtn.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {

            //Bitmap bitmapImageView = (Bitmap) data.getExtras().get("data");
            bitmap = BitmapFactory.decodeFile(pictureImagePath);
            if(bitmap!=null){
                int height = bitmap.getHeight();
                int width = bitmap.getWidth();
                bitmap = getResizedBitmap(bitmap, height/3, width/3);
                finalImage = imageToString();
                binding.imageViewAc.setImageBitmap(bitmap);
                binding.imageViewAc.setVisibility(View.VISIBLE);
                binding.imageUploadBtn.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(), "Something went wrong! Please Try again", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void getIntentData() {
        Intent intent = getIntent();
        currentServiceItem = intent.getParcelableExtra("service item");
        Log.d("TAG", "getIntentData: "+currentServiceItem.getServicedetails());
    }

    private boolean getLatLng(){
        GPSTracker gps = new GPSTracker(getApplicationContext());
        location = gps.getLocation();
        if(location!=null){
            lat = String.valueOf(location.getLatitude());
            lng = String.valueOf(location.getLongitude());
            return true;
        }else {
            lat = " ";
            lng = " ";
            return true;
        }
    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        pictureImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }


    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

    }

}
