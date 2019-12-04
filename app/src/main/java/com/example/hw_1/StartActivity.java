package com.example.hw_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Switch;

public class StartActivity extends AppCompatActivity {
    int time = 600;
    int pos = 0;
    ImageView[] playerPics;
    MediaPlayer mediaPlayer;
    Button BTN_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);
        unmute();
        mediaPlayer = MediaPlayer.create(this.getApplicationContext(),R.raw.background_music);
        mediaPlayer.start();
        BTN_settings = findViewById(R.id.BTN_settings);
        playerPics = new ImageView[] {
                findViewById(R.id.start_STITCH1),findViewById(R.id.start_STITCH2),findViewById(R.id.start_STITCH3)};
        Button BTN_start_game = findViewById(R.id.BTN_start_game);
        showPlayerFirstTime();

        BTN_start_game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openNewActivity();
            }
        });
        BTN_settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openPopupWindowSettings(v);
            }
        });
        startLoopFunc();
    }

    // functions :
    private void openNewActivity(){
        Intent intent = new Intent (this, MainActivity.class);
        mediaPlayer.stop();
        startActivity(intent);
    }

    private void showPlayerFirstTime(){
        for (int i=0; i<playerPics.length; i++){
            if (i == pos)
                playerPics[i].setVisibility(View.VISIBLE);
            else
                playerPics[i].setVisibility(View.INVISIBLE);
        }
    }

    private void showPlayer (){
        pos++;
        if (pos >= playerPics.length)
            pos = 0;

        for (int i=0; i<playerPics.length; i++){
            if (pos == i)
                playerPics[i].setVisibility(View.VISIBLE);
            else
                playerPics[i].setVisibility(View.INVISIBLE);
        }
    }

    private void startLoopFunc (){
        final Handler handler = new Handler();
        Runnable runTimer = new Runnable() {
            public void run(){
                startLoopFunc();
                showPlayer();
            }
        };
        handler.postDelayed(runTimer,time);
    }

    private void openPopupWindowSettings (View v){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_window_settings, null);
        final Switch SWITCH_sound = popupView.findViewById(R.id.SWITCH_sound);
        //SWITCH_sound.setTextOff("Sound on");
        //SWITCH_sound.setTextOn("Sound off");
        Button BTN_ok = popupView.findViewById(R.id.BTN_ok);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

        SWITCH_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    mute();
            }
        });

        BTN_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void mute() {
        //mute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
    }

    private void unmute() {
        //mute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE,0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }
}
