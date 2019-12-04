package com.example.hw_1;

import android.app.Application;
import android.content.Context;
import android.media.MediaPlayer;

public class MyApplication extends Application {

    private MediaPlayer mediaPlayer;
    public MediaPlayer getMediaPlayer(){
        return mediaPlayer;
    }

    public void setMediaPlayer(){
        this.mediaPlayer=mediaPlayer;
    }
}
