package com.example.wambugu.YPlayer.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.wambugu.YPlayer.Constants.Values;

/**
 * Created by wambugu on 05/12/2017.
 */

public class SharedPreferenceManager{

    private String pSort ="pSort";
    private String pMax = "pMax";
    private String pSafe ="pSafe";

    private String vDuration = "vDuration";
    private String vSort = "vSort";
    private String vMax ="vMax";
    private String vSafe ="vSafe";

    SharedPreferences sharedPreferences;


    public SharedPreferenceManager(Context context){

      sharedPreferences = context.getSharedPreferences(Values.PREF_LOCATION,Context.MODE_PRIVATE);

    }



    //Playlist search parameters

    public void setPlaylistSortBy(String sortBy){

        sharedPreferences.edit().putString(pSort,sortBy).apply();
        Log.e("SharedPreferenceManager",sortBy);

    }
    public void setPlaylistMaxResult(String maxResult ){

        sharedPreferences.edit().putString(pMax,maxResult).apply();
        Log.e("SharedPreferenceManager",maxResult);
    }
    public void setPlaylistSafeSearch(String safeSearch){

        sharedPreferences.edit().putString(pSafe,safeSearch).apply();
        Log.e("SharedPreferenceManager",safeSearch);

    }
    public String getPlaylistSortBy(){

        return sharedPreferences.getString(pSort,"relevance");

    }
    public String getPlaylistMaxResult(){

        return sharedPreferences.getString(pMax,"25");
    }
    public String getPlaylistSafeSearch(){

        return sharedPreferences.getString(pSafe,"none");
    }


    //video search parameters

    public void setVideoDuration(String videoDuration){

        sharedPreferences.edit().putString(vDuration,videoDuration).apply();

    }
    public void setVideoSortBy(String sortBy){

        sharedPreferences.edit().putString(vSort,sortBy).apply();
    }
    public void setVideoSafeSearch(String safeSearch){

        sharedPreferences.edit().putString(vSafe,safeSearch).apply();
    }
    public void setVideoMaxResult(String videoMaxResult){

        sharedPreferences.edit().putString(vMax,videoMaxResult).apply();
    }
    public String getVideoSortBy(){

      return sharedPreferences.getString(vSort,"relevance");

    }
    public String getVideoMaxResult(){

       return sharedPreferences.getString(vMax,"25");
    }
    public String getVideoSafeSearch(){

        return sharedPreferences.getString(vSafe,"none");
    }
    public String getVideoDuration(){
        return  sharedPreferences.getString(vDuration,"any");

    }




}
