package com.example.hw_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HighScoresActivity extends FragmentActivity {

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
    }

    void backToStartActivity(){
        Intent intent = new Intent(this,StartActivity.class);
        startActivity(intent);
        finish();
    }
}
