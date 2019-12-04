package com.example.hw_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    TextView END_score;
    Button BTN_END_start_new_game;
    Button BTN_END_goto_menu;
    MediaPlayer endSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_end);
        Intent intent = getIntent();
        String score = intent.getStringExtra(MainActivity.endScore);
        END_score = findViewById(R.id.END_score);
        END_score.setText("final Score: "+score);

        BTN_END_start_new_game = findViewById(R.id.BTN_END_start_new_game);
        BTN_END_goto_menu = findViewById(R.id.BTN_END_goto_menu);
        endSound = MediaPlayer.create(this.getApplicationContext(),R.raw.game_sound);
        endSound.start();
        BTN_END_start_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        BTN_END_goto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartActivity();
            }
        });

    }

    public void openMainActivity(){
        endSound.stop();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openStartActivity(){
        endSound.stop();
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        endSound.stop();
    }
}
