package com.devbase.Flashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import yuku.ambilwarna.AmbilWarnaDialog;

public class ScreenColor extends AppCompatActivity {

    ConstraintLayout colorPickerLayout;
    int mDefaultColor;
    Button colorPickerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_color);

        //Click sound
        MediaPlayer clickSound = MediaPlayer.create(this,R.raw.flash_click_2);


        //Remove status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        colorPickerLayout = (ConstraintLayout) findViewById(R.id.colorPickerLayout);
        mDefaultColor = ContextCompat.getColor(ScreenColor.this, R.color.design_default_color_on_primary);
        colorPickerButton = (Button) findViewById(R.id.colorPickerButton);
        colorPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickSound.start();
                openColorPicker();
            }

        });
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(ScreenColor.this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                colorPickerLayout.setBackgroundColor(mDefaultColor);


            }
        });

        colorPicker.show();
    }

}