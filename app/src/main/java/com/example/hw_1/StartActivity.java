package com.example.hw_1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    public static final String KEY_NUM_OF_LANES = "KEY_NUM_OF_LANES";
    public static final String GAME_PREFS = "ArithmeticFile";
    public static final String KEY_TILT = "KEY_TILT";
    public static final String KEY_MUSIC = "KEY_MUSIC";
    int time = 600;
    int pos = 0;
    ImageView[] playerPics;
    MediaPlayer startSound;
    Button BTN_settings, BTN_highScores, BTN_setName, BTN_start_game;
    EditText start_EDT_name;
    int lanesNumber;
    int state = 0;
    boolean tiltIsChecked = false;
    String playerName;
    boolean isClicked = false;
    Player player = new Player(null,null,0,0);
    SharedPreferences prefs;
    private int screenH,screenW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_start);
        prefs = getSharedPreferences(GAME_PREFS,0);

        unmute();
        BTN_start_game = findViewById(R.id.BTN_start_game);
        startSound = MediaPlayer.create(this.getApplicationContext(),R.raw.background_music);
        startSound.start();
        start_EDT_name = findViewById(R.id.start_EDT_name);
        BTN_settings = findViewById(R.id.BTN_settings);
        BTN_highScores = findViewById(R.id.BTN_highScores);
        BTN_highScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScoresActivity();
            }
        });
        playerPics = new ImageView[] {
                findViewById(R.id.start_STITCH1),findViewById(R.id.start_STITCH2),findViewById(R.id.start_STITCH3)};
        showPlayerFirstTime();

        BTN_start_game.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                playerName = start_EDT_name.getText() + "";
                if (playerName == "" || playerName == null || playerName == " ")
                    playerName = "unknown user";
                Toast.makeText(getBaseContext(), "name saved!", Toast.LENGTH_SHORT).show();
                player.setName(playerName);
                saveNameToSP(player);
                openNewActivity();
            }
        });
        BTN_settings.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                openPopupWindowSettings(v);
            }
        });
        setDimensions();
        startLoopFunc();
    }

    // functions :
    private void setDimensions() {
        ImageView start_LOGO = findViewById(R.id.start_LOGO);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenH = displayMetrics.heightPixels - playerPics[0].getHeight() - start_LOGO.getHeight();
        screenW = displayMetrics.widthPixels;
        int buttonH = (screenH-800) / 3;
        int textH = buttonH;

        BTN_start_game.requestLayout();
        BTN_start_game.getLayoutParams().height = buttonH;
        BTN_settings.requestLayout();
        BTN_settings.getLayoutParams().height = buttonH;
        BTN_highScores.requestLayout();
        BTN_highScores.getLayoutParams().height = buttonH;
        TextView start_TXT_entername = findViewById(R.id.start_TXT_entername);
        start_TXT_entername.requestLayout();
        start_TXT_entername.getLayoutParams().height = textH;
    }

    private void openNewActivity(){
        Intent intent = new Intent (this, MainActivity.class);
        Bundle extras = new Bundle();
        extras.putInt(KEY_NUM_OF_LANES,lanesNumber);
        extras.putBoolean(KEY_TILT,tiltIsChecked);
        //intent.putExtra(KEY_NUM_OF_LANES,lanesNumber);
        intent.putExtras(extras);
        startSound.stop();
        startActivity(intent);
        finish();
    }

    private void openScoresActivity(){
        Intent intent = new Intent(this, HighScoresActivity.class);
        //intent.putExtra(KEY_MUSIC, startSound);
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
        final Switch settings_SWITCH_sound = popupView.findViewById(R.id.settings_SWITCH_sound);
        final Button settings_BTN_ok = popupView.findViewById(R.id.settings_BTN_ok);
        final CheckBox settings_CHECKBOX_tilt = popupView.findViewById(R.id.settings_CHECKBOX_tilt);
        settings_CHECKBOX_tilt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (settings_CHECKBOX_tilt.isChecked()) {
                    tiltIsChecked = true;
                    Toast.makeText(getBaseContext(), "Accelerator on", Toast.LENGTH_SHORT).show();
                } else {
                    tiltIsChecked = false;
                    Toast.makeText(getBaseContext(), "Accelerator off", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Spinner settings_SPINNER_lanes = popupView.findViewById(R.id.settings_SPINNER_lanes);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.lanes,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settings_SPINNER_lanes.setAdapter(adapter);
        settings_SPINNER_lanes.setOnItemSelectedListener(this);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = false; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        if (state == 1)
            settings_SWITCH_sound.setChecked(true);
        else
            settings_SWITCH_sound.setChecked(false);
        settings_SWITCH_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    settings_SWITCH_sound.setChecked(true);
                    Toast.makeText(getBaseContext(), "sound off", Toast.LENGTH_SHORT).show();
                    state = 1;
                    mute();
                }
                else {
                    settings_SWITCH_sound.setChecked(false);
                    Toast.makeText(getBaseContext(), "sound on", Toast.LENGTH_SHORT).show();
                    state = 0;
                    unmute();
                }
            }
        });

        settings_BTN_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void saveNameToSP(Player p){
        SharedPreferences.Editor editor = prefs.edit();
        String recentNames = prefs.getString(Fragment2.KEY_NAME, "unknown user");
        recentNames = recentNames +"\n"+ p.getName() ;
        editor.putString(Fragment2.KEY_NAME, recentNames);
        editor.commit();
    }

    private void mute() {
        //mute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_MUTE,0);
    }

    private void unmute() {
        //unmute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_UNMUTE,0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        startSound.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (state == 1)
            mute();
        else
            unmute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        lanesNumber = Integer.parseInt(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        lanesNumber = 5;
    }
}
