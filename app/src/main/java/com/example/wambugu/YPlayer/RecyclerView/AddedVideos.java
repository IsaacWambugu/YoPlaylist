package com.example.wambugu.YPlayer.RecyclerView;

/**
 * Created by wacuka on 22/11/2017.
 */

public class AddedVideos {

    private String video_id,video_title,video_duration,video_channel,thumbnail_url;


    public AddedVideos(String video_id ,String video_title,String thumbnail_url,String video_duration,String video_channnel){

        this.video_id = video_id;
        this.video_title = video_title;
        this.thumbnail_url = thumbnail_url;
        this.video_duration = video_duration;
        this.video_channel = video_channnel;


    }
    public String getVideoId(){
        return video_id;
    }
    public String getVideoTitle(){
        return video_title;
    }
    public String getVideoDuration(){
        return video_duration;
    }
    public String getVideoChannel(){
       return video_channel;
    }
    public String getThumbnailUrl(){
        return thumbnail_url;

    }
}
