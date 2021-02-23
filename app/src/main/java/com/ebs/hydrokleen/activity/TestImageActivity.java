package com.ebs.hydrokleen.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.ebs.hydrokleen.R;
import com.ebs.hydrokleen.databinding.ActivityTestImageBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class TestImageActivity extends AppCompatActivity {

    public ActivityTestImageBinding binding;
    private String pictureImagePath = "";
    private static final int CAMERA_REQUEST = 1888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test_image);

        binding.imageCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //openBackCamera();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    File imageFile = null;

                    try {
                        imageFile = getImageFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if(imageFile!=null){
                        Uri imageUri = FileProvider.getUriForFile(TestImageActivity.this,
                                "com.ebs.hydrokleen.fileprovider", imageFile);

                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }

                }
            }
        });

    }

    public void displayImage(){

        Bitmap bitmap = BitmapFactory.decodeFile(pictureImagePath);
        binding.imageView.setImageBitmap(bitmap);

    }

    private File getImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        pictureImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            displayImage();
        }

    }
}
