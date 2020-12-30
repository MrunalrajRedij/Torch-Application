package com.devbase.Flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MainActivity extends AppCompatActivity {

    private AdView mAdView;

    ImageButton imageSwitchButton, blinkButton;

    private static final int CAMERA_REQUEST = 123;
    boolean hasCameraflash = false;

    boolean flashLightState = false;

    private ImageButton screenColorButton, settingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        blinkButton = findViewById(R.id.blink_2);


        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        hasCameraflash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        blinkButton = findViewById(R.id.blink_2);

        //switch button initialisation
        imageSwitchButton = findViewById(R.id.torchButton);

        imageSwitchButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if (hasCameraflash) {
                    if (flashLightState == true) {
                        click_sound_1();
                        imageSwitchButton.setImageResource(R.drawable.power_off);
                        flashLightOff();
                        blinkButton.setImageResource(R.drawable.blink2);
                        flashLightState = false;
                    } else {
                        click_sound_1();
                        imageSwitchButton.setImageResource(R.drawable.power_on);
                        flashLightOn();
                        blinkButton.setImageResource(R.drawable.blink1);
                        flashLightState = true;
                    }

                } else {
                    Toast.makeText(MainActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();
                }
            }
        });


        blinkButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                    if (flashLightState == true) {
                        click_sound_1();
                        blinkButton.setImageResource(R.drawable.blink1);
                        blinkflash();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Press above button first", Toast.LENGTH_SHORT).show();
                    }
                }
        });

        //Click Sound
        final MediaPlayer ClickSound = MediaPlayer.create(this, R.raw.flash_click_2);


        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        screenColorButton = findViewById(R.id.screenColorButton);
        screenColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickSound.start();
                openScreenColor();

            }
        });

        settingButton = findViewById(R.id.setting);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_sound_2();
                startActivity(new Intent(MainActivity.this, settingsMenu.class));
                Animatoo.animateSlideRight(MainActivity.this);
            }
        });


    }

    private void openScreenColor() {
        Intent intent = new Intent(this, ScreenColor.class);
        startActivity(intent);
    }


    private void click_sound_1() {
        MediaPlayer clickSound1 = MediaPlayer.create(this, R.raw.flash_click_1);
        clickSound1.start();
    }

    private void click_sound_2() {
        MediaPlayer clickSound2 = MediaPlayer.create(this, R.raw.flash_click_2);
        clickSound2.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOff() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraID, false);
        } catch (CameraAccessException e) {
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLightOn() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraID, true);
        } catch (CameraAccessException e) {
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void blinkflash() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        String myString = "10101010";
        long blinkDelay = 50;
        for (int i = 0; i < myString.length(); i++) {

            if (myString.charAt(i) == '0') {
                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, true);
                    blinkButton.setImageResource(R.drawable.blink1);
                } catch (CameraAccessException e) { }
            }
            else {
                try {
                    String cameraID = cameraManager.getCameraIdList()[0];
                    cameraManager.setTorchMode(cameraID, false);
                    blinkButton.setImageResource(R.drawable.blink2);
                } catch (CameraAccessException e) { }
            }
            try {
            Thread.sleep(blinkDelay);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        try {
            String cameraID = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraID, false);
        }catch (CameraAccessException e){}
        finish();
        super.onBackPressed();
    }
}