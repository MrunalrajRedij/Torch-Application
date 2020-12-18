package com.devbase.Flashlight;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class AboutDialogue extends AppCompatDialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("About app");
        builder.setMessage("Free, fast and easy to use flashlight app for android.\n" +
                "App uses the built-in camera LED flash and provides the brightest light as possible.\n" +
                "If your device dosen't have a flash (or flash doesn't work) then you can use the screen color mode.\n"+
                "In screen color mode you have all colours to choose from.\n" +
                "You can also use strobe(blinking) flash mode.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }
}

