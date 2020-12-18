package com.devbase.Flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

public class settingsMenu extends AppCompatActivity {

    ImageButton backButton;
    ImageButton shareButton;
    ImageButton aboutButton;

    boolean Nmute = true ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_menu);

        MediaPlayer clicksound = MediaPlayer.create(this, R.raw.flash_click_2);

        //Remove status bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);



        backButton = findViewById(R.id.back1);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                backButton.setImageResource(R.drawable.back_torch1);
                finish();
                Animatoo.animateSlideLeft(settingsMenu.this);
            }
        });


        shareButton = findViewById(R.id.shareButton);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Torch Application");
                    String shareMessage ="https://play.google.com/store/apps/details?id="+BuildConfig.APPLICATION_ID+"\n\n";
                    intent.putExtra(Intent.EXTRA_TEXT,shareMessage);
                    startActivity(Intent.createChooser(intent,"Share by"));
                }catch (Exception e) {
                    Toast.makeText(settingsMenu.this,"Error Ocurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

        aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicksound.start();
                openAboutDialogue();
            }
        });
    }

    private void openAboutDialogue(){
        AboutDialogue aboutDialogue = new AboutDialogue();
        aboutDialogue.show(getSupportFragmentManager(),"About app");
    }

}