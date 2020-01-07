package com.example.hw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class HighScoresActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    ArrayList<Player> allPlayersGson;
    SharedPreferences gamePrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        gamePrefs = getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        loadData();
        sortByScore();
        Button btn_Back = findViewById(R.id.btn_Back);
        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToStartActivity();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.content_fragment1_map);
        mapFragment.getMapAsync(this);
    }

    void backToStartActivity(){
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(31.4117257,35.0818155),6.0f));
        for (int i=0; i<allPlayersGson.size(); i++){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(allPlayersGson.get(i).getLatitude(),allPlayersGson.get(i).getLongitude())))
                    .setTitle((i+1)+"."+allPlayersGson.get(i).getName());

        }
    }

    private void loadData(){
        Gson gson = new Gson();
        String json = gamePrefs.getString(EndActivity.KEY_JSON,null);
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        allPlayersGson = gson.fromJson(json,type);

        if (allPlayersGson == null)
            allPlayersGson = new ArrayList<>();
    }

    public void sortByScore(){
        if (allPlayersGson!=null)
            Collections.sort(allPlayersGson,Collections.<Player>reverseOrder());
    }
}
