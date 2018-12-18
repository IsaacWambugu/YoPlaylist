package com.example.wambugu.YPlayer.Utility;

import android.app.Activity;
import android.content.Intent;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * Created by wacuka on 28/10/2017.
 */

public final class StartCustomYPlayer {



    private Intent intent;
    private final String api_key = "AIzaSyDy-Yq19ErQ4TnwObH8klxBawfuPgVpREo";



    public  void startYouTubeVideoPlayer(Activity activity, String video_id){

        intent  = YouTubeStandalonePlayer.createVideoIntent(activity,api_key,video_id,0,false,true);
        activity.startActivity(intent);

    }
    public  void startYouTubePlaylistPlayer(Activity activity, String playlist_id){

        intent  = YouTubeStandalonePlayer.createPlaylistIntent(activity,api_key,playlist_id,0,0,false,true);
        activity.startActivity(intent);

    }

}
