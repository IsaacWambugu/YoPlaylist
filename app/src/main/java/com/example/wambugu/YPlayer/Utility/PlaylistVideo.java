package com.example.wambugu.YPlayer.Utility;

/**
 * Created by wacuka on 11/11/2017.
 */

public class PlaylistVideo  {
    private String video_position ;
    private String video_title;
    private String video_id;
    private String video_thumbnail_url;



    public PlaylistVideo(String video_position,String video_title ,String video_id,String video_thumbnail_url){

        this.video_position = video_position;
        this.video_id = video_id;
        this.video_title = video_title;
        this.video_thumbnail_url = video_thumbnail_url;



    }
    public String getVideoPosition(){
        return video_position;

    }
    public String getVideoTitle(){
        return video_title;
    }
    public String getVideoId(){
        return video_id;

    }
    public String getVideoThumbnail(){
        return video_thumbnail_url;

    }
}
