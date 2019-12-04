package com.example.hw_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String endScore = "com.example.hw_1.MainActivity.END_score";
    private Button main_BUTTON_go_right;
    private Button main_BUTTON_go_left;
    private Button main_IMAGEBUTTON_pause;
    private TextView main_VIEW_score;
    private ImageView[] players;
    private ImageView[] lives;
    private ImageView[][] obstacles;
    private int positionPlayer = 1;
    private int positionObstacleRow = -1;
    private int positionObstacleCol = 0;
    private int time = 400;
    private int score = 0;
    private int num_of_lives = 3;
    boolean isGame = true;
    private Random rand = new Random();
    MediaPlayer ouchSound;
    MediaPlayer gameSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_BUTTON_go_right = findViewById(R.id.main_BUTTON_go_right);
        main_BUTTON_go_left = findViewById(R.id.main_BUTTON_go_left);
        main_VIEW_score = findViewById(R.id.main_VIEW_score);
        main_IMAGEBUTTON_pause = findViewById(R.id.main_IMAGEBUTTON_pause);

        ouchSound = MediaPlayer.create(getApplicationContext(),R.raw.ouch_sound);

        gameSound = MediaPlayer.create(getApplicationContext(),R.raw.game_sound);
        gameSound.start();
        main_BUTTON_go_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveRight();
            }
        });

        main_BUTTON_go_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveLeft();
            }
        });

        main_IMAGEBUTTON_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGame = false;
                openPopupWindow(v);
            }
        });

        lives = new ImageView[]{
                findViewById(R.id.main_IMAGE_LIVE),
                findViewById(R.id.main_IMAGE_LIVE2),
                findViewById(R.id.main_IMAGE_LIVE3)
        };

        players = new ImageView[]{
                findViewById(R.id.main_IMAGE_stitch),
                findViewById(R.id.main_IMAGE_stitch2),
                findViewById(R.id.main_IMAGE_stitch3)
        };

        obstacles = new ImageView[][]{
                {findViewById(R.id.image_BADGUY00),findViewById(R.id.image_BADGUY01),findViewById(R.id.image_BADGUY02)},
                {findViewById(R.id.image_BADGUY10),findViewById(R.id.image_BADGUY11),findViewById(R.id.image_BADGUY12)},
                {findViewById(R.id.image_BADGUY20),findViewById(R.id.image_BADGUY21),findViewById(R.id.image_BADGUY22)},
                {findViewById(R.id.image_BADGUY30),findViewById(R.id.image_BADGUY31),findViewById(R.id.image_BADGUY32)}
        };
        makeObstaclesInvisible();
        startPlayer();
        startLives();
        loopFunc();
    }

    // functions :
    private void makeObstaclesInvisible (){
        for (int i=0; i<obstacles.length; i++){
            for (int j=0; j<obstacles[0].length; j++)
                obstacles[i][j].setVisibility(View.INVISIBLE);
        }
    }
    private void startLives (){
        for (int i=0; i<lives.length; i++){
            lives[i].setVisibility(View.VISIBLE);
        }
    }
    private void startPlayer(){
        for (int i=0; i<players.length; i++){
            if (i == positionPlayer)
                players[i].setVisibility(View.VISIBLE);
            else
                players[i].setVisibility(View.INVISIBLE);
        }
    }
    private void moveRight(){
        positionPlayer +=1;
        if (positionPlayer >= players.length)
            positionPlayer = 0;
        startPlayer();
    }
    private void moveLeft(){
        positionPlayer -=1;
        if (positionPlayer < 0)
            positionPlayer = players.length - 1;
        startPlayer();
    }
    private void randObstaclesPos() {
        score+=10;
        main_VIEW_score.setText("Your score "+ score);
        if (positionObstacleRow == -1)
            positionObstacleCol = rand.nextInt((3));

        positionObstacleRow++;
        if (positionObstacleRow == obstacles.length){
            if (positionPlayer == positionObstacleCol) {
                ouchSound.start();
                checkLost();
            }
            else
            {
                obstacles[positionObstacleRow-1][positionObstacleCol].setVisibility(View.INVISIBLE);
                positionObstacleRow = 0;
                positionObstacleCol = rand.nextInt((3));
            }
        }
            for (int i = 0; i < obstacles.length; i++) {
                    if (positionObstacleRow == i )
                        obstacles[i][positionObstacleCol].setVisibility(View.VISIBLE);
                    else
                        obstacles[i][positionObstacleCol].setVisibility(View.INVISIBLE);
            }
        }

    private void loopFunc(){
        final Handler handler = new Handler();
        Runnable runTimer = new Runnable() {
            public void run(){
                if(isGame) {
                    time -=3;
                    if (time < 200)
                        time = 200;
                    loopFunc();
                    randObstaclesPos();
                }
            }
        };
        handler.postDelayed(runTimer,time);
    }

    private void checkLost(){
        if (num_of_lives > 1){
            lives[num_of_lives-1].setVisibility(View.INVISIBLE);
            num_of_lives--;
        }
        else {
            isGame = false;
            openEndPage();
        }

        obstacles[positionObstacleRow-1][positionObstacleCol].setVisibility(View.INVISIBLE);
        positionObstacleRow = 0;
        positionObstacleCol =rand.nextInt(3);
    }

    private void openEndPage(){
        gameSound.stop();
        Intent intent = new Intent(this,EndActivity.class);
        intent.putExtra(endScore,score+ "");
        startActivity(intent);
        finish();
    }

    private void openPopupWindow (View v){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_window, null);
        Button BTN_POPUP_resume = popupView.findViewById(R.id.BTN_POPUP_resume);
        Button BTN_POPUP_newGame = popupView.findViewById(R.id.BTN_POPUP_newGame);

        BTN_POPUP_newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameSound.stop();
                openNewGame();
            }
        });

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        BTN_POPUP_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isGame = true;
                loopFunc();
                popupWindow.dismiss();
            }
        });
    }

    public void openNewGame(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
