package com.example.wambugu.YPlayer.RecyclerView;

/**
 * Created by wacuka on 17/11/2017.
 */

public class SearchVideoResults {

    private String video_id,video_title,thumbnail_url,video_duration,channel,views;



    public SearchVideoResults(String video_id , String video_title , String thumbnail_url , String video_duration , String channel, String views){

        this.video_id = video_id;
        this.video_title = video_title;
        this.thumbnail_url = thumbnail_url;
        this.video_duration = video_duration;
        this.channel = channel;
        this.views = views;


    }
    public String getVideoId(){
        return video_id;

    }
    public String getVideoTitle(){
        return video_title;

    }
    public String getThumbnailURL(){
        return thumbnail_url;

    }
    public String getVideoDuration(){
        return video_duration;

    }
    public String  getChannel(){
        return channel;

    }
    public String getViews(){
        return views;

    }
}
