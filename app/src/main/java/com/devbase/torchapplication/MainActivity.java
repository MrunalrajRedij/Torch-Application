package com.devbase.torchapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ImageButton imageSwitchButton;

    boolean flashLightState=false;

    private ImageButton screenColorButton ;
    private ImageButton settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //switch button initialisation
        imageSwitchButton = findViewById(R.id.torchButton);

        //Click Sound
        MediaPlayer flashClickSound = MediaPlayer.create(this,R.raw.flash_click_1);



        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        settingButton = findViewById(R.id.settingButton);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                flashClickSound.start();
                openSettings();
            }
        });




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

        screenColorButton = findViewById(R.id.screenColorButton);
        screenColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashClickSound.start();
                openScreenColor();
            }
        });
    }


    private void openSettings(){
        Intent intent = new Intent(this, settingsMenu.class);
        startActivity(intent);
    }


    private void openScreenColor(){
        Intent intent = new Intent(this, ScreenColor.class);
        startActivity(intent);
    }



    //Flash on/off function
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runFlashLight() {
        imageSwitchButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {


                if ( flashLightState == false ) {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

                    try {

                        String cameraID = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraID, true);
                        flashLightState = true;
                        imageSwitchButton.setImageResource(R.drawable.power_on);
                    }
                    catch (CameraAccessException e)
                    {
                    }
                }
                else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraID = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraID, false);
                        flashLightState = false;
                        imageSwitchButton.setImageResource(R.drawable.power_off);
                    }
                    catch (CameraAccessException e)
                    {
                    }
                }
            }
        });
    }








}