package com.example.hw_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment2 extends Fragment {
    public static ArrayList<Player> allPlayersSorted= new ArrayList<Player>();
    //public static ArrayList<Player> allPlayers= new ArrayList<Player>();
    public static final String KEY_NAME = "KEY_NAME35";
    public static final String KEY_SCORE = "KEY_SCORE35";
    public static int numberOfLines = 0;
    String lastScore,lastName;
    SharedPreferences prefs;
    String curName;
    String curScore;
    ArrayList<TextView> namesTV = new ArrayList<TextView>();
    ArrayList<TextView> scoresTV = new ArrayList<TextView>();

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
        prefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        ArrayList<Player> allPlayers;
        Type type = new TypeToken<ArrayList<Player>>(){}.getType();
        String allPlayersJson1 = prefs.getString("JSON_PLAYERS","N/A");
        //Gson gson = new Gson();
        if (allPlayersJson1 != "N/A")
            allPlayers = new Gson().fromJson(allPlayersJson1,type);
        else
            allPlayers = null;

        //getNameFromSP();
        //getScoreFromSP();
        //setAllNames(allPlayers);
        //setAllScores(allPlayers);
        sortByScore(EndActivity.allPlayers);
        putValuesToTable(EndActivity.allPlayers);


        //setAllLocation();
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

    /*public void getScoreFromSP() {
        String allScores = prefs.getString(KEY_SCORE, null);
        String[] allScoresArr = allScores.split("\n");
        int numberOfScores = allScoresArr.length;
        int SPnumOfLines = prefs.getInt("KEY_NUM_OF_LINES",0);
        curScore = allScoresArr[numberOfScores-1];
        Player curPlayer = new Player();
        curPlayer.setName(curName);
        curPlayer.setScore(curScore);
        curPlayer.setNameTXTVIEW(namesTV.get(SPnumOfLines));
        curPlayer.setScoreTV(scoresTV.get(SPnumOfLines));
        if (EndActivity.allPlayers.size()>=10) {
            if (Integer.parseInt(curScore) > Integer.parseInt(EndActivity.allPlayers.get(9).getScore()))
                EndActivity.allPlayers.set(9,curPlayer);
        }else
            EndActivity.allPlayers.add(curPlayer);
        SPnumOfLines++;
        if (SPnumOfLines > 9)
            SPnumOfLines = 9;
    }*/

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
        //for (int i=0; i<allP.size(); i++)
          //allPlayersSorted.add(allP.get(i));
        if (allP!=null)
            Collections.sort(allP,Collections.<Player>reverseOrder());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        prefs.edit().putInt("KEY_NUM_OF_LINES",numberOfLines);
        prefs.edit().commit();
    }
}
