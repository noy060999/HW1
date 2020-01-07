package com.example.hw_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment2 extends Fragment {
   // public static ArrayList<Player> allPlayersSorted= new ArrayList<Player>();
    //public static ArrayList<Player> allPlayers= new ArrayList<Player>();
    public static final String KEY_NAME = "KEY_NAME35";
    public static final String KEY_SCORE = "KEY_SCORE35";
    public static final ArrayList<Button> pins = new ArrayList<>();
    public static int numberOfLines = 0;
    int i;
    SharedPreferences gamePrefs;
    String curName;
    ArrayList<TextView> namesTV = new ArrayList<TextView>();
    ArrayList<TextView> scoresTV = new ArrayList<TextView>();
    ArrayList<Player> allPlayersGson;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2_table, container, false);
        
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name1));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name2));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name3));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name4));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name5));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name6));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name7));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name8));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name9));
        namesTV.add((TextView) view.findViewById(R.id.fragment2_TXT_name10));

        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score1));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score2));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score3));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score4));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score5));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score6));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score7));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score8));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score9));
        scoresTV.add((TextView) view.findViewById(R.id.fragment2_TXT_score10));

        pins.add((Button) view.findViewById(R.id.pin1));
        pins.add((Button) view.findViewById(R.id.pin2));
        pins.add((Button) view.findViewById(R.id.pin3));
        pins.add((Button) view.findViewById(R.id.pin4));
        pins.add((Button) view.findViewById(R.id.pin5));
        pins.add((Button) view.findViewById(R.id.pin6));
        pins.add((Button) view.findViewById(R.id.pin7));
        pins.add((Button) view.findViewById(R.id.pin8));
        pins.add((Button) view.findViewById(R.id.pin9));
        pins.add((Button) view.findViewById(R.id.pin10));
        //setDimensions();

            pins.get(0).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //HighScoresActivity.showSpecificLocation(googleMap,0);
                }
            });

        String[] arrayOfNames;
        String[] arrayOfScoresString;
        ArrayList<Integer> arrayOfScoresInt = new ArrayList<Integer>();
        ArrayList<String> arrayListNames = new ArrayList<String>();
        gamePrefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);

        loadData();
        sortByScore();
        putValuesToTable();
        return view;
    }

    private void putValuesToTable() {
        if (allPlayersGson!=null) {
            for (int j = 0; j < allPlayersGson.size(); j++) {
                namesTV.get(j).setText(allPlayersGson.get(j).getName());
                scoresTV.get(j).setText(allPlayersGson.get(j).getScore());
            }
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

    public void setDimensions(){
        TableLayout table = getView().findViewById(R.id.content_fragment2_table);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        int tableH = displayMetrics.heightPixels;
        int tableW = displayMetrics.widthPixels - 55;
        int pinH = tableH/10;
        int pinW = displayMetrics.heightPixels - tableW;
        table.requestLayout();
        table.getLayoutParams().height = tableH;
        table.getLayoutParams().width = tableW;
        for (int i=0; i<pins.size(); i++){
            pins.get(i).requestLayout();
            pins.get(i).getLayoutParams().height = pinH;
            pins.get(i).getLayoutParams().width = pinW;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
