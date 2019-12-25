package com.example.hw_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class EndActivity extends AppCompatActivity {

    public static final String GAME_PREFS = "ArithmeticFile";
    public static final String KEY_NOT_FIRST_GAME = "KEY_NOT_FIRST_GAME";
    public static final String KEY_NEW_NUMBER_OF_LANES = "KEY_NEW_NUMBER_OF_LANES";
    public static ArrayList<Player> allPlayers= new ArrayList<Player>();
    Gson gson = new Gson();
    String curName,curScore;
    TextView END_score;
    Button BTN_END_start_new_game;
    Button BTN_END_goto_menu;
    int numberOfLanes, finalScore;
    boolean notFirstGame = true;
    ArrayList<String> highScores = new ArrayList<String>();
    String finalScoreString;
    private SharedPreferences gamePrefs;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_end);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        finalScore = extras.getInt(MainActivity.KEY_SCORE);
        numberOfLanes = extras.getInt(StartActivity.KEY_NUM_OF_LANES);
        END_score = findViewById(R.id.END_score);
        gamePrefs=getSharedPreferences(GAME_PREFS, 0);
        finalScoreString = finalScore + "";
        END_score.setText("final Score: "+ finalScore);
        Player player = new Player(gamePrefs.getString(Fragment2.KEY_NAME,"unknown user"),finalScoreString,null);
        saveScoreToSP(player);
        BTN_END_start_new_game = findViewById(R.id.BTN_END_start_new_game);
        BTN_END_goto_menu = findViewById(R.id.BTN_END_goto_menu);
        BTN_END_start_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
                setAllNames(allPlayers);
                setAllScores(allPlayers);
            }
        });

        BTN_END_goto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartActivity();
                setAllNames(allPlayers);
                setAllScores(allPlayers);
            }
        });

    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        //extras.putBoolean(KEY_NOT_FIRST_GAME, notFirstGame);
        extras.putInt(KEY_NEW_NUMBER_OF_LANES, numberOfLanes);
        //intent.putExtra(StartActivity.KEY_NUM_OF_LANES, numberOfLanes);
        startActivity(intent);
        finish();
    }

    public void openStartActivity(){
       Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveScoreToSP(Player p){
        SharedPreferences.Editor editor = getSharedPreferences(GAME_PREFS, MODE_PRIVATE).edit();
        String recentData = gamePrefs.getString(Fragment2.KEY_SCORE, null);
        recentData = recentData +"\n"+ p.getScore() ;
        editor.putString(Fragment2.KEY_SCORE, recentData);
        editor.commit();
    }

    public void setAllScores(ArrayList<Player> allP) {
        String allScores = gamePrefs.getString(Fragment2.KEY_SCORE, null);
        String[] allScoresArr = allScores.split("\n");
        int numberOfScores = allScoresArr.length;
        int SPnumOfLines = gamePrefs.getInt("KEY_NUM_OF_LINES",0);
        curScore = allScoresArr[numberOfScores-1];
        Player curPlayer = new Player();
        curPlayer.setName(curName);
        curPlayer.setScore(curScore);
        if (allP.size()>=10) {
            if (Integer.parseInt(curScore) > Integer.parseInt(allP.get(9).getScore()))
                allP.set(9,curPlayer);
        }else
            allP.add(curPlayer);
        SPnumOfLines++;
        if (SPnumOfLines > 9)
            SPnumOfLines = 9;
        if (allP != null) {
            String allPlayersJson = gson.toJson(allP);
            gamePrefs.edit().putString("JSON_PLAYERS", allPlayersJson);
            gamePrefs.edit().commit();
        }
    }



    public void setAllNames(ArrayList<Player> allP){
        String allNames = gamePrefs.getString(Fragment2.KEY_NAME, "unknown user");
        String[] allNamesArr = allNames.split("\n");
        int numberOfNames = allNamesArr.length;
        curName = allNamesArr[numberOfNames-1];
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
