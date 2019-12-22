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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Fragment2 extends Fragment {
    ArrayList<Player> allPlayersSorted= new ArrayList<Player>();
    public static final String KEY_NAME = "KEY_NAME17";
    public static final String KEY_SCORE = "KEY_SCORE17";
    String lastScore,lastName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2_table, container, false);

        Player player1 = new Player(), player2= new Player(), player3=new Player(), player4=new Player(),
                player5=new Player(), player6=new Player(), player7=new Player(),
                player8=new Player(), player9=new Player(), player10=new Player();
        //Player[] allPlayers = {player1, player2, player3, player4, player5, player6, player7, player8, player9, player10};
        //Player[] allPlayers = {player10,player9,player8,player7,player6,player5,player4,player3,player2,player1};
        ArrayList<Player> allPlayers= new ArrayList<Player>();
        allPlayers.add(player10);
        allPlayers.add(player9);
        allPlayers.add(player8);
        allPlayers.add(player7);
        allPlayers.add(player6);
        allPlayers.add(player5);
        allPlayers.add(player4);
        allPlayers.add(player3);
        allPlayers.add(player2);
        allPlayers.add(player1);

        player1.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name1));
        player1.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score1));
        player1.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location1));

        player2.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name2));
        player2.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score2));
        player2.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location2));

        player3.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name3));
        player3.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score3));
        player3.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location3));

        player4.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name4));
        player4.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score4));
        player4.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location4));

        player5.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name5));
        player5.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score5));
        player5.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location5));

        player6.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name6));
        player6.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score6));
        player6.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location6));

        player7.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name7));
        player7.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score7));
        player7.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location7));

        player8.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name8));
        player8.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score8));
        player8.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location8));

        player9.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name9));
        player9.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score9));
        player9.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location9));

        player10.setNameTXTVIEW((TextView) view.findViewById(R.id.fragment2_TXT_name10));
        player10.setScoreTV((TextView) view.findViewById(R.id.fragment2_TXT_score10));
        player10.setLocationTV((TextView) view.findViewById(R.id.fragment2_TXT_location10));

        String[] arrayOfNames;
        String[] arrayOfScoresString;
        ArrayList<Integer> arrayOfScoresInt = new ArrayList<Integer>();
        ArrayList<String> arrayListNames = new ArrayList<String>();


        /*if (allPlayers.size() >= 10) { //works until game 11
            Player newPlayer = new Player();
            newPlayer.setName(lastName);
            newPlayer.setScore(lastScore);
            /if (lastScore != null && allPlayersArrayList.get(allPlayersArrayList.size()).getScore() != null) { //without
                if (Integer.parseInt(lastScore) >
                        Integer.parseInt(allPlayersArrayList.get(allPlayersArrayList.size()).getScore())) //without
                    allPlayers.add(newPlayer);

                //allPlayers.add(newPlayer);
            }
        }*/

        setAllNames(allPlayers);
        setAllScores(allPlayers);
        sortByScore(allPlayers);
        putValuesToTable(allPlayers);

        //setAllLocation();
        return view;
    }

    private void putValuesToTable(ArrayList<Player> allPlayers) {
        for (int j=0; j<10; j++){
            allPlayers.get(j).getNameTV().setText(allPlayersSorted.get(j).getName());
            allPlayers.get(j).getScoreTV().setText(allPlayersSorted.get(j).getScore());
        }
    }

    public void setAllScores(ArrayList<Player> allP) {
        SharedPreferences prefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        String allScores = prefs.getString(KEY_SCORE, null);
        String[] allScoresArr = allScores.split("\n");
        if (allScoresArr != null)
            lastScore = allScoresArr[allScoresArr.length-1];
        for (int i=1; i<allScoresArr.length; i++){
            allP.get(i-1).setScore(allScoresArr[i]);
        }
    }

    public void setAllNames(ArrayList<Player> allP){
        SharedPreferences prefs = getContext().getSharedPreferences(EndActivity.GAME_PREFS, MODE_PRIVATE);
        String allNames = prefs.getString(KEY_NAME, "unknown user");
        String[] allNamesArr = allNames.split("\n");
        if (allNamesArr != null)
            lastName = allNamesArr[allNamesArr.length-1];
        for (int i=1; i<allNamesArr.length; i++){
            allP.get(i-1).setName(allNamesArr[i]);
        }
    }

    public void setAllLocation(){

    }

    public void sortByScore(ArrayList<Player> allP){
        for (int i=0; i<allP.size(); i++)
          allPlayersSorted.add(allP.get(i));

        Collections.sort(allPlayersSorted);
    }
}
