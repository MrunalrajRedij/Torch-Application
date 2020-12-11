package com.devbase.torchapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton imageButton;
    boolean flashLightState=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.torchButton);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                runFlashLight();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission required !", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {


            }
        }).check();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runFlashLight(){

     imageButton.setOnClickListener(new View.OnClickListener() {
         @SuppressLint("NewApi")
         @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
         @Override
         public void onClick(View v) {
            if (flashLightState == false)
            {
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                try {

                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID,true);
                    flashLightState = true;
                    imageButton.setImageResource(R.drawable.torch_on);
                }
                catch (CameraAccessException e)
                { }
            }
            else {
                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                try {

                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID,false);
                    flashLightState = false;
                    imageButton.setImageResource(R.drawable.torch_off);
                }
                catch (CameraAccessException e)
                { }
            }

         }
     });



    }
}