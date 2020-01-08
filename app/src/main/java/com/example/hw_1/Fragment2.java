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

    public static final String KEY_NAME = "KEY_NAME35";
    public static final String KEY_SCORE = "KEY_SCORE35";
    public static int numberOfLines = 0;
    int i;
    SharedPreferences gamePrefs;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
