package com.example.hw_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
    public static final String KEY_JSON = "KEY_JSON";
    public static ArrayList<Player> allPlayers= new ArrayList<Player>();
    ArrayList<Player> listGSON = new ArrayList<>();
    String curName,curScore;
    double lat,lon;
    private FusedLocationProviderClient mFusedLocationClient;
    private int PERMISSION_ID = 20;
    TextView END_score;
    Button BTN_END_start_new_game;
    Button BTN_END_goto_menu;
    int numberOfLanes, finalScore;
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
        Player player = new Player(gamePrefs.getString(Fragment2.KEY_NAME,"unknown user"),finalScoreString,0,0);
        saveScoreToSP(player);
        BTN_END_start_new_game = findViewById(R.id.BTN_END_start_new_game);
        BTN_END_goto_menu = findViewById(R.id.BTN_END_goto_menu);
        BTN_END_start_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
                setAllNames(allPlayers);
                setAllScores(allPlayers);
                getListGsonFromSP(allPlayers);
            }
        });

        BTN_END_goto_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStartActivity();
                getListGsonFromSP(allPlayers);
                setAllNames(allPlayers);
                setAllScores(allPlayers);
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
        setDimensions();

    }

    //functions:
    private void setDimensions() {
        ImageView logo = findViewById(R.id.logo);
        TextView END_game_over = findViewById(R.id.END_game_over);
        TextView END_score = findViewById(R.id.END_score);
        ImageView END_stitchSad = findViewById(R.id.END_stitchSad);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenH = displayMetrics.heightPixels - logo.getHeight() - END_game_over.getHeight() - END_stitchSad.getHeight();
        int screenW = displayMetrics.widthPixels;
        int buttonH = 175;
        int scoreH = buttonH;

        BTN_END_goto_menu.requestLayout();
        BTN_END_goto_menu.getLayoutParams().height = buttonH;
        BTN_END_start_new_game.requestLayout();
        BTN_END_start_new_game.getLayoutParams().height = buttonH;
        END_score.requestLayout();
        END_score.getLayoutParams().height = scoreH;
    }

    public void openMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(KEY_NEW_NUMBER_OF_LANES, numberOfLanes);
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

    public void getListGsonFromSP(ArrayList<Player> allP){
        SharedPreferences.Editor editor = getSharedPreferences(GAME_PREFS, MODE_PRIVATE).edit();
        String jsonString = gamePrefs.getString(KEY_JSON,null);
        Gson json = new Gson();
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        listGSON = json.fromJson(jsonString,type);
        /*for (int i=0; i<allP.size(); i++){
            listGSON.add(allP.get(i));
        }*/
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
        curPlayer.setLatitude(lat);
        curPlayer.setLongitude(lon);
        if (allP != null) {
            if (allP.size() >= 10) {
                if (Integer.parseInt(curScore) > Integer.parseInt(allP.get(9).getScore()))
                    allP.set(9, curPlayer);
            } else {
                allP.add(curPlayer);
            }
        }
        else {
            allP = new ArrayList<>();
            allP.add(curPlayer);
        }
        Gson myGson = new Gson();
        String jsonString = myGson.toJson(allP);
        gamePrefs.edit().putString(KEY_JSON,jsonString);
        gamePrefs.edit().commit();
        SPnumOfLines++;
        if (SPnumOfLines > 9)
            SPnumOfLines = 9;
        /*if (allP != null) {
            String allPlayersJson = gson.toJson(allP);
            gamePrefs.edit().putString("JSON_PLAYERS", allPlayersJson);
            gamePrefs.edit().commit();
        }*/
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

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();

                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    lat = location.getLatitude();
                                    lon = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            lat = mLastLocation.getLatitude();
            lon = mLastLocation.getLongitude();
        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
}
