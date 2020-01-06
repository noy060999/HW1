package com.example.hw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HighScoresActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

       Button BTN_back = findViewById(R.id.BTN_back);
        BTN_back.setOnClickListener(new View.OnClickListener() {
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
        for (int i=0; i<EndActivity.allPlayers.size(); i++){
            googleMap.addMarker(new MarkerOptions().position(new LatLng(EndActivity.allPlayers.get(i).getLatitude(),EndActivity.allPlayers.get(i).getLongitude())))
                    .setTitle((i+1)+"."+EndActivity.allPlayers.get(i).getName());

        }
    }

    /*public static void showSpecificLocation(GoogleMap googleMap, int pos){
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom
                (new LatLng(EndActivity.allPlayers.get(pos).getLatitude(),EndActivity.allPlayers.get(pos).getLongitude()),6.0f));

    }*/
}
