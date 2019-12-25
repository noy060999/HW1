package com.example.hw_1;
//Noy Danenberg 207897893
//Shelly Krihali 209677863
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
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

    public static final String KEY_SCORE = "KEY_SCORE";
    private Button main_BUTTON_go_right, main_BUTTON_go_left, main_IMAGEBUTTON_pause;
    private TextView main_VIEW_score;
    private ImageView[] players, lives;
    private ImageView[][] obstacles;
    private int positionPlayer = 1, positionObstacleRow = -1, positionObstacleCol = 0;
    private int positionPrizeRow = -1, positionPrizeCol;
    private int time = 400;
    private int score = 0;
    private int screenH,screenW,objectH,objectW;
    boolean isGame = true;
    private Random rand = new Random();
    private int numberOfLanes, numberOfRows = 5,numberOfLives = 3, numberOfGames = 0;
    boolean useAccelerator = false, isFirstGame = true;
    MediaPlayer ouchSound,gameSound,biteSound;
    private SensorManager sensorManager;
    private Sensor mSensor;
    double ax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            //get extras from start activity
            if (extras != null) {
                numberOfLanes = extras.getInt(StartActivity.KEY_NUM_OF_LANES);
                useAccelerator = extras.getBoolean(StartActivity.KEY_TILT);
            }
            if (extras == null) {
                //Intent fromEndIntent = getIntent();
                Bundle extras2 = intent.getExtras();
                if (extras2 != null) {
                    numberOfLanes = extras2.getInt(EndActivity.KEY_NEW_NUMBER_OF_LANES);
                }
            }

        if (numberOfLanes != 3)
            numberOfLanes = 5;

        main_BUTTON_go_right = findViewById(R.id.main_BUTTON_go_right);
        main_BUTTON_go_left = findViewById(R.id.main_BUTTON_go_left);

        if (useAccelerator == true){
            main_BUTTON_go_left.setVisibility(View.INVISIBLE);
            main_BUTTON_go_right.setVisibility(View.INVISIBLE);
            sensorManager=(SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(sensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        main_VIEW_score = findViewById(R.id.main_VIEW_score);
        main_IMAGEBUTTON_pause = findViewById(R.id.main_IMAGEBUTTON_pause);

        ouchSound = MediaPlayer.create(getApplicationContext(),R.raw.ouch_sound);
        gameSound = MediaPlayer.create(getApplicationContext(),R.raw.game_sound);
        biteSound = MediaPlayer.create(getApplicationContext(),R.raw.bite);
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
                main_BUTTON_go_left.setClickable(false);
                main_BUTTON_go_right.setClickable(false);
                openPopupWindow(v);
            }
        });

        lives = new ImageView[]{
                findViewById(R.id.main_IMAGE_LIVE),
                findViewById(R.id.main_IMAGE_LIVE2),
                findViewById(R.id.main_IMAGE_LIVE3)
        };
        if (numberOfLanes == 3) {
            players = new ImageView[]{
                    findViewById(R.id.main_IMAGE_stitch),
                    findViewById(R.id.main_IMAGE_stitch2),
                    findViewById(R.id.main_IMAGE_stitch3)
            };

            obstacles = new ImageView[][]{
                    {findViewById(R.id.image_BADGUY00), findViewById(R.id.image_BADGUY01), findViewById(R.id.image_BADGUY02)},
                    {findViewById(R.id.image_BADGUY10), findViewById(R.id.image_BADGUY11), findViewById(R.id.image_BADGUY12)},
                    {findViewById(R.id.image_BADGUY20), findViewById(R.id.image_BADGUY21), findViewById(R.id.image_BADGUY22)},
                    {findViewById(R.id.image_BADGUY30), findViewById(R.id.image_BADGUY31), findViewById(R.id.image_BADGUY32)}
            };
        }

        if (numberOfLanes == 5){
            players = new ImageView[]{
                    findViewById(R.id.main_IMAGE_stitch),
                    findViewById(R.id.main_IMAGE_stitch2),
                    findViewById(R.id.main_IMAGE_stitch3),
                    findViewById(R.id.main_IMAGE_stitch4),
                    findViewById(R.id.main_IMAGE_stitch5)
            };

            obstacles = new ImageView[][]{
                    {findViewById(R.id.image_BADGUY00), findViewById(R.id.image_BADGUY01), findViewById(R.id.image_BADGUY02),findViewById(R.id.image_BADGUY03),findViewById(R.id.image_BADGUY04)},
                    {findViewById(R.id.image_BADGUY10), findViewById(R.id.image_BADGUY11), findViewById(R.id.image_BADGUY12),findViewById(R.id.image_BADGUY13),findViewById(R.id.image_BADGUY14)},
                    {findViewById(R.id.image_BADGUY20), findViewById(R.id.image_BADGUY21), findViewById(R.id.image_BADGUY22),findViewById(R.id.image_BADGUY23),findViewById(R.id.image_BADGUY24)},
                    {findViewById(R.id.image_BADGUY30), findViewById(R.id.image_BADGUY31), findViewById(R.id.image_BADGUY32),findViewById(R.id.image_BADGUY33),findViewById(R.id.image_BADGUY34)},
            };
        }

        setDimensions();
        makeObstaclesInvisible();
        startPlayer();
        startLives();
        loopFunc();
    }

    // functions :
    private void setDimensions() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenH = displayMetrics.heightPixels - main_BUTTON_go_left.getLayoutParams().height - lives[0].getLayoutParams().height;
        screenW = displayMetrics.widthPixels;
        objectH = screenH/numberOfRows;
        objectW = screenW/numberOfLanes;
        for (int i=0; i<obstacles.length; i++){
            for (int j=0; j<obstacles[0].length; j++){
                obstacles[i][j].requestLayout();
                obstacles[i][j].getLayoutParams().height = objectH;
                obstacles[i][j].getLayoutParams().width = objectW;
            }
        }
        for (int k=0; k<players.length; k++){
            players[k].requestLayout();
            players[k].getLayoutParams().height = objectH;
            players[k].getLayoutParams().width = objectW;
        }
    }
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
        main_VIEW_score.setText("score "+ score);
        if (positionObstacleRow == -1) {
            positionObstacleCol = rand.nextInt(numberOfLanes);
            positionPrizeCol = rand.nextInt(numberOfLanes);
            while (positionPrizeCol == positionObstacleCol){
                positionPrizeCol = rand.nextInt(numberOfLanes);
            }
        }

        positionPrizeRow++;
        positionObstacleRow++;

        if (positionObstacleRow == obstacles.length ) {
            if (positionPlayer == positionObstacleCol) {
                MySignal.vibrate(this, 300);
                ouchSound.start();
                checkLost();
            } else {
                obstacles[positionObstacleRow - 1][positionObstacleCol].setVisibility(View.INVISIBLE);
                positionObstacleRow = 0;
                positionObstacleCol = rand.nextInt((numberOfLanes));
            }
        }
        if (positionPrizeRow == obstacles.length) {
            if (positionPlayer == positionPrizeCol) {
                score += 50;
                biteSound.start();
                main_VIEW_score.setText("score "+ score);
                obstacles[positionPrizeRow - 1][positionPrizeCol].setVisibility(View.INVISIBLE);
                positionPrizeRow = 0;
                positionPrizeCol = rand.nextInt(numberOfLanes);
            }
            else {
                obstacles[positionPrizeRow - 1][positionPrizeCol].setVisibility(View.INVISIBLE);
                positionPrizeRow = 0;
                positionPrizeCol = rand.nextInt(numberOfLanes);
                while (positionPrizeCol == positionObstacleCol)
                    positionPrizeCol = rand.nextInt(numberOfLanes);
            }
        }

        for (int i = 0; i < obstacles.length; i++) {
            if (positionObstacleRow == i) {
                obstacles[i][positionPrizeCol].setImageResource(R.drawable.joomba);
                obstacles[i][positionObstacleCol].setVisibility(View.VISIBLE);
            }

            else {
                obstacles[i][positionPrizeCol].setImageResource(R.drawable.joomba);
                obstacles[i][positionObstacleCol].setVisibility(View.INVISIBLE);
            }
        }

        for (int i = 0; i < obstacles.length; i++){
            if (positionPrizeRow == i) {
                obstacles[positionPrizeRow][positionPrizeCol].setImageResource(R.drawable.cookie);
                obstacles[positionObstacleRow][positionObstacleCol].setImageResource(R.drawable.joomba);
                obstacles[i][positionPrizeCol].setVisibility(View.VISIBLE);
            }
            else {
                obstacles[i][positionPrizeCol].setImageResource(R.drawable.joomba);
                obstacles[i][positionPrizeCol].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void loopFunc(){
        final Handler handler = new Handler();
        Runnable runTimer = new Runnable() {
            public void run(){
                if(isGame) {
                    time -=2;
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
        if (numberOfLives > 1){
            lives[numberOfLives-1].setVisibility(View.INVISIBLE);
            numberOfLives--;
        }
        else {
            isGame = false;
            isFirstGame = false;
            openEndPage();
        }

        obstacles[positionObstacleRow-1][positionObstacleCol].setVisibility(View.INVISIBLE);
        positionObstacleRow = 0;
        positionObstacleCol =rand.nextInt(numberOfLanes);
    }

    private void openEndPage(){
        gameSound.stop();
        Intent intent = new Intent(this,EndActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(KEY_SCORE, score);
        extras.putInt(StartActivity.KEY_NUM_OF_LANES, numberOfLanes);
        intent.putExtras(extras);
        //intent.putExtra(KEY_SCORE,score+ "");
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
                main_BUTTON_go_left.setClickable(true);
                main_BUTTON_go_right.setClickable(true);
                loopFunc();
                popupWindow.dismiss();
            }
        });
    }

    public void openNewGame(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        isGame = false;
        gameSound.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGame == false) {
            View v = new View(this);
            openPopupWindow(v);
        }
        gameSound.start();
        isGame = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameSound.start();
        isGame = false;
    }

    SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            ax = event.values[0];
            if (numberOfLanes == 3) {
                if (ax > 3 && ax <= 10){ // 3< ax <=10
                    setPlayerPos(0);
                }
                if (ax >= -3 && ax <= 3) { // -3<= ax <= 3
                    setPlayerPos(1);
                }
                if (ax >= -10 && ax < -3) { // -10<= ax <-3
                    setPlayerPos(2);
                }
            }

            if (numberOfLanes == 5){
                if (ax > 7 && ax <= 10){ // 7< ax <=10
                    setPlayerPos(0);
                }
                if (ax > 2 && ax <= 7){ // 2< ax <=7
                    setPlayerPos(1);
                }
                if (ax > -2 && ax <= 2){ // -2< ax <=2
                    setPlayerPos(2);
                }
                if (ax > -7 && ax <= -2){ // -7< ax <=-2
                    setPlayerPos(3);
                }
                if (ax >= -10 && ax <= -7){ // -10<= ax <=-7
                    setPlayerPos(4);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    void setPlayerPos(int position){
        for (int i=0; i<players.length; i++){
            if (position == i)
                players[i].setVisibility(View.VISIBLE);
            else
                players[i].setVisibility(View.INVISIBLE);
        }
    }
}
