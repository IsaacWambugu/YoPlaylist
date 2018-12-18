package com.example.wambugu.YPlayer.Singletons;

import android.provider.MediaStore;

/**
 * Created by wacuka on 25/12/2017.
 */

public class VideoIdHolderSingleton {
    private String videoId ;
    private int position;


    private static VideoIdHolderSingleton videoIdHolderSingleton = new VideoIdHolderSingleton();

    private VideoIdHolderSingleton(){}

    public static VideoIdHolderSingleton getInstance(){

        return videoIdHolderSingleton;

    }
    public void setVideoId(String videoId){

       this.videoId = videoId;

    }
    public String getVideoId(){

        return videoId;
    }
    public void setPosition(int position){
        this.position = position;

    }
    public Integer getPosition(){
        return position;
    }
}
