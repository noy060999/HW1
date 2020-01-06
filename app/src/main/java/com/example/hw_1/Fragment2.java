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
    GoogleMap googleMap;
    SharedPreferences prefs;
    String curName;
    ArrayList<TextView> namesTV = new ArrayList<TextView>();
    ArrayList<TextView> scoresTV = new ArrayList<TextView>();
    ArrayList<Player> allPlayers = EndActivity.allPlayers;


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
        setDimensions();

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
        prefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        ArrayList<Player> allPlayersJson;
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        String jsonString = prefs.getString(EndActivity.KEY_JSON,null);
        Gson gson = new Gson();
        allPlayersJson = gson.fromJson(jsonString,type);
        if (allPlayersJson == null)
            allPlayersJson = new ArrayList<>();

        sortByScore(allPlayers);
        putValuesToTable(allPlayers);
        return view;
    }

    private void putValuesToTable(ArrayList<Player> allP) {
        if (allP!=null) {
            for (int j = 0; j < allP.size(); j++) {
                namesTV.get(j).setText(allP.get(j).getName());
                scoresTV.get(j).setText(allP.get(j).getScore());
            }
        }
    }


    public void getNameFromSP(){
        SharedPreferences prefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        String allNames = prefs.getString(KEY_NAME, "unknown user");
        String[] allNamesArr = allNames.split("\n");
        int numberOfNames = allNamesArr.length;
        curName = allNamesArr[numberOfNames-1];
    }

    public void setAllLocation(){

    }

    public void sortByScore(ArrayList<Player> allP){
        if (allP!=null)
            Collections.sort(allP,Collections.<Player>reverseOrder());
    }

    public void setDimensions(){
        int pinH = 55;
        int pinW = pinH;
        for (int i=0; i<pins.size(); i++){
            pins.get(i).requestLayout();
            pins.get(i).getLayoutParams().height = pinH;
            pins.get(i).getLayoutParams().width = pinW;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        prefs.edit().putInt("KEY_NUM_OF_LINES",numberOfLines);
        prefs.edit().commit();
    }
}
