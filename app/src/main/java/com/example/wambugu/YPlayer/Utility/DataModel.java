package com.example.wambugu.YPlayer.Utility;

/**
 * Created by wacuka on 28/10/2017.
 */
public class DataModel {
    String name ;
    String video_id;


    public DataModel(String name , String video_id){

        this.name = name;
        this.video_id = video_id;


    }
    public String getName(){
        return name;

    }
    public String getVideoId(){
        return video_id;

    }

}
